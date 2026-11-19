package controller;

import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewsServlet extends HttpServlet {
    private final NewsDAO newsDAO = new NewsDAO();
    private final SettingDAO settingDAO = new SettingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.startsWith(contextPath) ? uri.substring(contextPath.length()) : uri;

        SettingWebsiteInfo setting = (SettingWebsiteInfo) req.getAttribute("settingWebsiteInfo");
        if (setting == null) {
            setting = settingDAO.getSetting();
            req.setAttribute("settingWebsiteInfo", setting);
        }
        String brandName = (setting != null && setting.getName() != null && !setting.getName().trim().isEmpty())
                ? setting.getName().trim() : "DUT Travel";

        req.setAttribute("pageCurrent", "news");

        // 1. Chi tiết bài viết: /news/detail/{slug}
        if (path.startsWith("/news/detail/") || path.startsWith("/tin-tuc/detail/")) {
            String slug = path.substring(path.lastIndexOf("/") + 1);
            if (slug.isEmpty()) {
                resp.sendRedirect(contextPath + "/news");
                return;
            }

            News article = newsDAO.getNewsBySlug(slug);
            if (article == null) {
                resp.sendRedirect(contextPath + "/news");
                return;
            }

            // Tăng lượt xem
            newsDAO.incrementViews(article.getId());
            article.setViews(article.getViews() + 1);

            req.setAttribute("article", article);
            req.setAttribute("pageTitle", article.getTitle() + " | " + brandName);

            // Bài viết liên quan & bài viết nổi bật
            List<News> relatedNews = newsDAO.getRelatedNews(article.getCategory(), article.getId(), 3);
            List<News> topViewsNews = newsDAO.getTopViewsNews(5);
            req.setAttribute("relatedNews", relatedNews);
            req.setAttribute("topViewsNews", topViewsNews);

            // Breadcrumb
            Map<String, Object> breadcrumb = new HashMap<>();
            breadcrumb.put("title", article.getTitle());
            breadcrumb.put("image", article.getAvatar().startsWith("/") ? req.getContextPath() + article.getAvatar() : article.getAvatar());
            List<Map<String, String>> list = new ArrayList<>();
            Map<String, String> itemHome = new HashMap<>();
            itemHome.put("title", "Trang Chủ");
            itemHome.put("link", "/");
            list.add(itemHome);
            Map<String, String> itemNews = new HashMap<>();
            itemNews.put("title", "Tin Tức");
            itemNews.put("link", "/news");
            list.add(itemNews);
            breadcrumb.put("list", list);
            req.setAttribute("breadcrumb", breadcrumb);

            req.getRequestDispatcher("/webContent/client/pages/news-detail.jsp").forward(req, resp);
            return;
        }

        // 2. Danh sách bài viết: /news
        String category = req.getParameter("category");
        String keyword = req.getParameter("keyword");
        int page = 1;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageStr.trim()));
            } catch (Exception ignored) {}
        }

        int limit = 6;
        int total = newsDAO.countNews(category, keyword);
        int totalPages = (int) Math.ceil((double) total / limit);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        List<News> newsList = newsDAO.getNewsList(category, keyword, page, limit);
        News featuredNews = newsDAO.getFeaturedNews();
        List<News> topViewsNews = newsDAO.getTopViewsNews(5);

        req.setAttribute("newsList", newsList);
        req.setAttribute("featuredNews", featuredNews);
        req.setAttribute("topViewsNews", topViewsNews);
        req.setAttribute("newsCategoryCurrent", category);
        req.setAttribute("keyword", keyword);

        // Phân trang
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalPages", totalPages);
        pagination.put("total", total);
        req.setAttribute("pagination", pagination);

        req.setAttribute("pageTitle", "Cẩm Nang Du Lịch & Tin Tức Mới Nhất | " + brandName);

        // Breadcrumb
        Map<String, Object> breadcrumb = new HashMap<>();
        breadcrumb.put("title", "Cẩm Nang & Tin Tức Du Lịch");
        breadcrumb.put("image", "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?auto=format&fit=crop&w=1920&q=80");
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> itemHome = new HashMap<>();
        itemHome.put("title", "Trang Chủ");
        itemHome.put("link", "/");
        list.add(itemHome);
        Map<String, String> itemNews = new HashMap<>();
        itemNews.put("title", "Tin Tức");
        itemNews.put("link", "/news");
        list.add(itemNews);
        breadcrumb.put("list", list);
        req.setAttribute("breadcrumb", breadcrumb);

        req.getRequestDispatcher("/webContent/client/pages/news.jsp").forward(req, resp);
    }
}
