package com.tour.dao;

import com.tour.model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    public List<Contact> getAllContacts() {
        List<Contact> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM contacts WHERE deleted = 0 ORDER BY id DESC";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Contact c = new Contact();
                    c.setId(rs.getInt("id"));
                    c.setEmail(rs.getString("email"));
                    c.setDeleted(rs.getBoolean("deleted"));
                    c.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(c);
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (list.isEmpty()) {
            list.add(new Contact(1, "khachhang1@gmail.com"));
            list.add(new Contact(2, "khachhang2@gmail.com"));
            list.add(new Contact(3, "khachhang3@gmail.com"));
        }
        return list;
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
}
