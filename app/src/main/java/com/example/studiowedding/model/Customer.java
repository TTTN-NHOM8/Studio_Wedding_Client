package com.example.studiowedding.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Parcelable {
    @SerializedName("idKhachHang")
    private int id;
    @SerializedName("tenKhachHang")

    private String name;
    @SerializedName("dienThoai")

    private String phone;
    @SerializedName("diaChi")


    private String address;


    private ImageView imgUpdateClient;

    public Customer(ImageView imgUpdateClient) {
        this.imgUpdateClient = imgUpdateClient;
    }

    public Customer(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Customer(int id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }


    public ImageView getImgUpdateClient() {
        return imgUpdateClient;
    }

    public void setImgUpdateClient(ImageView imgUpdateClient) {
        this.imgUpdateClient = imgUpdateClient;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    protected Customer(Parcel in) {
        id=in.readInt();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
