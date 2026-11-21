package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.BEAN.*;
import model.DAO.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartServlet extends HttpServlet {
    private final TourDAO tourDAO = new TourDAO();
    private final CityDAO cityDAO = new CityDAO();
    private final CouponDAO couponDAO = new CouponDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        List<Coupon> couponList = couponDAO.getActiveCoupons(10);
        req.setAttribute("couponList", couponList);

        req.setAttribute("pageTitle", "Giỏ hàng - DUT Travel");
        req.getRequestDispatcher("/webContent/client/pages/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        String jsonBody = sb.toString();

        // 1. Áp dụng mã giảm giá: /cart/apply-coupon
        if (path.contains("/apply-coupon")) {
            handleApplyCoupon(jsonBody, resp);
            return;
        }

        // 2. Lấy thông tin chi tiết giỏ hàng: /cart/detail hoặc /cart
        handleCartDetail(jsonBody, resp);
    }

    private void handleApplyCoupon(String jsonBody, HttpServletResponse resp) throws IOException {
        ObjectNode responseJson = mapper.createObjectNode();
        try {
            JsonNode bodyNode = mapper.readTree(jsonBody);
            String couponCode = bodyNode.has("couponCode") ? bodyNode.get("couponCode").asText("") : "";
            double subTotal = bodyNode.has("subTotal") ? bodyNode.get("subTotal").asDouble(0) : 0;

            CouponResult result = couponDAO.validateAndCalculate(couponCode, subTotal);
            if (result.isSuccess()) {
                responseJson.put("code", "success");
                responseJson.put("message", result.getMessage());
                responseJson.put("couponCode", result.getCouponCode());
                responseJson.put("couponTitle", result.getCouponTitle());
                responseJson.put("discountType", result.getDiscountType());
                responseJson.put("discountValue", result.getDiscountValue());
                responseJson.put("discount", result.getDiscount());
                responseJson.put("subTotal", result.getSubTotal());
                responseJson.put("total", result.getNewTotal());
                if (result.getCoupon() != null) {
                    responseJson.put("minOrderValue", result.getCoupon().getMinOrderValue());
                    responseJson.put("valueFormatted", result.getCoupon().getValueFormatted());
                }
            } else {
                responseJson.put("code", "error");
                responseJson.put("message", result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseJson.put("code", "error");
            responseJson.put("message", "Đã có lỗi xảy ra khi xử lý mã giảm giá!");
        }
        resp.getWriter().write(mapper.writeValueAsString(responseJson));
    }

    private void handleCartDetail(String jsonBody, HttpServletResponse resp) throws IOException {
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
                        node.put("code", tour.getCode() != null ? tour.getCode() : ("DUT000" + tour.getId()));
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
