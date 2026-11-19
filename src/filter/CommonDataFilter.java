package filter;

import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonDataFilter implements Filter {
    private final SettingDAO settingDAO = new SettingDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();

        // Bỏ qua tài nguyên tĩnh và tương thích ngược các đường dẫn cũ
        String contextPath = httpRequest.getContextPath();
        String path = uri.startsWith(contextPath) ? uri.substring(contextPath.length()) : uri;

        if (path.startsWith("/public/") || path.startsWith("/assets/") || path.startsWith("/admin/assets/") 
                || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".svg") 
                || path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".jpeg") 
                || path.endsWith(".gif") || path.endsWith(".ico") || path.endsWith(".woff") 
                || path.endsWith(".woff2") || path.endsWith(".ttf")) {
            
            if (path.startsWith("/assets/")) {
                httpRequest.getRequestDispatcher("/public" + path).forward(request, response);
                return;
            }
            if (path.startsWith("/admin/assets/")) {
                httpRequest.getRequestDispatcher("/public" + path).forward(request, response);
                return;
            }
            chain.doFilter(request, response);
            return;
        }
            httpRequest.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            // Biến toàn cục
            httpRequest.setAttribute("pathAdmin", "admin");

            if (httpRequest.getAttribute("settingWebsiteInfo") == null) {
                httpRequest.setAttribute("settingWebsiteInfo", settingDAO.getSetting());
            }
            if (httpRequest.getAttribute("categoryList") == null) {
                httpRequest.setAttribute("categoryList", categoryDAO.getCategoryTree());
            }

            // Thiết lập tài khoản admin cho trang quản trị
            if (uri.contains("/admin")) {
                boolean isAccountRoute = uri.contains("/admin/account") || uri.contains("/admin/login");
                Account sessionAcc = (Account) httpRequest.getSession().getAttribute("account");

                // Nếu chưa có session (ví dụ sau khi mở lại trình duyệt hoặc hết hạn session),
                // kiểm tra cookie "token" (chế độ duy trì đăng nhập / nhớ mật khẩu)
                if (sessionAcc == null || sessionAcc.getId() <= 0) {
                    javax.servlet.http.Cookie[] cookies = httpRequest.getCookies();
                    if (cookies != null) {
                        for (javax.servlet.http.Cookie c : cookies) {
                            if ("token".equals(c.getName()) && c.getValue() != null && !c.getValue().trim().isEmpty()) {
                                Account tokenAcc = accountDAO.getAccountByToken(c.getValue().trim());
                                if (tokenAcc != null && "active".equalsIgnoreCase(tokenAcc.getStatus())) {
                                    httpRequest.getSession().setAttribute("account", tokenAcc);
                                    sessionAcc = tokenAcc;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (!isAccountRoute) {
                    if (sessionAcc == null || sessionAcc.getId() <= 0) {
                        String accept = httpRequest.getHeader("Accept");
                        String contentType = httpRequest.getContentType();
                        boolean isAjax = (accept != null && accept.contains("application/json"))
                                || (contentType != null && contentType.contains("application/json"))
                                || "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With"));
                        if (isAjax) {
                            HttpServletResponse httpResponse = (HttpServletResponse) response;
                            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            httpResponse.setContentType("application/json;charset=UTF-8");
                            httpResponse.getWriter().write("{\"code\":\"error\",\"message\":\"Phiên làm việc đã hết hạn. Vui lòng đăng nhập lại!\"}");
                            return;
                        }
                        ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/admin/account/login");
                        return;
                    }
                }
                if (sessionAcc != null) {
                    httpRequest.setAttribute("currentAccountAdmin", sessionAcc);
                    httpRequest.setAttribute("account", sessionAcc);

                    // Phân quyền truy cập các chức năng quản trị
                    if (!isAccountRoute) {
                        if (uri.endsWith("/admin/dashboard") || uri.endsWith("/admin") || uri.endsWith("/admin/")) {
                            if (!sessionAcc.hasPermission("dashboard-view")) {
                                redirectFirstAccessibleModule(httpRequest, (HttpServletResponse) response, sessionAcc);
                                return;
                            }
                        } else {
                            String requiredPerm = getRequiredPermission(uri);
                            if (requiredPerm != null && !sessionAcc.hasPermission(requiredPerm)) {
                                String accept = httpRequest.getHeader("Accept");
                                String contentType = httpRequest.getContentType();
                                boolean isAjax = (accept != null && accept.contains("application/json"))
                                        || (contentType != null && contentType.contains("application/json"))
                                        || "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With"));
                                if (isAjax) {
                                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    httpResponse.setContentType("application/json;charset=UTF-8");
                                    httpResponse.getWriter().write("{\"code\":\"error\",\"message\":\"Bạn không có quyền thực hiện chức năng này!\"}");
                                    return;
                                }
                                HttpServletResponse httpResponse = (HttpServletResponse) response;
                                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                httpRequest.setAttribute("pageTitle", "Truy cập bị từ chối");
                                httpRequest.getRequestDispatcher("/webContent/admin/pages/error-403.jsp").forward(request, response);
                                return;
                            }
                        }
                    }
                }
            }

        chain.doFilter(request, response);
    }

    private void redirectFirstAccessibleModule(HttpServletRequest req, HttpServletResponse resp, Account acc) throws IOException {
        String ctx = req.getContextPath();
        if (acc.hasPermission("tour-view")) {
            resp.sendRedirect(ctx + "/admin/tour/list");
        } else if (acc.hasPermission("category-view")) {
            resp.sendRedirect(ctx + "/admin/category/list");
        } else if (acc.hasPermission("order-view")) {
            resp.sendRedirect(ctx + "/admin/order/list");
        } else if (acc.hasPermission("user-view")) {
            resp.sendRedirect(ctx + "/admin/user/list");
        } else if (acc.hasPermission("contact-view")) {
            resp.sendRedirect(ctx + "/admin/contact/list");
        } else if (acc.hasPermission("setting-view") || acc.hasPermission("role-view") || acc.hasPermission("account-view")) {
            resp.sendRedirect(ctx + "/admin/setting/list");
        } else {
            resp.sendRedirect(ctx + "/admin/profile/edit");
        }
    }

    private String getRequiredPermission(String uri) {
        if (uri.contains("/admin/account") || uri.contains("/admin/profile") || uri.contains("/admin/login") || uri.contains("/admin/logout")) {
            return null;
        }
        if (uri.contains("/admin/tour")) {
            if (uri.contains("/create")) return "tour-create";
            if (uri.contains("/edit")) return "tour-edit";
            if (uri.contains("/delete") || uri.contains("/change-multi")) return "tour-delete";
            if (uri.contains("/trash")) return "tour-trash";
            return "tour-view";
        }
        if (uri.contains("/admin/category")) {
            if (uri.contains("/create")) return "category-create";
            if (uri.contains("/edit")) return "category-edit";
            if (uri.contains("/delete") || uri.contains("/change-multi")) return "category-delete";
            if (uri.contains("/trash")) return "category-trash";
            return "category-view";
        }
        if (uri.contains("/admin/order")) {
            if (uri.contains("/edit")) return "order-edit";
            if (uri.contains("/delete") || uri.contains("/change-multi")) return "order-delete";
            if (uri.contains("/trash")) return "order-trash";
            return "order-view";
        }
        if (uri.contains("/admin/user") || uri.contains("/admin/customer")) {
            if (uri.contains("/edit")) return "user-edit";
            if (uri.contains("/delete") || uri.contains("/change-multi") || uri.contains("/undo")) return "user-delete";
            if (uri.contains("/trash")) return "user-trash";
            return "user-view";
        }
        if (uri.contains("/admin/contact")) {
            if (uri.contains("/delete") || uri.contains("/change-multi")) return "contact-delete";
            if (uri.contains("/trash")) return "contact-trash";
            return "contact-view";
        }
        if (uri.contains("/admin/setting")) {
            if (uri.contains("/role")) {
                if (uri.contains("/create")) return "role-create";
                if (uri.contains("/edit")) return "role-edit";
                if (uri.contains("/delete") || uri.contains("/change-multi")) return "role-delete";
                if (uri.contains("/trash")) return "role-trash";
                return "role-view";
            }
            if (uri.contains("/account-admin")) {
                if (uri.contains("/create")) return "account-create";
                if (uri.contains("/edit")) return "account-edit";
                if (uri.contains("/delete") || uri.contains("/change-multi")) return "account-delete";
                return "account-view";
            }
            if (uri.contains("/website-info")) {
                return "setting-edit";
            }
            return "setting-view";
        }
        if (uri.endsWith("/admin/dashboard") || uri.endsWith("/admin") || uri.endsWith("/admin/")) {
            return "dashboard-view";
        }
        return null;
    }

    @Override
    public void destroy() {}
}