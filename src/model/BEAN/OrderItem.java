package model.BEAN;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderItem {
    private int id;
    private int orderId;
    private int tourId;
    private String name;
    private String slug;
    private String avatar;
    private int quantityAdult;
    private int quantityChildren;
    private int quantityBaby;
    private double priceNewAdult;
    private double priceNewChildren;
    private double priceNewBaby;
    private int locationFrom;
    private String locationFromName;
    private Date departureDate;
    private String departureDateFormatted;

    public OrderItem() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getTourId() { return tourId; }
    public void setTourId(int tourId) { this.tourId = tourId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getAvatar() { 
        if (avatar != null && !avatar.trim().isEmpty()) {
            return avatar;
        }
        return "/public/admin/assets/images/Tour-Icon.jpg"; 
    }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public int getQuantityAdult() { return quantityAdult; }
    public void setQuantityAdult(int quantityAdult) { this.quantityAdult = quantityAdult; }

    public int getQuantityChildren() { return quantityChildren; }
    public void setQuantityChildren(int quantityChildren) { this.quantityChildren = quantityChildren; }

    public int getQuantityBaby() { return quantityBaby; }
    public void setQuantityBaby(int quantityBaby) { this.quantityBaby = quantityBaby; }

    public double getPriceNewAdult() { return priceNewAdult; }
    public void setPriceNewAdult(double priceNewAdult) { this.priceNewAdult = priceNewAdult; }

    public double getPriceNewChildren() { return priceNewChildren; }
    public void setPriceNewChildren(double priceNewChildren) { this.priceNewChildren = priceNewChildren; }

    public double getPriceNewBaby() { return priceNewBaby; }
    public void setPriceNewBaby(double priceNewBaby) { this.priceNewBaby = priceNewBaby; }

    public String getPriceNewAdultFormatted() {
        return new java.text.DecimalFormat("#,###", new java.text.DecimalFormatSymbols(new java.util.Locale("vi", "VN"))).format(priceNewAdult);
    }

    public String getPriceNewChildrenFormatted() {
        return new java.text.DecimalFormat("#,###", new java.text.DecimalFormatSymbols(new java.util.Locale("vi", "VN"))).format(priceNewChildren);
    }

    public String getPriceNewBabyFormatted() {
        return new java.text.DecimalFormat("#,###", new java.text.DecimalFormatSymbols(new java.util.Locale("vi", "VN"))).format(priceNewBaby);
    }

    public int getLocationFrom() { return locationFrom; }
    public void setLocationFrom(int locationFrom) { this.locationFrom = locationFrom; }

    public String getLocationFromName() { return locationFromName != null ? locationFromName : "Đà Nẵng"; }
    public void setLocationFromName(String locationFromName) { this.locationFromName = locationFromName; }

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
}

