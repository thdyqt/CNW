package com.tour.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.dao.OrderDAO;
import com.tour.model.Order;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminOrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        if (path.contains("/edit/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                Order order = orderDAO.getOrderById(id);
                request.setAttribute("order", order);
            } catch (Exception e) {
                request.setAttribute("order", orderDAO.getAllOrders().get(0));
            }
            request.setAttribute("pageTitle", "Chi tiết đơn hàng");
            request.getRequestDispatcher("/views/admin/pages/order-edit.jsp").forward(request, response);
            return;
        }

        // Default: /list
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orderList", orders);
        request.setAttribute("pageTitle", "Quản lý đơn hàng");
        request.getRequestDispatcher("/views/admin/pages/order-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Cập nhật đơn hàng thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/order/list");
    }
}
