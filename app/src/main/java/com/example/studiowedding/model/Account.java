package com.example.studiowedding.model;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("idNhanVien")
    private String idNhanVien;

    @SerializedName("matKhau")
    private String matKhau;

    @SerializedName("vaiTro")
    private String vaiTro;
    @SerializedName("hoVaTen")
    private String hoVaTen;
    @SerializedName("dienThoai")
    private String dienThoai;
    @SerializedName("diaChi")
    private String diaChi;
    @SerializedName("ngaySinh")
    private String ngaySinh;
    private String matKhauCu;
    private String matKhauMoi;

    @SerializedName("gioiTinh")
    private String gioiTinh;

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public Account() {
    }


    public Account(String idNhanVien, String matKhau, String vaiTro, String hoVaTen, String dienThoai, String diaChi, String ngaySinh, String matKhauCu, String matKhauMoi, String gioiTinh) {
        this.idNhanVien = idNhanVien;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.hoVaTen = hoVaTen;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.matKhauCu = matKhauCu;
        this.matKhauMoi = matKhauMoi;
        this.gioiTinh = gioiTinh;
    }

    public String getMatKhauCu() {
        return matKhauCu;
    }

    public void setMatKhauCu(String matKhauCu) {
        this.matKhauCu = matKhauCu;
    }

    public String getMatKhauMoi() {
        return matKhauMoi;
    }

    public void setMatKhauMoi(String matKhauMoi) {
        this.matKhauMoi = matKhauMoi;
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

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idNhanVien='" + idNhanVien + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", vaiTro='" + vaiTro + '\'' +
                '}';
    }
}
