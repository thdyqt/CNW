package com.tour.dao;

import com.tour.model.SettingWebsiteInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SettingDAO {
    public SettingWebsiteInfo getSetting() {
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM setting_website_info LIMIT 1";
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new SettingWebsiteInfo(
                            rs.getInt("id"),
                            rs.getString("website_name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getString("logo"),
                            rs.getString("favicon")
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Fallback default setting
        return new SettingWebsiteInfo();
    }

    public boolean updateSetting(SettingWebsiteInfo info) {
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                StringBuilder sql = new StringBuilder("UPDATE setting_website_info SET website_name = ?, phone = ?, email = ?, address = ?");
                if (info.getLogo() != null) {
                    sql.append(", logo = ?");
                }
                if (info.getFavicon() != null) {
                    sql.append(", favicon = ?");
                }
                sql.append(" WHERE id = ?");

                try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                    int idx = 1;
                    ps.setString(idx++, info.getWebsiteName());
                    ps.setString(idx++, info.getPhone());
                    ps.setString(idx++, info.getEmail());
                    ps.setString(idx++, info.getAddress());
                    if (info.getLogo() != null) {
                        ps.setString(idx++, info.getLogo());
                    }
                    if (info.getFavicon() != null) {
                        ps.setString(idx++, info.getFavicon());
                    }
                    ps.setInt(idx, info.getId() > 0 ? info.getId() : 1);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
