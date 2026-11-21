package model.BEAN;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Coupon {
    private int id;
    private String code;
    private String title;
    private String type; // 'percentage' hoặc 'fixed'
    private double value;
    private double minOrderValue;
    private double maxDiscountValue;
    private int usageLimit; // 0 = không giới hạn
    private int usedCount;
    private Date startDate;
    private Date endDate;
    private String status; // 'active' hoặc 'inactive'
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
    private boolean deleted;
    private String deletedBy;
    private Date deletedAt;

    private String startDateFormatted;
    private String endDateFormatted;
    private String createdAtFormatted;

    public Coupon() {
        this.type = "percentage";
        this.status = "active";
        this.usageLimit = 0;
        this.usedCount = 0;
        this.createdAt = new Date();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code != null ? code.trim().toUpperCase() : ""; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public double getMinOrderValue() { return minOrderValue; }
    public void setMinOrderValue(double minOrderValue) { this.minOrderValue = minOrderValue; }

    public double getMaxDiscountValue() { return maxDiscountValue; }
    public void setMaxDiscountValue(double maxDiscountValue) { this.maxDiscountValue = maxDiscountValue; }

    public int getUsageLimit() { return usageLimit; }
    public void setUsageLimit(int usageLimit) { this.usageLimit = usageLimit; }

    public int getUsedCount() { return usedCount; }
    public void setUsedCount(int usedCount) { this.usedCount = usedCount; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { 
        this.startDate = startDate;
        if (startDate != null) {
            this.startDateFormatted = new SimpleDateFormat("dd/MM/yyyy").format(startDate);
        }
    }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { 
        this.endDate = endDate;
        if (endDate != null) {
            this.endDateFormatted = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
        }
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedBy() { return createdBy != null ? createdBy : "Phan Thanh Duy"; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy != null ? updatedBy : ""; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { 
        this.createdAt = createdAt;
        if (createdAt != null) {
            this.createdAtFormatted = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createdAt);
        }
    }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String getDeletedBy() { return deletedBy; }
    public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }

    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }

    // Các hàm helper định dạng
    public String getValueFormatted() {
        if ("percentage".equalsIgnoreCase(type)) {
            if (value == (long) value) {
                return String.format(Locale.US, "%d%%", (long) value);
            }
            return String.format(Locale.US, "%.1f%%", value);
        } else {
            return formatCurrency(value) + "đ";
        }
    }

    public String getMinOrderValueFormatted() {
        if (minOrderValue <= 0) return "0đ (Không yêu cầu)";
        return formatCurrency(minOrderValue) + "đ";
    }

    public String getMaxDiscountValueFormatted() {
        if (maxDiscountValue <= 0) return "Không giới hạn";
        return formatCurrency(maxDiscountValue) + "đ";
    }

    public String getStartDateFormatted() {
        if (startDateFormatted == null && startDate != null) {
            startDateFormatted = new SimpleDateFormat("dd/MM/yyyy").format(startDate);
        }
        return startDateFormatted != null ? startDateFormatted : "";
    }

    public String getStartDateInputFormatted() {
        if (startDate != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(startDate);
        }
        return "";
    }

    public String getEndDateFormatted() {
        if (endDateFormatted == null && endDate != null) {
            endDateFormatted = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
        }
        return endDateFormatted != null ? endDateFormatted : "";
    }

    public String getEndDateInputFormatted() {
        if (endDate != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(endDate);
        }
        return "";
    }

    public String getCreatedAtFormatted() {
        if (createdAtFormatted == null && createdAt != null) {
            createdAtFormatted = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createdAt);
        }
        return createdAtFormatted != null ? createdAtFormatted : "";
    }

    public String getStatusName() {
        return "active".equalsIgnoreCase(status) ? "Hoạt động" : "Tạm khóa";
    }

    public String getTypeName() {
        return "percentage".equalsIgnoreCase(type) ? "Theo phần trăm (%)" : "Số tiền cố định (VNĐ)";
    }

    public boolean isExpired() {
        if (endDate == null) return false;
        Date today = new Date();
        // Set time to start of day for comparison
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date todayDate = sdf.parse(sdf.format(today));
            Date expDate = sdf.parse(sdf.format(endDate));
            return todayDate.after(expDate);
        } catch (Exception ignored) {}
        return today.after(endDate);
    }

    public boolean isExhausted() {
        return usageLimit > 0 && usedCount >= usageLimit;
    }

    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#,###", new DecimalFormatSymbols(new Locale("vi", "VN")));
        return df.format(amount);
    }
}
