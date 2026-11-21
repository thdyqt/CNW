package model.DAO;

import model.BEAN.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/** Persistence for customer sign-up OTPs. Kept separate from admin password-reset OTPs. */
public class CustomerRegistrationOtpDAO {
    private static final int MAX_ATTEMPTS = 5;

    public boolean isResendBlocked(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        String sql = "SELECT 1 FROM customer_registration_otp "
                   + "WHERE email = ? AND expires_at > NOW() AND resend_after > NOW() LIMIT 1";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {
            if (ps == null) return false;
            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean savePendingRegistration(User user, String passwordHash, String otpHash) {
        if (user == null || user.getEmail() == null || passwordHash == null || otpHash == null) return false;
        String sql = "INSERT INTO customer_registration_otp "
                   + "(email, full_name, phone, address, password_hash, otp_hash, attempts, expires_at, resend_after, created_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, 0, DATE_ADD(NOW(), INTERVAL 5 MINUTE), "
                   + "DATE_ADD(NOW(), INTERVAL 60 SECOND), NOW()) "
                   + "ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), phone = VALUES(phone), "
                   + "address = VALUES(address), password_hash = VALUES(password_hash), otp_hash = VALUES(otp_hash), "
                   + "attempts = 0, expires_at = VALUES(expires_at), resend_after = VALUES(resend_after), created_at = NOW()";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {
            if (ps == null) return false;
            ps.setString(1, user.getEmail().trim());
            ps.setString(2, user.getFullName().trim());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setString(5, passwordHash);
            ps.setString(6, otpHash);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public OtpSnapshot replaceOtpAfterCooldown(String email, String otpHash) {
        if (email == null || email.trim().isEmpty() || otpHash == null) return null;
        String selectSql = "SELECT otp_hash, attempts, expires_at, resend_after FROM customer_registration_otp "
                         + "WHERE email = ? AND resend_after <= NOW() FOR UPDATE";
        String updateSql = "UPDATE customer_registration_otp SET otp_hash = ?, attempts = 0, "
                         + "expires_at = DATE_ADD(NOW(), INTERVAL 5 MINUTE), "
                         + "resend_after = DATE_ADD(NOW(), INTERVAL 60 SECOND), created_at = NOW() WHERE email = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) return null;
            boolean previousAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                OtpSnapshot previous;
                try (PreparedStatement select = conn.prepareStatement(selectSql)) {
                    select.setString(1, email.trim());
                    try (ResultSet rs = select.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return null;
                        }
                        previous = new OtpSnapshot(rs.getString("otp_hash"), rs.getInt("attempts"),
                                rs.getTimestamp("expires_at"), rs.getTimestamp("resend_after"));
                    }
                }
                try (PreparedStatement update = conn.prepareStatement(updateSql)) {
                    update.setString(1, otpHash);
                    update.setString(2, email.trim());
                    if (update.executeUpdate() <= 0) {
                        conn.rollback();
                        return null;
                    }
                }
                conn.commit();
                return previous;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(previousAutoCommit);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean restoreAfterFailedResend(String email, String attemptedOtpHash, OtpSnapshot previous) {
        if (email == null || attemptedOtpHash == null || previous == null) return false;
        String sql = "UPDATE customer_registration_otp SET otp_hash = ?, attempts = ?, expires_at = ?, "
                   + "resend_after = ?, created_at = NOW() WHERE email = ? AND otp_hash = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {
            if (ps == null) return false;
            ps.setString(1, previous.getOtpHash());
            ps.setInt(2, previous.getAttempts());
            ps.setTimestamp(3, previous.getExpiresAt());
            ps.setTimestamp(4, previous.getResendAfter());
            ps.setString(5, email.trim());
            ps.setString(6, attemptedOtpHash);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class OtpSnapshot {
        private final String otpHash;
        private final int attempts;
        private final Timestamp expiresAt;
        private final Timestamp resendAfter;

        public OtpSnapshot(String otpHash, int attempts, Timestamp expiresAt, Timestamp resendAfter) {
            this.otpHash = otpHash;
            this.attempts = attempts;
            this.expiresAt = expiresAt;
            this.resendAfter = resendAfter;
        }

        public String getOtpHash() { return otpHash; }
        public int getAttempts() { return attempts; }
        public Timestamp getExpiresAt() { return expiresAt; }
        public Timestamp getResendAfter() { return resendAfter; }
    }

    public User verifyAndGetRegistration(String email, String otp, UserDAO userDAO) {
        if (email == null || otp == null || userDAO == null) return null;
        String sql = "SELECT full_name, email, phone, address, password_hash, otp_hash "
                   + "FROM customer_registration_otp "
                   + "WHERE email = ? AND expires_at > NOW() AND attempts < ? LIMIT 1";
        try (Connection conn = DBContext.getConnection()) {
            if (conn == null) return null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email.trim());
                ps.setInt(2, MAX_ATTEMPTS);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) return null;
                    String otpHash = rs.getString("otp_hash");
                    if (!userDAO.checkPassword(otp, otpHash)) {
                        incrementFailedAttempt(email.trim());
                        return null;
                    }
                    User user = new User();
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setPassword(rs.getString("password_hash"));
                    user.setStatus("active");
                    user.setCreatedBy("Khách hàng");
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void incrementFailedAttempt(String email) {
        String sql = "UPDATE customer_registration_otp SET attempts = attempts + 1 "
                   + "WHERE email = ? AND expires_at > NOW() AND attempts < ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {
            if (ps != null) {
                ps.setString(1, email);
                ps.setInt(2, MAX_ATTEMPTS);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deletePendingRegistration(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        String sql = "DELETE FROM customer_registration_otp WHERE email = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {
            if (ps == null) return false;
            ps.setString(1, email.trim());
            return ps.executeUpdate() >= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
