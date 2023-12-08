package com.example.studiowedding.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName("idSanPham")
    private int id;
    @SerializedName("tenSanPham")
    private String name;
    @SerializedName("anhSanPham")
    private String imgUrl;
    @SerializedName("giaThue")
    private float price;
    @SerializedName("trangThai")
    private String status;
    @SerializedName("loaiSanPham")
    private String type;

    public Product() {
    }

    public Product(int id, String name, String imgUrl, float price, String status, String type) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.status = status;
        this.type = type;
    }
    public Product( String name, float price, String status, String type,String anhSanPham) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.type = type;
        this.imgUrl = anhSanPham;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
