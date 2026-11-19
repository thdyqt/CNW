package model.DAO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.BEAN.*;
import model.BEAN.*;
import model.BEAN.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean createOrder(Order order) {
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO orders (code, full_name, phone, note, sub_total, total, payment_method, payment_status, status, items, created_at, updated_at, deleted) "
                           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), 0)";
                try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, order.getCode());
                    ps.setString(2, order.getFullName());
                    ps.setString(3, order.getPhone());
                    ps.setString(4, order.getNote());
                    ps.setDouble(5, order.getSubTotal());
                    ps.setDouble(6, order.getTotal());
                    ps.setString(7, order.getPaymentMethod() != null ? order.getPaymentMethod() : "money");
                    ps.setString(8, order.getPaymentStatus() != null ? order.getPaymentStatus() : "unpaid");
                    ps.setString(9, order.getStatus() != null ? order.getStatus() : "initial");

                    String itemsJson = objectMapper.writeValueAsString(order.getItems());
                    ps.setString(10, itemsJson);

                    int affected = ps.executeUpdate();
                    if (affected > 0) {
                        try (ResultSet rs = ps.getGeneratedKeys()) {
                            if (rs.next()) {
                                int orderId = rs.getInt(1);
                                order.setId(orderId);

                                String itemSql = "INSERT INTO order_items (order_id, tour_id, quantity_adult, quantity_children, quantity_baby, price_new_adult, price_new_children, price_new_baby, location_from) "
                                               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                try (PreparedStatement itemPs = conn.prepareStatement(itemSql)) {
                                    for (OrderItem item : order.getItems()) {
                                        itemPs.setInt(1, orderId);
                                        itemPs.setInt(2, item.getTourId());
                                        itemPs.setInt(3, item.getQuantityAdult());
                                        itemPs.setInt(4, item.getQuantityChildren());
                                        itemPs.setInt(5, item.getQuantityBaby());
                                        itemPs.setDouble(6, item.getPriceNewAdult());
                                        itemPs.setDouble(7, item.getPriceNewChildren());
                                        itemPs.setDouble(8, item.getPriceNewBaby());
                                        itemPs.setInt(9, item.getLocationFrom());
                                        itemPs.addBatch();
                                    }
                                    itemPs.executeBatch();
                                }
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

    public List<Order> getOrders(String status, String startDate, String endDate, String paymentMethod, String paymentStatus, String keyword, int page, int limit) {
        List<Order> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM orders WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append(" AND created_at >= ?");
            params.add(startDate.trim() + " 00:00:00");
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND created_at <= ?");
            params.add(endDate.trim() + " 23:59:59");
        }
        if (paymentMethod != null && !paymentMethod.trim().isEmpty()) {
            if ("bank".equalsIgnoreCase(paymentMethod.trim()) || "banking".equalsIgnoreCase(paymentMethod.trim())) {
                sql.append(" AND (payment_method = 'bank' OR payment_method = 'banking')");
            } else {
                sql.append(" AND payment_method = ?");
                params.add(paymentMethod.trim());
            }
        }
        if (paymentStatus != null && !paymentStatus.trim().isEmpty()) {
            sql.append(" AND payment_status = ?");
            params.add(paymentStatus.trim());
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (code LIKE ? OR full_name LIKE ? OR phone LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw);
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
                            list.add(mapResultSetToOrder(rs, conn));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countOrders(String status, String startDate, String endDate, String paymentMethod, String paymentStatus, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM orders WHERE deleted = 0");
        List<Object> params = new ArrayList<>();

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append(" AND created_at >= ?");
            params.add(startDate.trim() + " 00:00:00");
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND created_at <= ?");
            params.add(endDate.trim() + " 23:59:59");
        }
        if (paymentMethod != null && !paymentMethod.trim().isEmpty()) {
            if ("bank".equalsIgnoreCase(paymentMethod.trim()) || "banking".equalsIgnoreCase(paymentMethod.trim())) {
                sql.append(" AND (payment_method = 'bank' OR payment_method = 'banking')");
            } else {
                sql.append(" AND payment_method = ?");
                params.add(paymentMethod.trim());
            }
        }
        if (paymentStatus != null && !paymentStatus.trim().isEmpty()) {
            sql.append(" AND payment_status = ?");
            params.add(paymentStatus.trim());
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (code LIKE ? OR full_name LIKE ? OR phone LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw);
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

    public List<Order> getAllOrders() {
        return getOrders(null, null, null, null, null, null, 1, 1000);
    }

    public Order getOrderById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ? AND deleted = 0";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToOrder(rs, conn);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrderByCodeAndPhone(String code, String phone) {
        String sql = "SELECT * FROM orders WHERE code = ? AND (phone = ? OR ? IS NULL) AND deleted = 0";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, code);
                    ps.setString(2, phone);
                    ps.setString(3, phone);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapResultSetToOrder(rs, conn);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET full_name = ?, phone = ?, note = ?, payment_method = ?, payment_status = ?, status = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, order.getFullName());
                    ps.setString(2, order.getPhone());
                    ps.setString(3, order.getNote());
                    ps.setString(4, order.getPaymentMethod());
                    ps.setString(5, order.getPaymentStatus());
                    ps.setString(6, order.getStatus());
                    ps.setInt(7, order.getId());
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE orders SET status = ?, updated_at = NOW() WHERE id = ?";
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

    public boolean deleteOrder(int id, String deletedBy) {
        String sql = "UPDATE orders SET deleted = 1, deleted_by = ?, deleted_at = NOW(), updated_at = NOW() WHERE id = ?";
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

    public boolean deleteOrder(int id) {
        return deleteOrder(id, "Phan Thanh Duy");
    }

    public boolean undoOrder(int id) {
        String sql = "UPDATE orders SET deleted = 0, deleted_by = NULL, deleted_at = NULL, updated_at = NOW() WHERE id = ?";
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

    public boolean deleteDestroyOrder(int id) {
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                // 1. Delete child items
                String delItems = "DELETE FROM order_items WHERE order_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(delItems)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                } catch (Exception ignored) {}

                // 2. Delete order
                String sql = "DELETE FROM orders WHERE id = ?";
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

    public List<Order> getTrashOrders(String keyword, int page, int limit) {
        List<Order> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM orders WHERE deleted = 1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (code LIKE ? OR full_name LIKE ? OR phone LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw);
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
                            list.add(mapResultSetToOrder(rs, conn));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTrashOrders(String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM orders WHERE deleted = 1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (code LIKE ? OR full_name LIKE ? OR phone LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw);
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

    private Order mapResultSetToOrder(ResultSet rs, Connection conn) throws Exception {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setCode(rs.getString("code"));
        o.setFullName(rs.getString("full_name"));
        o.setPhone(rs.getString("phone"));
        o.setNote(rs.getString("note"));
        o.setSubTotal(rs.getDouble("sub_total"));
        o.setTotal(rs.getDouble("total"));
        o.setPaymentMethod(rs.getString("payment_method"));
        o.setPaymentStatus(rs.getString("payment_status"));
        o.setStatus(rs.getString("status"));
        o.setCreatedAt(rs.getTimestamp("created_at"));
        try {
            o.setDeleted(rs.getInt("deleted") == 1);
            o.setDeletedBy(rs.getString("deleted_by"));
            o.setDeletedAt(rs.getTimestamp("deleted_at"));
            o.setUpdatedBy(rs.getString("updated_by"));
        } catch (Exception ignored) {}

        String itemsJson = null;
        try {
            itemsJson = rs.getString("items");
        } catch (Exception ignored) {}

        loadOrderItems(o, itemsJson, conn);
        return o;
    }

    private void loadOrderItems(Order o, String itemsJson, Connection conn) {
        // 1. Try loading from order_items table
        try {
            String itemSql = "SELECT oi.*, t.name, t.slug, t.avatar, c.name as city_name "
                           + "FROM order_items oi "
                           + "LEFT JOIN tours t ON oi.tour_id = t.id "
                           + "LEFT JOIN cities c ON oi.location_from = c.id "
                           + "WHERE oi.order_id = ?";
            try (PreparedStatement itemPs = conn.prepareStatement(itemSql)) {
                itemPs.setInt(1, o.getId());
                try (ResultSet itemRs = itemPs.executeQuery()) {
                    while (itemRs.next()) {
                        OrderItem item = new OrderItem();
                        item.setId(itemRs.getInt("id"));
                        item.setTourId(itemRs.getInt("tour_id"));
                        item.setName(itemRs.getString("name"));
                        item.setSlug(itemRs.getString("slug"));
                        item.setAvatar(itemRs.getString("avatar"));
                        item.setQuantityAdult(itemRs.getInt("quantity_adult"));
                        item.setQuantityChildren(itemRs.getInt("quantity_children"));
                        item.setQuantityBaby(itemRs.getInt("quantity_baby"));
                        item.setPriceNewAdult(itemRs.getDouble("price_new_adult"));
                        item.setPriceNewChildren(itemRs.getDouble("price_new_children"));
                        item.setPriceNewBaby(itemRs.getDouble("price_new_baby"));
                        item.setLocationFromName(itemRs.getString("city_name"));
                        item.setDepartureDate(itemRs.getDate("departure_date"));
                        o.getItems().add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. If order_items table has no rows, parse items JSON string
        if (o.getItems().isEmpty() && itemsJson != null && !itemsJson.trim().isEmpty()) {
            try {
                JsonNode arr = objectMapper.readTree(itemsJson);
                if (arr != null && arr.isArray()) {
                    for (JsonNode node : arr) {
                        OrderItem item = new OrderItem();
                        String tourIdStr = node.has("tourId") ? node.get("tourId").asText() : "";
                        int tourId = resolveTourId(tourIdStr);
                        item.setTourId(tourId);

                        Tour tour = getTourInfo(tourId, conn);
                        if (tour != null) {
                            item.setName(tour.getName());
                            item.setAvatar(tour.getAvatar());
                            item.setSlug(tour.getSlug());
                        } else {
                            item.setName("Tour #" + tourId);
                            item.setAvatar("/public/admin/assets/images/Tour-Icon.jpg");
                        }

                        if (node.has("quantityAdult")) item.setQuantityAdult(node.get("quantityAdult").asInt());
                        if (node.has("quantityChildren")) item.setQuantityChildren(node.get("quantityChildren").asInt());
                        if (node.has("quantityBaby")) item.setQuantityBaby(node.get("quantityBaby").asInt());

                        if (node.has("priceNewAdult")) item.setPriceNewAdult(node.get("priceNewAdult").asDouble());
                        if (node.has("priceNewChildren")) item.setPriceNewChildren(node.get("priceNewChildren").asDouble());
                        if (node.has("priceNewBaby")) item.setPriceNewBaby(node.get("priceNewBaby").asDouble());

                        if (node.has("departureDate")) {
                            String depStr = node.get("departureDate").asText();
                            try {
                                if (depStr.contains(" ")) {
                                    item.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(depStr));
                                } else {
                                    item.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse(depStr));
                                }
                            } catch (Exception ignored) {}
                        }

                        o.getItems().add(item);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int resolveTourId(String tourIdStr) {
        if ("6acb69adc47144e89567b1ab".equals(tourIdStr)) return 1;
        if ("6acb6a3ac47144e89567b1ac".equals(tourIdStr)) return 2;
        if ("6ad4a73c1dc6114b7491c17e".equals(tourIdStr)) return 3;
        try {
            return Integer.parseInt(tourIdStr);
        } catch (Exception ignored) {}
        return 1;
    }

    private Tour getTourInfo(int tourId, Connection conn) {
        String sql = "SELECT id, name, slug, avatar FROM tours WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tourId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tour t = new Tour();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setSlug(rs.getString("slug"));
                    t.setAvatar(rs.getString("avatar"));
                    return t;
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    public Map<Integer, Double> getDailyRevenue(int year, int month) {
        Map<Integer, Double> map = new HashMap<>();
        String sql = "SELECT DAY(created_at) AS day_num, SUM(total) AS daily_total "
                   + "FROM orders "
                   + "WHERE deleted = 0 AND status != 'cancel' "
                   + "  AND YEAR(created_at) = ? AND MONTH(created_at) = ? "
                   + "GROUP BY DAY(created_at)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, year);
            ps.setInt(2, month);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getInt("day_num"), rs.getDouble("daily_total"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

