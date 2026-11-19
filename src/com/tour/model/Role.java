package com.tour.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Role {
    private int id;
    private String name;
    private String description;
    private List<String> permissions = new ArrayList<>();
    private boolean deleted;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
    private Date createdAt;
    private String createdAtFormatted;
    private Date deletedAt;
    private String deletedAtFormatted;

    public Role() {}

    public Role(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description != null ? description : ""; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { 
        this.permissions = permissions != null ? permissions : new ArrayList<>(); 
    }

    public boolean hasPermission(String perm) {
        return permissions != null && permissions.contains(perm);
    }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String getCreatedBy() { return createdBy != null ? createdBy : "Phan Thanh Duy"; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy != null ? updatedBy : ""; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public String getDeletedBy() { return deletedBy != null ? deletedBy : "Phan Thanh Duy"; }
    public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }

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
