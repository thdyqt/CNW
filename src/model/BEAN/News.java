package model.BEAN;

import java.text.SimpleDateFormat;
import java.util.Date;

public class News {
    private int id;
    private String title;
    private String slug;
    private String category;
    private String avatar;
    private String description;
    private String content;
    private String author;
    private int views;
    private boolean featured;
    private String status;
    private boolean deleted;
    private String deletedBy;
    private Date deletedAt;
    private String deletedAtFormatted;
    private Date createdAt;
    private String createdAtFormatted;
    private Date updatedAt;

    public News() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title != null ? title : ""; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug != null ? slug : ""; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getCategory() { return category != null ? category : "Cẩm nang du lịch"; }
    public void setCategory(String category) { this.category = category; }

    public String getAvatar() { return avatar != null ? avatar : ""; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getDescription() { return description != null ? description : ""; }
    public void setDescription(String description) { this.description = description; }

    public String getContent() { return content != null ? content : ""; }
    public void setContent(String content) { this.content = content; }

    public String getAuthor() { return author != null ? author : "DUT Travel Editor"; }
    public void setAuthor(String author) { this.author = author; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public String getStatus() { return status != null ? status : "active"; }
    public void setStatus(String status) { this.status = status; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

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

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { 
        this.createdAt = createdAt;
        if (createdAt != null) {
            this.createdAtFormatted = new SimpleDateFormat("dd/MM/yyyy").format(createdAt);
        }
    }

    public String getCreatedAtFormatted() { 
        if (createdAtFormatted == null && createdAt != null) {
            createdAtFormatted = new SimpleDateFormat("dd/MM/yyyy").format(createdAt);
        }
        return createdAtFormatted != null ? createdAtFormatted : ""; 
    }
    public void setCreatedAtFormatted(String createdAtFormatted) { this.createdAtFormatted = createdAtFormatted; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
