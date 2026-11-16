package com.tour.filter;

import com.tour.dao.AccountDAO;
import com.tour.dao.CategoryDAO;
import com.tour.dao.SettingDAO;
import com.tour.model.Account;
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

        // Không xử lý tài nguyên tĩnh
        if (!uri.contains("/assets/")) {
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
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
