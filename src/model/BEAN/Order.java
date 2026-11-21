package model.BEAN;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private String code;
    private String fullName;
    private String phone;
    private String email;
    private String note;
    private double subTotal;
    private double total;
    private String couponCode;
    private double discount;
    private String paymentMethod;
    private String paymentMethodName;
    private String paymentStatus;
    private String paymentStatusName;
    private String status;
    private String statusName;
    private Date createdAt;
    private String createdAtFormatted;
    private boolean deleted;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
    private Date deletedAt;
    private String deletedAtFormatted;
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
        this.createdAt = new Date();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email != null ? email : ""; }
    public void setEmail(String email) { this.email = email; }

    public String getNote() { return note != null ? note : ""; }
    public void setNote(String note) { this.note = note; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getTotalFormatted() {
        return new java.text.DecimalFormat("#,###", new java.text.DecimalFormatSymbols(new java.util.Locale("vi", "VN"))).format(total);
    }

    public String getSubTotalFormatted() {
        double val = subTotal > 0 ? subTotal : total;
        return new java.text.DecimalFormat("#,###", new java.text.DecimalFormatSymbols(new java.util.Locale("vi", "VN"))).format(val);
    }

    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public String getDiscountFormatted() {
        return new java.text.DecimalFormat("#,###", new java.text.DecimalFormatSymbols(new java.util.Locale("vi", "VN"))).format(discount);
    }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { 
        this.paymentMethod = paymentMethod;
        if ("money".equalsIgnoreCase(paymentMethod)) {
            this.paymentMethodName = "Tiền mặt";
        } else if ("vnpay".equalsIgnoreCase(paymentMethod)) {
            this.paymentMethodName = "VNPay";
        } else if ("zalopay".equalsIgnoreCase(paymentMethod)) {
            this.paymentMethodName = "ZaloPay";
        } else if ("momo".equalsIgnoreCase(paymentMethod)) {
            this.paymentMethodName = "Ví MoMo";
        } else if ("bank".equalsIgnoreCase(paymentMethod) || "banking".equalsIgnoreCase(paymentMethod)) {
            this.paymentMethodName = "Tài khoản ngân hàng";
        } else {
            this.paymentMethodName = paymentMethod != null ? paymentMethod : "Tiền mặt";
        }
    }

    public String getPaymentMethodName() { return paymentMethodName; }
    public void setPaymentMethodName(String paymentMethodName) { this.paymentMethodName = paymentMethodName; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { 
        this.paymentStatus = paymentStatus;
        if ("paid".equalsIgnoreCase(paymentStatus)) {
            this.paymentStatusName = "Đã thanh toán";
        } else {
            this.paymentStatusName = "Chưa thanh toán";
        }
    }

    public String getPaymentStatusName() { return paymentStatusName; }
    public void setPaymentStatusName(String paymentStatusName) { this.paymentStatusName = paymentStatusName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status;
        if ("done".equalsIgnoreCase(status)) {
            this.statusName = "Hoàn thành";
        } else if ("cancel".equalsIgnoreCase(status)) {
            this.statusName = "Hủy";
        } else {
            this.statusName = "Khởi tạo";
        }
    }

    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { 
        this.createdAt = createdAt;
        if (createdAt != null) {
            this.createdAtFormatted = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createdAt);
        }
    }

    public String getCreatedAtFormatted() { 
        if (createdAtFormatted == null && createdAt != null) {
            createdAtFormatted = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createdAt);
        }
        return createdAtFormatted != null ? createdAtFormatted : "";
    }
    public void setCreatedAtFormatted(String createdAtFormatted) { this.createdAtFormatted = createdAtFormatted; }

    public String getCreatedAtFormattedDate() {
        if (createdAt != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(createdAt);
        }
        return "";
    }

    public String getCreatedAtFormattedTime() {
        if (createdAt != null) {
            return new SimpleDateFormat("HH:mm").format(createdAt);
        }
        return "";
    }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String getCreatedBy() { return createdBy != null ? createdBy : "Khách đặt online"; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy != null ? updatedBy : ""; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public String getDeletedBy() { return deletedBy != null ? deletedBy : "Phan Thanh Duy"; }
    public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }

    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
        if (deletedAt != null) {
            this.deletedAtFormatted = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(deletedAt);
        }
    }

    public String getDeletedAtFormatted() {
        if (deletedAtFormatted == null && deletedAt != null) {
            deletedAtFormatted = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(deletedAt);
        }
        return deletedAtFormatted != null ? deletedAtFormatted : "";
    }
    public void setDeletedAtFormatted(String deletedAtFormatted) { this.deletedAtFormatted = deletedAtFormatted; }
}

