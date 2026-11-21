package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Error404Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

        String uri = (String) req.getAttribute("javax.servlet.error.request_uri");
        if (uri == null) {
            uri = req.getRequestURI();
        }

        String ctx = req.getContextPath();
        String pathInApp = (ctx != null && uri.startsWith(ctx)) ? uri.substring(ctx.length()) : uri;

        if (pathInApp != null && pathInApp.startsWith("/admin")) {
            req.setAttribute("pageTitle", "404 - Không tìm thấy trang (Admin)");
            req.getRequestDispatcher("/webContent/admin/pages/error-404.jsp").forward(req, resp);
        } else {
            req.setAttribute("pageTitle", "404 - Không tìm thấy trang | DUT Travel");
            req.getRequestDispatcher("/webContent/client/pages/error-404.jsp").forward(req, resp);
        }
    }
}
