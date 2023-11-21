package com.example.studiowedding.model;

import java.util.Date;

public class Contract {
    private String idHopDong;
    private Date ngayTao;
    private Date ngayThanhToan;
    private Float tienCoc;
    private Float giamGia;
    private Float tongTien;
    private String trangThaiThanhToan;
    private String trangThaiHopDong;
    private String trangThaiPhatSinh;
    private int hienThi;
    private int idKhachHang;

    private String tenKH;

    public Contract(String idHopDong, Date ngayTao, Date ngayThanhToan, Float tienCoc, Float giamGia, Float tongTien, String trangThaiThanhToan, String trangThaiHopDong, String trangThaiPhatSinh, int hienThi, int idKhachHang, String tenKH) {
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
        this.idKhachHang = idKhachHang;
        this.tenKH = tenKH;
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

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
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

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }
}
