package controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminDashboardServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int currentYear = cal.get(java.util.Calendar.YEAR);
        int currentMonth = cal.get(java.util.Calendar.MONTH) + 1;

        int totalUsers = 0;
        int totalAdmins = 0;
        int totalOrdersThisMonth = 0;
        long totalRevenueThisMonth = 0L;

        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                String sql = "SELECT "
                           + "(SELECT COUNT(*) FROM users WHERE deleted = 0) AS u_count, "
                           + "(SELECT COUNT(*) FROM accounts_admin WHERE deleted = 0) AS a_count, "
                           + "(SELECT COUNT(*) FROM orders WHERE deleted = 0 AND YEAR(created_at) = ? AND MONTH(created_at) = ?) AS o_month_count, "
                           + "(SELECT COALESCE(SUM(total), 0) FROM orders WHERE deleted = 0 AND status != 'cancel' AND YEAR(created_at) = ? AND MONTH(created_at) = ?) AS r_month_sum";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, currentYear);
                    ps.setInt(2, currentMonth);
                    ps.setInt(3, currentYear);
                    ps.setInt(4, currentMonth);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            totalUsers = rs.getInt("u_count");
                            totalAdmins = rs.getInt("a_count");
                            totalOrdersThisMonth = rs.getInt("o_month_count");
                            totalRevenueThisMonth = (long) Math.round(rs.getDouble("r_month_sum"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> overview = new HashMap<>();
        overview.put("totalUser", totalUsers);
        overview.put("totalAdmin", totalAdmins);
        overview.put("totalOrder", totalOrdersThisMonth);
        overview.put("totalRevenue", totalRevenueThisMonth);

        List<Order> recentOrders = orderDAO.getAllOrders();
        request.setAttribute("overview", overview);
        request.setAttribute("recentOrders", recentOrders);
        request.setAttribute("pageTitle", "Tổng quan");

        request.getRequestDispatcher("/webContent/admin/pages/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/revenue-chart")) {
            response.setContentType("application/json;charset=UTF-8");

            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(request.getReader());
            } catch (Exception ignored) {}

            int currentMonth = 11;
            int currentYear = 2026;
            int previousMonth = 10;
            int previousYear = 2026;
            int days = 30;

            if (jsonNode != null) {
                if (jsonNode.has("currentMonth")) currentMonth = jsonNode.get("currentMonth").asInt();
                if (jsonNode.has("currentYear")) currentYear = jsonNode.get("currentYear").asInt();
                if (jsonNode.has("previousMonth")) previousMonth = jsonNode.get("previousMonth").asInt();
                if (jsonNode.has("previousYear")) previousYear = jsonNode.get("previousYear").asInt();
                if (jsonNode.has("arrayDay") && jsonNode.get("arrayDay").isArray()) {
                    days = jsonNode.get("arrayDay").size();
                }
            }

            Map<Integer, Double> curMap = orderDAO.getDailyRevenue(currentYear, currentMonth);
            Map<Integer, Double> prevMap = orderDAO.getDailyRevenue(previousYear, previousMonth);

            List<Double> dataCurrentMonth = new ArrayList<>();
            List<Double> dataPreviousMonth = new ArrayList<>();

            for (int day = 1; day <= days; day++) {
                double curVal = curMap.getOrDefault(day, 0.0);
                double prevVal = prevMap.getOrDefault(day, 0.0);

                dataCurrentMonth.add(curVal);
                dataPreviousMonth.add(prevVal);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("dataCurrentMonth", dataCurrentMonth);
            result.put("dataPreviousMonth", dataPreviousMonth);

            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        doGet(request, response);
    }
}