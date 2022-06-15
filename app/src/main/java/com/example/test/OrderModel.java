package com.example.test;

public class OrderModel {
    private String category;
    private String title;
    private String price;
    private String img;

    public OrderModel() {
    }

    public OrderModel(String category, String title, String price, String uri) {
        this.category = category;
        this.title = title;
        this.price = price;
        this.img = uri;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String image) {
        this.category = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
