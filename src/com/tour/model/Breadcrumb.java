package com.tour.model;

import java.util.ArrayList;
import java.util.List;

public class Breadcrumb {
    private String title;
    private String image;
    private List<BreadcrumbItem> list = new ArrayList<>();

    public static class BreadcrumbItem {
        private String link;
        private String title;

        public BreadcrumbItem() {}
        public BreadcrumbItem(String link, String title) {
            this.link = link;
            this.title = title;
        }
        public String getLink() { return link; }
        public void setLink(String link) { this.link = link; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
    }

    public Breadcrumb() {}
    public Breadcrumb(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public List<BreadcrumbItem> getList() { return list; }
    public void setList(List<BreadcrumbItem> list) { this.list = list; }

    public void addItem(String link, String title) {
        this.list.add(new BreadcrumbItem(link, title));
    }
}
