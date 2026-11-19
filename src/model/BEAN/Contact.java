package model.BEAN;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Contact {
    private int id;
    private String email;
    private String name;
    private String phone;
    private String subject;
    private String message;
    private boolean deleted;
    private Date createdAt;
    private String createdAtFormatted;
    private String deletedBy;
    private Date deletedAt;
    private String deletedAtFormatted;

    public Contact() {}

    public Contact(int id, String email) {
        this.id = id;
        this.email = email;
        this.createdAt = new Date();
    }

    public Contact(int id, String name, String phone, String email, String subject, String message) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.createdAt = new Date();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name != null ? name : ""; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone != null ? phone : ""; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSubject() { return subject != null ? subject : ""; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message != null ? message : ""; }
    public void setMessage(String message) { this.message = message; }

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
        return createdAtFormatted != null ? createdAtFormatted : ""; 
    }
    public void setCreatedAtFormatted(String createdAtFormatted) { this.createdAtFormatted = createdAtFormatted; }

    public String getDeletedBy() { return deletedBy != null ? deletedBy : "Phan Thanh Duy"; }
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

