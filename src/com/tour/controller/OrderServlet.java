package com.tour.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tour.dao.CityDAO;
import com.tour.dao.OrderDAO;
import com.tour.dao.TourDAO;
import com.tour.model.City;
import com.tour.model.Order;
import com.tour.model.OrderItem;
import com.tour.model.Tour;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final TourDAO tourDAO = new TourDAO();
    private final CityDAO cityDAO = new CityDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String orderCode = req.getParameter("orderCode");
        String phone = req.getParameter("phone");

        Order orderDetail = orderDAO.getOrderByCodeAndPhone(orderCode, phone);

        req.setAttribute("pageTitle", "Đặt hàng thành công - 28Tech Travel");
        req.setAttribute("orderDetail", orderDetail);
        req.getRequestDispatcher("/views/client/pages/order-success.jsp").forward(req, resp);
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
        String orderCode = "OD" + (1000000000L + new Random().nextInt(900000000));

        try {
            JsonNode root = mapper.readTree(jsonBody);
            Order order = new Order();
            order.setCode(orderCode);
            order.setFullName(root.has("fullName") ? root.get("fullName").asText() : "");
            order.setPhone(root.has("phone") ? root.get("phone").asText() : "");
            order.setNote(root.has("note") ? root.get("note").asText() : "");
            order.setPaymentMethod(root.has("paymentMethod") ? root.get("paymentMethod").asText() : "money");
            order.setPaymentStatus("unpaid");
            order.setStatus("initial");

            double subTotal = 0;
            JsonNode itemsNode = root.get("items");
            if (itemsNode != null && itemsNode.isArray()) {
                for (JsonNode it : itemsNode) {
                    OrderItem item = new OrderItem();
                    int tourId = it.has("tourId") ? it.get("tourId").asInt() : 1;
                    Tour tour = tourDAO.getTourById(tourId);

                    item.setTourId(tourId);
                    item.setName(tour != null ? tour.getName() : "Tour Du Lịch");
                    item.setSlug(tour != null ? tour.getSlug() : "tour");
                    item.setAvatar(tour != null ? tour.getAvatar() : "/assets/images/Product-1.jpg");

                    int qAdult = it.has("quantityAdult") ? it.get("quantityAdult").asInt() : 1;
                    int qChild = it.has("quantityChildren") ? it.get("quantityChildren").asInt() : 0;
                    int qBaby = it.has("quantityBaby") ? it.get("quantityBaby").asInt() : 0;

                    item.setQuantityAdult(qAdult);
                    item.setQuantityChildren(qChild);
                    item.setQuantityBaby(qBaby);

                    double pAdult = tour != null ? tour.getPriceNewAdult() : 3890000;
                    double pChild = tour != null ? tour.getPriceNewChildren() : 2700000;
                    double pBaby = tour != null ? tour.getPriceNewBaby() : 800000;

                    item.setPriceNewAdult(pAdult);
                    item.setPriceNewChildren(pChild);
                    item.setPriceNewBaby(pBaby);

                    int locFrom = it.has("locationFrom") ? it.get("locationFrom").asInt() : 1;
                    item.setLocationFrom(locFrom);
                    City city = cityDAO.getCityById(locFrom);
                    item.setLocationFromName(city != null ? city.getName() : "Đà Nẵng");

                    subTotal += (pAdult * qAdult) + (pChild * qChild) + (pBaby * qBaby);
                    order.getItems().add(item);
                }
            }

            order.setSubTotal(subTotal);
            order.setTotal(subTotal);

            orderDAO.createOrder(order);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("code", "success");
        respJson.put("message", "Đặt hàng thành công!");
        respJson.put("orderCode", orderCode);

        resp.getWriter().write(mapper.writeValueAsString(respJson));
    }
}
