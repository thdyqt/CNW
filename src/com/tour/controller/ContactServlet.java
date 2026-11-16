package com.tour.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tour.dao.ContactDAO;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContactServlet extends HttpServlet {
    private final ContactDAO contactDAO = new ContactDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        ObjectNode respJson = mapper.createObjectNode();

        try {
            JsonNode root = mapper.readTree(sb.toString());
            String email = root.has("email") ? root.get("email").asText().trim() : "";

            if (email.isEmpty()) {
                respJson.put("code", "error");
                respJson.put("message", "Vui lòng nhập email!");
            } else if (contactDAO.existEmail(email)) {
                respJson.put("code", "error");
                respJson.put("message", "Email của bạn đã được đăng ký!");
            } else {
                contactDAO.createContact(email);
                respJson.put("code", "success");
                respJson.put("message", "Đăng ký thành công!");
            }
        } catch (Exception e) {
            respJson.put("code", "error");
            respJson.put("message", "Lỗi xử lý yêu cầu!");
        }

        resp.getWriter().write(mapper.writeValueAsString(respJson));
    }
}
