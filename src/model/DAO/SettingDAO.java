package model.DAO;

import model.BEAN.*;
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
                        SettingWebsiteInfo info = new SettingWebsiteInfo(
                            rs.getInt("id"),
                            rs.getString("website_name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getString("logo"),
                            rs.getString("favicon")
                        );
                        try { info.setFacebook(rs.getString("facebook")); } catch (Exception ignored) {}
                        try { info.setInstagram(rs.getString("instagram")); } catch (Exception ignored) {}
                        try { info.setWhatsapp(rs.getString("whatsapp")); } catch (Exception ignored) {}
                        try { info.setGithub(rs.getString("github")); } catch (Exception ignored) {}
                        try { info.setYoutube(rs.getString("youtube")); } catch (Exception ignored) {}
                        try { info.setCopyright(rs.getString("copyright")); } catch (Exception ignored) {}
                        return info;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SettingWebsiteInfo();
    }

    public boolean updateSetting(SettingWebsiteInfo info) {
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                StringBuilder sql = new StringBuilder("UPDATE setting_website_info SET website_name = ?, phone = ?, email = ?, address = ?, facebook = ?, instagram = ?, github = ?, whatsapp = ?, youtube = ?, copyright = ?");
                if (info.getLogo() != null) {
                    sql.append(", logo = ?");
                }
                if (info.getFavicon() != null) {
                    sql.append(", favicon = ?");
                }
                sql.append(" WHERE id = ?");

                try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                    int idx = 1;
                    ps.setString(idx++, info.getWebsiteName() != null ? info.getWebsiteName() : "");
                    ps.setString(idx++, info.getPhone() != null ? info.getPhone() : "");
                    ps.setString(idx++, info.getEmail() != null ? info.getEmail() : "");
                    ps.setString(idx++, info.getAddress() != null ? info.getAddress() : "");
                    ps.setString(idx++, info.getFacebook() != null ? info.getFacebook() : "");
                    ps.setString(idx++, info.getInstagram() != null ? info.getInstagram() : "");
                    ps.setString(idx++, info.getGithub() != null ? info.getGithub() : "");
                    ps.setString(idx++, info.getWhatsapp() != null ? info.getWhatsapp() : "");
                    ps.setString(idx++, info.getYoutube() != null ? info.getYoutube() : "");
                    ps.setString(idx++, info.getCopyright() != null ? info.getCopyright() : "© 2026 DUTTech. All rights reserved.");
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
        return false;
    }
}
