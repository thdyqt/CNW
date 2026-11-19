package model.BEAN;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private Integer parentId;
    private int position;
    private String avatar;
    private String description;
    private String status;
    private String slug;
    private boolean deleted;
    private Date createdAt;
    private String createdBy;
    private String createdAtFormatted;
    private String updatedBy;
    private String updatedAtFormatted;
    private String deletedBy;
    private String deletedAtFormatted;
    private List<Category> children = new ArrayList<>();

    public Category() {}

    public Category(int id, String name, Integer parentId, int position, String avatar, String description, String status, String slug, boolean deleted) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.position = position;
        this.avatar = avatar;
        this.description = description;
        this.status = status;
        this.slug = slug;
        this.deleted = deleted;
    }

    public Category(int id, String name, Integer parentId, int position, String avatar, String description, String status, String slug, boolean deleted, String createdBy, String createdAtFormatted, String updatedBy, String updatedAtFormatted) {
        this(id, name, parentId, position, avatar, description, status, slug, deleted);
        this.createdBy = createdBy;
        this.createdAtFormatted = createdAtFormatted;
        this.updatedBy = updatedBy;
        this.updatedAtFormatted = updatedAtFormatted;
    }

    public Category(int id, String name, Integer parentId, int position, String avatar, String description, String status, String slug, boolean deleted, String createdBy, String createdAtFormatted, String updatedBy, String updatedAtFormatted, String deletedBy, String deletedAtFormatted) {
        this(id, name, parentId, position, avatar, description, status, slug, deleted, createdBy, createdAtFormatted, updatedBy, updatedAtFormatted);
        this.deletedBy = deletedBy;
        this.deletedAtFormatted = deletedAtFormatted;
    }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getCreatedAtFormatted() { return createdAtFormatted; }
    public void setCreatedAtFormatted(String createdAtFormatted) { this.createdAtFormatted = createdAtFormatted; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public String getUpdatedAtFormatted() { return updatedAtFormatted; }
    public void setUpdatedAtFormatted(String updatedAtFormatted) { this.updatedAtFormatted = updatedAtFormatted; }

    public String getDeletedBy() { return deletedBy; }
    public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }

    public String getDeletedAtFormatted() { return deletedAtFormatted; }
    public void setDeletedAtFormatted(String deletedAtFormatted) { this.deletedAtFormatted = deletedAtFormatted; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public List<Category> getChildren() { return children; }
    public void setChildren(List<Category> children) { this.children = children; }

    public void addChild(Category child) {
        this.children.add(child);
    }
}

