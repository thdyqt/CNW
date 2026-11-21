package util;

import model.BEAN.Order;
import model.BEAN.OrderItem;
import model.BEAN.Tour;
import model.BEAN.User;
import model.DAO.OrderDAO;
import model.DAO.TourDAO;
import model.DAO.UserDAO;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderEmailUtil {

    private static final NumberFormat VND_FORMAT = NumberFormat.getInstance(new Locale("vi", "VN"));
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private static String formatMoney(double amount) {
        return VND_FORMAT.format(Math.round(amount)) + "đ";
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }

    /**
     * Gửi email xác nhận đơn hàng bất đồng bộ
     */
    public static void sendOrderConfirmationAsync(Order order) {
        if (order == null) return;
        new Thread(() -> {
            try {
                sendOrderConfirmation(order);
            } catch (Exception e) {
                System.err.println("[OrderEmailUtil] Lỗi gửi email xác nhận cho đơn hàng " + order.getCode() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Gửi email xác nhận đơn hàng đồng bộ
     */
    public static boolean sendOrderConfirmation(Order order) {
        if (order == null) return false;

        // Đảm bảo đơn hàng có đầy đủ thông tin items
        if (order.getItems() == null || order.getItems().isEmpty()) {
            Order fullOrder = new OrderDAO().getOrderByCode(order.getCode());
            if (fullOrder != null && fullOrder.getItems() != null && !fullOrder.getItems().isEmpty()) {
                order = fullOrder;
            }
        }

        // Xác định email người nhận
        String recipientEmail = order.getEmail();
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            // Fallback: Tìm qua số điện thoại tài khoản khách hàng
            if (order.getPhone() != null && !order.getPhone().trim().isEmpty()) {
                User u = new UserDAO().getUserByPhone(order.getPhone().trim());
                if (u != null && u.getEmail() != null && !u.getEmail().trim().isEmpty()) {
                    recipientEmail = u.getEmail().trim();
                }
            }
        }

        if (recipientEmail == null || recipientEmail.trim().isEmpty() || !recipientEmail.contains("@")) {
            System.err.println("[OrderEmailUtil] Không tìm thấy email hợp lệ cho đơn hàng: " + order.getCode() + " (phone: " + order.getPhone() + ")");
            return false;
        }

        recipientEmail = recipientEmail.trim();
        String subject = "[DUT Travel] Xác nhận đặt tour thành công - Mã đơn hàng #" + order.getCode();
        String htmlContent = buildEmailHtml(order);

        boolean sent = MailUtil.sendMail(recipientEmail, subject, htmlContent);
        if (sent) {
            System.out.println("[OrderEmailUtil] Đã gửi thành công email xác nhận đơn #" + order.getCode() + " tới " + recipientEmail);
        } else {
            System.err.println("[OrderEmailUtil] Gửi email xác nhận đơn #" + order.getCode() + " tới " + recipientEmail + " thất bại!");
        }
        return sent;
    }

    /**
     * Xây dựng nội dung HTML email chuẩn hóa hiển thị đẹp trên mọi thiết bị
     */
    public static String buildEmailHtml(Order order) {
        TourDAO tourDAO = new TourDAO();
        StringBuilder sb = new StringBuilder();

        String customerName = escapeHtml(order.getFullName() != null && !order.getFullName().trim().isEmpty() ? order.getFullName().trim() : "Quý khách");
        String orderCode = escapeHtml(order.getCode());
        String phone = escapeHtml(order.getPhone());
        String email = escapeHtml(order.getEmail() != null ? order.getEmail() : "");
        String note = escapeHtml(order.getNote() != null ? order.getNote().trim() : "");

        String orderTimeStr;
        if (order.getCreatedAt() != null) {
            orderTimeStr = DATETIME_FORMAT.format(order.getCreatedAt());
        } else {
            orderTimeStr = DATETIME_FORMAT.format(new Date());
        }

        // Phương thức thanh toán
        String methodText = "Thanh toán tiền mặt khi đi tour";
        if ("bank".equalsIgnoreCase(order.getPaymentMethod())) {
            methodText = "Chuyển khoản ngân hàng";
        } else if ("vnpay".equalsIgnoreCase(order.getPaymentMethod())) {
            methodText = "Thanh toán trực tuyến VNPay";
        }

        // Trạng thái thanh toán
        boolean isPaid = "paid".equalsIgnoreCase(order.getPaymentStatus());
        String paymentStatusBadge = isPaid
                ? "<span style=\"display: inline-block; background-color: #ecfdf5; color: #047857; padding: 4px 12px; border-radius: 6px; font-weight: 700; font-size: 13px; border: 1px solid #a7f3d0;\">✓ ĐÃ THANH TOÁN</span>"
                : "<span style=\"display: inline-block; background-color: #fffbeb; color: #b45309; padding: 4px 12px; border-radius: 6px; font-weight: 700; font-size: 13px; border: 1px solid #fde68a;\">⏳ CHỜ THANH TOÁN</span>";

        sb.append("<!DOCTYPE html>");
        sb.append("<html lang=\"vi\">");
        sb.append("<head><meta charset=\"UTF-8\"><title>Xác nhận đơn hàng</title></head>");
        sb.append("<body style=\"margin: 0; padding: 0; background-color: #f1f5f9; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; color: #1e293b; line-height: 1.6;\">");

        sb.append("<div style=\"max-width: 660px; margin: 30px auto; background-color: #ffffff; border-radius: 16px; overflow: hidden; box-shadow: 0 10px 25px rgba(0,0,0,0.08); border: 1px solid #e2e8f0;\">");

        // Header Banner
        sb.append("<div style=\"background: linear-gradient(135deg, #1d4ed8 0%, #2563eb 50%, #3b82f6 100%); padding: 35px 30px; text-align: center; color: #ffffff;\">");
        sb.append("<div style=\"font-size: 26px; font-weight: 800; letter-spacing: 1px; text-transform: uppercase; margin-bottom: 8px;\">✈️ DUT TRAVEL</div>");
        sb.append("<div style=\"font-size: 14px; opacity: 0.9; margin-bottom: 20px; text-transform: uppercase; letter-spacing: 0.5px;\">Công Ty Cổ Phần Lữ Hành Du Lịch DUT Travel</div>");
        sb.append("<div style=\"display: inline-block; background-color: rgba(255, 255, 255, 0.2); backdrop-filter: blur(4px); padding: 8px 20px; border-radius: 30px; font-weight: 700; font-size: 15px; border: 1px solid rgba(255, 255, 255, 0.4);\">");
        sb.append("🎉 XÁC NHẬN ĐẶT TOUR THÀNH CÔNG");
        sb.append("</div>");
        sb.append("</div>");

        // Body Container
        sb.append("<div style=\"padding: 30px 30px 20px 30px;\">");

        // Greeting
        sb.append("<p style=\"font-size: 15px; margin-top: 0; margin-bottom: 15px;\">Kính gửi <strong>").append(customerName).append("</strong>,</p>");
        sb.append("<p style=\"font-size: 14px; color: #475569; margin-bottom: 24px; line-height: 1.6;\">");
        sb.append("Cảm ơn quý khách đã tin tưởng và lựa chọn đồng hành cùng <strong>DUT Travel</strong> cho kỳ nghỉ tuyệt vời sắp tới. ");
        sb.append("Chúng tôi xin trân trọng thông báo đơn đặt tour của quý khách đã được tiếp nhận và xử lý thành công trên hệ thống.");
        sb.append("</p>");

        // Order Highlight Card
        sb.append("<div style=\"background-color: #f8fafc; border: 1.5px dashed #cbd5e1; border-radius: 12px; padding: 18px 22px; margin-bottom: 28px;\">");
        sb.append("<table style=\"width: 100%; border-collapse: collapse;\">");
        sb.append("<tr>");
        sb.append("<td style=\"padding: 6px 0; font-size: 14px; color: #64748b; width: 40%;\">Mã đơn hàng:</td>");
        sb.append("<td style=\"padding: 6px 0; font-size: 16px; font-weight: 800; color: #2563eb;\">#").append(orderCode).append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td style=\"padding: 6px 0; font-size: 14px; color: #64748b;\">Thời gian đặt:</td>");
        sb.append("<td style=\"padding: 6px 0; font-size: 14px; color: #334155; font-weight: 600;\">").append(orderTimeStr).append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td style=\"padding: 6px 0; font-size: 14px; color: #64748b;\">Phương thức thanh toán:</td>");
        sb.append("<td style=\"padding: 6px 0; font-size: 14px; color: #334155; font-weight: 600;\">").append(methodText).append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td style=\"padding: 6px 0; font-size: 14px; color: #64748b;\">Trạng thái thanh toán:</td>");
        sb.append("<td style=\"padding: 6px 0;\">").append(paymentStatusBadge).append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("</div>");

        // Customer Info Card
        sb.append("<div style=\"margin-bottom: 28px;\">");
        sb.append("<div style=\"font-size: 16px; font-weight: 700; color: #0f172a; margin-bottom: 12px; border-bottom: 2px solid #e2e8f0; padding-bottom: 8px; display: flex; align-items: center;\">");
        sb.append("👤 THÔNG TIN KHÁCH HÀNG");
        sb.append("</div>");
        sb.append("<table style=\"width: 100%; border-collapse: collapse; font-size: 14px;\">");
        sb.append("<tr><td style=\"padding: 6px 0; color: #64748b; width: 35%;\">Họ và tên:</td><td style=\"padding: 6px 0; font-weight: 600;\">").append(customerName).append("</td></tr>");
        sb.append("<tr><td style=\"padding: 6px 0; color: #64748b;\">Số điện thoại:</td><td style=\"padding: 6px 0; font-weight: 600;\">").append(phone).append("</td></tr>");
        if (!email.isEmpty()) {
            sb.append("<tr><td style=\"padding: 6px 0; color: #64748b;\">Email:</td><td style=\"padding: 6px 0; font-weight: 600;\">").append(email).append("</td></tr>");
        }
        if (!note.isEmpty()) {
            sb.append("<tr><td style=\"padding: 6px 0; color: #64748b;\">Ghi chú:</td><td style=\"padding: 6px 0; color: #475569;\">").append(note).append("</td></tr>");
        }
        sb.append("</table>");
        sb.append("</div>");

        // Tour Details & Passengers
        sb.append("<div style=\"margin-bottom: 28px;\">");
        sb.append("<div style=\"font-size: 16px; font-weight: 700; color: #0f172a; margin-bottom: 14px; border-bottom: 2px solid #e2e8f0; padding-bottom: 8px;\">");
        sb.append("🏖️ DANH SÁCH TOUR ĐÃ ĐẶT");
        sb.append("</div>");

        List<Tour> tourList = new ArrayList<>();
        if (order.getItems() != null) {
            for (OrderItem it : order.getItems()) {
                Tour tour = tourDAO.getTourById(it.getTourId());
                if (tour != null) tourList.add(tour);

                String depDateStr = it.getDepartureDate() != null ? DATE_FORMAT.format(it.getDepartureDate()) : "Theo thoả thuận";
                String locFrom = it.getLocationFromName() != null && !it.getLocationFromName().isEmpty() ? it.getLocationFromName() : "Đà Nẵng";
                String tourName = it.getName() != null && !it.getName().isEmpty() ? it.getName() : (tour != null ? tour.getName() : "Tour du lịch");

                sb.append("<div style=\"background-color: #ffffff; border: 1px solid #e2e8f0; border-radius: 12px; padding: 18px; margin-bottom: 16px; box-shadow: 0 2px 4px rgba(0,0,0,0.02);\">");
                sb.append("<div style=\"font-size: 16px; font-weight: 700; color: #1e40af; margin-bottom: 10px;\">").append(escapeHtml(tourName)).append("</div>");

                sb.append("<table style=\"width: 100%; border-collapse: collapse; font-size: 13px; color: #475569; margin-bottom: 12px;\">");
                sb.append("<tr>");
                sb.append("<td style=\"padding: 4px 0; width: 50%;\">📅 Ngày khởi hành: <strong style=\"color: #0f172a;\">").append(depDateStr).append("</strong></td>");
                sb.append("<td style=\"padding: 4px 0; width: 50%;\">📍 Điểm xuất phát: <strong style=\"color: #0f172a;\">").append(escapeHtml(locFrom)).append("</strong></td>");
                sb.append("</tr>");
                if (tour != null) {
                    sb.append("<tr>");
                    if (tour.getTime() != null && !tour.getTime().isEmpty()) {
                        sb.append("<td style=\"padding: 4px 0;\">⏱️ Thời gian: <strong style=\"color: #0f172a;\">").append(escapeHtml(tour.getTime())).append("</strong></td>");
                    }
                    if (tour.getVehicle() != null && !tour.getVehicle().isEmpty()) {
                        sb.append("<td style=\"padding: 4px 0;\">🚗 Phương tiện: <strong style=\"color: #0f172a;\">").append(escapeHtml(tour.getVehicle())).append("</strong></td>");
                    }
                    sb.append("</tr>");
                }
                sb.append("</table>");

                // Passenger counts breakdown
                sb.append("<table style=\"width: 100%; border-collapse: collapse; font-size: 13px; background-color: #f8fafc; border-radius: 8px; overflow: hidden;\">");
                sb.append("<tr style=\"background-color: #f1f5f9; color: #475569; font-weight: 600;\">");
                sb.append("<th style=\"text-align: left; padding: 8px 12px;\">Loại khách</th>");
                sb.append("<th style=\"text-align: center; padding: 8px 12px;\">Số lượng</th>");
                sb.append("<th style=\"text-align: right; padding: 8px 12px;\">Đơn giá</th>");
                sb.append("<th style=\"text-align: right; padding: 8px 12px;\">Thành tiền</th>");
                sb.append("</tr>");

                if (it.getQuantityAdult() > 0) {
                    double itemTotal = it.getQuantityAdult() * it.getPriceNewAdult();
                    sb.append("<tr>");
                    sb.append("<td style=\"padding: 8px 12px; border-bottom: 1px solid #e2e8f0;\">Người lớn</td>");
                    sb.append("<td style=\"padding: 8px 12px; border-bottom: 1px solid #e2e8f0; text-align: center; font-weight: 600;\">").append(it.getQuantityAdult()).append("</td>");
                    sb.append("<td style=\"padding: 8px 12px; border-bottom: 1px solid #e2e8f0; text-align: right;\">").append(formatMoney(it.getPriceNewAdult())).append("</td>");
                    sb.append("<td style=\"padding: 8px 12px; border-bottom: 1px solid #e2e8f0; text-align: right; font-weight: 600; color: #0f172a;\">").append(formatMoney(itemTotal)).append("</td>");
                    sb.append("</tr>");
                }
                if (it.getQuantityChildren() > 0) {
                    double itemTotal = it.getQuantityChildren() * it.getPriceNewChildren();
                    sb.append("<tr>");
                    sb.append("<td style=\"padding: 8px 12px; border-bottom: 1px solid #e2e8f0;\">Trẻ em</td>");
                    sb.append("<td style=\"padding: 8px 12px; border-bottom: 1px solid #e2e8f0; text-align: center; font-weight: 600;\">").append(it.getQuantityChildren()).append("</td>");
                    sb.append("<td style=\"padding: 8px 12px; border-bottom: 1px solid #e2e8f0; text-align: right;\">").append(formatMoney(it.getPriceNewChildren())).append("</td>");
                    sb.append("<td style=\"padding: 8px 12px; border-bottom: 1px solid #e2e8f0; text-align: right; font-weight: 600; color: #0f172a;\">").append(formatMoney(itemTotal)).append("</td>");
                    sb.append("</tr>");
                }
                if (it.getQuantityBaby() > 0) {
                    double itemTotal = it.getQuantityBaby() * it.getPriceNewBaby();
                    sb.append("<tr>");
                    sb.append("<td style=\"padding: 8px 12px;\">Em bé</td>");
                    sb.append("<td style=\"padding: 8px 12px; text-align: center; font-weight: 600;\">").append(it.getQuantityBaby()).append("</td>");
                    sb.append("<td style=\"padding: 8px 12px; text-align: right;\">").append(formatMoney(it.getPriceNewBaby())).append("</td>");
                    sb.append("<td style=\"padding: 8px 12px; text-align: right; font-weight: 600; color: #0f172a;\">").append(formatMoney(itemTotal)).append("</td>");
                    sb.append("</tr>");
                }
                sb.append("</table>");
                sb.append("</div>");
            }
        }
        sb.append("</div>");

        // Tour Itinerary Section (Lịch Trình Chi Tiết)
        sb.append("<div style=\"margin-bottom: 28px;\">");
        sb.append("<div style=\"font-size: 16px; font-weight: 700; color: #0f172a; margin-bottom: 14px; border-bottom: 2px solid #e2e8f0; padding-bottom: 8px;\">");
        sb.append("🗺️ LỊCH TRÌNH TOUR CHI TIẾT");
        sb.append("</div>");

        if (tourList.isEmpty()) {
            Tour defaultTour = tourDAO.getTourById(1);
            if (defaultTour != null) tourList.add(defaultTour);
        }

        for (Tour t : tourList) {
            sb.append("<div style=\"margin-bottom: 18px;\">");
            sb.append("<div style=\"font-size: 14px; font-weight: 700; color: #1e3a8a; margin-bottom: 10px; background-color: #eff6ff; padding: 6px 12px; border-radius: 6px; border-left: 3px solid #3b82f6;\">");
            sb.append("Lịch trình: ").append(escapeHtml(t.getName()));
            sb.append("</div>");

            List<Tour.ScheduleItem> schedules = t.getSchedules();
            boolean hasValidSchedule = false;
            if (schedules != null) {
                for (Tour.ScheduleItem s : schedules) {
                    if ((s.getTitle() != null && !s.getTitle().trim().isEmpty()) ||
                        (s.getDescription() != null && !s.getDescription().trim().isEmpty())) {
                        hasValidSchedule = true;
                        break;
                    }
                }
            }

            if (hasValidSchedule) {
                int dayIndex = 1;
                for (Tour.ScheduleItem s : schedules) {
                    if ((s.getTitle() == null || s.getTitle().trim().isEmpty()) &&
                        (s.getDescription() == null || s.getDescription().trim().isEmpty())) {
                        continue;
                    }
                    String dayTitle = s.getTitle() != null && !s.getTitle().trim().isEmpty() ? s.getTitle().trim() : ("Ngày " + dayIndex);
                    String dayDesc = s.getDescription() != null ? s.getDescription().trim() : "";

                    sb.append("<div style=\"background-color: #f8fafc; border: 1px solid #e2e8f0; border-left: 4px solid #2563eb; border-radius: 8px; padding: 12px 16px; margin-bottom: 10px;\">");
                    sb.append("<div style=\"font-weight: 700; color: #1e40af; font-size: 14px; margin-bottom: 6px;\">");
                    sb.append("🚩 ").append(escapeHtml(dayTitle));
                    sb.append("</div>");
                    sb.append("<div style=\"font-size: 13px; color: #334155; line-height: 1.6; white-space: pre-line;\">");
                    sb.append(escapeHtml(dayDesc));
                    sb.append("</div>");
                    sb.append("</div>");
                    dayIndex++;
                }
            } else {
                // Lịch trình mẫu tiêu chuẩn 3 ngày 2 đêm nếu tour chưa cấu hình chi tiết
                sb.append("<div style=\"background-color: #f8fafc; border: 1px solid #e2e8f0; border-left: 4px solid #2563eb; border-radius: 8px; padding: 12px 16px; margin-bottom: 10px;\">");
                sb.append("<div style=\"font-weight: 700; color: #1e40af; font-size: 14px; margin-bottom: 6px;\">🚩 Ngày 1: Đón khách - Nhận phòng & Tham quan điểm đến</div>");
                sb.append("<div style=\"font-size: 13px; color: #334155; line-height: 1.6;\">Xe và hướng dẫn viên đón quý khách tại điểm hẹn, di chuyển đến khách sạn nhận phòng nghỉ ngơi. Buổi chiều đoàn tham quan các danh thắng biểu tượng và tự do tắm biển/khám phá. Buổi tối thưởng thức đặc sản địa phương và tự do dạo phố.</div>");
                sb.append("</div>");

                sb.append("<div style=\"background-color: #f8fafc; border: 1px solid #e2e8f0; border-left: 4px solid #2563eb; border-radius: 8px; padding: 12px 16px; margin-bottom: 10px;\">");
                sb.append("<div style=\"font-weight: 700; color: #1e40af; font-size: 14px; margin-bottom: 6px;\">🚩 Ngày 2: Khám phá trọn vẹn văn hoá & Trải nghiệm sinh thái</div>");
                sb.append("<div style=\"font-size: 13px; color: #334155; line-height: 1.6;\">Dùng điểm tâm sáng tại khách sạn. Quý khách lên đường khám phá các quần thể di tích, khu du lịch sinh thái nổi tiếng trong vùng, tham gia các hoạt động vui chơi giải trí và chụp ảnh lưu niệm. Dùng bữa trưa và bữa tối theo chương trình.</div>");
                sb.append("</div>");

                sb.append("<div style=\"background-color: #f8fafc; border: 1px solid #e2e8f0; border-left: 4px solid #2563eb; border-radius: 8px; padding: 12px 16px; margin-bottom: 10px;\">");
                sb.append("<div style=\"font-weight: 700; color: #1e40af; font-size: 14px; margin-bottom: 6px;\">🚩 Ngày 3: Tự do mua sắm đặc sản - Trả phòng & Tạm biệt</div>");
                sb.append("<div style=\"font-size: 13px; color: #334155; line-height: 1.6;\">Dùng bữa sáng tại khách sạn. Quý khách tự do dạo chợ mua quà lưu niệm, hải sản tươi sống và đặc sản trứ danh. Làm thủ tục trả phòng, xe đưa quý khách về điểm hẹn ban đầu. HDV chia tay và hẹn gặp lại quý khách!</div>");
                sb.append("</div>");
            }

            sb.append("</div>");
        }
        sb.append("</div>");

        // Financial Summary
        sb.append("<div style=\"background-color: #f8fafc; border-radius: 12px; padding: 20px 24px; margin-bottom: 28px; border: 1px solid #e2e8f0;\">");
        sb.append("<table style=\"width: 100%; border-collapse: collapse; font-size: 14px;\">");
        sb.append("<tr>");
        sb.append("<td style=\"padding: 6px 0; color: #64748b;\">Tạm tính:</td>");
        sb.append("<td style=\"padding: 6px 0; text-align: right; font-weight: 600; color: #334155;\">").append(formatMoney(order.getSubTotal())).append("</td>");
        sb.append("</tr>");

        if (order.getCouponCode() != null && !order.getCouponCode().trim().isEmpty() && order.getDiscount() > 0) {
            sb.append("<tr>");
            sb.append("<td style=\"padding: 6px 0; color: #16a34a;\">Mã giảm giá (<strong>").append(escapeHtml(order.getCouponCode())).append("</strong>):</td>");
            sb.append("<td style=\"padding: 6px 0; text-align: right; font-weight: 600; color: #16a34a;\">-").append(formatMoney(order.getDiscount())).append("</td>");
            sb.append("</tr>");
        }

        sb.append("<tr>");
        sb.append("<td style=\"padding: 12px 0 4px 0; font-size: 16px; font-weight: 700; color: #0f172a; border-top: 2px solid #cbd5e1;\">Tổng tiền thanh toán:</td>");
        sb.append("<td style=\"padding: 12px 0 4px 0; font-size: 20px; font-weight: 800; color: #dc2626; text-align: right; border-top: 2px solid #cbd5e1;\">").append(formatMoney(order.getTotal())).append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("</div>");

        // Bank transfer instruction if method is 'bank'
        if ("bank".equalsIgnoreCase(order.getPaymentMethod()) && !isPaid) {
            sb.append("<div style=\"background-color: #eff6ff; border: 1.5px solid #60a5fa; border-radius: 12px; padding: 20px 22px; margin-bottom: 28px;\">");
            sb.append("<div style=\"font-size: 15px; font-weight: 700; color: #1d4ed8; margin-bottom: 10px;\">💳 HƯỚNG DẪN CHUYỂN KHOẢN NGÂN HÀNG</div>");
            sb.append("<div style=\"font-size: 13px; color: #334155; line-height: 1.7;\">");
            sb.append("• <strong>Ngân hàng:</strong> MBBank (Ngân Hàng Quân Đội)<br>");
            sb.append("• <strong>Chủ tài khoản:</strong> PHAN THANH DUY<br>");
            sb.append("• <strong>Số tài khoản:</strong> <span style=\"font-family: monospace; font-size: 15px; font-weight: 700; color: #1d4ed8;\">000002907</span><br>");
            sb.append("• <strong>Số tiền:</strong> <strong style=\"color: #dc2626;\">").append(formatMoney(order.getTotal())).append("</strong><br>");
            sb.append("• <strong>Nội dung chuyển khoản:</strong> <span style=\"background-color: #dbeafe; padding: 2px 8px; border-radius: 4px; font-family: monospace; font-weight: 700; color: #1e40af;\">DUT ").append(orderCode).append("</span><br>");
            sb.append("<span style=\"color: #64748b; font-size: 12px;\">(Sau khi chuyển khoản, hệ thống sẽ xác nhận và cập nhật trạng thái đơn hàng của quý khách trong 15-30 phút).</span>");
            sb.append("</div>");
            sb.append("</div>");
        }

        // Travel Tips & Notes
        sb.append("<div style=\"background-color: #fffbeb; border: 1px solid #fef3c7; border-radius: 12px; padding: 18px 20px; margin-bottom: 25px;\">");
        sb.append("<div style=\"font-size: 14px; font-weight: 700; color: #92400e; margin-bottom: 8px;\">📌 LƯU Ý QUAN TRỌNG CHO CHUYẾN ĐI</div>");
        sb.append("<ul style=\"margin: 0; padding-left: 20px; font-size: 13px; color: #78350f; line-height: 1.6;\">");
        sb.append("<li>Quý khách vui lòng mang theo bản gốc CMND/CCCD hoặc Hộ chiếu còn hạn sử dụng.</li>");
        sb.append("<li>Vui lòng có mặt tại điểm đón trước giờ khởi hành tối thiểu 30 phút.</li>");
        sb.append("<li>Hướng dẫn viên của DUT Travel sẽ liên hệ với quý khách trước ngày khởi hành 01 ngày để dặn dò chu đáo.</li>");
        sb.append("</ul>");
        sb.append("</div>");

        // Footer Support Info
        sb.append("<div style=\"border-top: 1px solid #e2e8f0; padding-top: 20px; text-align: center; font-size: 13px; color: #64748b; line-height: 1.7;\">");
        sb.append("Quý khách cần hỗ trợ thêm thông tin hoặc thay đổi lịch trình? Vui lòng liên hệ:<br>");
        sb.append("📞 Hotline hỗ trợ 24/7: <strong style=\"color: #2563eb;\">1900 6868</strong> - <strong>0905 123 456</strong><br>");
        sb.append("✉️ Email: <strong style=\"color: #2563eb;\">ptd.ntn2907@gmail.com</strong> | Địa chỉ: 54 Nguyễn Lương Bằng, TP. Đà Nẵng<br>");
        sb.append("<div style=\"margin-top: 12px; font-size: 12px; color: #94a3b8;\">© 2026 DUT Travel. Toàn quyền bảo lưu. Kính chúc quý khách có một chuyến du lịch ngập tràn niềm vui!</div>");
        sb.append("</div>");

        sb.append("</div>"); // Body Container
        sb.append("</div>"); // Box Container
        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();
    }
}
