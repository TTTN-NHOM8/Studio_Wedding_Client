package com.example.studiowedding.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {

    private String idNhanVien;
    @SerializedName("hoVaTen")
    private String hoTen;
    private String matKhau;
    private String ngaySinh;
    private String gioiTinh;
    private String dienThoai;
    private String diaChi;
    @SerializedName("anhDaiDien")
    private String anh;
    private String vaiTro;
    public Employee(String idNhanVien, String hoTen, String ngaySinh, String gioiTinh, String dienThoai, String diaChi, String anh, String vaiTro) {
        this.idNhanVien = idNhanVien;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
        this.anh = anh;
        this.vaiTro = vaiTro;
    }
    public Employee(String idNhanVien, String hoTen, String matKhau, String ngaySinh, String gioiTinh, String dienThoai, String diaChi, String anh, String vaiTro) {
        this.idNhanVien = idNhanVien;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
        this.anh = anh;
        this.vaiTro = vaiTro;
    }
    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }
    public String getHoTen() {
        return hoTen;
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
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

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "idNhanVien='" + idNhanVien + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", dienThoai='" + dienThoai + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", anh='" + anh + '\'' +
                ", vaiTro='" + vaiTro + '\'' +
                '}';
    }
}