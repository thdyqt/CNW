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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final TourDAO tourDAO = new TourDAO();
    private final CityDAO cityDAO = new CityDAO();
    private final CouponDAO couponDAO = new CouponDAO();
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

        // 3. Tra cứu đơn hàng: /order/lookup hoặc /order/tracking
        if (path.contains("/lookup") || path.contains("/tracking")) {
            handleOrderLookup(req, resp);
            return;
        }

        // 4. Trang đặt hàng thành công mặc định: /order/success hoặc /order
        String orderCode = req.getParameter("orderCode");
        String phone = req.getParameter("phone");

        Order orderDetail = orderDAO.getOrderByCodeAndPhone(orderCode, phone);

        req.setAttribute("pageTitle", "Đặt hàng thành công - DUT Travel");
        req.setAttribute("orderDetail", orderDetail);
        req.getRequestDispatcher("/webContent/client/pages/order-success.jsp").forward(req, resp);
    }

    private void handleOrderLookup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderCode = req.getParameter("orderCode");
        String phone = req.getParameter("phone");

        Order orderDetail = null;
        boolean searched = false;

        if (orderCode != null && phone != null) {
            searched = true;
            String trimmedCode = orderCode.trim();
            String trimmedPhone = phone.trim();

            if (!trimmedCode.isEmpty() && !trimmedPhone.isEmpty()) {
                orderDetail = orderDAO.getOrderByCodeAndPhone(trimmedCode, trimmedPhone);
                if (orderDetail == null) {
                    orderDetail = orderDAO.getOrderByCodeAndPhone(trimmedCode.toUpperCase(), trimmedPhone);
                }
            }
        }

        // Breadcrumb setup
        Map<String, Object> breadcrumb = new HashMap<>();
        breadcrumb.put("title", "Tra Cứu Đơn Hàng");
        breadcrumb.put("image", "https://images.unsplash.com/photo-1488646953014-85cb44e25828?auto=format&fit=crop&w=1920&q=80");
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> itemHome = new HashMap<>();
        itemHome.put("title", "Trang Chủ");
        itemHome.put("link", "/");
        list.add(itemHome);
        Map<String, String> itemLookup = new HashMap<>();
        itemLookup.put("title", "Tra Cứu Đơn Hàng");
        itemLookup.put("link", "/order/lookup");
        list.add(itemLookup);
        breadcrumb.put("list", list);
        req.setAttribute("breadcrumb", breadcrumb);

        req.setAttribute("pageTitle", "Tra cứu đơn hàng - DUT Travel");
        req.setAttribute("pageCurrent", "lookup");
        req.setAttribute("orderDetail", orderDetail);
        req.setAttribute("searched", searched);
        req.setAttribute("searchedCode", orderCode != null ? orderCode.trim() : "");
        req.setAttribute("searchedPhone", phone != null ? phone.trim() : "");
        req.getRequestDispatcher("/webContent/client/pages/order-lookup.jsp").forward(req, resp);
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
                    try {
                        Order paidOrder = orderDAO.getOrderByCode(orderCode);
                        if (paidOrder != null) {
                            OrderEmailUtil.sendOrderConfirmationAsync(paidOrder);
                        }
                    } catch (Exception mailEx) {
                        mailEx.printStackTrace();
                    }
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
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        if (path.contains("/lookup") || path.contains("/tracking")) {
            handleOrderLookup(req, resp);
            return;
        }

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
            String email = root.has("email") ? root.get("email").asText("").trim() : "";
            if (email.isEmpty() && !order.getPhone().isEmpty()) {
                User u = new UserDAO().getUserByPhone(order.getPhone());
                if (u != null && u.getEmail() != null && !u.getEmail().trim().isEmpty()) {
                    email = u.getEmail().trim();
                }
            }
            order.setEmail(email);
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

            // Kiểm tra và áp dụng mã giảm giá nếu khách hàng nhập mã
            String couponCode = root.has("couponCode") ? root.get("couponCode").asText("").trim() : "";
            if (!couponCode.isEmpty()) {
                CouponResult cRes = couponDAO.validateAndCalculate(couponCode, subTotal);
                if (!cRes.isSuccess()) {
                    ObjectNode respJson = mapper.createObjectNode();
                    respJson.put("code", "error");
                    respJson.put("message", cRes.getMessage());
                    resp.getWriter().write(mapper.writeValueAsString(respJson));
                    return;
                }
                order.setCouponCode(cRes.getCouponCode());
                order.setDiscount(cRes.getDiscount());
                order.setTotal(cRes.getNewTotal());
            } else {
                order.setDiscount(0);
                order.setTotal(subTotal);
            }

            OrderResult result = orderDAO.createOrderWithStockCheck(order);
            ObjectNode respJson = mapper.createObjectNode();
            if (result != null && result.isSuccess()) {
                respJson.put("code", "success");
                respJson.put("message", "Đặt hàng thành công!");
                respJson.put("orderCode", orderCode);

                // Gửi email xác nhận đơn hàng kèm lịch trình tour tự động (chạy bất đồng bộ)
                try {
                    OrderEmailUtil.sendOrderConfirmationAsync(order);
                } catch (Exception mailEx) {
                    mailEx.printStackTrace();
                }
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
