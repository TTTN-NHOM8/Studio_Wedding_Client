package com.example.studiowedding.model;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("idNhanVien")
    private String idNhanVien;

    @SerializedName("matKhau")
    private String matKhau;

    @SerializedName("vaitro")
    private String vaitro;

    public Account() {
    }

    public Account(String idNhanVien, String matKhau, String vaitro) {
        this.idNhanVien = idNhanVien;
        this.matKhau = matKhau;
        this.vaitro = vaitro;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idNhanVien='" + idNhanVien + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", vaitro='" + vaitro + '\'' +
                '}';
    }
}
