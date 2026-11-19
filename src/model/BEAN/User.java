package model.BEAN;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String avatar;
    private String status = "active";
    private String tokenUser;
    private String slug;
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private String createdAtFormatted;
    private Date updatedAt;
    private String updatedAtFormatted;
    private boolean deleted;
    private String deletedBy;
    private Date deletedAt;
    private String deletedAtFormatted;

    public User() {}

    public User(int id, String fullName, String email, String phone, String address, String avatar, String status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName != null ? fullName : ""; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email != null ? email : ""; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone != null ? phone : ""; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address != null ? address : ""; }
    public void setAddress(String address) { this.address = address; }

    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getStatus() { return status != null ? status : "active"; }
    public void setStatus(String status) { this.status = status; }

    public String getTokenUser() { return tokenUser; }
    public void setTokenUser(String tokenUser) { this.tokenUser = tokenUser; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

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

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        if (updatedAt != null) {
            this.updatedAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(updatedAt);
        }
    }

    public String getUpdatedAtFormatted() {
        if (updatedAtFormatted == null && updatedAt != null) {
            updatedAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(updatedAt);
        }
        return updatedAtFormatted != null ? updatedAtFormatted : "";
    }
    public void setUpdatedAtFormatted(String updatedAtFormatted) { this.updatedAtFormatted = updatedAtFormatted; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String getDeletedBy() { return deletedBy; }
    public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }

    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
        if (deletedAt != null) {
            this.deletedAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(deletedAt);
        }
    }

    public String getDeletedAtFormatted() {
        if (deletedAtFormatted == null && deletedAt != null) {
            deletedAtFormatted = new SimpleDateFormat("HH:mm - dd/MM/yyyy").format(deletedAt);
        }
        return deletedAtFormatted != null ? deletedAtFormatted : "";
    }
    public void setDeletedAtFormatted(String deletedAtFormatted) { this.deletedAtFormatted = deletedAtFormatted; }
}
