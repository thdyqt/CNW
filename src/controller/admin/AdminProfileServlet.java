package controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class AdminProfileServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        if ("PATCH".equalsIgnoreCase(method)) {
            doPost(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        if (uri.contains("/change-password")) {
            request.setAttribute("pageTitle", "Đổi mật khẩu");
            request.getRequestDispatcher("/webContent/admin/pages/profile-change-password.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        int accountId = (acc != null && acc.getId() > 0) ? acc.getId() : 2;
        Account dbAcc = accountDAO.getAccountById(accountId);
        if (dbAcc != null) {
            acc = dbAcc;
            session.setAttribute("account", acc);
        } else if (acc == null) {
            acc = new Account();
            session.setAttribute("account", acc);
        }

        request.setAttribute("currentAccountAdmin", acc);
        request.setAttribute("account", acc);
        request.setAttribute("pageTitle", "Thông tin cá nhân");
        request.getRequestDispatcher("/webContent/admin/pages/profile-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());
        HttpSession session = request.getSession();

        Account acc = (Account) session.getAttribute("account");
        int accountId = (acc != null && acc.getId() > 0) ? acc.getId() : 2;

        if (path.contains("/change-password")) {
            try {
                String password = null;
                if (request.getContentType() != null && request.getContentType().contains("application/json")) {
                    StringBuilder sb = new StringBuilder();
                    java.io.BufferedReader reader = request.getReader();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    if (sb.length() > 0) {
                        com.fasterxml.jackson.databind.JsonNode node = objectMapper.readTree(sb.toString());
                        if (node.has("password")) password = node.get("password").asText();
                    }
                } else {
                    password = request.getParameter("password");
                }

                if (password != null && !password.trim().isEmpty()) {
                    accountDAO.updatePassword(accountId, password.trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Đổi mật khẩu thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        // Profile edit
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        String idStr = null;
        if (path.contains("/edit/")) {
            idStr = path.substring(path.lastIndexOf("/") + 1);
        }
        if (idStr == null || idStr.isEmpty()) {
            idStr = request.getParameter("id");
        }
        if (idStr != null && !idStr.isEmpty()) {
            try {
                accountId = Integer.parseInt(idStr);
            } catch (Exception ignored) {}
        }

        String newAvatarUrl = null;
        try {
            Part avatarPart = request.getPart("avatar");
            if (avatarPart != null && avatarPart.getSize() > 0 && avatarPart.getContentType() != null && avatarPart.getContentType().startsWith("image/")) {
                newAvatarUrl = CloudinaryUtil.upload(avatarPart);
            }
        } catch (Exception ignored) {}

        String avatarParam = request.getParameter("avatar");
        if ("deleted".equalsIgnoreCase(avatarParam)) {
            newAvatarUrl = "";
        }

        accountDAO.updateProfile(accountId, fullName, email, phone, newAvatarUrl);

        Account updatedAcc = accountDAO.getAccountById(accountId);
        if (updatedAcc != null) {
            session.setAttribute("account", updatedAcc);
        }

        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", "success");
        result.put("message", "Cập nhật thành công!");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}