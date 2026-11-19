package controller;

import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CategoryServlet extends HttpServlet {
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final TourDAO tourDAO = new TourDAO();
    private final CityDAO cityDAO = new CityDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        String slug = "";
        if (pathInfo != null && pathInfo.length() > 1) {
            slug = pathInfo.substring(1);
        }

        Category category = categoryDAO.getCategoryBySlug(slug);
        if (category == null) {
            category = categoryDAO.getAllCategories().get(0);
        }

        Breadcrumb breadcrumb = new Breadcrumb(category.getName(), category.getAvatar());
        breadcrumb.addItem("/", "Trang Chủ");

        if (category.getParentId() != null && category.getParentId() > 0) {
            Category parent = categoryDAO.getCategoryById(category.getParentId());
            if (parent != null) {
                breadcrumb.addItem("/category/" + parent.getSlug(), parent.getName());
            }
        }
        breadcrumb.addItem("/category/" + category.getSlug(), category.getName());

        List<Integer> catIds = new ArrayList<>();
        catIds.add(category.getId());
        for (Category sub : categoryDAO.getAllCategories()) {
            if (sub.getParentId() != null && sub.getParentId() == category.getId()) {
                catIds.add(sub.getId());
            }
        }

        String locationFrom = req.getParameter("locationFrom");
        String locationTo = req.getParameter("locationTo");
        String departureDate = req.getParameter("departureDate");

        Integer stockAdult = null;
        Integer stockChildren = null;
        Integer stockBaby = null;

        try {
            if (req.getParameter("stockAdult") != null && !req.getParameter("stockAdult").trim().isEmpty()) {
                int v = Integer.parseInt(req.getParameter("stockAdult").trim());
                if (v > 0) stockAdult = v;
            }
            if (req.getParameter("stockChildren") != null && !req.getParameter("stockChildren").trim().isEmpty()) {
                int v = Integer.parseInt(req.getParameter("stockChildren").trim());
                if (v > 0) stockChildren = v;
            }
            if (req.getParameter("stockBaby") != null && !req.getParameter("stockBaby").trim().isEmpty()) {
                int v = Integer.parseInt(req.getParameter("stockBaby").trim());
                if (v > 0) stockBaby = v;
            }
        } catch (Exception ignored) {}

        String price = req.getParameter("price");
        String sortKey = req.getParameter("sortKey");
        String sortValue = req.getParameter("sortValue");

        List<Tour> tourListSection9 = tourDAO.getToursByCategoriesWithFilter(
            catIds, locationFrom, locationTo, departureDate, stockAdult, stockChildren, stockBaby, price, sortKey, sortValue, 100
        );
        List<City> cityList = cityDAO.getAllCities();

        req.setAttribute("pageTitle", category.getName() + " - Danh sách tour");
        req.setAttribute("breadcrumb", breadcrumb);
        req.setAttribute("category", category);
        req.setAttribute("tourListSection9", tourListSection9);
        req.setAttribute("cityList", cityList);

        req.getRequestDispatcher("/webContent/client/pages/tour-list.jsp").forward(req, resp);
    }
}
