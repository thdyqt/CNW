package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import util.EnvUtil;

public class DBContext {
    private static final String HOST = EnvUtil.get("DB_HOST", "127.0.0.1");
    private static final String PRIMARY_PORT = EnvUtil.get("DB_PORT", "3307");
    private static final String DB_NAME = EnvUtil.get("DB_NAME", "tour_management");
    private static final String USER = EnvUtil.get("DB_USER", "root");
    private static final String PASSWORD = EnvUtil.get("DB_PASSWORD", "");

    public static Connection getConnection() {
        Connection conn = tryConnect(PRIMARY_PORT);
        if (conn != null) {
            return conn;
        }
        // Fallback sang cổng 3306 nếu cấu hình chính 3307 không kết nối được
        if (!"3306".equals(PRIMARY_PORT)) {
            Connection fallback = tryConnect("3306");
            if (fallback != null) {
                return fallback;
            }
        }
        return null;
    }

    private static Connection tryConnect(String port) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + HOST + ":" + port + "/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh&characterEncoding=UTF-8";
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (Exception e) {
            return null;
        }
    }
}
