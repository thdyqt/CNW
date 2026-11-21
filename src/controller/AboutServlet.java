package controller;

import model.BEAN.Breadcrumb;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AboutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Breadcrumb breadcrumb = new Breadcrumb("Về Chúng Tôi - DUT Travel", "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?auto=format&fit=crop&w=1920&q=80");
        breadcrumb.addItem("/", "Trang Chủ");
        breadcrumb.addItem("/about", "Giới Thiệu");

        req.setAttribute("breadcrumb", breadcrumb);
        req.setAttribute("pageTitle", "Giới Thiệu Về DUT Travel | Lịch Sử, Sứ Mệnh & Đội Ngũ");
        req.setAttribute("pageCurrent", "about");
        req.getRequestDispatcher("/webContent/client/pages/about.jsp").forward(req, resp);
    }
}
