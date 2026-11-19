package model.DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.BEAN.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Role mapResultSetToRole(ResultSet rs) throws Exception {
        Role r = new Role();
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setDescription(rs.getString("description"));
        String permStr = rs.getString("permissions");
        if (permStr != null && !permStr.trim().isEmpty()) {
            try {
                List<String> perms = objectMapper.readValue(permStr, new TypeReference<List<String>>() {});
                r.setPermissions(perms);
            } catch (Exception ignored) {}
        }
        r.setDeleted(rs.getBoolean("deleted"));
        r.setCreatedBy(rs.getString("created_by"));
        r.setUpdatedBy(rs.getString("updated_by"));
        r.setDeletedBy(rs.getString("deleted_by"));
        try {
            r.setCreatedAt(rs.getTimestamp("created_at"));
        } catch (Exception ignored) {}
        try {
            r.setDeletedAt(rs.getTimestamp("deleted_at"));
        } catch (Exception ignored) {}
        return r;
    }

    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM roles WHERE deleted = 0 ORDER BY id ASC";
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToRole(rs));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Role getRoleById(int id) {
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM roles WHERE id = ? AND deleted = 0";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToRole(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countRoles(String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM roles WHERE deleted = 0");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (name LIKE ? OR description LIKE ?)");
            String k = "%" + keyword.trim() + "%";
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

    public List<Role> getRoles(String keyword, int page, int limit) {
        List<Role> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM roles WHERE deleted = 0");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (name LIKE ? OR description LIKE ?)");
            String k = "%" + keyword.trim() + "%";
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
                            list.add(mapResultSetToRole(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTrashRoles(String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM roles WHERE deleted = 1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (name LIKE ? OR description LIKE ?)");
            String k = "%" + keyword.trim() + "%";
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

    public List<Role> getTrashRoles(String keyword, int page, int limit) {
        List<Role> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM roles WHERE deleted = 1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (name LIKE ? OR description LIKE ?)");
            String k = "%" + keyword.trim() + "%";
            params.add(k);
            params.add(k);
        }
        sql.append(" ORDER BY deleted_at DESC, id DESC LIMIT ? OFFSET ?");
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
                            list.add(mapResultSetToRole(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createRole(Role role) {
        String sql = "INSERT INTO roles (name, description, permissions, slug, created_by, updated_by, created_at, updated_at, deleted) "
                   + "VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW(), 0)";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, role.getName());
                    ps.setString(2, role.getDescription());
                    String permsJson = objectMapper.writeValueAsString(role.getPermissions());
                    ps.setString(3, permsJson);
                    String slug = CategoryDAO.toSlug(role.getName());
                    ps.setString(4, slug);
                    ps.setString(5, role.getCreatedBy() != null ? role.getCreatedBy() : "Phan Thanh Duy");
                    ps.setString(6, role.getUpdatedBy() != null ? role.getUpdatedBy() : "Phan Thanh Duy");
                    int affected = ps.executeUpdate();
                    if (affected > 0) {
                        try (ResultSet rs = ps.getGeneratedKeys()) {
                            if (rs.next()) {
                                role.setId(rs.getInt(1));
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

    public boolean updateRole(Role role) {
        String sql = "UPDATE roles SET name = ?, description = ?, permissions = ?, slug = ?, updated_by = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, role.getName());
                    ps.setString(2, role.getDescription());
                    String permsJson = objectMapper.writeValueAsString(role.getPermissions());
                    ps.setString(3, permsJson);
                    String slug = CategoryDAO.toSlug(role.getName());
                    ps.setString(4, slug);
                    ps.setString(5, role.getUpdatedBy() != null ? role.getUpdatedBy() : "Phan Thanh Duy");
                    ps.setInt(6, role.getId());
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRole(int id, String deletedBy) {
        String sql = "UPDATE roles SET deleted = 1, deleted_by = ?, deleted_at = NOW() WHERE id = ?";
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

    public boolean undoRole(int id) {
        String sql = "UPDATE roles SET deleted = 0, deleted_by = NULL, deleted_at = NULL WHERE id = ?";
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

    public boolean deleteDestroyRole(int id) {
        String sql = "DELETE FROM roles WHERE id = ?";
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

    public void changeMulti(List<Integer> ids, String option, String deletedBy) {
        if (ids == null || ids.isEmpty() || option == null || option.isEmpty()) return;
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) placeholders.append(",");
            placeholders.append("?");
        }
        String sql = "";
        if ("delete".equalsIgnoreCase(option)) {
            sql = "UPDATE roles SET deleted = 1, deleted_by = ?, deleted_at = NOW() WHERE id IN (" + placeholders + ")";
        } else if ("undo".equalsIgnoreCase(option)) {
            sql = "UPDATE roles SET deleted = 0, deleted_by = NULL, deleted_at = NULL WHERE id IN (" + placeholders + ")";
        } else if ("delete-destroy".equalsIgnoreCase(option)) {
            sql = "DELETE FROM roles WHERE id IN (" + placeholders + ")";
        } else {
            return;
        }

        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    int paramIdx = 1;
                    if ("delete".equalsIgnoreCase(option)) {
                        ps.setString(paramIdx++, deletedBy != null ? deletedBy : "Phan Thanh Duy");
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
