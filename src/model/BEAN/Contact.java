package model.BEAN;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Contact {
    private int id;
    private String email;
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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

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

