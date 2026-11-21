package controller;

import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TourDetailServlet extends HttpServlet {
    private final TourDAO tourDAO = new TourDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
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

        Tour tourDetail = tourDAO.getTourBySlug(slug);

        if (tourDetail != null) {
            Breadcrumb breadcrumb = new Breadcrumb(tourDetail.getName(), tourDetail.getAvatar());
            breadcrumb.addItem("/", "Trang Chủ");

            Category category = categoryDAO.getCategoryById(tourDetail.getCategoryId());
            if (category != null) {
                if (category.getParentId() != null && category.getParentId() > 0) {
                    Category parent = categoryDAO.getCategoryById(category.getParentId());
                    if (parent != null) {
                        breadcrumb.addItem("/category/" + parent.getSlug(), parent.getName());
                    }
                }
                breadcrumb.addItem("/category/" + category.getSlug(), category.getName());
            }

            breadcrumb.addItem("/tour/detail/" + tourDetail.getSlug(), tourDetail.getName());

            List<City> cityList = cityDAO.getCitiesByTourLocations(tourDetail.getLocations());
            if (cityList == null || cityList.isEmpty()) {
                cityList = cityDAO.getCitiesWithTours();
            }

            req.setAttribute("pageTitle", tourDetail.getName() + " - Chi tiết tour");
            req.setAttribute("tourDetail", tourDetail);
            req.setAttribute("cityList", cityList);
            req.setAttribute("breadcrumb", breadcrumb);

            req.getRequestDispatcher("/webContent/client/pages/tour-detail.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}