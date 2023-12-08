package com.example.studiowedding.view.activity.account;

import com.example.studiowedding.model.Account;

public class AccountResponse {
    private String status;
    private String message;
    private Account userAccount;
    private String token;

    private double totalRevenue;

    private double doanhthuthang;

    private double doanhthunam;
    private Account allthongtin;

    public Account getAllthongtin() {
        return allthongtin;
    }

    private int  soluong;
    public int getSoluong() {
        return soluong;
    }

    public double getDoanhthunam() {
        return doanhthunam;
    }

    public double getDoanhthuthang() {
        return doanhthuthang;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
    private String revenue;

    private int orderCount;

    public int getOrderCount() {
        return orderCount;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public Account getUserAccount() {
        return userAccount;
    }

}