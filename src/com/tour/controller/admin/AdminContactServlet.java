package com.tour.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.dao.ContactDAO;
import com.tour.model.Contact;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminContactServlet extends HttpServlet {
    private final ContactDAO contactDAO = new ContactDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Contact> contacts = contactDAO.getAllContacts();
        request.setAttribute("contactList", contacts);
        request.setAttribute("pageTitle", "Thông tin liên hệ");
        request.getRequestDispatcher("/views/admin/pages/contact-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", "success");
        result.put("message", "Thao tác thành công!");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
