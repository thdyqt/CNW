package com.tour.dao;

import com.tour.model.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM roles WHERE deleted = 0 ORDER BY id ASC";
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Role r = new Role(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                        );
                        list.add(r);
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
                            return new Role(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description")
                            );
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
