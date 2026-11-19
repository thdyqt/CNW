package controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class AdminCategoryServlet extends HttpServlet {
    private final CategoryDAO categoryDAO = new CategoryDAO();
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
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        if (path.endsWith("/create")) {
            request.setAttribute("pageTitle", "Tạo danh mục mới");
            request.setAttribute("categoryList", categoryDAO.getCategoryTree());
            request.getRequestDispatcher("/webContent/admin/pages/category-create.jsp").forward(request, response);
            return;
        }

        if (path.contains("/edit/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                Category cat = categoryDAO.getCategoryById(id);
                if (cat == null && !categoryDAO.getAllCategoriesAdmin().isEmpty()) {
                    cat = categoryDAO.getAllCategoriesAdmin().get(0);
                }
                request.setAttribute("category", cat);
            } catch (Exception e) {
                if (!categoryDAO.getAllCategoriesAdmin().isEmpty()) {
                    request.setAttribute("category", categoryDAO.getAllCategoriesAdmin().get(0));
                }
            }
            request.setAttribute("pageTitle", "Chỉnh sửa danh mục");
            request.setAttribute("categoryList", categoryDAO.getCategoryTree());
            request.getRequestDispatcher("/webContent/admin/pages/category-edit.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/trash")) {
            List<Category> trashList = categoryDAO.getTrashCategories();
            String keyword = request.getParameter("keyword");
            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = keyword.toLowerCase().trim();
                List<Category> filteredTrash = new ArrayList<>();
                for (Category c : trashList) {
                    if (c.getName().toLowerCase().contains(kw) || (c.getSlug() != null && c.getSlug().toLowerCase().contains(kw))) {
                        filteredTrash.add(c);
                    }
                }
                trashList = filteredTrash;
            }

            int page = 1;
            String pageStr = request.getParameter("page");
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                try {
                    page = Math.max(1, Integer.parseInt(pageStr.trim()));
                } catch (Exception ignored) {}
            }

            int limit = 10;
            int total = trashList.size();
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

            int fromIndex = total > 0 ? (page - 1) * limit : 0;
            int toIndex = Math.min(fromIndex + limit, total);
            List<Category> pagedTrash = trashList.subList(fromIndex, toIndex);

            request.setAttribute("categoryTrashList", pagedTrash);
            request.setAttribute("pagination", pagination);
            request.setAttribute("pageTitle", "Thùng rác danh mục");
            request.getRequestDispatcher("/webContent/admin/pages/category-trash.jsp").forward(request, response);
            return;
        }

        // Default: /list
        List<Category> categories = categoryDAO.getAllCategoriesAdmin();
        String status = request.getParameter("status");
        String keyword = request.getParameter("keyword");
        String createdBy = request.getParameter("createdBy");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        if (startDateStr != null && !startDateStr.trim().isEmpty()) {
            try {
                startDate = df.parse(startDateStr.trim());
            } catch (Exception ignored) {}
        }
        if (endDateStr != null && !endDateStr.trim().isEmpty()) {
            try {
                Date parsedEnd = df.parse(endDateStr.trim());
                Calendar cal = Calendar.getInstance();
                cal.setTime(parsedEnd);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                endDate = cal.getTime();
            } catch (Exception ignored) {}
        }

        List<Category> filtered = new ArrayList<>();
        for (Category c : categories) {
            boolean match = true;
            if (status != null && !status.isEmpty() && !c.getStatus().equalsIgnoreCase(status)) {
                match = false;
            }
            if (createdBy != null && !createdBy.isEmpty()) {
                boolean userMatch = false;
                Account selectedAdmin = null;
                try {
                    int aId = Integer.parseInt(createdBy.trim());
                    selectedAdmin = accountDAO.getAccountById(aId);
                } catch (Exception ignored) {}
                String adminName = selectedAdmin != null ? selectedAdmin.getFullName() : null;

                if (c.getCreatedBy() != null) {
                    if (c.getCreatedBy().equalsIgnoreCase(createdBy)) {
                        userMatch = true;
                    } else if (adminName != null && c.getCreatedBy().equalsIgnoreCase(adminName)) {
                        userMatch = true;
                    } else if (adminName != null && c.getCreatedBy().contains(adminName)) {
                        userMatch = true;
                    }
                }
                if (!userMatch) match = false;
            }
            if (startDate != null && c.getCreatedAt() != null) {
                if (c.getCreatedAt().before(startDate)) {
                    match = false;
                }
            }
            if (endDate != null && c.getCreatedAt() != null) {
                if (c.getCreatedAt().after(endDate)) {
                    match = false;
                }
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = keyword.toLowerCase().trim();
                String kwSlug = kw.replace(" ", "-");
                boolean nameMatch = c.getName().toLowerCase().contains(kw);
                boolean slugMatch = c.getSlug() != null && c.getSlug().toLowerCase().contains(kwSlug);
                if (!nameMatch && !slugMatch) {
                    match = false;
                }
            }
            if (match) filtered.add(c);
        }

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageStr.trim()));
            } catch (Exception ignored) {}
        }

        int limit = 10;
        int total = filtered.size();
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

        int fromIndex = total > 0 ? (page - 1) * limit : 0;
        int toIndex = Math.min(fromIndex + limit, total);
        List<Category> pagedCategories = filtered.subList(fromIndex, toIndex);

        request.setAttribute("categoryList", pagedCategories);
        request.setAttribute("adminList", accountDAO.getAccounts(null, null, null, null, null, 1, 100));
        request.setAttribute("pagination", pagination);
        request.setAttribute("pageTitle", "Quản lý danh mục");
        request.getRequestDispatcher("/webContent/admin/pages/category-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        // 1. Change Multi (PATCH /category/change-multi)
        if (path.contains("/change-multi")) {
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                if (sb.length() > 0) {
                    JsonNode node = objectMapper.readTree(sb.toString());
                    String option = node.has("option") ? node.get("option").asText() : "";
                    if (node.has("ids") && node.get("ids").isArray()) {
                        for (JsonNode idNode : node.get("ids")) {
                            int id = idNode.asInt();
                            if ("delete".equalsIgnoreCase(option)) {
                                categoryDAO.deleteCategory(id);
                            } else if ("undo".equalsIgnoreCase(option)) {
                                categoryDAO.undoCategory(id);
                            } else if ("active".equalsIgnoreCase(option)) {
                                categoryDAO.changeStatus(id, "active");
                            } else if ("inactive".equalsIgnoreCase(option)) {
                                categoryDAO.changeStatus(id, "inactive");
                            } else if ("delete-destroy".equalsIgnoreCase(option)) {
                                categoryDAO.deleteDestroyCategory(id);
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

        // 2. Soft delete (PATCH /category/delete/{id})
        if (path.contains("/delete/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                categoryDAO.deleteCategory(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "success", "Đã di chuyển danh mục vào thùng rác!");
            return;
        }

        // 3. Restore / Undo (PATCH /category/undo/{id})
        if (path.contains("/undo/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                categoryDAO.undoCategory(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "success", "Khôi phục danh mục thành công!");
            return;
        }

        // 4. Permanent delete (PATCH /category/delete-destroy/{id})
        if (path.contains("/delete-destroy/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                categoryDAO.deleteDestroyCategory(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "success", "Đã xóa vĩnh viễn danh mục!");
            return;
        }

        // 5. Create category (POST /category/create)
        if (path.endsWith("/create") || path.contains("/category/create")) {
            Map<String, String> formFields = new HashMap<>();
            Part avatarPart = null;
            try {
                for (Part part : request.getParts()) {
                    if (part.getSubmittedFileName() == null) {
                        try (InputStream is = part.getInputStream()) {
                            String val = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                            formFields.put(part.getName(), val);
                        }
                    } else {
                        if ("avatar".equals(part.getName())) {
                            avatarPart = part;
                        }
                    }
                }
            } catch (Exception ignored) {}

            String name = getField(request, formFields, "name");
            String parentStr = getField(request, formFields, "parent");
            String positionStr = getField(request, formFields, "position");
            String status = getField(request, formFields, "status");
            String description = getField(request, formFields, "description");
            String avatarText = getField(request, formFields, "avatar");

            Integer parentId = null;
            if (parentStr != null && !parentStr.trim().isEmpty()) {
                try {
                    parentId = Integer.parseInt(parentStr.trim());
                } catch (Exception ignored) {}
            }

            int position = 1;
            if (positionStr != null && !positionStr.trim().isEmpty()) {
                try {
                    position = Integer.parseInt(positionStr.trim());
                } catch (Exception ignored) {}
            } else {
                position = categoryDAO.getNextPosition();
            }

            if (status == null || status.trim().isEmpty()) status = "active";
            if (description == null) description = "";

            String avatar = "/public/assets/images/Background-1.jpg";
            if (avatarPart != null && avatarPart.getSize() > 0 && avatarPart.getSubmittedFileName() != null && !avatarPart.getSubmittedFileName().trim().isEmpty()) {
                String cloudUrl = util.CloudinaryUtil.upload(avatarPart);
                if (cloudUrl != null && !cloudUrl.isEmpty()) {
                    avatar = cloudUrl;
                } else {
                    try {
                        String uploadDir = request.getServletContext().getRealPath("/public/assets/images");
                        File dir = new File(uploadDir);
                        if (!dir.exists()) dir.mkdirs();
                        String fileName = System.currentTimeMillis() + "_" + avatarPart.getSubmittedFileName().replaceAll("\\s+", "_");
                        avatarPart.write(uploadDir + File.separator + fileName);
                        avatar = "/public/assets/images/" + fileName;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (avatarText != null && !avatarText.trim().isEmpty() && !"null".equalsIgnoreCase(avatarText) && !"deleted".equalsIgnoreCase(avatarText)) {
                avatar = avatarText;
            }

            String nowFormatted = formatCurrentDate();
            Category c = new Category(
                0,
                name != null ? name.trim() : "",
                parentId,
                position,
                avatar,
                description,
                status,
                CategoryDAO.toSlug(name),
                false,
                "Phan Thanh Duy",
                nowFormatted,
                "Phan Thanh Duy",
                nowFormatted
            );
            categoryDAO.createCategory(c);

            sendJsonResponse(response, "success", "Tạo danh mục mới thành công!");
            return;
        }

        // 6. Edit category (PATCH /category/edit/{id} or POST)
        if (path.contains("/edit/")) {
            int id = 0;
            try {
                id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
            } catch (Exception ignored) {}

            Map<String, String> formFields = new HashMap<>();
            Part avatarPart = null;
            try {
                for (Part part : request.getParts()) {
                    if (part.getSubmittedFileName() == null) {
                        try (InputStream is = part.getInputStream()) {
                            String val = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                            formFields.put(part.getName(), val);
                        }
                    } else {
                        if ("avatar".equals(part.getName())) {
                            avatarPart = part;
                        }
                    }
                }
            } catch (Exception ignored) {}

            String name = getField(request, formFields, "name");
            String parentStr = getField(request, formFields, "parent");
            String positionStr = getField(request, formFields, "position");
            String status = getField(request, formFields, "status");
            String description = getField(request, formFields, "description");
            String avatarText = getField(request, formFields, "avatar");

            Category existing = categoryDAO.getCategoryById(id);
            if (existing != null) {
                if (name != null && !name.trim().isEmpty()) {
                    existing.setName(name.trim());
                    existing.setSlug(CategoryDAO.toSlug(name));
                }

                if (parentStr != null && !parentStr.trim().isEmpty()) {
                    try {
                        existing.setParentId(Integer.parseInt(parentStr.trim()));
                    } catch (Exception ignored) {
                        existing.setParentId(null);
                    }
                } else {
                    existing.setParentId(null);
                }

                if (positionStr != null && !positionStr.trim().isEmpty()) {
                    try {
                        existing.setPosition(Integer.parseInt(positionStr.trim()));
                    } catch (Exception ignored) {}
                }

                if (status != null && !status.trim().isEmpty()) {
                    existing.setStatus(status.trim());
                }

                if (description != null) {
                    existing.setDescription(description);
                }

                if (avatarPart != null && avatarPart.getSize() > 0 && avatarPart.getSubmittedFileName() != null && !avatarPart.getSubmittedFileName().trim().isEmpty()) {
                    String cloudUrl = util.CloudinaryUtil.upload(avatarPart);
                    if (cloudUrl != null && !cloudUrl.isEmpty()) {
                        existing.setAvatar(cloudUrl);
                    } else {
                        try {
                            String uploadDir = request.getServletContext().getRealPath("/public/assets/images");
                            File dir = new File(uploadDir);
                            if (!dir.exists()) dir.mkdirs();
                            String fileName = System.currentTimeMillis() + "_" + avatarPart.getSubmittedFileName().replaceAll("\\s+", "_");
                            avatarPart.write(uploadDir + File.separator + fileName);
                            existing.setAvatar("/public/assets/images/" + fileName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if ("deleted".equalsIgnoreCase(avatarText)) {
                    existing.setAvatar("");
                }

                existing.setUpdatedBy("Phan Thanh Duy");
                existing.setUpdatedAtFormatted(formatCurrentDate());

                categoryDAO.updateCategory(existing);
            }

            sendJsonResponse(response, "success", "Cập nhật danh mục thành công!");
            return;
        }

        // Fallback for any other POST/PATCH
        sendJsonResponse(response, "success", "Thao tác thành công!");
    }

    private String getField(HttpServletRequest request, Map<String, String> formFields, String name) {
        if (formFields.containsKey(name)) {
            return formFields.get(name);
        }
        return request.getParameter(name);
    }

    private void sendJsonResponse(HttpServletResponse response, String code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    private String formatCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy"));
    }
}