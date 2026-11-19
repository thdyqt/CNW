package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContactServlet extends HttpServlet {
    private final ContactDAO contactDAO = new ContactDAO();
    private final SettingDAO settingDAO = new SettingDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        SettingWebsiteInfo setting = (SettingWebsiteInfo) req.getAttribute("settingWebsiteInfo");
        if (setting == null) {
            setting = settingDAO.getSetting();
            req.setAttribute("settingWebsiteInfo", setting);
        }

        String brandName = (setting != null && setting.getName() != null && !setting.getName().trim().isEmpty())
                ? setting.getName().trim() : "DUT Travel";

        req.setAttribute("pageTitle", "Liên Hệ Với Chúng Tôi | " + brandName);
        req.setAttribute("pageCurrent", "contact");

        // Breadcrumb setup
        Map<String, Object> breadcrumb = new HashMap<>();
        breadcrumb.put("title", "Liên Hệ Với Chúng Tôi");
        breadcrumb.put("image", "https://images.unsplash.com/photo-1488646953014-85cb44e25828?auto=format&fit=crop&w=1920&q=80");
        
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> itemHome = new HashMap<>();
        itemHome.put("title", "Trang Chủ");
        itemHome.put("link", "/");
        list.add(itemHome);

        Map<String, String> itemContact = new HashMap<>();
        itemContact.put("title", "Liên Hệ");
        itemContact.put("link", "/contact");
        list.add(itemContact);

        breadcrumb.put("list", list);
        req.setAttribute("breadcrumb", breadcrumb);

        req.getRequestDispatcher("/webContent/client/pages/contact.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String contentType = req.getContentType();
        boolean isJson = contentType != null && contentType.toLowerCase().contains("application/json");

        if (isJson) {
            handleJsonPost(req, resp);
        } else {
            handleFormPost(req, resp);
        }
    }

    private void handleJsonPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            
            // Check if this is a full contact inquiry form
            boolean isFullForm = root.has("fullName") || root.has("name") || root.has("message") || root.has("phone");
            
            if (isFullForm) {
                String name = root.has("fullName") ? root.get("fullName").asText().trim() : (root.has("name") ? root.get("name").asText().trim() : "");
                String phone = root.has("phone") ? root.get("phone").asText().trim() : "";
                String email = root.has("email") ? root.get("email").asText().trim() : "";
                String subject = root.has("subject") ? root.get("subject").asText().trim() : "Tư vấn tour";
                String message = root.has("message") ? root.get("message").asText().trim() : "";

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || message.isEmpty()) {
                    respJson.put("code", "error");
                    respJson.put("message", "Vui lòng điền đầy đủ họ tên, số điện thoại, email và nội dung liên hệ!");
                } else {
                    boolean success = contactDAO.createContact(name, phone, email, subject, message);
                    if (success) {
                        respJson.put("code", "success");
                        respJson.put("message", "Cảm ơn bạn đã liên hệ! Đội ngũ tư vấn sẽ phản hồi trong thời gian sớm nhất.");
                    } else {
                        respJson.put("code", "error");
                        respJson.put("message", "Có lỗi xảy ra khi gửi tin nhắn, vui lòng thử lại sau!");
                    }
                }
            } else {
                // Newsletter subscription from footer
                String email = root.has("email") ? root.get("email").asText().trim() : "";

                if (email.isEmpty()) {
                    respJson.put("code", "error");
                    respJson.put("message", "Vui lòng nhập địa chỉ email của bạn!");
                } else if (contactDAO.existEmail(email)) {
                    respJson.put("code", "exist");
                    respJson.put("message", "Email của bạn đã được đăng ký nhận thông tin trước đó rồi!");
                } else {
                    contactDAO.createContact(email);
                    respJson.put("code", "success");
                    respJson.put("message", "Đăng ký nhận thông tin ưu đãi thành công! Cảm ơn bạn đã quan tâm.");
                }
            }
        } catch (Exception e) {
            respJson.put("code", "error");
            respJson.put("message", "Có lỗi xảy ra trong quá trình xử lý, vui lòng thử lại sau!");
        }

        resp.getWriter().write(mapper.writeValueAsString(respJson));
    }

    private void handleFormPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("fullName");
        if (name == null || name.trim().isEmpty()) {
            name = req.getParameter("name");
        }
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");

        name = name != null ? name.trim() : "";
        phone = phone != null ? phone.trim() : "";
        email = email != null ? email.trim() : "";
        subject = (subject != null && !subject.trim().isEmpty()) ? subject.trim() : "Tư vấn tour";
        message = message != null ? message.trim() : "";

        String accept = req.getHeader("Accept");
        boolean wantsJson = accept != null && accept.contains("application/json");

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || message.isEmpty()) {
            if (wantsJson) {
                resp.setContentType("application/json;charset=UTF-8");
                ObjectNode respJson = mapper.createObjectNode();
                respJson.put("code", "error");
                respJson.put("message", "Vui lòng điền đầy đủ các thông tin bắt buộc!");
                resp.getWriter().write(mapper.writeValueAsString(respJson));
                return;
            }
            req.getSession().setAttribute("contactError", "Vui lòng điền đầy đủ các trường thông tin!");
            resp.sendRedirect(req.getContextPath() + "/contact");
            return;
        }

        boolean success = contactDAO.createContact(name, phone, email, subject, message);
        if (wantsJson) {
            resp.setContentType("application/json;charset=UTF-8");
            ObjectNode respJson = mapper.createObjectNode();
            if (success) {
                respJson.put("code", "success");
                respJson.put("message", "Cảm ơn bạn! Yêu cầu liên hệ đã được gửi thành công.");
            } else {
                respJson.put("code", "error");
                respJson.put("message", "Không thể gửi liên hệ, vui lòng thử lại!");
            }
            resp.getWriter().write(mapper.writeValueAsString(respJson));
            return;
        }

        if (success) {
            req.getSession().setAttribute("contactSuccess", "Gửi yêu cầu liên hệ thành công! Chúng tôi sẽ liên lạc với bạn sớm nhất.");
        } else {
            req.getSession().setAttribute("contactError", "Gửi liên hệ thất bại, vui lòng thử lại.");
        }
        resp.sendRedirect(req.getContextPath() + "/contact");
    }
}
