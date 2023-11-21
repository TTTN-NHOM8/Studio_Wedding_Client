package com.example.studiowedding.model;

// ExampleData.java
public class Product {
    private String tvvaycuoi;
    private String tvtrangthai;
    private int tvgiavay;

    public Product(String tvvaycuoi, String tvtrangthai, int tvgiavay) {
        this.tvvaycuoi = tvvaycuoi;
        this.tvtrangthai = tvtrangthai;
        this.tvgiavay = tvgiavay;
    }

    public String getTvvaycuoi() {
        return tvvaycuoi;
    }

    public String getTvtrangthai() {
        return tvtrangthai;
    }

    public int getTvgiavay() {
        return tvgiavay;
    }
}
