package model.DAO;

import model.BEAN.News;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {

    public List<News> getNewsList(String category, String keyword, int page, int limit) {
        List<News> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM news WHERE deleted = 0 AND status = 'active'");
        List<Object> params = new ArrayList<>();

        if (category != null && !category.trim().isEmpty() && !"all".equalsIgnoreCase(category.trim())) {
            sql.append(" AND category = ?");
            params.add(category.trim());
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR description LIKE ?)");
            params.add("%" + keyword.trim() + "%");
            params.add("%" + keyword.trim() + "%");
        }

        sql.append(" ORDER BY featured DESC, id DESC LIMIT ? OFFSET ?");
        params.add(limit > 0 ? limit : 9);
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
                            list.add(mapResultSetToNews(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countNews(String category, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM news WHERE deleted = 0 AND status = 'active'");
        List<Object> params = new ArrayList<>();

        if (category != null && !category.trim().isEmpty() && !"all".equalsIgnoreCase(category.trim())) {
            sql.append(" AND category = ?");
            params.add(category.trim());
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR description LIKE ?)");
            params.add("%" + keyword.trim() + "%");
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

    public News getFeaturedNews() {
        String sql = "SELECT * FROM news WHERE deleted = 0 AND status = 'active' AND featured = 1 ORDER BY updated_at DESC, id DESC LIMIT 1";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToNews(rs);
                        }
                    }
                }
                // Fallback nếu không có bài nào đặt featured = 1
                String fallbackSql = "SELECT * FROM news WHERE deleted = 0 AND status = 'active' ORDER BY id DESC LIMIT 1";
                try (PreparedStatement ps = conn.prepareStatement(fallbackSql)) {
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToNews(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<News> getLatestNewsExcluding(int excludeId, int limit) {
        List<News> list = new ArrayList<>();
        String sql = excludeId > 0
                ? "SELECT * FROM news WHERE deleted = 0 AND status = 'active' AND id != ? ORDER BY id DESC LIMIT ?"
                : "SELECT * FROM news WHERE deleted = 0 AND status = 'active' ORDER BY id DESC LIMIT ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    if (excludeId > 0) {
                        ps.setInt(1, excludeId);
                        ps.setInt(2, limit > 0 ? limit : 4);
                    } else {
                        ps.setInt(1, limit > 0 ? limit : 4);
                    }
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            list.add(mapResultSetToNews(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void resetAllFeatured(int exceptId) {
        String sql = exceptId > 0 ? "UPDATE news SET featured = 0 WHERE id != ?" : "UPDATE news SET featured = 0";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    if (exceptId > 0) {
                        ps.setInt(1, exceptId);
                    }
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<News> getTopViewsNews(int limit) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM news WHERE deleted = 0 AND status = 'active' ORDER BY views DESC LIMIT ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, limit > 0 ? limit : 5);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            list.add(mapResultSetToNews(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public News getNewsBySlug(String slug) {
        String sql = "SELECT * FROM news WHERE slug = ? AND deleted = 0 LIMIT 1";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, slug);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToNews(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<News> getRelatedNews(String category, int currentId, int limit) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM news WHERE deleted = 0 AND status = 'active' AND id != ? AND (category = ? OR 1=1) ORDER BY (category = ?) DESC, id DESC LIMIT ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, currentId);
                    ps.setString(2, category != null ? category : "");
                    ps.setString(3, category != null ? category : "");
                    ps.setInt(4, limit > 0 ? limit : 3);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            list.add(mapResultSetToNews(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void incrementViews(int id) {
        String sql = "UPDATE news SET views = views + 1 WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public News getNewsById(int id) {
        String sql = "SELECT * FROM news WHERE id = ? LIMIT 1";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToNews(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createNews(News n) {
        if (n.isFeatured()) {
            resetAllFeatured(0);
        }
        String sql = "INSERT INTO news (title, slug, category, avatar, description, content, author, views, featured, status, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, n.getTitle());
                    ps.setString(2, n.getSlug());
                    ps.setString(3, n.getCategory());
                    ps.setString(4, n.getAvatar());
                    ps.setString(5, n.getDescription());
                    ps.setString(6, n.getContent());
                    ps.setString(7, n.getAuthor());
                    ps.setInt(8, n.getViews());
                    ps.setInt(9, n.isFeatured() ? 1 : 0);
                    ps.setString(10, n.getStatus());
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNews(News n) {
        if (n.isFeatured()) {
            resetAllFeatured(n.getId());
        }
        String sql = "UPDATE news SET title = ?, slug = ?, category = ?, avatar = ?, description = ?, content = ?, author = ?, featured = ?, status = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, n.getTitle());
                    ps.setString(2, n.getSlug());
                    ps.setString(3, n.getCategory());
                    ps.setString(4, n.getAvatar());
                    ps.setString(5, n.getDescription());
                    ps.setString(6, n.getContent());
                    ps.setString(7, n.getAuthor());
                    ps.setInt(8, n.isFeatured() ? 1 : 0);
                    ps.setString(9, n.getStatus());
                    ps.setInt(10, n.getId());
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNews(int id, String deletedBy) {
        String sql = "UPDATE news SET deleted = 1, deleted_by = ?, deleted_at = NOW() WHERE id = ?";
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

    public boolean undoNews(int id) {
        String sql = "UPDATE news SET deleted = 0, deleted_by = NULL, deleted_at = NULL WHERE id = ?";
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

    public boolean destroyNews(int id) {
        String sql = "DELETE FROM news WHERE id = ?";
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

    public boolean changeStatus(int id, String status) {
        String sql = "UPDATE news SET status = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, status);
                    ps.setInt(2, id);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<News> getAllNewsAdmin(String status, String category, String keyword, int page, int limit) {
        List<News> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM news WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }
        if (category != null && !category.trim().isEmpty() && !"all".equalsIgnoreCase(category.trim())) {
            sql.append(" AND category = ?");
            params.add(category.trim());
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR author LIKE ?)");
            params.add("%" + keyword.trim() + "%");
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
                            list.add(mapResultSetToNews(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countAllNewsAdmin(String status, String category, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM news WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }
        if (category != null && !category.trim().isEmpty() && !"all".equalsIgnoreCase(category.trim())) {
            sql.append(" AND category = ?");
            params.add(category.trim());
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR author LIKE ?)");
            params.add("%" + keyword.trim() + "%");
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

    public List<News> getTrashNewsAdmin(String keyword, int page, int limit) {
        List<News> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM news WHERE deleted = 1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR author LIKE ?)");
            params.add("%" + keyword.trim() + "%");
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
                            list.add(mapResultSetToNews(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTrashNewsAdmin(String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM news WHERE deleted = 1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR author LIKE ?)");
            params.add("%" + keyword.trim() + "%");
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

    private News mapResultSetToNews(ResultSet rs) throws Exception {
        News n = new News();
        n.setId(rs.getInt("id"));
        n.setTitle(rs.getString("title"));
        n.setSlug(rs.getString("slug"));
        n.setCategory(rs.getString("category"));
        n.setAvatar(rs.getString("avatar"));
        n.setDescription(rs.getString("description"));
        n.setContent(rs.getString("content"));
        n.setAuthor(rs.getString("author"));
        n.setViews(rs.getInt("views"));
        n.setFeatured(rs.getInt("featured") == 1);
        n.setStatus(rs.getString("status"));
        n.setDeleted(rs.getInt("deleted") == 1);
        n.setCreatedAt(rs.getTimestamp("created_at"));
        n.setUpdatedAt(rs.getTimestamp("updated_at"));
        try {
            n.setDeletedBy(rs.getString("deleted_by"));
            n.setDeletedAt(rs.getTimestamp("deleted_at"));
        } catch (Exception ignored) {}
        return n;
    }
}
