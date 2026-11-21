package model.DAO;

import model.BEAN.Coupon;
import model.BEAN.CouponResult;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CouponDAO {

    public List<Coupon> getCoupons(String status, String keyword, int page, int limit) {
        List<Coupon> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM coupons WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (code LIKE ? OR title LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw);
            params.add(kw);
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
                            list.add(mapResultSetToCoupon(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countCoupons(String status, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM coupons WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (code LIKE ? OR title LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw);
            params.add(kw);
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

    public Coupon getCouponById(int id) {
        String sql = "SELECT * FROM coupons WHERE id = ? AND deleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCoupon(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Coupon getCouponByIdIncludeTrash(int id) {
        String sql = "SELECT * FROM coupons WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCoupon(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Coupon getCouponByCode(String code) {
        if (code == null || code.trim().isEmpty()) return null;
        String sql = "SELECT * FROM coupons WHERE code = ? AND deleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code.trim().toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCoupon(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isCodeExists(String code, int excludeId) {
        if (code == null || code.trim().isEmpty()) return false;
        String sql = "SELECT COUNT(*) FROM coupons WHERE code = ? AND id != ? AND deleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code.trim().toUpperCase());
            ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createCoupon(Coupon coupon) {
        String sql = "INSERT INTO coupons (code, title, type, value, min_order_value, max_discount_value, "
                   + "usage_limit, used_count, start_date, end_date, status, created_by, updated_by, created_at, updated_at, deleted) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?, ?, ?, NOW(), NOW(), 0)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, coupon.getCode().trim().toUpperCase());
            ps.setString(2, coupon.getTitle());
            ps.setString(3, coupon.getType() != null ? coupon.getType() : "percentage");
            ps.setDouble(4, coupon.getValue());
            ps.setDouble(5, coupon.getMinOrderValue());
            ps.setDouble(6, coupon.getMaxDiscountValue());
            ps.setInt(7, coupon.getUsageLimit());
            ps.setDate(8, coupon.getStartDate() != null ? new Date(coupon.getStartDate().getTime()) : null);
            ps.setDate(9, coupon.getEndDate() != null ? new Date(coupon.getEndDate().getTime()) : null);
            ps.setString(10, coupon.getStatus() != null ? coupon.getStatus() : "active");
            ps.setString(11, coupon.getCreatedBy());
            ps.setString(12, coupon.getUpdatedBy());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        coupon.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCoupon(Coupon coupon) {
        String sql = "UPDATE coupons SET code = ?, title = ?, type = ?, value = ?, min_order_value = ?, "
                   + "max_discount_value = ?, usage_limit = ?, start_date = ?, end_date = ?, status = ?, "
                   + "updated_by = ?, updated_at = NOW() WHERE id = ? AND deleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, coupon.getCode().trim().toUpperCase());
            ps.setString(2, coupon.getTitle());
            ps.setString(3, coupon.getType());
            ps.setDouble(4, coupon.getValue());
            ps.setDouble(5, coupon.getMinOrderValue());
            ps.setDouble(6, coupon.getMaxDiscountValue());
            ps.setInt(7, coupon.getUsageLimit());
            ps.setDate(8, coupon.getStartDate() != null ? new Date(coupon.getStartDate().getTime()) : null);
            ps.setDate(9, coupon.getEndDate() != null ? new Date(coupon.getEndDate().getTime()) : null);
            ps.setString(10, coupon.getStatus());
            ps.setString(11, coupon.getUpdatedBy());
            ps.setInt(12, coupon.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeStatus(int id, String status) {
        String sql = "UPDATE coupons SET status = ?, updated_at = NOW() WHERE id = ? AND deleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCoupon(int id, String deletedBy) {
        String sql = "UPDATE coupons SET deleted = 1, deleted_by = ?, deleted_at = NOW(), updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, deletedBy != null ? deletedBy : "Phan Thanh Duy");
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Coupon> getTrashCoupons(String keyword, int page, int limit) {
        List<Coupon> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM coupons WHERE deleted = 1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (code LIKE ? OR title LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw);
            params.add(kw);
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
                            list.add(mapResultSetToCoupon(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTrashCoupons(String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM coupons WHERE deleted = 1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (code LIKE ? OR title LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw);
            params.add(kw);
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

    public boolean undoCoupon(int id) {
        String sql = "UPDATE coupons SET deleted = 0, deleted_by = NULL, deleted_at = NULL, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean destroyCoupon(int id) {
        String sql = "DELETE FROM coupons WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Coupon> getActiveCoupons(int limit) {
        List<Coupon> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM coupons WHERE deleted = 0 AND status = 'active' "
                   + "AND (start_date IS NULL OR start_date <= CURDATE()) "
                   + "AND (end_date IS NULL OR end_date >= CURDATE()) "
                   + "AND (usage_limit = 0 OR used_count < usage_limit) "
                   + "ORDER BY id DESC");
        if (limit > 0) {
            sql.append(" LIMIT ?");
        }
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            if (limit > 0) {
                ps.setInt(1, limit);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToCoupon(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Coupon> getActiveCoupons() {
        return getActiveCoupons(0);
    }

    /**
     * Kiểm tra tính hợp lệ và tính số tiền giảm giá cho giỏ hàng
     */
    public CouponResult validateAndCalculate(String code, double subTotal) {
        if (code == null || code.trim().isEmpty()) {
            return new CouponResult(false, "Vui lòng nhập mã giảm giá!");
        }

        Coupon coupon = getCouponByCode(code.trim().toUpperCase());
        if (coupon == null) {
            return new CouponResult(false, "Mã giảm giá '" + code.trim().toUpperCase() + "' không tồn tại!");
        }

        if (!"active".equalsIgnoreCase(coupon.getStatus())) {
            return new CouponResult(false, "Mã giảm giá '" + coupon.getCode() + "' hiện đang tạm khóa hoặc ngừng áp dụng!");
        }

        java.util.Date now = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date today = sdf.parse(sdf.format(now));
            if (coupon.getStartDate() != null) {
                java.util.Date start = sdf.parse(sdf.format(coupon.getStartDate()));
                if (today.before(start)) {
                    return new CouponResult(false, "Mã giảm giá có hiệu lực từ ngày " + coupon.getStartDateFormatted() + "!");
                }
            }
            if (coupon.getEndDate() != null) {
                java.util.Date end = sdf.parse(sdf.format(coupon.getEndDate()));
                if (today.after(end)) {
                    return new CouponResult(false, "Mã giảm giá đã hết hạn sử dụng vào ngày " + coupon.getEndDateFormatted() + "!");
                }
            }
        } catch (Exception ignored) {}

        if (coupon.getUsageLimit() > 0 && coupon.getUsedCount() >= coupon.getUsageLimit()) {
            return new CouponResult(false, "Mã giảm giá này đã hết lượt sử dụng!");
        }

        if (coupon.getMinOrderValue() > 0 && subTotal < coupon.getMinOrderValue()) {
            DecimalFormat df = new DecimalFormat("#,###", new DecimalFormatSymbols(new Locale("vi", "VN")));
            return new CouponResult(false, "Đơn hàng tối thiểu " + df.format(coupon.getMinOrderValue()) + "đ để áp dụng mã giảm giá này!");
        }

        // Tính toán số tiền giảm
        double discount = 0;
        if ("percentage".equalsIgnoreCase(coupon.getType())) {
            discount = subTotal * (coupon.getValue() / 100.0);
            if (coupon.getMaxDiscountValue() > 0 && discount > coupon.getMaxDiscountValue()) {
                discount = coupon.getMaxDiscountValue();
            }
        } else {
            discount = coupon.getValue();
        }

        discount = Math.min(discount, subTotal);
        double newTotal = Math.max(0, subTotal - discount);

        CouponResult result = new CouponResult(true, "Áp dụng mã giảm giá thành công!");
        result.setCouponCode(coupon.getCode());
        result.setCouponTitle(coupon.getTitle());
        result.setDiscountType(coupon.getType());
        result.setDiscountValue(coupon.getValue());
        result.setDiscount(discount);
        result.setSubTotal(subTotal);
        result.setNewTotal(newTotal);
        result.setCoupon(coupon);

        return result;
    }

    /**
     * Tăng số lượt sử dụng mã giảm giá một cách an toàn (nguyên tử) trong Transaction
     */
    public boolean incrementUsedCount(Connection conn, String code) {
        if (code == null || code.trim().isEmpty()) return true;
        String sql = "UPDATE coupons SET used_count = used_count + 1 "
                   + "WHERE code = ? AND (usage_limit = 0 OR used_count < usage_limit) "
                   + "  AND status = 'active' AND deleted = 0";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code.trim().toUpperCase());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Giảm số lượt sử dụng mã giảm giá khi đơn hàng bị hủy
     */
    public boolean decrementUsedCount(Connection conn, String code) {
        if (code == null || code.trim().isEmpty()) return true;
        String sql = "UPDATE coupons SET used_count = GREATEST(0, used_count - 1) WHERE code = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code.trim().toUpperCase());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Coupon mapResultSetToCoupon(ResultSet rs) throws Exception {
        Coupon c = new Coupon();
        c.setId(rs.getInt("id"));
        c.setCode(rs.getString("code"));
        c.setTitle(rs.getString("title"));
        c.setType(rs.getString("type"));
        c.setValue(rs.getDouble("value"));
        c.setMinOrderValue(rs.getDouble("min_order_value"));
        c.setMaxDiscountValue(rs.getDouble("max_discount_value"));
        c.setUsageLimit(rs.getInt("usage_limit"));
        c.setUsedCount(rs.getInt("used_count"));
        c.setStartDate(rs.getDate("start_date"));
        c.setEndDate(rs.getDate("end_date"));
        c.setStatus(rs.getString("status"));
        c.setCreatedBy(rs.getString("created_by"));
        c.setUpdatedBy(rs.getString("updated_by"));
        c.setCreatedAt(rs.getTimestamp("created_at"));
        c.setUpdatedAt(rs.getTimestamp("updated_at"));
        c.setDeleted(rs.getInt("deleted") == 1);
        c.setDeletedBy(rs.getString("deleted_by"));
        c.setDeletedAt(rs.getTimestamp("deleted_at"));
        return c;
    }
}
