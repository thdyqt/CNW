package controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.BEAN.*;
import model.DAO.*;
import util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminCouponServlet extends HttpServlet {
    private final CouponDAO couponDAO = new CouponDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        if ("PATCH".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
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

        Account currentAcc = (Account) request.getSession().getAttribute("account");
        String adminName = (currentAcc != null && currentAcc.getFullName() != null) ? currentAcc.getFullName() : "Phan Thanh Duy";

        // 1. Trang tạo mới mã giảm giá: /admin/coupon/create
        if (path.endsWith("/create")) {
            request.setAttribute("pageTitle", "Tạo mã giảm giá mới");
            request.getRequestDispatcher("/webContent/admin/pages/coupon-create.jsp").forward(request, response);
            return;
        }

        // 2. Trang chỉnh sửa mã giảm giá: /admin/coupon/edit/{id}
        if (path.contains("/edit/")) {
            int id = 0;
            try {
                id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
            } catch (Exception ignored) {}

            Coupon coupon = couponDAO.getCouponById(id);
            if (coupon == null) {
                response.sendRedirect(contextPath + "/admin/coupon/list");
                return;
            }

            request.setAttribute("coupon", coupon);
            request.setAttribute("pageTitle", "Chỉnh sửa mã: " + coupon.getCode());
            request.getRequestDispatcher("/webContent/admin/pages/coupon-edit.jsp").forward(request, response);
            return;
        }

        // 3. Thùng rác mã giảm giá: /admin/coupon/trash
        if (path.endsWith("/trash")) {
            String keyword = request.getParameter("keyword");
            int page = 1;
            String pageStr = request.getParameter("page");
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                try {
                    page = Math.max(1, Integer.parseInt(pageStr.trim()));
                } catch (Exception ignored) {}
            }

            int limit = 10;
            int total = couponDAO.countTrashCoupons(keyword);
            int totalPages = (int) Math.ceil((double) total / limit);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) page = totalPages;

            int start = total > 0 ? (page - 1) * limit + 1 : 0;
            int end = Math.min(page * limit, total);

            List<Coupon> trashList = couponDAO.getTrashCoupons(keyword, page, limit);

            Map<String, Object> pagination = new HashMap<>();
            pagination.put("currentPage", page);
            pagination.put("totalPages", totalPages);
            pagination.put("total", total);
            pagination.put("start", start);
            pagination.put("end", end);

            request.setAttribute("couponTrashList", trashList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("pageTitle", "Thùng rác mã giảm giá");
            request.getRequestDispatcher("/webContent/admin/pages/coupon-trash.jsp").forward(request, response);
            return;
        }

        // 4. Danh sách mã giảm giá mặc định: /admin/coupon hoặc /admin/coupon/list
        String status = request.getParameter("status");
        String keyword = request.getParameter("keyword");

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageStr.trim()));
            } catch (Exception ignored) {}
        }

        int limit = 10;
        int total = couponDAO.countCoupons(status, keyword);
        int totalPages = (int) Math.ceil((double) total / limit);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        int start = total > 0 ? (page - 1) * limit + 1 : 0;
        int end = Math.min(page * limit, total);

        List<Coupon> couponList = couponDAO.getCoupons(status, keyword, page, limit);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalPages", totalPages);
        pagination.put("total", total);
        pagination.put("start", start);
        pagination.put("end", end);

        request.setAttribute("couponList", couponList);
        request.setAttribute("pagination", pagination);
        request.setAttribute("pageTitle", "Quản lý mã giảm giá");
        request.getRequestDispatcher("/webContent/admin/pages/coupon-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        Account currentAcc = (Account) request.getSession().getAttribute("account");
        String adminName = (currentAcc != null && currentAcc.getFullName() != null) ? currentAcc.getFullName() : "Phan Thanh Duy";

        // 1. Thao tác hàng loạt: /admin/coupon/change-multi
        if (path.contains("/change-multi")) {
            handleChangeMulti(request, response, adminName);
            return;
        }

        // 2. Đổi trạng thái: /admin/coupon/change-status/{status}/{id}
        if (path.contains("/change-status/")) {
            String[] parts = path.split("/");
            if (parts.length >= 2) {
                try {
                    int id = Integer.parseInt(parts[parts.length - 1]);
                    String newStatus = parts[parts.length - 2];
                    boolean ok = couponDAO.changeStatus(id, newStatus);
                    sendJsonResponse(response, ok ? "success" : "error",
                            ok ? "Cập nhật trạng thái thành công!" : "Cập nhật trạng thái thất bại!");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sendJsonResponse(response, "error", "Yêu cầu thay đổi trạng thái không hợp lệ!");
            return;
        }

        // 3. Xóa mềm vào thùng rác: /admin/coupon/delete/{id}
        if (path.contains("/delete/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                boolean ok = couponDAO.deleteCoupon(id, adminName);
                sendJsonResponse(response, ok ? "success" : "error",
                        ok ? "Đã chuyển mã giảm giá vào thùng rác!" : "Xóa mã giảm giá thất bại!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "error", "Yêu cầu xóa mã giảm giá không hợp lệ!");
            return;
        }

        // 4. Khôi phục: /admin/coupon/undo/{id}
        if (path.contains("/undo/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                boolean ok = couponDAO.undoCoupon(id);
                sendJsonResponse(response, ok ? "success" : "error",
                        ok ? "Khôi phục mã giảm giá thành công!" : "Khôi phục mã giảm giá thất bại!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "error", "Yêu cầu khôi phục không hợp lệ!");
            return;
        }

        // 5. Xóa vĩnh viễn: /admin/coupon/destroy/{id} hoặc /admin/coupon/delete-destroy/{id}
        if (path.contains("/destroy/") || path.contains("/delete-destroy/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                boolean ok = couponDAO.destroyCoupon(id);
                sendJsonResponse(response, ok ? "success" : "error",
                        ok ? "Đã xóa vĩnh viễn mã giảm giá!" : "Xóa vĩnh viễn thất bại!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "error", "Yêu cầu xóa vĩnh viễn không hợp lệ!");
            return;
        }

        // 6. Xử lý lưu form tạo mới: /admin/coupon/create
        if (path.endsWith("/create")) {
            handleCreatePost(request, response, adminName);
            return;
        }

        // 7. Xử lý lưu form chỉnh sửa: /admin/coupon/edit/{id}
        if (path.contains("/edit/")) {
            handleEditPost(request, response, adminName);
            return;
        }

        response.sendRedirect(contextPath + "/admin/coupon/list");
    }

    private void handleCreatePost(HttpServletRequest request, HttpServletResponse response, String adminName)
            throws IOException, ServletException {
        String code = request.getParameter("code");
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String valueStr = request.getParameter("value");
        String minOrderStr = request.getParameter("minOrderValue");
        String maxDiscountStr = request.getParameter("maxDiscountValue");
        String usageLimitStr = request.getParameter("usageLimit");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String status = request.getParameter("status");

        if (code == null || code.trim().isEmpty() || title == null || title.trim().isEmpty()) {
            request.setAttribute("error", "Mã và tiêu đề mã giảm giá không được để trống!");
            request.getRequestDispatcher("/webContent/admin/pages/coupon-create.jsp").forward(request, response);
            return;
        }

        code = code.trim().toUpperCase();
        if (couponDAO.isCodeExists(code, 0)) {
            request.setAttribute("error", "Mã giảm giá '" + code + "' đã tồn tại trong hệ thống!");
            request.getRequestDispatcher("/webContent/admin/pages/coupon-create.jsp").forward(request, response);
            return;
        }

        Coupon coupon = new Coupon();
        coupon.setCode(code);
        coupon.setTitle(title.trim());
        coupon.setType("fixed".equalsIgnoreCase(type) ? "fixed" : "percentage");

        try {
            coupon.setValue(Double.parseDouble(valueStr.trim()));
        } catch (Exception e) {
            coupon.setValue(0);
        }

        try {
            coupon.setMinOrderValue(minOrderStr != null && !minOrderStr.trim().isEmpty() ? Double.parseDouble(minOrderStr.trim()) : 0);
        } catch (Exception e) {
            coupon.setMinOrderValue(0);
        }

        try {
            coupon.setMaxDiscountValue(maxDiscountStr != null && !maxDiscountStr.trim().isEmpty() ? Double.parseDouble(maxDiscountStr.trim()) : 0);
        } catch (Exception e) {
            coupon.setMaxDiscountValue(0);
        }

        try {
            coupon.setUsageLimit(usageLimitStr != null && !usageLimitStr.trim().isEmpty() ? Integer.parseInt(usageLimitStr.trim()) : 0);
        } catch (Exception e) {
            coupon.setUsageLimit(0);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (startDateStr != null && !startDateStr.trim().isEmpty()) {
            try {
                coupon.setStartDate(sdf.parse(startDateStr.trim()));
            } catch (Exception ignored) {}
        }
        if (endDateStr != null && !endDateStr.trim().isEmpty()) {
            try {
                coupon.setEndDate(sdf.parse(endDateStr.trim()));
            } catch (Exception ignored) {}
        }

        coupon.setStatus("inactive".equalsIgnoreCase(status) ? "inactive" : "active");
        coupon.setCreatedBy(adminName);
        coupon.setUpdatedBy(adminName);

        boolean success = couponDAO.createCoupon(coupon);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/coupon/list");
        } else {
            request.setAttribute("error", "Có lỗi xảy ra khi lưu mã giảm giá!");
            request.getRequestDispatcher("/webContent/admin/pages/coupon-create.jsp").forward(request, response);
        }
    }

    private void handleEditPost(HttpServletRequest request, HttpServletResponse response, String adminName)
            throws IOException, ServletException {
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            String path = request.getRequestURI();
            try {
                id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
            } catch (Exception ignored) {}
        }

        Coupon existing = couponDAO.getCouponById(id);
        if (existing == null) {
            response.sendRedirect(request.getContextPath() + "/admin/coupon/list");
            return;
        }

        String code = request.getParameter("code");
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String valueStr = request.getParameter("value");
        String minOrderStr = request.getParameter("minOrderValue");
        String maxDiscountStr = request.getParameter("maxDiscountValue");
        String usageLimitStr = request.getParameter("usageLimit");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String status = request.getParameter("status");

        if (code == null || code.trim().isEmpty() || title == null || title.trim().isEmpty()) {
            request.setAttribute("error", "Mã và tiêu đề mã giảm giá không được để trống!");
            request.setAttribute("coupon", existing);
            request.getRequestDispatcher("/webContent/admin/pages/coupon-edit.jsp").forward(request, response);
            return;
        }

        code = code.trim().toUpperCase();
        if (couponDAO.isCodeExists(code, id)) {
            request.setAttribute("error", "Mã giảm giá '" + code + "' đã được sử dụng bởi mã khác!");
            request.setAttribute("coupon", existing);
            request.getRequestDispatcher("/webContent/admin/pages/coupon-edit.jsp").forward(request, response);
            return;
        }

        existing.setCode(code);
        existing.setTitle(title.trim());
        existing.setType("fixed".equalsIgnoreCase(type) ? "fixed" : "percentage");

        try {
            existing.setValue(Double.parseDouble(valueStr.trim()));
        } catch (Exception e) {
            existing.setValue(0);
        }

        try {
            existing.setMinOrderValue(minOrderStr != null && !minOrderStr.trim().isEmpty() ? Double.parseDouble(minOrderStr.trim()) : 0);
        } catch (Exception e) {
            existing.setMinOrderValue(0);
        }

        try {
            existing.setMaxDiscountValue(maxDiscountStr != null && !maxDiscountStr.trim().isEmpty() ? Double.parseDouble(maxDiscountStr.trim()) : 0);
        } catch (Exception e) {
            existing.setMaxDiscountValue(0);
        }

        try {
            existing.setUsageLimit(usageLimitStr != null && !usageLimitStr.trim().isEmpty() ? Integer.parseInt(usageLimitStr.trim()) : 0);
        } catch (Exception e) {
            existing.setUsageLimit(0);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (startDateStr != null && !startDateStr.trim().isEmpty()) {
            try {
                existing.setStartDate(sdf.parse(startDateStr.trim()));
            } catch (Exception ignored) {}
        } else {
            existing.setStartDate(null);
        }

        if (endDateStr != null && !endDateStr.trim().isEmpty()) {
            try {
                existing.setEndDate(sdf.parse(endDateStr.trim()));
            } catch (Exception ignored) {}
        } else {
            existing.setEndDate(null);
        }

        existing.setStatus("inactive".equalsIgnoreCase(status) ? "inactive" : "active");
        existing.setUpdatedBy(adminName);

        boolean success = couponDAO.updateCoupon(existing);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/coupon/list");
        } else {
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật mã giảm giá!");
            request.setAttribute("coupon", existing);
            request.getRequestDispatcher("/webContent/admin/pages/coupon-edit.jsp").forward(request, response);
        }
    }

    private void handleChangeMulti(HttpServletRequest request, HttpServletResponse response, String adminName)
            throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        try {
            JsonNode root = objectMapper.readTree(sb.toString());
            String action = root.has("action") ? root.get("action").asText() : "";
            JsonNode idsNode = root.get("ids");

            if (idsNode != null && idsNode.isArray()) {
                for (JsonNode idNode : idsNode) {
                    int id = idNode.asInt();
                    if ("active".equalsIgnoreCase(action) || "inactive".equalsIgnoreCase(action)) {
                        couponDAO.changeStatus(id, action);
                    } else if ("delete".equalsIgnoreCase(action)) {
                        couponDAO.deleteCoupon(id, adminName);
                    } else if ("undo".equalsIgnoreCase(action)) {
                        couponDAO.undoCoupon(id);
                    } else if ("delete-destroy".equalsIgnoreCase(action) || "destroy".equalsIgnoreCase(action)) {
                        couponDAO.destroyCoupon(id);
                    }
                }
            }
            sendJsonResponse(response, "success", "Thực hiện thao tác hàng loạt thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, "error", "Dữ liệu thao tác không hợp lệ!");
        }
    }

    private void sendJsonResponse(HttpServletResponse response, String code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ObjectNode node = objectMapper.createObjectNode();
        node.put("code", code);
        node.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(node));
    }
}
