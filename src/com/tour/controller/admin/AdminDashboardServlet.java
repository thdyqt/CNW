package com.tour.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.dao.OrderDAO;
import com.tour.model.Order;
import java.io.IOException;
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
        String pathInfo = request.getServletPath();

        List<Order> orders = orderDAO.getAllOrders();
        double totalRevenue = 0;
        for (Order o : orders) {
            totalRevenue += o.getTotal();
        }

        Map<String, Object> overview = new HashMap<>();
        overview.put("totalUser", 1250);
        overview.put("totalAdmin", 5);
        overview.put("totalOrder", orders.size());
        overview.put("totalRevenue", totalRevenue > 0 ? totalRevenue : 348500000.0);

        request.setAttribute("overview", overview);
        request.setAttribute("recentOrders", orders);
        request.setAttribute("pageTitle", "Tổng quan");

        request.getRequestDispatcher("/views/admin/pages/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/revenue-chart")) {
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");

            List<Double> currentMonth = new ArrayList<>();
            List<Double> prevMonth = new ArrayList<>();
            for (int i = 1; i <= 31; i++) {
                currentMonth.add(Math.round((Math.random() * 8000000 + 2000000) / 100000.0) * 100000.0);
                prevMonth.add(Math.round((Math.random() * 6000000 + 1500000) / 100000.0) * 100000.0);
            }
            result.put("dataCurrentMonth", currentMonth);
            result.put("dataPreviousMonth", prevMonth);

            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        doGet(request, response);
    }
}
