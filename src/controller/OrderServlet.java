package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
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

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        // 1. Nhận kết quả thanh toán từ VNPay: /order/payment-vnpay-result
        if (path.contains("/payment-vnpay-result")) {
            handleVNPayResult(req, resp);
            return;
        }

        // 2. Chuyển hướng thanh toán VNPay: /order/payment-vnpay
        if (path.contains("/payment-vnpay")) {
            handleVNPayRedirect(req, resp);
            return;
        }

        // 3. Trang đặt hàng thành công mặc định: /order/success hoặc /order
        String orderCode = req.getParameter("orderCode");
        String phone = req.getParameter("phone");

        Order orderDetail = orderDAO.getOrderByCodeAndPhone(orderCode, phone);

        req.setAttribute("pageTitle", "Đặt hàng thành công - DUT Travel");
        req.setAttribute("orderDetail", orderDetail);
        req.getRequestDispatcher("/webContent/client/pages/order-success.jsp").forward(req, resp);
    }

    private void handleVNPayRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String orderCode = req.getParameter("orderCode");
        if (orderCode == null || orderCode.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        Order order = orderDAO.getOrderByCode(orderCode.trim());
        if (order == null || order.isDeleted()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // Nếu đơn hàng đã thanh toán thành công rồi thì chuyển thẳng sang trang success
        if ("paid".equalsIgnoreCase(order.getPaymentStatus())) {
            resp.sendRedirect(req.getContextPath() + "/order/success?orderCode=" + order.getCode() + "&phone=" + (order.getPhone() != null ? order.getPhone() : ""));
            return;
        }

        // Xây dựng returnUrl động tương thích cả localhost lẫn domain thật
        String baseUrl = req.getScheme() + "://" + req.getServerName()
                + ((req.getServerPort() == 80 || req.getServerPort() == 443) ? "" : ":" + req.getServerPort())
                + req.getContextPath();
        String returnUrl = baseUrl + "/order/payment-vnpay-result";

        String paymentUrl = VNPayUtil.createPaymentUrl(req, order.getCode(), (long) order.getTotal(), returnUrl);
        resp.sendRedirect(paymentUrl);
    }

    private void handleVNPayResult(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String secretKey = EnvUtil.get("VNPAY_SECRET", "CNBZTMKIPROHQISCKDKSWCCUQJRUNOBW");
        boolean validSignature = VNPayUtil.verifyCallback(req, secretKey);

        String vnp_ResponseCode = req.getParameter("vnp_ResponseCode");
        String vnp_TransactionStatus = req.getParameter("vnp_TransactionStatus");
        String vnp_TxnRef = req.getParameter("vnp_TxnRef");

        String orderCode = "";
        if (vnp_TxnRef != null && vnp_TxnRef.contains("-")) {
            orderCode = vnp_TxnRef.split("-")[0];
        } else if (vnp_TxnRef != null) {
            orderCode = vnp_TxnRef;
        }

        Order order = !orderCode.isEmpty() ? orderDAO.getOrderByCode(orderCode) : null;
        String phoneParam = (order != null && order.getPhone() != null) ? order.getPhone() : "";

        if (validSignature) {
            if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                // Giao dịch thành công
                if (!orderCode.isEmpty()) {
                    orderDAO.updatePaymentStatus(orderCode, "paid");
                }
                resp.sendRedirect(req.getContextPath() + "/order/success?orderCode=" + orderCode + "&phone=" + phoneParam + "&paymentSuccess=1");
            } else {
                // Khách hàng hủy giao dịch hoặc thanh toán không thành công
                resp.sendRedirect(req.getContextPath() + "/order/success?orderCode=" + orderCode + "&phone=" + phoneParam + "&paymentFailed=1");
            }
        } else {
            // Chữ ký không hợp lệ
            if (order != null) {
                resp.sendRedirect(req.getContextPath() + "/order/success?orderCode=" + orderCode + "&phone=" + phoneParam + "&paymentFailed=1");
            } else {
                resp.sendRedirect(req.getContextPath() + "/cart");
            }
        }
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
                    item.setAvatar(tour != null ? tour.getAvatar() : "/public/assets/images/Product-1.jpg");

                    int qAdult = it.has("quantityAdult") ? it.get("quantityAdult").asInt() : 0;
                    int qChild = it.has("quantityChildren") ? it.get("quantityChildren").asInt() : 0;
                    int qBaby = it.has("quantityBaby") ? it.get("quantityBaby").asInt() : 0;

                    item.setQuantityAdult(qAdult);
                    item.setQuantityChildren(qChild);
                    item.setQuantityBaby(qBaby);

                    double pAdult = tour != null ? tour.getPriceNewAdult() : 0;
                    double pChild = tour != null ? tour.getPriceNewChildren() : 0;
                    double pBaby = tour != null ? tour.getPriceNewBaby() : 0;

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

            OrderResult result = orderDAO.createOrderWithStockCheck(order);
            ObjectNode respJson = mapper.createObjectNode();
            if (result != null && result.isSuccess()) {
                respJson.put("code", "success");
                respJson.put("message", "Đặt hàng thành công!");
                respJson.put("orderCode", orderCode);
            } else {
                respJson.put("code", "error");
                respJson.put("message", result != null ? result.getMessage() : "Đặt hàng không thành công!");
            }
            resp.getWriter().write(mapper.writeValueAsString(respJson));

        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode respJson = mapper.createObjectNode();
            respJson.put("code", "error");
            respJson.put("message", "Đã xảy ra lỗi khi tạo đơn hàng: " + e.getMessage());
            resp.getWriter().write(mapper.writeValueAsString(respJson));
        }
    }
}
