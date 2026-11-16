package com.tour.model;

import java.util.Date;

public class Contact {
    private int id;
    private String email;
    private boolean deleted;
    private Date createdAt;

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
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
