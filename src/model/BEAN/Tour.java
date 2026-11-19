package model.BEAN;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tour {
    private int id;
    private String code;
    private String name;
    private int categoryId;
    private int position;
    private String status;
    private String avatar;
    private List<String> images = new ArrayList<>();
    private long priceAdult;
    private long priceChildren;
    private long priceBaby;
    private long priceNewAdult;
    private long priceNewChildren;
    private long priceNewBaby;
    private int stockAdult;
    private int stockChildren;
    private int stockBaby;
    private String locations;
    private String locationFromName;
    private String time;
    private String vehicle;
    private Date departureDate;
    private String departureDateFormatted;
    private Date createdAt;
    private String createdAtFormatted;
    private String createdBy;
    private String updatedBy;
    private String updatedAtFormatted;
    private String deletedBy;
    private String deletedAtFormatted;
    private String information;
    private List<ScheduleItem> schedules = new ArrayList<>();
    private String slug;
    private int discount;
    private boolean deleted;

    public static class ScheduleItem {
        private String title;
        private String description;

        public ScheduleItem() {}
        public ScheduleItem(String title, String description) {
            this.title = title;
            this.description = description;
        }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public Tour() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code != null && !code.isEmpty() ? code : "28T0000" + id; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public long getPriceAdult() { return priceAdult; }
    public void setPriceAdult(long priceAdult) { this.priceAdult = priceAdult; calculateDiscount(); }
    public void setPriceAdult(double priceAdult) { this.priceAdult = (long) priceAdult; calculateDiscount(); }

    public long getPriceChildren() { return priceChildren; }
    public void setPriceChildren(long priceChildren) { this.priceChildren = priceChildren; }
    public void setPriceChildren(double priceChildren) { this.priceChildren = (long) priceChildren; }

    public long getPriceBaby() { return priceBaby; }
    public void setPriceBaby(long priceBaby) { this.priceBaby = priceBaby; }
    public void setPriceBaby(double priceBaby) { this.priceBaby = (long) priceBaby; }

    public long getPriceNewAdult() { return priceNewAdult; }
    public void setPriceNewAdult(long priceNewAdult) { this.priceNewAdult = priceNewAdult; calculateDiscount(); }
    public void setPriceNewAdult(double priceNewAdult) { this.priceNewAdult = (long) priceNewAdult; calculateDiscount(); }

    public long getPriceNewChildren() { return priceNewChildren; }
    public void setPriceNewChildren(long priceNewChildren) { this.priceNewChildren = priceNewChildren; }
    public void setPriceNewChildren(double priceNewChildren) { this.priceNewChildren = (long) priceNewChildren; }

    public long getPriceNewBaby() { return priceNewBaby; }
    public void setPriceNewBaby(long priceNewBaby) { this.priceNewBaby = priceNewBaby; }
    public void setPriceNewBaby(double priceNewBaby) { this.priceNewBaby = (long) priceNewBaby; }

    public int getStockAdult() { return stockAdult; }
    public void setStockAdult(int stockAdult) { this.stockAdult = stockAdult; }

    public int getStockChildren() { return stockChildren; }
    public void setStockChildren(int stockChildren) { this.stockChildren = stockChildren; }

    public int getStockBaby() { return stockBaby; }
    public void setStockBaby(int stockBaby) { this.stockBaby = stockBaby; }

    public String getLocations() { return locations; }
    public void setLocations(String locations) { this.locations = locations; }

    public String getLocationFromName() { return locationFromName; }
    public void setLocationFromName(String locationFromName) { this.locationFromName = locationFromName; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getVehicle() { return vehicle; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }

    public Date getDepartureDate() { return departureDate; }
    public void setDepartureDate(Date departureDate) { 
        this.departureDate = departureDate;
        if (departureDate != null) {
            this.departureDateFormatted = new SimpleDateFormat("dd/MM/yyyy").format(departureDate);
        }
    }

    public String getDepartureDateFormatted() { 
        if (departureDateFormatted == null && departureDate != null) {
            departureDateFormatted = new SimpleDateFormat("dd/MM/yyyy").format(departureDate);
        }
        return departureDateFormatted != null ? departureDateFormatted : "20/09/2026"; 
    }
    public void setDepartureDateFormatted(String departureDateFormatted) { this.departureDateFormatted = departureDateFormatted; }

    public String getDepartureDateYMD() {
        if (departureDate != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(departureDate);
        }
        return "";
    }

    public boolean hasLocation(int cityId) {
        if (locations == null || locations.isEmpty()) return false;
        String idStr = String.valueOf(cityId);
        for (String part : locations.replaceAll("[\\[\\]\"']", "").split(",")) {
            if (part.trim().equals(idStr)) return true;
        }
        return false;
    }

    public String getImagesJson() {
        if (images == null || images.isEmpty()) return "[]";
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(images);
        } catch (Exception e) {
            return "[]";
        }
    }

    public String getImagesString() {
        if (images == null || images.isEmpty()) return "";
        return String.join(",", images);
    }

    public String getSchedulesJson() {
        if (schedules == null || schedules.isEmpty()) return "[]";
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(schedules);
        } catch (Exception e) {
            return "[]";
        }
    }

    public String getInformation() { return information; }
    public void setInformation(String information) { this.information = information; }

    public List<ScheduleItem> getSchedules() { return schedules; }
    public void setSchedules(List<ScheduleItem> schedules) { this.schedules = schedules; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

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
        return createdAtFormatted != null ? createdAtFormatted : "14:40 - 29/09/2026";
    }
    public void setCreatedAtFormatted(String createdAtFormatted) { this.createdAtFormatted = createdAtFormatted; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public String getUpdatedAtFormatted() { return updatedAtFormatted != null ? updatedAtFormatted : "17:38 - 11/10/2026"; }
    public void setUpdatedAtFormatted(String updatedAtFormatted) { this.updatedAtFormatted = updatedAtFormatted; }

    public String getDeletedBy() { return deletedBy; }
    public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }

    public String getDeletedAtFormatted() { return deletedAtFormatted != null ? deletedAtFormatted : "18:00 - 15/11/2026"; }
    public void setDeletedAtFormatted(String deletedAtFormatted) { this.deletedAtFormatted = deletedAtFormatted; }

    private void calculateDiscount() {
        if (priceAdult > 0 && priceNewAdult > 0 && priceAdult > priceNewAdult) {
            this.discount = (int) Math.round(((double) (priceAdult - priceNewAdult) / priceAdult) * 100);
        } else {
            this.discount = 0;
        }
    }
}

