package com.tour.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    private static final String HOST = "127.0.0.1";
    private static final String DB_NAME = "tour_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        // Try port 3307 first (XAMPP MySQL / phpMyAdmin port on this environment)
        Connection conn = tryConnect("3307");
        if (conn != null) {
            return conn;
        }
        // Fallback to default port 3306
        return tryConnect("3306");
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
