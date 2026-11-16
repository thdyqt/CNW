package com.tour.controller;

import com.tour.dao.TourDAO;
import com.tour.model.Tour;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {
    private final TourDAO tourDAO = new TourDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String locationFrom = req.getParameter("locationFrom");
        String locationTo = req.getParameter("locationTo");
        String departureDate = req.getParameter("departureDate");

        Integer stockAdult = null;
        Integer stockChildren = null;
        Integer stockBaby = null;

        try {
            if (req.getParameter("stockAdult") != null) stockAdult = Integer.parseInt(req.getParameter("stockAdult"));
            if (req.getParameter("stockChildren") != null) stockChildren = Integer.parseInt(req.getParameter("stockChildren"));
            if (req.getParameter("stockBaby") != null) stockBaby = Integer.parseInt(req.getParameter("stockBaby"));
        } catch (Exception ignored) {}

        String price = req.getParameter("price");

        List<Tour> tourList = tourDAO.searchTours(locationFrom, locationTo, departureDate, stockAdult, stockChildren, stockBaby, price);

        req.setAttribute("pageTitle", "Kết quả tìm kiếm");
        req.setAttribute("tourList", tourList);

        req.getRequestDispatcher("/views/client/pages/search.jsp").forward(req, resp);
    }
}
