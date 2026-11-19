package com.tour.controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.dao.UserDAO;
import com.tour.model.Account;
import com.tour.model.User;
import com.tour.util.CloudinaryUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class AdminUserServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();
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
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        if (uri.contains("/customer/")) {
            request.setAttribute("pageTitle", "Danh sách khách hàng");
            request.getRequestDispatcher("/views/admin/pages/customer-list.jsp").forward(request, response);
            return;
        }

        if (path.contains("/user/edit/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (Exception ignored) {}
            User user = userDAO.getUserById(id);
            if (user == null) {
                user = new User();
            }
            request.setAttribute("user", user);
            request.setAttribute("pageTitle", "Chỉnh sửa người dùng");
            request.getRequestDispatcher("/views/admin/pages/user-edit.jsp").forward(request, response);
            return;
        }

        // List users
        String status = request.getParameter("status");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String keyword = request.getParameter("keyword");
        int page = 1;
        try {
            String pageStr = request.getParameter("page");
            if (pageStr != null && !pageStr.isEmpty()) {
                page = Integer.parseInt(pageStr);
            }
        } catch (Exception ignored) {}

        int limit = 10;
        int total = userDAO.countUsers(status, startDate, endDate, keyword);
        List<User> userList = userDAO.getUsers(status, startDate, endDate, keyword, page, limit);

        int totalPages = (int) Math.ceil((double) total / limit);
        if (totalPages == 0) totalPages = 1;
        int start = total == 0 ? 0 : (page - 1) * limit + 1;
        int end = Math.min(page * limit, total);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalPages", totalPages);
        pagination.put("total", total);
        pagination.put("start", start);
        pagination.put("end", end);

        request.setAttribute("userList", userList);
        request.setAttribute("pagination", pagination);
        request.setAttribute("pageTitle", "Quản lý người dùng");
        request.getRequestDispatcher("/views/admin/pages/user-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        String actor = "Phan Thanh Duy";
        HttpSession session = request.getSession(false);
        if (session != null) {
            Account loggedIn = (Account) session.getAttribute("account");
            if (loggedIn != null && loggedIn.getFullName() != null && !loggedIn.getFullName().trim().isEmpty()) {
                actor = loggedIn.getFullName();
            }
        }

        // 1. Delete
        if (path.contains("/user/delete/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                userDAO.deleteUser(id, actor);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "success");
                result.put("message", "Xóa người dùng thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 2. Change Multi
        if (path.contains("/user/change-multi")) {
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                JsonNode rootNode = objectMapper.readTree(sb.toString());
                String option = rootNode.has("option") ? rootNode.get("option").asText() : "";
                List<Integer> ids = new ArrayList<>();
                if (rootNode.has("ids")) {
                    for (JsonNode idNode : rootNode.get("ids")) {
                        ids.add(idNode.asInt());
                    }
                }
                userDAO.changeMulti(ids, option, actor);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "success");
                result.put("message", "Thao tác thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 3. Edit User
        if (path.contains("/user/edit")) {
            String idStr = null;
            if (path.contains("/edit/")) {
                idStr = path.substring(path.lastIndexOf("/") + 1);
            }
            if (idStr == null || idStr.isEmpty()) {
                idStr = request.getParameter("id");
            }
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (Exception ignored) {}

            User existing = userDAO.getUserById(id);
            if (existing != null) {
                existing.setFullName(request.getParameter("fullName"));
                existing.setPhone(request.getParameter("phone"));
                existing.setAddress(request.getParameter("address"));
                String status = request.getParameter("status");
                if (status != null && !status.isEmpty()) {
                    existing.setStatus(status);
                }

                String avatarUrl = null;
                try {
                    Part avatarPart = request.getPart("avatar");
                    if (avatarPart != null && avatarPart.getSize() > 0 && avatarPart.getContentType() != null && avatarPart.getContentType().startsWith("image/")) {
                        avatarUrl = CloudinaryUtil.upload(avatarPart);
                    }
                } catch (Exception ignored) {}

                String avatarParam = request.getParameter("avatar");
                if ("deleted".equalsIgnoreCase(avatarParam)) {
                    existing.setAvatar("");
                } else if (avatarUrl != null) {
                    existing.setAvatar(avatarUrl);
                }

                String password = request.getParameter("password");
                existing.setUpdatedBy(actor);
                userDAO.updateUser(existing, password);
            }

            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Cập nhật thông tin người dùng thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        // Default response
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", "success");
        result.put("message", "Thao tác thành công!");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}