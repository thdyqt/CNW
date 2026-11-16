package com.tour.controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("/customer/")) {
            request.setAttribute("pageTitle", "Danh sách khách hàng");
            request.getRequestDispatcher("/views/admin/pages/customer-list.jsp").forward(request, response);
            return;
        }

        request.setAttribute("pageTitle", "Quản lý người dùng");
        request.getRequestDispatcher("/views/admin/pages/user-list.jsp").forward(request, response);
    }
}
