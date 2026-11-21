package controller;

import model.BEAN.Breadcrumb;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PolicyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();
        if ("/privacy".equalsIgnoreCase(path) || "/chinh-sach-bao-mat".equalsIgnoreCase(path)) {
            Breadcrumb breadcrumb = new Breadcrumb("Chính Sách Bảo Mật", "https://images.unsplash.com/photo-1563986768609-322da13575f3?auto=format&fit=crop&w=1920&q=80");
            breadcrumb.addItem("/", "Trang Chủ");
            breadcrumb.addItem("/privacy", "Chính Sách Bảo Mật");
            req.setAttribute("breadcrumb", breadcrumb);
            req.setAttribute("pageTitle", "Chính Sách Bảo Mật | DUT Travel");
            req.getRequestDispatcher("/webContent/client/pages/privacy.jsp").forward(req, resp);
        } else {
            Breadcrumb breadcrumb = new Breadcrumb("Điều Khoản Dịch Vụ", "https://images.unsplash.com/photo-1450133064473-71024230f91b?auto=format&fit=crop&w=1920&q=80");
            breadcrumb.addItem("/", "Trang Chủ");
            breadcrumb.addItem("/terms", "Điều Khoản Dịch Vụ");
            req.setAttribute("breadcrumb", breadcrumb);
            req.setAttribute("pageTitle", "Điều Khoản Dịch Vụ | DUT Travel");
            req.getRequestDispatcher("/webContent/client/pages/terms.jsp").forward(req, resp);
        }
    }
}
