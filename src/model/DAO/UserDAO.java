package model.DAO;

import model.BEAN.*;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO {

    public String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            return null;
        }
        String p = plainPassword.trim();
        if (p.startsWith("$2a$") || p.startsWith("$2b$") || p.startsWith("$2y$")) {
            return p;
        }
        return BCrypt.hashpw(p, BCrypt.gensalt(10));
    }

    public boolean checkPassword(String plainPassword, String storedPassword) {
        if (plainPassword == null || storedPassword == null) {
            return false;
        }
        if (plainPassword.equals(storedPassword)) {
            return true;
        }
        try {
            String hashToCheck = storedPassword;
            if (hashToCheck.startsWith("$2b$")) {
                hashToCheck = "$2a$" + hashToCheck.substring(4);
            }
            return BCrypt.checkpw(plainPassword, hashToCheck);
        } catch (Exception e) {
            return false;
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws Exception {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setPhone(rs.getString("phone"));
        u.setAddress(rs.getString("address"));
        u.setAvatar(rs.getString("avatar"));
        u.setStatus(rs.getString("status"));
        u.setTokenUser(rs.getString("token_user"));
        u.setSlug(rs.getString("slug"));
        u.setCreatedBy(rs.getString("created_by"));
        u.setUpdatedBy(rs.getString("updated_by"));
        u.setDeleted(rs.getBoolean("deleted"));
        u.setDeletedBy(rs.getString("deleted_by"));

        try {
            u.setCreatedAt(rs.getTimestamp("created_at"));
        } catch (Exception ignored) {}
        try {
            u.setUpdatedAt(rs.getTimestamp("updated_at"));
        } catch (Exception ignored) {}
        try {
            u.setDeletedAt(rs.getTimestamp("deleted_at"));
        } catch (Exception ignored) {}
        return u;
    }

    public int countUsers(String status, String startDate, String endDate, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM users WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append(" AND DATE(created_at) >= ?");
            params.add(startDate.trim());
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND DATE(created_at) <= ?");
            params.add(endDate.trim());
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (full_name LIKE ? OR email LIKE ? OR phone LIKE ? OR address LIKE ?)");
            String k = "%" + keyword.trim() + "%";
            params.add(k);
            params.add(k);
            params.add(k);
            params.add(k);
        }

        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                    for (int i = 0; i < params.size(); i++) {
                        ps.setObject(i + 1, params.get(i));
                    }
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> getUsers(String status, String startDate, String endDate, String keyword, int page, int limit) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append(" AND DATE(created_at) >= ?");
            params.add(startDate.trim());
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND DATE(created_at) <= ?");
            params.add(endDate.trim());
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (full_name LIKE ? OR email LIKE ? OR phone LIKE ? OR address LIKE ?)");
            String k = "%" + keyword.trim() + "%";
            params.add(k);
            params.add(k);
            params.add(k);
            params.add(k);
        }

        sql.append(" ORDER BY id ASC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add((page - 1) * limit);

        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                    for (int i = 0; i < params.size(); i++) {
                        ps.setObject(i + 1, params.get(i));
                    }
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            list.add(mapResultSetToUser(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserById(int id) {
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM users WHERE id = ? AND deleted = 0";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToUser(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM users WHERE email = ? AND deleted = 0 LIMIT 1";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email.trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToUser(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Checks every row, including soft-deleted users, because email is unique in users. */
    public int countEmailRows(String email) {
        if (email == null || email.trim().isEmpty()) return 0;
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email.trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        return rs.next() ? rs.getInt(1) : 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /** Updates only customer-editable profile fields; email and account status are immutable here. */
    public boolean updateCustomerProfile(int id, String fullName, String phone, String address) {
        String sql = "UPDATE users SET full_name = ?, phone = ?, address = ?, slug = ?, "
                   + "updated_by = 'Khách hàng', updated_at = NOW() "
                   + "WHERE id = ? AND deleted = 0 AND status = 'active'";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, fullName != null ? fullName.trim() : "");
                    ps.setString(2, phone != null ? phone.trim() : "");
                    ps.setString(3, address != null ? address.trim() : "");
                    ps.setString(4, CategoryDAO.toSlug(fullName));
                    ps.setInt(5, id);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCustomerPassword(int id, String passwordHash) {
        if (id <= 0 || passwordHash == null || passwordHash.trim().isEmpty()) return false;
        String sql = "UPDATE users SET password = ?, updated_by = 'Khách hàng', updated_at = NOW() "
                   + "WHERE id = ? AND deleted = 0 AND status = 'active'";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, passwordHash);
                    ps.setInt(2, id);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) return null;
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM users WHERE phone = ? AND deleted = 0 LIMIT 1";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, phone.trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToUser(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByToken(String token) {
        if (token == null || token.trim().isEmpty()) return null;
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM users WHERE token_user = ? AND deleted = 0 LIMIT 1";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, token.trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToUser(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createUser(User user, String plainPassword) {
        String sql = "INSERT INTO users (full_name, email, password, phone, address, avatar, status, token_user, slug, created_by, created_at, updated_at, deleted) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), 0)";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, user.getFullName());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, hashPassword(plainPassword));
                    ps.setString(4, user.getPhone());
                    ps.setString(5, user.getAddress());
                    ps.setString(6, user.getAvatar());
                    ps.setString(7, user.getStatus() != null ? user.getStatus() : "active");
                    String token = user.getTokenUser();
                    if (token == null || token.isEmpty()) {
                        token = "tok_" + UUID.randomUUID().toString().replace("-", "");
                    }
                    ps.setString(8, token);
                    ps.setString(9, CategoryDAO.toSlug(user.getFullName()));
                    ps.setString(10, user.getCreatedBy() != null ? user.getCreatedBy() : "Khách hàng");

                    int affected = ps.executeUpdate();
                    if (affected > 0) {
                        try (ResultSet rs = ps.getGeneratedKeys()) {
                            if (rs.next()) {
                                user.setId(rs.getInt(1));
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user, String plainPassword) {
        StringBuilder sql = new StringBuilder("UPDATE users SET full_name = ?, phone = ?, address = ?, status = ?, slug = ?, updated_by = ?, updated_at = NOW()");
        List<Object> params = new ArrayList<>();
        params.add(user.getFullName());
        params.add(user.getPhone());
        params.add(user.getAddress());
        params.add(user.getStatus());
        params.add(CategoryDAO.toSlug(user.getFullName()));
        params.add(user.getUpdatedBy() != null ? user.getUpdatedBy() : "Phan Thanh Duy");

        if (plainPassword != null && !plainPassword.trim().isEmpty()) {
            sql.append(", password = ?");
            params.add(hashPassword(plainPassword));
        }
        if (user.getAvatar() != null && !user.getAvatar().trim().isEmpty()) {
            sql.append(", avatar = ?");
            params.add(user.getAvatar());
        }

        sql.append(" WHERE id = ?");
        params.add(user.getId());

        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                    for (int i = 0; i < params.size(); i++) {
                        ps.setObject(i + 1, params.get(i));
                    }
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int id, String deletedBy) {
        String sql = "UPDATE users SET deleted = 1, deleted_by = ?, deleted_at = NOW() WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, deletedBy != null ? deletedBy : "Phan Thanh Duy");
                    ps.setInt(2, id);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean undoUser(int id) {
        String sql = "UPDATE users SET deleted = 0, deleted_by = NULL, deleted_at = NULL, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDestroyUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countTrashUsers(String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM users WHERE deleted = 1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (full_name LIKE ? OR email LIKE ? OR phone LIKE ? OR address LIKE ?)");
            String k = "%" + keyword.trim() + "%";
            params.add(k);
            params.add(k);
            params.add(k);
            params.add(k);
        }
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                    for (int i = 0; i < params.size(); i++) {
                        ps.setObject(i + 1, params.get(i));
                    }
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> getTrashUsers(String keyword, int page, int limit) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE deleted = 1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (full_name LIKE ? OR email LIKE ? OR phone LIKE ? OR address LIKE ?)");
            String k = "%" + keyword.trim() + "%";
            params.add(k);
            params.add(k);
            params.add(k);
            params.add(k);
        }
        sql.append(" ORDER BY deleted_at DESC, id DESC LIMIT ? OFFSET ?");
        params.add(limit > 0 ? limit : 10);
        int offset = page > 1 ? (page - 1) * limit : 0;
        params.add(offset);

        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                    for (int i = 0; i < params.size(); i++) {
                        ps.setObject(i + 1, params.get(i));
                    }
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            list.add(mapResultSetToUser(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void changeMulti(List<Integer> ids, String option, String actor) {
        if (ids == null || ids.isEmpty() || option == null || option.isEmpty()) return;
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) placeholders.append(",");
            placeholders.append("?");
        }
        String sql;
        if ("active".equalsIgnoreCase(option)) {
            sql = "UPDATE users SET status = 'active', updated_by = ?, updated_at = NOW() WHERE id IN (" + placeholders + ")";
        } else if ("inactive".equalsIgnoreCase(option)) {
            sql = "UPDATE users SET status = 'inactive', updated_by = ?, updated_at = NOW() WHERE id IN (" + placeholders + ")";
        } else if ("delete".equalsIgnoreCase(option)) {
            sql = "UPDATE users SET deleted = 1, deleted_by = ?, deleted_at = NOW() WHERE id IN (" + placeholders + ")";
        } else if ("undo".equalsIgnoreCase(option)) {
            sql = "UPDATE users SET deleted = 0, deleted_by = NULL, deleted_at = NULL, updated_at = NOW() WHERE id IN (" + placeholders + ")";
        } else if ("delete-destroy".equalsIgnoreCase(option)) {
            sql = "DELETE FROM users WHERE id IN (" + placeholders + ")";
        } else {
            return;
        }

        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    int paramIdx = 1;
                    if ("active".equalsIgnoreCase(option) || "inactive".equalsIgnoreCase(option) || "delete".equalsIgnoreCase(option)) {
                        ps.setString(paramIdx++, actor != null ? actor : "Phan Thanh Duy");
                    }
                    for (int id : ids) {
                        ps.setInt(paramIdx++, id);
                    }
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
