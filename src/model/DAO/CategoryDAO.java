package model.DAO;

import model.BEAN.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CategoryDAO {
    private static final List<Category> MOCK_CATEGORIES = new CopyOnWriteArrayList<>();
    private static final AtomicInteger ID_SEQ = new AtomicInteger(50);

    private Category mapResultSetToCategory(ResultSet rs) throws Exception {
        int parentIdVal = rs.getInt("parent_id");
        Integer parentId = rs.wasNull() ? null : parentIdVal;

        String createdBy = rs.getString("created_by");
        if (createdBy == null || createdBy.trim().isEmpty()) {
            createdBy = "Phan Thanh Duy";
        }

        Date createdAtDate = null;
        String createdAtFormatted = "14:40 - 29/09/2026";
        try {
            java.sql.Timestamp cAt = rs.getTimestamp("created_at");
            if (cAt != null) {
                createdAtDate = new Date(cAt.getTime());
                createdAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(cAt);
            }
        } catch (Exception ignored) {}

        String updatedBy = rs.getString("updated_by");
        if (updatedBy == null || updatedBy.trim().isEmpty()) {
            updatedBy = "Phan Thanh Duy";
        }

        String updatedAtFormatted = "17:38 - 11/10/2026";
        try {
            java.sql.Timestamp uAt = rs.getTimestamp("updated_at");
            if (uAt != null) {
                updatedAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(uAt);
            }
        } catch (Exception ignored) {}

        String deletedBy = "Phan Thanh Duy";
        try {
            String dbDel = rs.getString("deleted_by");
            if (dbDel != null && !dbDel.trim().isEmpty()) {
                deletedBy = dbDel;
            }
        } catch (Exception ignored) {}

        String deletedAtFormatted = "18:00 - 15/11/2026";
        try {
            java.sql.Timestamp dAt = rs.getTimestamp("deleted_at");
            if (dAt != null) {
                deletedAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(dAt);
            } else {
                java.sql.Timestamp uAt = rs.getTimestamp("updated_at");
                if (uAt != null) {
                    deletedAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(uAt);
                }
            }
        } catch (Exception ignored) {}

        Category cat = new Category(
            rs.getInt("id"),
            rs.getString("name"),
            parentId,
            rs.getInt("position"),
            rs.getString("avatar"),
            rs.getString("description"),
            rs.getString("status"),
            rs.getString("slug"),
            rs.getBoolean("deleted"),
            createdBy,
            createdAtFormatted,
            updatedBy,
            updatedAtFormatted,
            deletedBy,
            deletedAtFormatted
        );
        cat.setCreatedAt(createdAtDate);
        return cat;
    }

    public List<Category> getAllCategories() {
        return getAllCategories(true);
    }

    public List<Category> getAllCategoriesAdmin() {
        return getAllCategories(false);
    }

    public List<Category> getAllCategories(boolean onlyActive) {
        List<Category> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM categories WHERE deleted = 0 " + (onlyActive ? "AND status = 'active' " : "") + "ORDER BY position DESC, id DESC";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(mapResultSetToCategory(rs));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!list.isEmpty()) {
            return list;
        }

        // Memory fallback if DB is offline
        List<Category> result = new ArrayList<>();
        for (Category c : MOCK_CATEGORIES) {
            if (!c.isDeleted()) {
                if (!onlyActive || "active".equalsIgnoreCase(c.getStatus())) {
                    result.add(c);
                }
            }
        }
        result.sort((a, b) -> {
            if (b.getPosition() != a.getPosition()) {
                return Integer.compare(b.getPosition(), a.getPosition());
            }
            return Integer.compare(b.getId(), a.getId());
        });
        return result;
    }

    public List<Category> getTrashCategories() {
        List<Category> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM categories WHERE deleted = 1 ORDER BY position DESC, id DESC";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(mapResultSetToCategory(rs));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!list.isEmpty()) {
            return list;
        }

        List<Category> result = new ArrayList<>();
        for (Category c : MOCK_CATEGORIES) {
            if (c.isDeleted()) {
                result.add(c);
            }
        }
        result.sort((a, b) -> Integer.compare(b.getId(), a.getId()));
        return result;
    }

    public Category getCategoryById(int id) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM categories WHERE id = ? LIMIT 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Category c = mapResultSetToCategory(rs);
                    conn.close();
                    return c;
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Category c : MOCK_CATEGORIES) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Category getCategoryBySlug(String slug) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM categories WHERE slug = ? AND deleted = 0 AND status = 'active' LIMIT 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, slug);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Category c = mapResultSetToCategory(rs);
                    conn.close();
                    return c;
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Category c : MOCK_CATEGORIES) {
            if (!c.isDeleted() && slug != null && slug.equalsIgnoreCase(c.getSlug())) {
                return c;
            }
        }
        return null;
    }

    public List<Category> getCategoryTree() {
        List<Category> all = getAllCategories(false);
        List<Category> tree = new ArrayList<>();
        Map<Integer, Category> map = new HashMap<>();

        for (Category c : all) {
            Category copy = new Category(c.getId(), c.getName(), c.getParentId(), c.getPosition(), c.getAvatar(), c.getDescription(), c.getStatus(), c.getSlug(), c.isDeleted(), c.getCreatedBy(), c.getCreatedAtFormatted(), c.getUpdatedBy(), c.getUpdatedAtFormatted());
            map.put(c.getId(), copy);
        }

        for (Category c : map.values()) {
            if (c.getParentId() == null || c.getParentId() == 0 || !map.containsKey(c.getParentId())) {
                tree.add(c);
            } else {
                Category parent = map.get(c.getParentId());
                if (parent != null) {
                    parent.addChild(c);
                }
            }
        }
        return tree;
    }

    public List<Integer> getSubcategoryIds(int categoryId) {
        List<Integer> ids = new ArrayList<>();
        ids.add(categoryId);
        List<Category> all = getAllCategories(false);
        findChildrenIds(categoryId, all, ids);
        return ids;
    }

    private void findChildrenIds(int parentId, List<Category> all, List<Integer> result) {
        for (Category c : all) {
            if (c.getParentId() != null && c.getParentId() == parentId) {
                if (!result.contains(c.getId())) {
                    result.add(c.getId());
                    findChildrenIds(c.getId(), all, result);
                }
            }
        }
    }

    public boolean createCategory(Category c) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "INSERT INTO categories (name, parent_id, position, avatar, description, status, slug, created_by, updated_by, created_at, updated_at, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), 0)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, c.getName());
                if (c.getParentId() != null && c.getParentId() > 0) ps.setInt(2, c.getParentId());
                else ps.setNull(2, java.sql.Types.INTEGER);
                ps.setInt(3, c.getPosition());
                ps.setString(4, c.getAvatar());
                ps.setString(5, c.getDescription());
                ps.setString(6, c.getStatus());
                ps.setString(7, c.getSlug());
                ps.setString(8, c.getCreatedBy() != null ? c.getCreatedBy() : "Phan Thanh Duy");
                ps.setString(9, c.getUpdatedBy() != null ? c.getUpdatedBy() : "Phan Thanh Duy");
                ps.executeUpdate();
                ResultSet gk = ps.getGeneratedKeys();
                if (gk.next()) {
                    c.setId(gk.getInt(1));
                }
                conn.close();
                MOCK_CATEGORIES.add(0, c);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int id = ID_SEQ.incrementAndGet();
        c.setId(id);
        MOCK_CATEGORIES.add(0, c);
        return true;
    }

    public boolean updateCategory(Category c) {
        for (Category existing : MOCK_CATEGORIES) {
            if (existing.getId() == c.getId()) {
                existing.setName(c.getName());
                existing.setParentId(c.getParentId());
                existing.setPosition(c.getPosition());
                existing.setStatus(c.getStatus());
                if (c.getAvatar() != null && !c.getAvatar().isEmpty()) {
                    existing.setAvatar(c.getAvatar());
                } else if ("".equals(c.getAvatar())) {
                    existing.setAvatar("");
                }
                existing.setDescription(c.getDescription());
                existing.setSlug(c.getSlug());
                if (c.getUpdatedBy() != null) existing.setUpdatedBy(c.getUpdatedBy());
                if (c.getUpdatedAtFormatted() != null) existing.setUpdatedAtFormatted(c.getUpdatedAtFormatted());
                break;
            }
        }

        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE categories SET name = ?, parent_id = ?, position = ?, avatar = ?, description = ?, status = ?, slug = ?, updated_by = ?, updated_at = NOW() WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, c.getName());
                if (c.getParentId() != null && c.getParentId() > 0) ps.setInt(2, c.getParentId());
                else ps.setNull(2, java.sql.Types.INTEGER);
                ps.setInt(3, c.getPosition());
                ps.setString(4, c.getAvatar());
                ps.setString(5, c.getDescription());
                ps.setString(6, c.getStatus());
                ps.setString(7, c.getSlug());
                ps.setString(8, c.getUpdatedBy() != null ? c.getUpdatedBy() : "Phan Thanh Duy");
                ps.setInt(9, c.getId());
                ps.executeUpdate();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean deleteCategory(int id) {
        for (Category c : MOCK_CATEGORIES) {
            if (c.getId() == id) {
                c.setDeleted(true);
                break;
            }
        }

        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE categories SET deleted = 1, deleted_by = 'Phan Thanh Duy', deleted_at = NOW(), updated_at = NOW() WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean undoCategory(int id) {
        for (Category c : MOCK_CATEGORIES) {
            if (c.getId() == id) {
                c.setDeleted(false);
                break;
            }
        }

        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE categories SET deleted = 0, deleted_by = NULL, deleted_at = NULL, updated_at = NOW() WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean deleteDestroyCategory(int id) {
        MOCK_CATEGORIES.removeIf(c -> c.getId() == id);

        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "DELETE FROM categories WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean changeStatus(int id, String status) {
        for (Category c : MOCK_CATEGORIES) {
            if (c.getId() == id) {
                c.setStatus(status);
                break;
            }
        }

        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE categories SET status = ?, updated_at = NOW() WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, status);
                ps.setInt(2, id);
                ps.executeUpdate();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public int getNextPosition() {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT MAX(position) FROM categories WHERE deleted = 0";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int max = rs.getInt(1);
                    conn.close();
                    return max + 1;
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int max = 0;
        for (Category c : MOCK_CATEGORIES) {
            if (!c.isDeleted() && c.getPosition() > max) {
                max = c.getPosition();
            }
        }
        return max + 1;
    }

    public static String toSlug(String input) {
        if (input == null || input.trim().isEmpty()) return "";
        String normalized = Normalizer.normalize(input.trim(), Normalizer.Form.NFD);
        String slug = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        slug = slug.replace("đ", "d").replace("Đ", "d");
        slug = slug.toLowerCase().replaceAll("[^a-z0-9\\s-]", "");
        slug = slug.replaceAll("\\s+", "-").replaceAll("-+", "-");
        return slug.trim();
    }
}

