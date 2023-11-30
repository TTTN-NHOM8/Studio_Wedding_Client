package com.example.studiowedding.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Contract implements Serializable {
    @SerializedName("idHopDong")

    private String idHopDong;
    @SerializedName("ngayTao")

    private Date ngayTao;
    @SerializedName("ngayThanhToan")

    private String ngayThanhToan;
    @SerializedName("tienCoc")

    private Float tienCoc;
    @SerializedName("giamGia")

    private Float giamGia;
    @SerializedName("tongTien")

    private Float tongTien;
    @SerializedName("trangThaiThanhToan")

    private String trangThaiThanhToan;
    @SerializedName("trangThaiHopDong")

    private String trangThaiHopDong;
    @SerializedName("trangThaiPhatSinh")

    private String trangThaiPhatSinh;
    @SerializedName("hienThi")

    private int hienThi;
    @SerializedName("idHDTamThoi")
    private String idHDTT;



    @SerializedName("idKhachHang")

    private int idKH;

    @SerializedName("tenKhachHang")
    private String tenKH;
    @SerializedName("dienThoai")

    private String dienThoai;
    @SerializedName("diaChi")
    private String diaChi;

    private Customer customer;


    public Contract(String idHopDong, Date ngayTao, String ngayThanhToan, Float tienCoc, Float giamGia, Float tongTien, String trangThaiThanhToan, String trangThaiHopDong, String trangThaiPhatSinh, int hienThi,String idHDTT, int idKH, String tenKH, String dienThoai, String diaChi) {
        this.idHopDong = idHopDong;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.tienCoc = tienCoc;
        this.giamGia = giamGia;
        this.tongTien = tongTien;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.trangThaiHopDong = trangThaiHopDong;
        this.trangThaiPhatSinh = trangThaiPhatSinh;
        this.hienThi = hienThi;
        this.idHDTT=idHDTT;
        this.idKH = idKH;
        this.tenKH = tenKH;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
    }


    public Contract(String idHopDong, String ngayThanhToan, Float tienCoc, Float giamGia, Float tongTien, String trangThaiThanhToan, String trangThaiHopDong, int idKH) {
        this.idHopDong = idHopDong;
        this.ngayThanhToan = ngayThanhToan;
        this.tienCoc = tienCoc;
        this.giamGia = giamGia;
        this.tongTien = tongTien;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.trangThaiHopDong = trangThaiHopDong;
        this.idKH = idKH;
    }

    public Contract(String idHopDong, String ngayThanhToan, Float giamGia, Float tongTien, String trangThaiThanhToan) {
        this.idHopDong = idHopDong;
        this.ngayThanhToan = ngayThanhToan;
        this.giamGia = giamGia;
        this.tongTien = tongTien;
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public String getIdHopDong() {
        return idHopDong;
    }

    public void setIdHopDong(String idHopDong) {
        this.idHopDong = idHopDong;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public Float getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(Float tienCoc) {
        this.tienCoc = tienCoc;
    }

    public Float getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(Float giamGia) {
        this.giamGia = giamGia;
    }

    public Float getTongTien() {
        return tongTien;
    }

    public void setTongTien(Float tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public String getTrangThaiHopDong() {
        return trangThaiHopDong;
    }

    public void setTrangThaiHopDong(String trangThaiHopDong) {
        this.trangThaiHopDong = trangThaiHopDong;
    }

    public String getTrangThaiPhatSinh() {
        return trangThaiPhatSinh;
    }

    public void setTrangThaiPhatSinh(String trangThaiPhatSinh) {
        this.trangThaiPhatSinh = trangThaiPhatSinh;
    }

    public int getHienThi() {
        return hienThi;
    }

    public void setHienThi(int hienThi) {
        this.hienThi = hienThi;
    }

    public int getIdKH() {
        return idKH;
    }

    public void setIdKH(int idKH) {
        this.idKH = idKH;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getIdHDTT() {
        return idHDTT;
    }

    public void setIdHDTT(String idHDTT) {
        this.idHDTT = idHDTT;
    }
}
