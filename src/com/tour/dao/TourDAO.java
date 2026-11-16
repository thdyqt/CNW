package com.tour.dao;

import com.tour.model.Tour;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TourDAO {

    private Tour mapResultSetToTour(ResultSet rs) throws Exception {
        Tour t = new Tour();
        t.setId(rs.getInt("id"));
        t.setCode(rs.getString("code"));
        t.setName(rs.getString("name"));
        t.setCategoryId(rs.getInt("category_id"));
        t.setPosition(rs.getInt("position"));
        t.setStatus(rs.getString("status"));
        t.setAvatar(rs.getString("avatar"));

        String imgs = rs.getString("images");
        if (imgs != null && !imgs.trim().isEmpty()) {
            t.setImages(new ArrayList<>(Arrays.asList(imgs.split(","))));
        } else {
            t.getImages().add(t.getAvatar());
        }

        t.setPriceAdult(rs.getDouble("price_adult"));
        t.setPriceChildren(rs.getDouble("price_children"));
        t.setPriceBaby(rs.getDouble("price_baby"));
        t.setPriceNewAdult(rs.getDouble("price_new_adult"));
        t.setPriceNewChildren(rs.getDouble("price_new_children"));
        t.setPriceNewBaby(rs.getDouble("price_new_baby"));
        t.setStockAdult(rs.getInt("stock_adult"));
        t.setStockChildren(rs.getInt("stock_children"));
        t.setStockBaby(rs.getInt("stock_baby"));
        t.setLocations(rs.getString("locations"));
        t.setTime(rs.getString("time"));
        t.setVehicle(rs.getString("vehicle"));
        t.setDepartureDate(rs.getDate("departure_date"));
        t.setInformation(rs.getString("information"));
        t.setSlug(rs.getString("slug"));
        t.setDeleted(rs.getBoolean("deleted"));

        try {
            java.sql.Timestamp cAt = rs.getTimestamp("created_at");
            if (cAt != null) {
                t.setCreatedAt(new Date(cAt.getTime()));
            }
        } catch (Exception ignored) {}

        try {
            String cb = rs.getString("created_by");
            if (cb != null && !cb.isEmpty()) t.setCreatedBy(cb);
            String ub = rs.getString("updated_by");
            if (ub != null && !ub.isEmpty()) t.setUpdatedBy(ub);
            java.sql.Timestamp uAt = rs.getTimestamp("updated_at");
            if (uAt != null) {
                t.setUpdatedAtFormatted(new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(uAt));
            }

            String dbDel = rs.getString("deleted_by");
            if (dbDel != null && !dbDel.trim().isEmpty()) {
                t.setDeletedBy(dbDel);
            }
            java.sql.Timestamp dAt = rs.getTimestamp("deleted_at");
            if (dAt != null) {
                t.setDeletedAtFormatted(new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(dAt));
            } else if (uAt != null) {
                t.setDeletedAtFormatted(new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(uAt));
            }
        } catch (Exception ignored) {}

        try {
            String schedStr = rs.getString("schedules");
            if (schedStr != null && !schedStr.trim().isEmpty() && !schedStr.equals("[]")) {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode rootNode = mapper.readTree(schedStr);
                if (rootNode.isArray()) {
                    for (com.fasterxml.jackson.databind.JsonNode itemNode : rootNode) {
                        String title = itemNode.has("title") ? itemNode.get("title").asText() : "";
                        String desc = itemNode.has("description") ? itemNode.get("description").asText() : "";
                        t.getSchedules().add(new Tour.ScheduleItem(title, desc));
                    }
                }
            }
        } catch (Exception ignored) {}

        if (t.getSchedules().isEmpty()) {
            t.getSchedules().add(new Tour.ScheduleItem("Ngày 1: Khởi hành - Trải nghiệm điểm đến", "Xe và HDV đón quý khách tại điểm hẹn, di chuyển và nhận phòng khách sạn. Khám phá ẩm thực địa phương."));
            t.getSchedules().add(new Tour.ScheduleItem("Ngày 2: Tham quan danh thắng nổi tiếng", "Hành trình trọn vẹn khám phá các địa danh biểu tượng, check-in và chụp ảnh lưu niệm."));
            t.getSchedules().add(new Tour.ScheduleItem("Ngày 3: Mua sắm đặc sản - Tiễn đoàn", "Tự do mua sắm quà lưu niệm, HDV hỗ trợ thủ tục trả phòng và tiễn đoàn về lại điểm đón ban đầu."));
        }

        return t;
    }

    public List<Tour> getAllTours() {
        List<Tour> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM tours WHERE deleted = 0 ORDER BY position ASC, id DESC";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(mapResultSetToTour(rs));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (list.isEmpty()) {
            list = getDefaultMockTours();
        }
        return list;
    }

    public List<Tour> getTrashTours() {
        List<Tour> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM tours WHERE deleted = 1 ORDER BY id DESC";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(mapResultSetToTour(rs));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (list.isEmpty()) {
            Tour t = new Tour();
            t.setId(99);
            t.setCode("28T00099");
            t.setName("Tour Mộc Châu - Mùa Mận Chín 2N1Đ");
            t.setTime("2 Ngày 1 Đêm");
            t.setAvatar("/assets/images/Background-2.jpg");
            t.setStatus("inactive");
            t.setPriceNewAdult(1450000);
            t.setPosition(99);
            list.add(t);
        }
        return list;
    }

    public List<Tour> getToursSection2(int limit) {
        List<Tour> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM tours WHERE deleted = 0 AND status = 'active' AND price_new_adult > 0 ORDER BY position DESC LIMIT ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, limit);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(mapResultSetToTour(rs));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (list.isEmpty()) {
            list = getDefaultMockTours();
            if (list.size() > limit) list = list.subList(0, limit);
        }
        return list;
    }

    public List<Tour> getToursByCategories(List<Integer> categoryIds, int limit) {
        List<Tour> list = new ArrayList<>();
        if (categoryIds == null || categoryIds.isEmpty()) return list;

        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                StringBuilder inClause = new StringBuilder();
                for (int i = 0; i < categoryIds.size(); i++) {
                    inClause.append("?");
                    if (i < categoryIds.size() - 1) inClause.append(",");
                }
                String sql = "SELECT * FROM tours WHERE deleted = 0 AND status = 'active' AND category_id IN (" + inClause + ") ORDER BY position DESC LIMIT ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                int idx = 1;
                for (int catId : categoryIds) {
                    ps.setInt(idx++, catId);
                }
                ps.setInt(idx, limit);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(mapResultSetToTour(rs));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (list.isEmpty()) {
            for (Tour t : getDefaultMockTours()) {
                if (categoryIds.contains(t.getCategoryId())) {
                    list.add(t);
                    if (list.size() >= limit) break;
                }
            }
            if (list.isEmpty()) {
                list = getDefaultMockTours();
                if (list.size() > limit) list = list.subList(0, limit);
            }
        }
        return list;
    }

    public Tour getTourBySlug(String slug) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM tours WHERE slug = ? AND deleted = 0 AND status = 'active' LIMIT 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, slug);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Tour t = mapResultSetToTour(rs);
                    conn.close();
                    return t;
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Tour t : getDefaultMockTours()) {
            if (slug != null && slug.equalsIgnoreCase(t.getSlug())) return t;
        }
        List<Tour> mocks = getDefaultMockTours();
        return mocks.isEmpty() ? null : mocks.get(0);
    }

    public Tour getTourById(int id) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM tours WHERE id = ? AND deleted = 0 LIMIT 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Tour t = mapResultSetToTour(rs);
                    conn.close();
                    return t;
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Tour t : getDefaultMockTours()) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public List<Tour> searchTours(String locationFrom, String locationTo, String departureDate, Integer stockAdult, Integer stockChildren, Integer stockBaby, String priceRange) {
        List<Tour> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                StringBuilder sql = new StringBuilder("SELECT * FROM tours WHERE deleted = 0 AND status = 'active'");
                List<Object> params = new ArrayList<>();

                if (locationTo != null && !locationTo.trim().isEmpty()) {
                    sql.append(" AND (name LIKE ? OR slug LIKE ?)");
                    params.add("%" + locationTo.trim() + "%");
                    params.add("%" + locationTo.trim() + "%");
                }
                if (locationFrom != null && !locationFrom.trim().isEmpty()) {
                    sql.append(" AND locations LIKE ?");
                    params.add("%" + locationFrom.trim() + "%");
                }
                if (stockAdult != null && stockAdult > 0) {
                    sql.append(" AND stock_adult >= ?");
                    params.add(stockAdult);
                }
                if (stockChildren != null && stockChildren > 0) {
                    sql.append(" AND stock_children >= ?");
                    params.add(stockChildren);
                }
                if (stockBaby != null && stockBaby > 0) {
                    sql.append(" AND stock_baby >= ?");
                    params.add(stockBaby);
                }
                if (priceRange != null && priceRange.contains("-")) {
                    String[] parts = priceRange.split("-");
                    try {
                        double min = Double.parseDouble(parts[0]);
                        double max = Double.parseDouble(parts[1]);
                        sql.append(" AND price_new_adult >= ? AND price_new_adult <= ?");
                        params.add(min);
                        params.add(max);
                    } catch (Exception ignored) {}
                }

                sql.append(" ORDER BY position DESC");
                PreparedStatement ps = conn.prepareStatement(sql.toString());
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(mapResultSetToTour(rs));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (list.isEmpty()) {
            list = getDefaultMockTours();
        }
        return list;
    }

    private List<Tour> getDefaultMockTours() {
        List<Tour> list = new ArrayList<>();

        Tour t1 = new Tour();
        t1.setId(1);
        t1.setCode("28T00001");
        t1.setName("Tour Đà Nẵng - Hội An - Bà Nà Hills 4N3Đ");
        t1.setCategoryId(3);
        t1.setPosition(10);
        t1.setStatus("active");
        t1.setAvatar("/assets/images/Product-1.jpg");
        t1.getImages().add("/assets/images/Product-1.jpg");
        t1.getImages().add("/assets/images/Background-1.jpg");
        t1.getImages().add("/assets/images/Background-2.jpg");
        t1.setPriceAdult(4500000);
        t1.setPriceChildren(3200000);
        t1.setPriceBaby(1000000);
        t1.setPriceNewAdult(3890000);
        t1.setPriceNewChildren(2700000);
        t1.setPriceNewBaby(800000);
        t1.setStockAdult(25);
        t1.setStockChildren(12);
        t1.setStockBaby(6);
        t1.setTime("4 Ngày 3 Đêm");
        t1.setVehicle("Máy bay & Ô tô du lịch");
        t1.setDepartureDateFormatted("20/09/2026");
        t1.setInformation("Khám phá thành phố biển đáng sống nhất Việt Nam, thăm phố cổ Hội An và Cầu Vàng trên đỉnh Bà Nà Hills.");
        t1.setSlug("tour-da-nang-hoi-an-ba-na-hills-4n3d");
        t1.getSchedules().add(new Tour.ScheduleItem("Ngày 1: Đón khách - Bán Đảo Sơn Trà - Biển Mỹ Khê", "Xe và HDV đón quý khách tại sân bay Đà Nẵng, nhận phòng khách sạn và tự do tắm biển."));
        t1.getSchedules().add(new Tour.ScheduleItem("Ngày 2: Bà Nà Hills - Cầu Vàng nổi tiếng", "Tham quan làng Pháp, trải nghiệm cáp treo đạt nhiều kỷ lục thế giới và check-in Cầu Vàng."));
        t1.getSchedules().add(new Tour.ScheduleItem("Ngày 3: Phố Cổ Hội An - Thả đèn hoa đăng", "Khám phá Chùa Cầu, nhà cổ Tấn Ký, thưởng thức đặc sản cao lầu và trải nghiệm thả đèn hoa đăng."));
        list.add(t1);

        Tour t2 = new Tour();
        t2.setId(2);
        t2.setCode("28T00002");
        t2.setName("Tour Hà Giang - Mùa Hoa Tam Giác Mạch 3N2Đ");
        t2.setCategoryId(2);
        t2.setPosition(9);
        t2.setStatus("active");
        t2.setAvatar("/assets/images/Background-2.jpg");
        t2.getImages().add("/assets/images/Background-2.jpg");
        t2.getImages().add("/assets/images/Background-3.jpg");
        t2.setPriceAdult(3200000);
        t2.setPriceChildren(2400000);
        t2.setPriceBaby(800000);
        t2.setPriceNewAdult(2690000);
        t2.setPriceNewChildren(1990000);
        t2.setPriceNewBaby(600000);
        t2.setStockAdult(18);
        t2.setStockChildren(8);
        t2.setStockBaby(4);
        t2.setTime("3 Ngày 2 Đêm");
        t2.setVehicle("Xe ô tô giường nằm VIP");
        t2.setDepartureDateFormatted("05/10/2026");
        t2.setInformation("Chinh phục đỉnh Mã Pì Lèng huyền thoại, ngắm hẻm vực Tu Sản sâu nhất Đông Nam Á.");
        t2.setSlug("tour-ha-giang-mua-hoa-tam-giac-mach-3n2d");
        list.add(t2);

        Tour t3 = new Tour();
        t3.setId(3);
        t3.setCode("28T00003");
        t3.setName("Tour Phú Quốc - Thiên Đường Nghỉ Dưỡng 3N2Đ");
        t3.setCategoryId(4);
        t3.setPosition(8);
        t3.setStatus("active");
        t3.setAvatar("/assets/images/Background-4.jpg");
        t3.getImages().add("/assets/images/Background-4.jpg");
        t3.setPriceAdult(5500000);
        t3.setPriceChildren(4200000);
        t3.setPriceBaby(1200000);
        t3.setPriceNewAdult(4590000);
        t3.setPriceNewChildren(3500000);
        t3.setPriceNewBaby(900000);
        t3.setStockAdult(30);
        t3.setStockChildren(15);
        t3.setStockBaby(8);
        t3.setTime("3 Ngày 2 Đêm");
        t3.setVehicle("Máy bay & Canô");
        t3.setDepartureDateFormatted("25/09/2026");
        t3.setInformation("Tận hưởng bãi biển ngọc ngà, lặn ngắm san hô 4 đảo, cáp treo Hòn Thơm.");
        t3.setSlug("tour-phu-quoc-thien-duong-nghi-duong-3n2d");
        list.add(t3);

        Tour t4 = new Tour();
        t4.setId(4);
        t4.setCode("28T00004");
        t4.setName("Tour Nhật Bản: Tokyo - Núi Phú Sĩ - Kyoto 5N4Đ");
        t4.setCategoryId(6);
        t4.setPosition(7);
        t4.setStatus("active");
        t4.setAvatar("/assets/images/Background-6.jpg");
        t4.getImages().add("/assets/images/Background-6.jpg");
        t4.setPriceAdult(28900000);
        t4.setPriceChildren(22000000);
        t4.setPriceBaby(8000000);
        t4.setPriceNewAdult(25900000);
        t4.setPriceNewChildren(19500000);
        t4.setPriceNewBaby(6500000);
        t4.setStockAdult(15);
        t4.setStockChildren(5);
        t4.setStockBaby(2);
        t4.setTime("5 Ngày 4 Đêm");
        t4.setVehicle("Vietnam Airlines");
        t4.setDepartureDateFormatted("10/11/2026");
        t4.setInformation("Khám phá xứ sở hoa anh đào, núi Phú Sĩ tuyết phủ và đền vàng Kinkaku-ji.");
        t4.setSlug("tour-nhat-ban-tokyo-nui-phu-si-kyoto-5n4d");
        list.add(t4);

        Tour t5 = new Tour();
        t5.setId(5);
        t5.setCode("28T00005");
        t5.setName("Tour Thái Lan: Bangkok - Pattaya Bay 4N3Đ");
        t5.setCategoryId(6);
        t5.setPosition(6);
        t5.setStatus("active");
        t5.setAvatar("/assets/images/Background-7.jpg");
        t5.getImages().add("/assets/images/Background-7.jpg");
        t5.setPriceAdult(7200000);
        t5.setPriceChildren(5800000);
        t5.setPriceBaby(1500000);
        t5.setPriceNewAdult(5990000);
        t5.setPriceNewChildren(4800000);
        t5.setPriceNewBaby(1200000);
        t5.setStockAdult(40);
        t5.setStockChildren(20);
        t5.setStockBaby(10);
        t5.setTime("4 Ngày 3 Đêm");
        t5.setVehicle("Máy bay & Ô tô");
        t5.setDepartureDateFormatted("30/09/2026");
        t5.setInformation("Khám phá xứ sở Chùa Vàng, dạo thuyền sông Chao Phraya, chợ nổi 4 miền.");
        t5.setSlug("tour-thai-lan-bangkok-pattaya-4n3d");
        list.add(t5);

        return list;
    }

    public boolean deleteTour(int id) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE tours SET deleted = 1, deleted_by = 'Phan Thanh Duy', deleted_at = NOW(), updated_at = NOW() WHERE id = ?";
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

    public boolean undoTour(int id) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE tours SET deleted = 0, deleted_by = NULL, deleted_at = NULL, updated_at = NOW() WHERE id = ?";
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

    public boolean deleteDestroyTour(int id) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "DELETE FROM tours WHERE id = ?";
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
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE tours SET status = ? WHERE id = ?";
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

    public boolean createTour(Tour tour) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "INSERT INTO tours (code, name, category_id, position, status, avatar, images, "
                        + "price_adult, price_children, price_baby, price_new_adult, price_new_children, price_new_baby, "
                        + "stock_adult, stock_children, stock_baby, locations, time, vehicle, departure_date, information, schedules, slug, "
                        + "created_by, updated_by, created_at, updated_at, deleted) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Phan Thanh Duy', 'Phan Thanh Duy', NOW(), NOW(), 0)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, tour.getCode());
                ps.setString(2, tour.getName());
                ps.setInt(3, tour.getCategoryId());
                ps.setInt(4, tour.getPosition());
                ps.setString(5, tour.getStatus());
                ps.setString(6, tour.getAvatar() != null ? tour.getAvatar() : "");
                ps.setString(7, tour.getImagesString());
                ps.setDouble(8, tour.getPriceAdult());
                ps.setDouble(9, tour.getPriceChildren());
                ps.setDouble(10, tour.getPriceBaby());
                ps.setDouble(11, tour.getPriceNewAdult());
                ps.setDouble(12, tour.getPriceNewChildren());
                ps.setDouble(13, tour.getPriceNewBaby());
                ps.setInt(14, tour.getStockAdult());
                ps.setInt(15, tour.getStockChildren());
                ps.setInt(16, tour.getStockBaby());
                ps.setString(17, tour.getLocations() != null ? tour.getLocations() : "");
                ps.setString(18, tour.getTime() != null ? tour.getTime() : "");
                ps.setString(19, tour.getVehicle() != null ? tour.getVehicle() : "");
                if (tour.getDepartureDate() != null) {
                    ps.setDate(20, new java.sql.Date(tour.getDepartureDate().getTime()));
                } else {
                    ps.setDate(20, new java.sql.Date(System.currentTimeMillis()));
                }
                ps.setString(21, tour.getInformation() != null ? tour.getInformation() : "");
                ps.setString(22, tour.getSchedulesJson());
                ps.setString(23, tour.getSlug() != null ? tour.getSlug() : "");
                ps.executeUpdate();
                conn.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateTour(Tour tour) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE tours SET name = ?, category_id = ?, position = ?, status = ?, avatar = ?, images = ?, "
                        + "price_adult = ?, price_children = ?, price_baby = ?, price_new_adult = ?, price_new_children = ?, price_new_baby = ?, "
                        + "stock_adult = ?, stock_children = ?, stock_baby = ?, locations = ?, time = ?, vehicle = ?, departure_date = ?, information = ?, schedules = ?, "
                        + "updated_by = 'Phan Thanh Duy', updated_at = NOW() WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, tour.getName());
                ps.setInt(2, tour.getCategoryId());
                ps.setInt(3, tour.getPosition());
                ps.setString(4, tour.getStatus());
                ps.setString(5, tour.getAvatar() != null ? tour.getAvatar() : "");
                ps.setString(6, tour.getImagesString());
                ps.setDouble(7, tour.getPriceAdult());
                ps.setDouble(8, tour.getPriceChildren());
                ps.setDouble(9, tour.getPriceBaby());
                ps.setDouble(10, tour.getPriceNewAdult());
                ps.setDouble(11, tour.getPriceNewChildren());
                ps.setDouble(12, tour.getPriceNewBaby());
                ps.setInt(13, tour.getStockAdult());
                ps.setInt(14, tour.getStockChildren());
                ps.setInt(15, tour.getStockBaby());
                ps.setString(16, tour.getLocations() != null ? tour.getLocations() : "");
                ps.setString(17, tour.getTime() != null ? tour.getTime() : "");
                ps.setString(18, tour.getVehicle() != null ? tour.getVehicle() : "");
                if (tour.getDepartureDate() != null) {
                    ps.setDate(19, new java.sql.Date(tour.getDepartureDate().getTime()));
                } else {
                    ps.setDate(19, new java.sql.Date(System.currentTimeMillis()));
                }
                ps.setString(20, tour.getInformation() != null ? tour.getInformation() : "");
                ps.setString(21, tour.getSchedulesJson());
                ps.setInt(22, tour.getId());
                ps.executeUpdate();
                conn.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
