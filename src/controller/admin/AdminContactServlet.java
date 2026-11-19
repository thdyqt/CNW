package controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.BufferedReader;
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
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        // 1. Contact Trash Page
        if (path.contains("/trash")) {
            String keyword = request.getParameter("keyword");
            int page = 1;
            String pageStr = request.getParameter("page");
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                try {
                    page = Math.max(1, Integer.parseInt(pageStr.trim()));
                } catch (Exception ignored) {}
            }

            int limit = 10;
            int total = contactDAO.countTrashContacts(keyword);
            int totalPages = (int) Math.ceil((double) total / limit);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) page = totalPages;

            int start = total > 0 ? (page - 1) * limit + 1 : 0;
            int end = Math.min(page * limit, total);

            Map<String, Object> pagination = new HashMap<>();
            pagination.put("currentPage", page);
            pagination.put("totalPages", totalPages);
            pagination.put("total", total);
            pagination.put("start", start);
            pagination.put("end", end);

            List<Contact> trashList = contactDAO.getTrashContacts(keyword, page, limit);

            request.setAttribute("contactTrashList", trashList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("pageTitle", "Thùng rác liên hệ");
            request.getRequestDispatcher("/webContent/admin/pages/contact-trash.jsp").forward(request, response);
            return;
        }

        // 2. Contact List Page
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String keyword = request.getParameter("keyword");

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageStr.trim()));
            } catch (Exception ignored) {}
        }

        int limit = 10;
        int total = contactDAO.countContacts(startDate, endDate, keyword);
        int totalPages = (int) Math.ceil((double) total / limit);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        int start = total > 0 ? (page - 1) * limit + 1 : 0;
        int end = Math.min(page * limit, total);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalPages", totalPages);
        pagination.put("total", total);
        pagination.put("start", start);
        pagination.put("end", end);

        List<Contact> contacts = contactDAO.getContacts(startDate, endDate, keyword, page, limit);

        request.setAttribute("contactList", contacts);
        request.setAttribute("pagination", pagination);
        request.setAttribute("pageTitle", "Thông tin liên hệ");
        request.getRequestDispatcher("/webContent/admin/pages/contact-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        // 1. Change Multi (PATCH /admin/contact/change-multi)
        if (path.contains("/change-multi")) {
            try {
                StringBuilder sb = new StringBuilder();
                String line;
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                if (sb.length() > 0) {
                    JsonNode node = objectMapper.readTree(sb.toString());
                    String option = node.has("option") ? node.get("option").asText() : "";
                    if (node.has("ids") && node.get("ids").isArray()) {
                        String currentAdminName = "Phan Thanh Duy";
                        Account sessionAcc = (Account) request.getSession().getAttribute("account");
                        if (sessionAcc != null && sessionAcc.getFullName() != null) {
                            currentAdminName = sessionAcc.getFullName();
                        }
                        for (JsonNode idNode : node.get("ids")) {
                            int id = idNode.asInt();
                            if ("delete".equalsIgnoreCase(option)) {
                                contactDAO.deleteContact(id, currentAdminName);
                            } else if ("undo".equalsIgnoreCase(option)) {
                                contactDAO.undoContact(id);
                            } else if ("delete-destroy".equalsIgnoreCase(option)) {
                                contactDAO.deleteDestroyContact(id);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "success", "Thao tác thành công!");
            return;
        }

        // 2. Undo / Restore (PATCH /admin/contact/undo/{id})
        if (path.contains("/undo/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                boolean success = contactDAO.undoContact(id);
                sendJsonResponse(response, success ? "success" : "error", success ? "Khôi phục thông tin liên hệ thành công!" : "Khôi phục thất bại!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                sendJsonResponse(response, "error", "Có lỗi xảy ra khi khôi phục thông tin liên hệ!");
                return;
            }
        }

        // 3. Permanent delete (PATCH /admin/contact/delete-destroy/{id})
        if (path.contains("/delete-destroy/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                boolean success = contactDAO.deleteDestroyContact(id);
                sendJsonResponse(response, success ? "success" : "error", success ? "Đã xóa vĩnh viễn thông tin liên hệ!" : "Xóa vĩnh viễn thất bại!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                sendJsonResponse(response, "error", "Có lỗi xảy ra khi xóa vĩnh viễn thông tin liên hệ!");
                return;
            }
        }

        // 4. Soft Delete (PATCH /admin/contact/delete/{id})
        if (path.contains("/contact/delete/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                String currentAdminName = "Phan Thanh Duy";
                Account sessionAcc = (Account) request.getSession().getAttribute("account");
                if (sessionAcc != null && sessionAcc.getFullName() != null) {
                    currentAdminName = sessionAcc.getFullName();
                }
                boolean success = contactDAO.deleteContact(id, currentAdminName);
                sendJsonResponse(response, success ? "success" : "error", success ? "Xóa thông tin liên hệ thành công!" : "Xóa thất bại!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                sendJsonResponse(response, "error", "Có lỗi xảy ra khi xóa thông tin liên hệ!");
                return;
            }
        }

        sendJsonResponse(response, "success", "Thao tác thành công!");
    }

    private void sendJsonResponse(HttpServletResponse response, String code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}