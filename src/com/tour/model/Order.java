package com.tour.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private String code;
    private String fullName;
    private String phone;
    private String note;
    private double subTotal;
    private double total;
    private String paymentMethod;
    private String paymentMethodName;
    private String paymentStatus;
    private String paymentStatusName;
    private String status;
    private String statusName;
    private Date createdAt;
    private String createdAtFormatted;
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

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { 
        this.paymentMethod = paymentMethod;
        if ("money".equalsIgnoreCase(paymentMethod)) this.paymentMethodName = "Thanh toán tiền mặt";
        else if ("vnpay".equalsIgnoreCase(paymentMethod)) this.paymentMethodName = "VNPay";
        else if ("bank".equalsIgnoreCase(paymentMethod)) this.paymentMethodName = "Chuyển khoản ngân hàng";
        else this.paymentMethodName = paymentMethod;
    }

    public String getPaymentMethodName() { return paymentMethodName; }
    public void setPaymentMethodName(String paymentMethodName) { this.paymentMethodName = paymentMethodName; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { 
        this.paymentStatus = paymentStatus;
        if ("paid".equalsIgnoreCase(paymentStatus)) this.paymentStatusName = "Đã thanh toán";
        else this.paymentStatusName = "Chưa thanh toán";
    }

    public String getPaymentStatusName() { return paymentStatusName; }
    public void setPaymentStatusName(String paymentStatusName) { this.paymentStatusName = paymentStatusName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status;
        if ("done".equalsIgnoreCase(status)) this.statusName = "Hoàn thành";
        else if ("cancel".equalsIgnoreCase(status)) this.statusName = "Hủy";
        else this.statusName = "Khởi tạo";
    }

    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { 
        this.createdAt = createdAt;
        if (createdAt != null) {
            this.createdAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(createdAt);
        }
    }

    public String getCreatedAtFormatted() { 
        if (createdAtFormatted == null && createdAt != null) {
            createdAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(createdAt);
        }
        return createdAtFormatted != null ? createdAtFormatted : "";
    }
    public void setCreatedAtFormatted(String createdAtFormatted) { this.createdAtFormatted = createdAtFormatted; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
