package model.DAO;

import model.BEAN.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {
    public List<City> getAllCities() {
        List<City> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM cities ORDER BY id ASC";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new City(rs.getInt("id"), rs.getString("name")));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (list.isEmpty()) {
            list.add(new City(1, "Hà Nội"));
            list.add(new City(2, "Đà Nẵng"));
            list.add(new City(3, "Hồ Chí Minh"));
            list.add(new City(4, "Hải Phòng"));
            list.add(new City(5, "Cần Thơ"));
            list.add(new City(6, "Nha Trang"));
            list.add(new City(7, "Quy Nhơn"));
            list.add(new City(8, "Huế"));
        }
        return list;
    }

    public City getCityById(int id) {
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM cities WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    City c = new City(rs.getInt("id"), rs.getString("name"));
                    conn.close();
                    return c;
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (City c : getAllCities()) {
            if (c.getId() == id) return c;
        }
        return new City(id, "Đà Nẵng");
    }

    public City getCityByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM cities WHERE name LIKE ? LIMIT 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + name.trim() + "%");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    City c = new City(rs.getInt("id"), rs.getString("name"));
                    conn.close();
                    return c;
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (City c : getAllCities()) {
            if (c.getName().equalsIgnoreCase(name.trim())) return c;
        }
        return null;
    }
}

