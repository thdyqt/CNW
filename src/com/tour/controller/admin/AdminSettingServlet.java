package com.tour.controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.dao.AccountDAO;
import com.tour.dao.RoleDAO;
import com.tour.dao.SettingDAO;
import com.tour.model.Account;
import com.tour.model.Role;
import com.tour.model.SettingWebsiteInfo;
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
public class AdminSettingServlet extends HttpServlet {
    private final SettingDAO settingDAO = new SettingDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static class PermissionItem {
        private String label;
        private String value;

        public PermissionItem(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() { return label; }
        public String getValue() { return value; }
    }

    private List<PermissionItem> getPermissionList() {
        List<PermissionItem> list = new ArrayList<>();
        // Tổng quan
        list.add(new PermissionItem("Xem trang Tổng quan", "dashboard-view"));
        // Danh mục
        list.add(new PermissionItem("Xem danh mục", "category-view"));
        list.add(new PermissionItem("Tạo danh mục", "category-create"));
        list.add(new PermissionItem("Sửa danh mục", "category-edit"));
        list.add(new PermissionItem("Xóa danh mục", "category-delete"));
        list.add(new PermissionItem("Thùng rác danh mục", "category-trash"));
        // Tour du lịch
        list.add(new PermissionItem("Xem tour", "tour-view"));
        list.add(new PermissionItem("Tạo tour", "tour-create"));
        list.add(new PermissionItem("Sửa tour", "tour-edit"));
        list.add(new PermissionItem("Xóa tour", "tour-delete"));
        list.add(new PermissionItem("Thùng rác tour", "tour-trash"));
        // Đơn hàng
        list.add(new PermissionItem("Xem đơn hàng", "order-view"));
        list.add(new PermissionItem("Sửa đơn hàng", "order-edit"));
        list.add(new PermissionItem("Xóa đơn hàng", "order-delete"));
        list.add(new PermissionItem("Thùng rác đơn hàng", "order-trash"));
        // Người dùng
        list.add(new PermissionItem("Xem người dùng", "user-view"));
        list.add(new PermissionItem("Sửa người dùng", "user-edit"));
        list.add(new PermissionItem("Xóa người dùng", "user-delete"));
        // Thông tin liên hệ
        list.add(new PermissionItem("Xem thông tin liên hệ", "contact-view"));
        list.add(new PermissionItem("Xóa thông tin liên hệ", "contact-delete"));
        list.add(new PermissionItem("Thùng rác liên hệ", "contact-trash"));
        // Cài đặt chung & Website
        list.add(new PermissionItem("Cài đặt chung & Website", "setting-view"));
        list.add(new PermissionItem("Chỉnh sửa thông tin website", "setting-edit"));
        // Nhóm quyền
        list.add(new PermissionItem("Xem nhóm quyền", "role-view"));
        list.add(new PermissionItem("Tạo nhóm quyền", "role-create"));
        list.add(new PermissionItem("Sửa nhóm quyền", "role-edit"));
        list.add(new PermissionItem("Xóa nhóm quyền", "role-delete"));
        list.add(new PermissionItem("Thùng rác nhóm quyền", "role-trash"));
        // Tài khoản quản trị
        list.add(new PermissionItem("Xem tài khoản quản trị", "account-view"));
        list.add(new PermissionItem("Tạo tài khoản quản trị", "account-create"));
        list.add(new PermissionItem("Sửa tài khoản quản trị", "account-edit"));
        list.add(new PermissionItem("Xóa tài khoản quản trị", "account-delete"));
        return list;
    }

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

        if (path.contains("/website-info")) {
            request.setAttribute("settingWebsiteInfo", settingDAO.getSetting());
            request.setAttribute("pageTitle", "Thông tin website");
            request.getRequestDispatcher("/views/admin/pages/website-info.jsp").forward(request, response);
            return;
        }

        if (path.contains("/account-admin/create")) {
            request.setAttribute("roleList", roleDAO.getAllRoles());
            request.setAttribute("pageTitle", "Thêm tài khoản quản trị");
            request.getRequestDispatcher("/views/admin/pages/admin-create.jsp").forward(request, response);
            return;
        }

        if (path.contains("/account-admin/edit/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (Exception ignored) {}
            Account acc = accountDAO.getAccountById(id);
            if (acc == null) {
                acc = new Account();
            }
            request.setAttribute("accountAdmin", acc);
            request.setAttribute("roleList", roleDAO.getAllRoles());
            request.setAttribute("pageTitle", "Chỉnh sửa tài khoản quản trị");
            request.getRequestDispatcher("/views/admin/pages/admin-edit.jsp").forward(request, response);
            return;
        }

        if (path.contains("/account-admin/list")) {
            String status = request.getParameter("status");
            String role = request.getParameter("role");
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
            int total = accountDAO.countAccounts(status, role, startDate, endDate, keyword);
            List<Account> accounts = accountDAO.getAccounts(status, role, startDate, endDate, keyword, page, limit);

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

            request.setAttribute("accountList", accounts);
            request.setAttribute("roleList", roleDAO.getAllRoles());
            request.setAttribute("pagination", pagination);
            request.setAttribute("pageTitle", "Tài khoản quản trị");
            request.getRequestDispatcher("/views/admin/pages/admin-list.jsp").forward(request, response);
            return;
        }

        // Roles: Trash
        if (path.contains("/role/trash")) {
            String keyword = request.getParameter("keyword");
            int page = 1;
            try {
                String pageStr = request.getParameter("page");
                if (pageStr != null && !pageStr.isEmpty()) {
                    page = Integer.parseInt(pageStr);
                }
            } catch (Exception ignored) {}

            int limit = 10;
            int total = roleDAO.countTrashRoles(keyword);
            List<Role> roleTrashList = roleDAO.getTrashRoles(keyword, page, limit);

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

            request.setAttribute("roleTrashList", roleTrashList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("pageTitle", "Thùng rác nhóm quyền");
            request.getRequestDispatcher("/views/admin/pages/role-trash.jsp").forward(request, response);
            return;
        }

        // Roles: Create
        if (path.contains("/role/create")) {
            request.setAttribute("permissionList", getPermissionList());
            request.setAttribute("pageTitle", "Tạo nhóm quyền");
            request.getRequestDispatcher("/views/admin/pages/role-create.jsp").forward(request, response);
            return;
        }

        // Roles: Edit
        if (path.contains("/role/edit/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (Exception ignored) {}
            Role currentRole = roleDAO.getRoleById(id);
            if (currentRole == null) {
                currentRole = new Role();
            }
            request.setAttribute("currentRole", currentRole);
            request.setAttribute("permissionList", getPermissionList());
            request.setAttribute("pageTitle", "Chỉnh sửa nhóm quyền");
            request.getRequestDispatcher("/views/admin/pages/role-edit.jsp").forward(request, response);
            return;
        }

        // Roles: List
        if (path.contains("/role/list")) {
            String keyword = request.getParameter("keyword");
            int page = 1;
            try {
                String pageStr = request.getParameter("page");
                if (pageStr != null && !pageStr.isEmpty()) {
                    page = Integer.parseInt(pageStr);
                }
            } catch (Exception ignored) {}

            int limit = 10;
            int total = roleDAO.countRoles(keyword);
            List<Role> roleList = roleDAO.getRoles(keyword, page, limit);

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

            request.setAttribute("roleList", roleList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("pageTitle", "Nhóm quyền");
            request.getRequestDispatcher("/views/admin/pages/role-list.jsp").forward(request, response);
            return;
        }

        // Default: /list
        request.setAttribute("pageTitle", "Cài đặt chung");
        request.getRequestDispatcher("/views/admin/pages/setting-list.jsp").forward(request, response);
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

        // 1. Website Info
        if (path.contains("/website-info")) {
            SettingWebsiteInfo current = settingDAO.getSetting();
            SettingWebsiteInfo info = new SettingWebsiteInfo();
            info.setId(current != null ? current.getId() : 1);
            info.setWebsiteName(request.getParameter("websiteName"));
            info.setPhone(request.getParameter("phone"));
            info.setEmail(request.getParameter("email"));
            info.setAddress(request.getParameter("address"));

            // Logo upload / keep / delete
            String newLogoUrl = null;
            try {
                Part logoPart = request.getPart("logo");
                if (logoPart != null && logoPart.getSize() > 0 && logoPart.getContentType() != null && logoPart.getContentType().startsWith("image/")) {
                    newLogoUrl = CloudinaryUtil.upload(logoPart);
                }
            } catch (Exception ignored) {}

            String logoParam = request.getParameter("logo");
            if ("deleted".equalsIgnoreCase(logoParam)) {
                newLogoUrl = "";
            } else if (newLogoUrl == null && current != null) {
                newLogoUrl = current.getLogo();
            }
            info.setLogo(newLogoUrl);

            // Favicon upload / keep / delete
            String newFaviconUrl = null;
            try {
                Part faviconPart = request.getPart("favicon");
                if (faviconPart != null && faviconPart.getSize() > 0 && faviconPart.getContentType() != null && faviconPart.getContentType().startsWith("image/")) {
                    newFaviconUrl = CloudinaryUtil.upload(faviconPart);
                }
            } catch (Exception ignored) {}

            String faviconParam = request.getParameter("favicon");
            if ("deleted".equalsIgnoreCase(faviconParam)) {
                newFaviconUrl = "";
            } else if (newFaviconUrl == null && current != null) {
                newFaviconUrl = current.getFavicon();
            }
            info.setFavicon(newFaviconUrl);

            settingDAO.updateSetting(info);
            getServletContext().setAttribute("settingWebsiteInfo", info);

            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Cập nhật thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        // 2. Account Admin: Delete
        if (path.contains("/account-admin/delete/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                accountDAO.deleteAccount(id);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "success");
                result.put("message", "Xóa tài khoản thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 3. Account Admin: Change Multi
        if (path.contains("/account-admin/change-multi")) {
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
                accountDAO.changeMulti(ids, option);
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

        // 4. Account Admin: Create
        if (path.contains("/account-admin/create")) {
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String roleStr = request.getParameter("role");
            String position = request.getParameter("position");
            String status = request.getParameter("status");
            String password = request.getParameter("password");

            Account acc = new Account();
            acc.setFullName(fullName);
            acc.setEmail(email);
            acc.setPhone(phone);
            try {
                if (roleStr != null && !roleStr.isEmpty()) acc.setRoleId(Integer.parseInt(roleStr));
            } catch (Exception ignored) {}
            acc.setPosition(position);
            acc.setStatus(status != null && !status.isEmpty() ? status : "active");

            String avatarUrl = null;
            try {
                Part avatarPart = request.getPart("avatar");
                if (avatarPart != null && avatarPart.getSize() > 0 && avatarPart.getContentType() != null && avatarPart.getContentType().startsWith("image/")) {
                    avatarUrl = CloudinaryUtil.upload(avatarPart);
                }
            } catch (Exception ignored) {}
            if (avatarUrl != null) {
                acc.setAvatar(avatarUrl);
            }

            accountDAO.createAccount(acc, password);

            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Tạo tài khoản quản trị thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        // 5. Account Admin: Edit
        if (path.contains("/account-admin/edit")) {
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

            Account existing = accountDAO.getAccountById(id);
            Account acc = new Account();
            acc.setId(id);
            acc.setFullName(request.getParameter("fullName"));
            acc.setEmail(request.getParameter("email"));
            acc.setPhone(request.getParameter("phone"));
            String roleStr = request.getParameter("role");
            try {
                if (roleStr != null && !roleStr.isEmpty()) acc.setRoleId(Integer.parseInt(roleStr));
            } catch (Exception ignored) {}
            acc.setPosition(request.getParameter("position"));
            acc.setStatus(request.getParameter("status"));

            String avatarUrl = null;
            try {
                Part avatarPart = request.getPart("avatar");
                if (avatarPart != null && avatarPart.getSize() > 0 && avatarPart.getContentType() != null && avatarPart.getContentType().startsWith("image/")) {
                    avatarUrl = CloudinaryUtil.upload(avatarPart);
                }
            } catch (Exception ignored) {}

            String avatarParam = request.getParameter("avatar");
            if ("deleted".equalsIgnoreCase(avatarParam)) {
                acc.setAvatar("");
            } else if (avatarUrl != null) {
                acc.setAvatar(avatarUrl);
            } else if (existing != null) {
                acc.setAvatar(existing.getAvatar());
            }

            String password = request.getParameter("password");
            accountDAO.updateAccount(acc, password);

            if (session != null) {
                Account loggedIn = (Account) session.getAttribute("account");
                if (loggedIn != null && loggedIn.getId() == acc.getId()) {
                    Account updatedAcc = accountDAO.getAccountById(acc.getId());
                    if (updatedAcc != null) {
                        session.setAttribute("account", updatedAcc);
                    }
                }
            }

            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Cập nhật tài khoản quản trị thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        // 6. Role: Delete Destroy
        if (path.contains("/role/delete-destroy/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                roleDAO.deleteDestroyRole(id);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "success");
                result.put("message", "Xóa vĩnh viễn nhóm quyền thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 7. Role: Undo
        if (path.contains("/role/undo/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                roleDAO.undoRole(id);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "success");
                result.put("message", "Khôi phục nhóm quyền thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 8. Role: Soft Delete
        if (path.contains("/role/delete/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                roleDAO.deleteRole(id, actor);
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "success");
                result.put("message", "Xóa nhóm quyền thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 9. Role: Change Multi
        if (path.contains("/role/change-multi")) {
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
                roleDAO.changeMulti(ids, option, actor);
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

        // 10. Role: Create
        if (path.contains("/role/create")) {
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String body = sb.toString();
                Role role = new Role();
                if (body != null && body.trim().startsWith("{")) {
                    JsonNode rootNode = objectMapper.readTree(body);
                    if (rootNode.has("name")) role.setName(rootNode.get("name").asText());
                    if (rootNode.has("description")) role.setDescription(rootNode.get("description").asText());
                    if (rootNode.has("permissions")) {
                        List<String> perms = new ArrayList<>();
                        for (JsonNode pNode : rootNode.get("permissions")) {
                            perms.add(pNode.asText());
                        }
                        role.setPermissions(perms);
                    }
                } else {
                    role.setName(request.getParameter("name"));
                    role.setDescription(request.getParameter("description"));
                    String[] perms = request.getParameterValues("permissions");
                    if (perms != null) {
                        List<String> permList = new ArrayList<>();
                        for (String p : perms) permList.add(p);
                        role.setPermissions(permList);
                    }
                }
                role.setCreatedBy(actor);
                role.setUpdatedBy(actor);
                roleDAO.createRole(role);

                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "success");
                result.put("message", "Tạo nhóm quyền thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 11. Role: Edit
        if (path.contains("/role/edit")) {
            try {
                String idStr = null;
                if (path.contains("/edit/")) {
                    idStr = path.substring(path.lastIndexOf("/") + 1);
                }
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String body = sb.toString();
                Role role = new Role();
                if (body != null && body.trim().startsWith("{")) {
                    JsonNode rootNode = objectMapper.readTree(body);
                    if (rootNode.has("name")) role.setName(rootNode.get("name").asText());
                    if (rootNode.has("description")) role.setDescription(rootNode.get("description").asText());
                    if (rootNode.has("permissions")) {
                        List<String> perms = new ArrayList<>();
                        for (JsonNode pNode : rootNode.get("permissions")) {
                            perms.add(pNode.asText());
                        }
                        role.setPermissions(perms);
                    }
                    if ((idStr == null || idStr.isEmpty()) && rootNode.has("id")) {
                        idStr = rootNode.get("id").asText();
                    }
                } else {
                    role.setName(request.getParameter("name"));
                    role.setDescription(request.getParameter("description"));
                    String[] perms = request.getParameterValues("permissions");
                    if (perms != null) {
                        List<String> permList = new ArrayList<>();
                        for (String p : perms) permList.add(p);
                        role.setPermissions(permList);
                    }
                    if (idStr == null || idStr.isEmpty()) {
                        idStr = request.getParameter("id");
                    }
                }
                int id = 0;
                try {
                    id = Integer.parseInt(idStr);
                } catch (Exception ignored) {}
                role.setId(id);
                role.setUpdatedBy(actor);
                roleDAO.updateRole(role);

                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("code", "success");
                result.put("message", "Cập nhật nhóm quyền thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Default Ajax
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", "success");
        result.put("message", "Cập nhật thành công!");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}