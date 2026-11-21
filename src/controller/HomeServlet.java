package controller;

import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet {
    private final TourDAO tourDAO = new TourDAO();
    private final NewsDAO newsDAO = new NewsDAO();
    private final CouponDAO couponDAO = new CouponDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<Tour> tourListSection2 = tourDAO.getToursSection2(6);
        List<Tour> tourListSection4 = tourDAO.getToursByCategories(Arrays.asList(1, 2, 3, 4), 8);
        List<Tour> tourListSection6 = tourDAO.getToursByCategories(Arrays.asList(5, 6, 7), 8);

        // Lấy 1 bài viết tiêu điểm và 4 bài viết mới nhất (loại trừ bài tiêu điểm)
        News featuredNews = newsDAO.getFeaturedNews();
        int excludeId = (featuredNews != null) ? featuredNews.getId() : 0;
        List<News> latestNews = newsDAO.getLatestNewsExcluding(excludeId, 4);

        // Lấy danh sách mã giảm giá còn hiệu lực
        List<Coupon> couponList = couponDAO.getActiveCoupons(6);

        req.setAttribute("pageTitle", "Trang chủ - DUT Travel");
        req.setAttribute("tourListSection2", tourListSection2);
        req.setAttribute("tourListSection4", tourListSection4);
        req.setAttribute("tourListSection6", tourListSection6);
        req.setAttribute("featuredNews", featuredNews);
        req.setAttribute("latestNews", latestNews);
        req.setAttribute("couponList", couponList);

        req.getRequestDispatcher("/webContent/client/pages/home.jsp").forward(req, resp);
    }
}
