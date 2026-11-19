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

        List<Tour> tourList = tourDAO.searchTours(locationFrom, locationTo, departureDate, stockAdult, stockChildren, stockBaby, price, sortKey, sortValue);

        req.setAttribute("pageTitle", "Kết quả tìm kiếm");
        req.setAttribute("tourList", tourList);

        req.getRequestDispatcher("/webContent/client/pages/search.jsp").forward(req, resp);
    }
}
