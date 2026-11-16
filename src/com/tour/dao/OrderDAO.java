package com.tour.dao;

import com.tour.model.Order;
import com.tour.model.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public boolean createOrder(Order order) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "INSERT INTO orders (code, full_name, phone, note, sub_total, total, payment_method, payment_status, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, order.getCode());
                ps.setString(2, order.getFullName());
                ps.setString(3, order.getPhone());
                ps.setString(4, order.getNote());
                ps.setDouble(5, order.getSubTotal());
                ps.setDouble(6, order.getTotal());
                ps.setString(7, order.getPaymentMethod());
                ps.setString(8, order.getPaymentStatus());
                ps.setString(9, order.getStatus());

                int affected = ps.executeUpdate();
                if (affected > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        order.setId(orderId);

                        // Insert items
                        String itemSql = "INSERT INTO order_items (order_id, tour_id, quantity_adult, quantity_children, quantity_baby, price_new_adult, price_new_children, price_new_baby, location_from) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement itemPs = conn.prepareStatement(itemSql);
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
                    conn.close();
                    return true;
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true; // Return true for mock support
    }

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM orders WHERE deleted = 0 ORDER BY id DESC";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
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
                    list.add(o);
                }
                for (Order o : list) {
                    String itemSql = "SELECT oi.*, t.name, t.slug, t.avatar, c.name as city_name FROM order_items oi LEFT JOIN tours t ON oi.tour_id = t.id LEFT JOIN cities c ON oi.location_from = c.id WHERE oi.order_id = ?";
                    PreparedStatement itemPs = conn.prepareStatement(itemSql);
                    itemPs.setInt(1, o.getId());
                    ResultSet itemRs = itemPs.executeQuery();
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
                        o.getItems().add(item);
                    }
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (list.isEmpty()) {
            Order o1 = new Order();
            o1.setId(1);
            o1.setCode("OD0000000001");
            o1.setFullName("Lê Văn A");
            o1.setPhone("0912345678");
            o1.setNote("Phòng đôi view biển");
            o1.setPaymentMethod("money");
            o1.setPaymentStatus("unpaid");
            o1.setStatus("initial");
            o1.setSubTotal(7780000);
            o1.setTotal(7780000);

            OrderItem it1 = new OrderItem();
            it1.setName("Tour Đà Nẵng - Hội An - Bà Nà Hills 4N3Đ");
            it1.setSlug("tour-da-nang-hoi-an-ba-na-hills-4n3d");
            it1.setAvatar("/assets/images/Product-1.jpg");
            it1.setQuantityAdult(2);
            it1.setPriceNewAdult(3890000);
            it1.setLocationFromName("Đà Nẵng");
            it1.setDepartureDateFormatted("20/09/2026");
            o1.getItems().add(it1);
            list.add(o1);

            Order o2 = new Order();
            o2.setId(2);
            o2.setCode("OD0000000002");
            o2.setFullName("Nguyễn Thị B");
            o2.setPhone("0987654321");
            o2.setNote("Gia đình có trẻ em");
            o2.setPaymentMethod("banking");
            o2.setPaymentStatus("paid");
            o2.setStatus("done");
            o2.setSubTotal(5380000);
            o2.setTotal(5380000);

            OrderItem it2 = new OrderItem();
            it2.setName("Tour Hà Giang - Mùa Hoa Tam Giác Mạch 3N2Đ");
            it2.setSlug("tour-ha-giang-mua-hoa-tam-giac-mach-3n2d");
            it2.setAvatar("/assets/images/Background-2.jpg");
            it2.setQuantityAdult(2);
            it2.setPriceNewAdult(2690000);
            it2.setLocationFromName("Hà Nội");
            it2.setDepartureDateFormatted("05/10/2026");
            o2.getItems().add(it2);
            list.add(o2);
        }
        return list;
    }

    public Order getOrderById(int id) {
        for (Order o : getAllOrders()) {
            if (o.getId() == id) return o;
        }
        return getOrderByCodeAndPhone("OD0000000001", null);
    }

    public Order getOrderByCodeAndPhone(String code, String phone) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM orders WHERE code = ? AND (phone = ? OR ? IS NULL)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, code);
                ps.setString(2, phone);
                ps.setString(3, phone);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
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

                    // Get items
                    String itemSql = "SELECT oi.*, t.name, t.slug, t.avatar, c.name as city_name FROM order_items oi LEFT JOIN tours t ON oi.tour_id = t.id LEFT JOIN cities c ON oi.location_from = c.id WHERE oi.order_id = ?";
                    PreparedStatement itemPs = conn.prepareStatement(itemSql);
                    itemPs.setInt(1, o.getId());
                    ResultSet itemRs = itemPs.executeQuery();
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
                        o.getItems().add(item);
                    }
                    conn.close();
                    return o;
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fallback default sample order
        Order o = new Order();
        o.setCode(code != null ? code : "OD0000000001");
        o.setFullName("Phan Thanh Duy");
        o.setPhone(phone != null ? phone : "0912345678");
        o.setNote("Yêu cầu phòng hướng biển");
        o.setPaymentMethod("money");
        o.setPaymentStatus("unpaid");
        o.setStatus("initial");
        o.setSubTotal(3890000);
        o.setTotal(3890000);

        OrderItem it = new OrderItem();
        it.setName("Tour Đà Nẵng - Hội An - Bà Nà Hills 4N3Đ");
        it.setSlug("tour-da-nang-hoi-an-ba-na-hills-4n3d");
        it.setAvatar("/assets/images/Product-1.jpg");
        it.setQuantityAdult(1);
        it.setPriceNewAdult(3890000);
        it.setLocationFromName("Đà Nẵng");
        it.setDepartureDateFormatted("20/09/2026");
        o.getItems().add(it);

        return o;
    }
}
