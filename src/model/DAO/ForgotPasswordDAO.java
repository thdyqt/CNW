package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ForgotPasswordDAO {

    /**
     * Kiểm tra xem email này đã có yêu cầu OTP còn hiệu lực (trong vòng 5 phút) hay chưa.
     */
    public boolean existsActiveOtp(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String sql = "SELECT COUNT(*) FROM forgot_password WHERE email = ? AND expire_at > NOW()";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email.trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt(1) > 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lưu mã OTP mới với thời hạn hiệu lực 5 phút. Xóa các bản ghi cũ của email trước khi thêm.
     */
    public boolean saveOtp(String email, String otp) {
        if (email == null || otp == null) {
            return false;
        }
        deleteOtp(email);

        String sql = "INSERT INTO forgot_password (email, otp, expire_at, created_at) " +
                     "VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 5 MINUTE), NOW())";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email.trim());
                    ps.setString(2, otp.trim());
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xác thực mã OTP: kiểm tra email và mã OTP khớp nhau và chưa hết hạn (expire_at >= NOW()).
     */
    public boolean verifyOtp(String email, String otp) {
        if (email == null || otp == null) {
            return false;
        }
        String sql = "SELECT COUNT(*) FROM forgot_password WHERE email = ? AND otp = ? AND expire_at >= NOW()";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email.trim());
                    ps.setString(2, otp.trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt(1) > 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa bản ghi OTP của email sau khi đổi mật khẩu thành công.
     */
    public boolean deleteOtp(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String sql = "DELETE FROM forgot_password WHERE email = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email.trim());
                    return ps.executeUpdate() >= 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

