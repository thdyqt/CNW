package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartServlet extends HttpServlet {
    private final TourDAO tourDAO = new TourDAO();
    private final CityDAO cityDAO = new CityDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("pageTitle", "Giỏ hàng - 28Tech Travel");
        req.getRequestDispatcher("/webContent/client/pages/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String jsonBody = sb.toString();
        ArrayNode resultCart = mapper.createArrayNode();

        try {
            JsonNode cartArray = mapper.readTree(jsonBody);
            if (cartArray != null && cartArray.isArray()) {
                for (JsonNode item : cartArray) {
                    int tourId = item.has("tourId") ? item.get("tourId").asInt() : 1;
                    Tour tour = tourDAO.getTourById(tourId);
                    if (tour != null) {
                        ObjectNode node = mapper.createObjectNode();
                        node.put("tourId", tour.getId());
                        node.put("name", tour.getName());
                        node.put("slug", tour.getSlug());
                        node.put("avatar", tour.getAvatar());
                        node.put("departureDateFormatted", tour.getDepartureDateFormatted());
                        node.put("checked", item.has("checked") && item.get("checked").asBoolean());

                        int locationFrom = item.has("locationFrom") ? item.get("locationFrom").asInt() : 1;
                        City city = cityDAO.getCityById(locationFrom);
                        node.put("locationFrom", locationFrom);
                        node.put("locationFromName", city != null ? city.getName() : "Đà Nẵng");

                        node.put("stockAdult", tour.getStockAdult());
                        node.put("stockChildren", tour.getStockChildren());
                        node.put("stockBaby", tour.getStockBaby());

                        int qAdult = item.has("quantityAdult") ? item.get("quantityAdult").asInt() : 1;
                        int qChild = item.has("quantityChildren") ? item.get("quantityChildren").asInt() : 0;
                        int qBaby = item.has("quantityBaby") ? item.get("quantityBaby").asInt() : 0;

                        node.put("quantityAdult", Math.min(qAdult, tour.getStockAdult()));
                        node.put("quantityChildren", Math.min(qChild, tour.getStockChildren()));
                        node.put("quantityBaby", Math.min(qBaby, tour.getStockBaby()));

                        node.put("priceNewAdult", (long) tour.getPriceNewAdult());
                        node.put("priceNewChildren", (long) tour.getPriceNewChildren());
                        node.put("priceNewBaby", (long) tour.getPriceNewBaby());

                        resultCart.add(node);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("code", "success");
        responseJson.put("message", "Thành công!");
        responseJson.set("cart", resultCart);

        resp.getWriter().write(mapper.writeValueAsString(responseJson));
    }
}