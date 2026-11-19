package model.DAO;

import model.BEAN.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    public List<Contact> getAllContacts() {
        return getContacts(null, null, null, 1, 1000);
    }

    public List<Contact> getContacts(String startDate, String endDate, String keyword, int page, int limit) {
        List<Contact> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM contacts WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append(" AND created_at >= ?");
            params.add(startDate.trim() + " 00:00:00");
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND created_at <= ?");
            params.add(endDate.trim() + " 23:59:59");
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND email LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }

        sql.append(" ORDER BY id DESC LIMIT ? OFFSET ?");
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
                            list.add(mapResultSetToContact(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countContacts(String startDate, String endDate, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM contacts WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append(" AND created_at >= ?");
            params.add(startDate.trim() + " 00:00:00");
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND created_at <= ?");
            params.add(endDate.trim() + " 23:59:59");
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND email LIKE ?");
            params.add("%" + keyword.trim() + "%");
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

    public List<Contact> getTrashContacts(String keyword, int page, int limit) {
        List<Contact> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM contacts WHERE deleted = 1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND email LIKE ?");
            params.add("%" + keyword.trim() + "%");
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
                            list.add(mapResultSetToContact(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTrashContacts(String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM contacts WHERE deleted = 1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND email LIKE ?");
            params.add("%" + keyword.trim() + "%");
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

    public boolean deleteContact(int id, String deletedBy) {
        String sql = "UPDATE contacts SET deleted = 1, deleted_by = ?, deleted_at = NOW(), updated_at = NOW() WHERE id = ?";
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

    public boolean deleteContact(int id) {
        return deleteContact(id, "Phan Thanh Duy");
    }

    public boolean undoContact(int id) {
        String sql = "UPDATE contacts SET deleted = 0, deleted_by = NULL, deleted_at = NULL, updated_at = NOW() WHERE id = ?";
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

    public boolean deleteDestroyContact(int id) {
        String sql = "DELETE FROM contacts WHERE id = ?";
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

    public boolean existEmail(String email) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT id FROM contacts WHERE email = ? AND deleted = 0 LIMIT 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                boolean exists = rs.next();
                conn.close();
                return exists;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean createContact(String email) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "INSERT INTO contacts (email) VALUES (?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                int affected = ps.executeUpdate();
                conn.close();
                return affected > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private Contact mapResultSetToContact(ResultSet rs) throws Exception {
        Contact c = new Contact();
        c.setId(rs.getInt("id"));
        c.setEmail(rs.getString("email"));
        c.setDeleted(rs.getInt("deleted") == 1);
        c.setCreatedAt(rs.getTimestamp("created_at"));
        try {
            c.setDeletedBy(rs.getString("deleted_by"));
            c.setDeletedAt(rs.getTimestamp("deleted_at"));
        } catch (Exception ignored) {}
        return c;
    }
}

