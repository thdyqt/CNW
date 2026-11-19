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

public class AdminOrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
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
            int total = orderDAO.countTrashOrders(keyword);
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

            List<Order> trashList = orderDAO.getTrashOrders(keyword, page, limit);

            request.setAttribute("orderTrashList", trashList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("pageTitle", "Thùng rác đơn hàng");
            request.getRequestDispatcher("/webContent/admin/pages/order-trash.jsp").forward(request, response);
            return;
        }

        if (path.contains("/edit/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                Order order = orderDAO.getOrderById(id);
                if (order != null) {
                    request.setAttribute("order", order);
                    request.setAttribute("currentOrder", order);
                    request.setAttribute("pageTitle", "Đơn hàng: " + order.getCode());
                } else {
                    response.sendRedirect(contextPath + "/admin/order/list");
                    return;
                }
            } catch (Exception e) {
                response.sendRedirect(contextPath + "/admin/order/list");
                return;
            }
            request.getRequestDispatcher("/webContent/admin/pages/order-edit.jsp").forward(request, response);
            return;
        }

        // List orders with filtering and pagination
        String status = request.getParameter("status");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String paymentMethod = request.getParameter("paymentMethod");
        String paymentStatus = request.getParameter("paymentStatus");
        String keyword = request.getParameter("keyword");

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageStr.trim()));
            } catch (Exception ignored) {}
        }

        int limit = 10;
        int total = orderDAO.countOrders(status, startDate, endDate, paymentMethod, paymentStatus, keyword);
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

        List<Order> orders = orderDAO.getOrders(status, startDate, endDate, paymentMethod, paymentStatus, keyword, page, limit);

        request.setAttribute("orderList", orders);
        request.setAttribute("pagination", pagination);
        request.setAttribute("pageTitle", "Quản lý đơn hàng");
        request.getRequestDispatcher("/webContent/admin/pages/order-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        String contentType = request.getContentType();
        String accept = request.getHeader("Accept");
        boolean isJson = (accept != null && accept.contains("application/json"))
                || (contentType != null && contentType.contains("application/json"));

        // 1. Change Multi (PATCH /admin/order/change-multi)
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
                                orderDAO.deleteOrder(id, currentAdminName);
                            } else if ("undo".equalsIgnoreCase(option)) {
                                orderDAO.undoOrder(id);
                            } else if ("delete-destroy".equalsIgnoreCase(option)) {
                                orderDAO.deleteDestroyOrder(id);
                            } else if ("initial".equalsIgnoreCase(option) || "processing".equalsIgnoreCase(option)
                                    || "success".equalsIgnoreCase(option) || "cancel".equalsIgnoreCase(option)) {
                                orderDAO.updateStatus(id, option.toLowerCase());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Thao tác thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        // 2. Undo / Restore (PATCH /admin/order/undo/{id})
        if (path.contains("/undo/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                boolean success = orderDAO.undoOrder(id);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", success ? "success" : "error");
                result.put("message", success ? "Khôi phục đơn hàng thành công!" : "Khôi phục đơn hàng thất bại!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "error");
                result.put("message", "Có lỗi xảy ra khi khôi phục đơn hàng!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            }
        }

        // 3. Permanent delete (PATCH /admin/order/delete-destroy/{id})
        if (path.contains("/delete-destroy/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                boolean success = orderDAO.deleteDestroyOrder(id);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", success ? "success" : "error");
                result.put("message", success ? "Đã xóa vĩnh viễn đơn hàng!" : "Xóa vĩnh viễn đơn hàng thất bại!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "error");
                result.put("message", "Có lỗi xảy ra khi xóa vĩnh viễn đơn hàng!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            }
        }

        // 4. Soft Delete Order (PATCH /admin/order/delete/{id})
        if (path.contains("/order/delete/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                String currentAdminName = "Phan Thanh Duy";
                Account sessionAcc = (Account) request.getSession().getAttribute("account");
                if (sessionAcc != null && sessionAcc.getFullName() != null) {
                    currentAdminName = sessionAcc.getFullName();
                }
                boolean success = orderDAO.deleteOrder(id, currentAdminName);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", success ? "success" : "error");
                result.put("message", success ? "Xóa đơn hàng thành công!" : "Xóa đơn hàng thất bại!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "error");
                result.put("message", "Có lỗi xảy ra khi xóa đơn hàng!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            }
        }

        // 5. Edit Order (PATCH/POST /admin/order/edit/{id})
        if (path.contains("/order/edit/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (Exception ignored) {}

            String fullName = null;
            String phone = null;
            String note = null;
            String paymentMethod = null;
            String paymentStatus = null;
            String status = null;

            if (contentType != null && contentType.contains("application/json")) {
                try {
                    JsonNode node = objectMapper.readTree(request.getReader());
                    if (node != null) {
                        if (node.has("fullName")) fullName = node.get("fullName").asText();
                        if (node.has("phone")) phone = node.get("phone").asText();
                        if (node.has("note")) note = node.get("note").asText();
                        if (node.has("paymentMethod")) paymentMethod = node.get("paymentMethod").asText();
                        if (node.has("paymentStatus")) paymentStatus = node.get("paymentStatus").asText();
                        if (node.has("status")) status = node.get("status").asText();
                    }
                } catch (Exception ignored) {}
            } else {
                fullName = request.getParameter("fullName");
                phone = request.getParameter("phone");
                note = request.getParameter("note");
                paymentMethod = request.getParameter("paymentMethod");
                paymentStatus = request.getParameter("paymentStatus");
                status = request.getParameter("status");
            }

            Order order = orderDAO.getOrderById(id);
            if (order != null) {
                if (fullName != null) order.setFullName(fullName.trim());
                if (phone != null) order.setPhone(phone.trim());
                if (note != null) order.setNote(note.trim());
                if (paymentMethod != null) order.setPaymentMethod(paymentMethod.trim());
                if (paymentStatus != null) order.setPaymentStatus(paymentStatus.trim());
                if (status != null) order.setStatus(status.trim());
                orderDAO.updateOrder(order);
            }

            if (isJson) {
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "success");
                result.put("message", "Cập nhật đơn hàng thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            }

            response.sendRedirect(contextPath + "/admin/order/list");
            return;
        }

        response.sendRedirect(contextPath + "/admin/order/list");
    }
}