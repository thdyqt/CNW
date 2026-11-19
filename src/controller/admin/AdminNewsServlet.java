package controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AdminNewsServlet extends HttpServlet {
    private final NewsDAO newsDAO = new NewsDAO();
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

        Account currentAcc = (Account) request.getSession().getAttribute("account");
        String authorName = (currentAcc != null && currentAcc.getFullName() != null) ? currentAcc.getFullName() : "DUT Travel Editor";

        // 1. Trang tạo mới bài viết: /admin/news/create
        if (path.endsWith("/create")) {
            request.setAttribute("pageTitle", "Tạo bài viết mới");
            request.getRequestDispatcher("/webContent/admin/pages/news-create.jsp").forward(request, response);
            return;
        }

        // 2. Trang chỉnh sửa bài viết: /admin/news/edit/{id}
        if (path.contains("/edit/")) {
            int id = 0;
            try {
                id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
            } catch (Exception ignored) {}

            News article = newsDAO.getNewsById(id);
            if (article == null) {
                response.sendRedirect(contextPath + "/admin/news/list");
                return;
            }

            request.setAttribute("article", article);
            request.setAttribute("pageTitle", "Chỉnh sửa bài viết");
            request.getRequestDispatcher("/webContent/admin/pages/news-edit.jsp").forward(request, response);
            return;
        }

        // 3. Thùng rác bài viết: /admin/news/trash
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
            int total = newsDAO.countTrashNewsAdmin(keyword);
            int totalPages = (int) Math.ceil((double) total / limit);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) page = totalPages;

            int start = total > 0 ? (page - 1) * limit + 1 : 0;
            int end = Math.min(page * limit, total);

            List<News> trashList = newsDAO.getTrashNewsAdmin(keyword, page, limit);

            Map<String, Object> pagination = new HashMap<>();
            pagination.put("currentPage", page);
            pagination.put("totalPages", totalPages);
            pagination.put("total", total);
            pagination.put("start", start);
            pagination.put("end", end);

            request.setAttribute("newsList", trashList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("pageTitle", "Thùng rác bài viết");
            request.getRequestDispatcher("/webContent/admin/pages/news-trash.jsp").forward(request, response);
            return;
        }

        // 4. Xóa bài viết vào thùng rác (hỗ trợ cả GET nếu có gọi): /admin/news/delete/{id}
        if (path.contains("/delete/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                boolean success = newsDAO.deleteNews(id, authorName);
                sendJsonResponse(response, success ? "success" : "error",
                        success ? "Đã chuyển bài viết vào thùng rác!" : "Không thể xóa bài viết!");
                return;
            } catch (Exception e) {
                sendJsonResponse(response, "error", "Yêu cầu xóa bài viết không hợp lệ!");
                return;
            }
        }

        // 5. Khôi phục bài viết (hỗ trợ cả GET): /admin/news/undo/{id}
        if (path.contains("/undo/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                boolean success = newsDAO.undoNews(id);
                sendJsonResponse(response, success ? "success" : "error",
                        success ? "Khôi phục bài viết thành công!" : "Khôi phục thất bại!");
                return;
            } catch (Exception e) {
                sendJsonResponse(response, "error", "Yêu cầu khôi phục không hợp lệ!");
                return;
            }
        }

        // 6. Xóa vĩnh viễn bài viết (hỗ trợ cả GET): /admin/news/destroy/{id} hoặc /admin/news/delete-destroy/{id}
        if (path.contains("/destroy/") || path.contains("/delete-destroy/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                boolean success = newsDAO.destroyNews(id);
                sendJsonResponse(response, success ? "success" : "error",
                        success ? "Đã xóa vĩnh viễn bài viết!" : "Xóa vĩnh viễn thất bại!");
                return;
            } catch (Exception e) {
                sendJsonResponse(response, "error", "Yêu cầu xóa vĩnh viễn không hợp lệ!");
                return;
            }
        }

        // 7. Đổi trạng thái (hỗ trợ cả GET): /admin/news/change-status/{status}/{id}
        if (path.contains("/change-status/")) {
            String[] parts = path.split("/");
            if (parts.length >= 2) {
                try {
                    int id = Integer.parseInt(parts[parts.length - 1]);
                    String newStatus = parts[parts.length - 2];
                    boolean ok = newsDAO.changeStatus(id, newStatus);
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

        // 8. Danh sách bài viết: /admin/news hoặc /admin/news/list
        String status = request.getParameter("status");
        String category = request.getParameter("category");
        String keyword = request.getParameter("keyword");
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageStr.trim()));
            } catch (Exception ignored) {}
        }

        int limit = 10;
        int total = newsDAO.countAllNewsAdmin(status, category, keyword);
        int totalPages = (int) Math.ceil((double) total / limit);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        int start = total > 0 ? (page - 1) * limit + 1 : 0;
        int end = Math.min(page * limit, total);

        List<News> newsList = newsDAO.getAllNewsAdmin(status, category, keyword, page, limit);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalPages", totalPages);
        pagination.put("total", total);
        pagination.put("start", start);
        pagination.put("end", end);

        request.setAttribute("newsList", newsList);
        request.setAttribute("pagination", pagination);
        request.setAttribute("pageTitle", "Quản lý bài viết");
        request.getRequestDispatcher("/webContent/admin/pages/news-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        Account currentAcc = (Account) request.getSession().getAttribute("account");
        String authorName = (currentAcc != null && currentAcc.getFullName() != null) ? currentAcc.getFullName() : "DUT Travel Editor";

        // 1. Thao tác hàng loạt: /admin/news/change-multi (PATCH / POST)
        if (path.contains("/change-multi")) {
            handleChangeMulti(request, response, authorName);
            return;
        }

        // 2. Đổi trạng thái: /admin/news/change-status/{status}/{id} (PATCH / POST)
        if (path.contains("/change-status/")) {
            String[] parts = path.split("/");
            if (parts.length >= 2) {
                try {
                    int id = Integer.parseInt(parts[parts.length - 1]);
                    String newStatus = parts[parts.length - 2];
                    boolean ok = newsDAO.changeStatus(id, newStatus);
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

        // 3. Xóa mềm vào thùng rác: /admin/news/delete/{id} (PATCH / POST)
        if (path.contains("/delete/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                boolean ok = newsDAO.deleteNews(id, authorName);
                sendJsonResponse(response, ok ? "success" : "error",
                        ok ? "Đã chuyển bài viết vào thùng rác!" : "Xóa bài viết thất bại!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "error", "Yêu cầu xóa bài viết không hợp lệ!");
            return;
        }

        // 4. Khôi phục bài viết: /admin/news/undo/{id} (PATCH / POST)
        if (path.contains("/undo/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                boolean ok = newsDAO.undoNews(id);
                sendJsonResponse(response, ok ? "success" : "error",
                        ok ? "Khôi phục bài viết thành công!" : "Khôi phục bài viết thất bại!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "error", "Yêu cầu khôi phục không hợp lệ!");
            return;
        }

        // 5. Xóa vĩnh viễn: /admin/news/destroy/{id} hoặc /delete-destroy/{id} (PATCH / POST)
        if (path.contains("/destroy/") || path.contains("/delete-destroy/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                boolean ok = newsDAO.destroyNews(id);
                sendJsonResponse(response, ok ? "success" : "error",
                        ok ? "Đã xóa vĩnh viễn bài viết!" : "Xóa vĩnh viễn thất bại!");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendJsonResponse(response, "error", "Yêu cầu xóa vĩnh viễn không hợp lệ!");
            return;
        }

        // 6. Chỉnh sửa bài viết: /admin/news/edit/{id} (PATCH / POST)
        if (path.contains("/edit/")) {
            handleEditNews(request, response, authorName, path);
            return;
        }

        // 7. Tạo bài viết mới: /admin/news/create (POST)
        if (path.endsWith("/create") || path.contains("/news/create")) {
            handleCreateNews(request, response, authorName);
            return;
        }

        sendJsonResponse(response, "success", "Thao tác thành công!");
    }

    private void handleCreateNews(HttpServletRequest request, HttpServletResponse response, String authorName)
            throws ServletException, IOException {
        Map<String, String> formFields = extractFields(request);
        String title = formFields.getOrDefault("title", "").trim();
        String category = formFields.getOrDefault("category", "Cẩm nang du lịch").trim();
        String description = formFields.getOrDefault("description", "").trim();
        String content = formFields.getOrDefault("content", "").trim();
        String author = formFields.getOrDefault("author", authorName).trim();
        String status = formFields.getOrDefault("status", "active").trim();
        boolean featured = "1".equals(formFields.get("featured")) || "true".equalsIgnoreCase(formFields.get("featured")) || "on".equalsIgnoreCase(formFields.get("featured"));

        if (title.isEmpty()) {
            sendJsonResponse(response, "error", "Vui lòng nhập tiêu đề bài viết!");
            return;
        }

        // Xử lý ảnh đại diện (FilePond hoặc file tải lên hoặc URL)
        String avatar = "/public/assets/images/Blog-1.jpg";
        Part avatarPart = getFilePart(request, "avatar");
        if (avatarPart != null && avatarPart.getSize() > 0 && avatarPart.getSubmittedFileName() != null && !avatarPart.getSubmittedFileName().trim().isEmpty()) {
            String cloudUrl = util.CloudinaryUtil.upload(avatarPart);
            if (cloudUrl != null && !cloudUrl.isEmpty()) {
                avatar = cloudUrl;
            } else {
                String uploadedUrl = saveUploadedFile(request, avatarPart);
                if (uploadedUrl != null) avatar = uploadedUrl;
            }
        } else {
            String avatarText = formFields.get("avatar");
            if (avatarText != null && !avatarText.trim().isEmpty() && !"null".equalsIgnoreCase(avatarText) && !"deleted".equalsIgnoreCase(avatarText)) {
                avatar = avatarText.trim();
            }
        }

        String slug = formFields.getOrDefault("slug", "").trim();
        if (slug.isEmpty()) {
            slug = toSlug(title);
        }
        if (newsDAO.getNewsBySlug(slug) != null) {
            slug = slug + "-" + System.currentTimeMillis();
        }

        News n = new News();
        n.setTitle(title);
        n.setSlug(slug);
        n.setCategory(category);
        n.setAvatar(avatar);
        n.setDescription(description);
        n.setContent(content);
        n.setAuthor(author.isEmpty() ? authorName : author);
        n.setViews(0);
        n.setFeatured(featured);
        n.setStatus(status);

        boolean success = newsDAO.createNews(n);
        if (success) {
            sendJsonResponse(response, "success", "Tạo bài viết mới thành công!");
        } else {
            sendJsonResponse(response, "error", "Không thể tạo bài viết, vui lòng thử lại!");
        }
    }

    private void handleEditNews(HttpServletRequest request, HttpServletResponse response, String authorName, String path)
            throws ServletException, IOException {
        int id = 0;
        try {
            id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
        } catch (Exception ignored) {}

        News article = newsDAO.getNewsById(id);
        if (article == null) {
            sendJsonResponse(response, "error", "Bài viết không tồn tại!");
            return;
        }

        Map<String, String> formFields = extractFields(request);
        String title = formFields.getOrDefault("title", "").trim();
        String category = formFields.getOrDefault("category", "Cẩm nang du lịch").trim();
        String description = formFields.getOrDefault("description", "").trim();
        String content = formFields.getOrDefault("content", "").trim();
        String author = formFields.getOrDefault("author", authorName).trim();
        String status = formFields.getOrDefault("status", "active").trim();
        boolean featured = "1".equals(formFields.get("featured")) || "true".equalsIgnoreCase(formFields.get("featured")) || "on".equalsIgnoreCase(formFields.get("featured"));

        if (title.isEmpty()) {
            sendJsonResponse(response, "error", "Tiêu đề không được để trống!");
            return;
        }

        Part avatarPart = getFilePart(request, "avatar");
        if (avatarPart != null && avatarPart.getSize() > 0 && avatarPart.getSubmittedFileName() != null && !avatarPart.getSubmittedFileName().trim().isEmpty()) {
            String cloudUrl = util.CloudinaryUtil.upload(avatarPart);
            if (cloudUrl != null && !cloudUrl.isEmpty()) {
                article.setAvatar(cloudUrl);
            } else {
                String uploadedUrl = saveUploadedFile(request, avatarPart);
                if (uploadedUrl != null) article.setAvatar(uploadedUrl);
            }
        } else {
            String avatarText = formFields.get("avatar");
            if ("deleted".equalsIgnoreCase(avatarText)) {
                article.setAvatar("/public/assets/images/Blog-1.jpg");
            } else if (avatarText != null && !avatarText.trim().isEmpty() && !"keep".equalsIgnoreCase(avatarText) && !"null".equalsIgnoreCase(avatarText)) {
                article.setAvatar(avatarText.trim());
            }
            // "keep" hoặc null thì giữ nguyên article.getAvatar()
        }

        String slug = formFields.getOrDefault("slug", "").trim();
        if (slug.isEmpty()) {
            slug = toSlug(title);
        }

        article.setTitle(title);
        article.setSlug(slug);
        article.setCategory(category);
        article.setDescription(description);
        article.setContent(content);
        article.setAuthor(author.isEmpty() ? authorName : author);
        article.setFeatured(featured);
        article.setStatus(status);

        boolean success = newsDAO.updateNews(article);
        if (success) {
            sendJsonResponse(response, "success", "Cập nhật bài viết thành công!");
        } else {
            sendJsonResponse(response, "error", "Cập nhật thất bại!");
        }
    }

    private void handleChangeMulti(HttpServletRequest request, HttpServletResponse response, String adminName)
            throws IOException {
        String option = "";
        List<String> idList = new ArrayList<>();

        // 1. Thử đọc JSON body (do script.js fetch gửi Content-Type: application/json)
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            if (sb.length() > 0) {
                JsonNode node = objectMapper.readTree(sb.toString());
                if (node.has("option")) {
                    option = node.get("option").asText();
                } else if (node.has("type")) {
                    option = node.get("type").asText();
                }
                if (node.has("ids") && node.get("ids").isArray()) {
                    for (JsonNode idNode : node.get("ids")) {
                        idList.add(idNode.asText());
                    }
                }
            }
        } catch (Exception ignored) {}

        // 2. Fallback nếu gửi qua tham số URL hoặc Form-Data thông thường
        if (option.isEmpty()) {
            option = request.getParameter("option") != null ? request.getParameter("option") : request.getParameter("type");
        }
        if (idList.isEmpty() && request.getParameter("ids") != null) {
            for (String part : request.getParameter("ids").split(",")) {
                if (!part.trim().isEmpty()) {
                    idList.add(part.trim());
                }
            }
        }

        int count = 0;
        if (option != null && !option.isEmpty() && !idList.isEmpty()) {
            for (String idStr : idList) {
                try {
                    int id = Integer.parseInt(idStr.trim());
                    if ("delete".equalsIgnoreCase(option)) {
                        if (newsDAO.deleteNews(id, adminName)) count++;
                    } else if ("active".equalsIgnoreCase(option) || "inactive".equalsIgnoreCase(option)) {
                        if (newsDAO.changeStatus(id, option.toLowerCase())) count++;
                    } else if ("undo".equalsIgnoreCase(option)) {
                        if (newsDAO.undoNews(id)) count++;
                    } else if ("destroy".equalsIgnoreCase(option) || "delete-destroy".equalsIgnoreCase(option)) {
                        if (newsDAO.destroyNews(id)) count++;
                    }
                } catch (Exception ignored) {}
            }
        }

        if (count > 0) {
            sendJsonResponse(response, "success", "Đã áp dụng thay đổi thành công!");
        } else {
            sendJsonResponse(response, "error", "Không thể cập nhật bài viết!");
        }
    }

    private Map<String, String> extractFields(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String contentType = request.getContentType();
        if (contentType != null && contentType.toLowerCase().startsWith("multipart/form-data")) {
            try {
                for (Part part : request.getParts()) {
                    if (part.getSubmittedFileName() == null) {
                        try (InputStream is = part.getInputStream()) {
                            String val = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                            map.put(part.getName(), val);
                        }
                    }
                }
            } catch (Exception ignored) {}
        } else {
            for (String name : request.getParameterMap().keySet()) {
                map.put(name, request.getParameter(name));
            }
        }
        return map;
    }

    private Part getFilePart(HttpServletRequest request, String name) {
        try {
            return request.getPart(name);
        } catch (Exception ignored) {
            return null;
        }
    }

    private String saveUploadedFile(HttpServletRequest request, Part part) {
        try {
            String origName = part.getSubmittedFileName();
            if (origName == null || origName.trim().isEmpty()) return null;
            String fileName = System.currentTimeMillis() + "_" + origName.replaceAll("\\s+", "-");
            String uploadDir = request.getServletContext().getRealPath("/public/assets/images");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            part.write(uploadDir + File.separator + fileName);
            return "/public/assets/images/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendJsonResponse(HttpServletResponse response, String code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ObjectNode node = objectMapper.createObjectNode();
        node.put("code", code);
        node.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(node));
    }

    private String toSlug(String input) {
        if (input == null) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noAccent = pattern.matcher(normalized).replaceAll("").toLowerCase();
        noAccent = noAccent.replace("đ", "d").replace("Đ", "d");
        noAccent = noAccent.replaceAll("[^a-z0-9\\s-]", "");
        noAccent = noAccent.trim().replaceAll("\\s+", "-");
        noAccent = noAccent.replaceAll("-+", "-");
        return noAccent;
    }
}
