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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<Tour> tourListSection2 = tourDAO.getToursSection2(6);
        List<Tour> tourListSection4 = tourDAO.getToursByCategories(Arrays.asList(1, 2, 3, 4), 8);
        List<Tour> tourListSection6 = tourDAO.getToursByCategories(Arrays.asList(5, 6, 7), 8);

        req.setAttribute("pageTitle", "Trang chủ - 28Tech Travel");
        req.setAttribute("tourListSection2", tourListSection2);
        req.setAttribute("tourListSection4", tourListSection4);
        req.setAttribute("tourListSection6", tourListSection6);

        req.getRequestDispatcher("/webContent/client/pages/home.jsp").forward(req, resp);
    }
}