package com.example.studiowedding.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Incurrent implements Serializable {

    @SerializedName("idPhatSinh")
    private int idPhatSinh;
    @SerializedName("noiDung")
    private String noiDung;
    @SerializedName("hanTra")
    private String hanTra;
    @SerializedName("phiPhatSinh")
    private Float phiPhatSinh;
    @SerializedName("hienThi")
    private Integer hienThi;
    @SerializedName("idHopDong")
    private String idHopDong;
    @SerializedName("idHopDongChiTiet")
    private String idHopDongChiTiet;
    @SerializedName("ngayThue")
    private Date ngayThue;
    @SerializedName("ngayTra")
    private Date ngayTra;
    @SerializedName("idSanPham")
    private String idSanPham;
    @SerializedName("tenSanPham")
    private String tenSanPham;
    @SerializedName("anh")
    private String anh;
    @SerializedName("giaThue")
    private Float giaThue;
    @SerializedName("ngayHoanThanh")
    private Date ngayHoanThanh;


    public Incurrent(int idPhatSinh, String noiDung, String hanTra, Float phiPhatSinh) {
        this.idPhatSinh = idPhatSinh;
        this.noiDung = noiDung;
        this.hanTra = hanTra;
        this.phiPhatSinh = phiPhatSinh;
    }

    public Incurrent(int idPhatSinh, String noiDung, String hanTra, Float phiPhatSinh, String idSanPham, String idHopDong,String idHopDongChiTiet) {
        this.idPhatSinh = idPhatSinh;
        this.noiDung = noiDung;
        this.hanTra = hanTra;
        this.phiPhatSinh = phiPhatSinh;
        this.idSanPham = idSanPham;
        this.idHopDong=idHopDong;
        this.idHopDongChiTiet=idHopDongChiTiet;
    }


    public Incurrent(String noiDung, String hanTra, Float phiPhatSinh, String idHopDong) {
        this.noiDung = noiDung;
        this.hanTra = hanTra;
        this.phiPhatSinh = phiPhatSinh;
        this.idHopDong = idHopDong;
    }



    public int getIdPhatSinh() {
        return idPhatSinh;
    }

    public void setIdPhatSinh(int idPhatSinh) {
        this.idPhatSinh = idPhatSinh;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getHanTra() {
        return hanTra;
    }

    public void setHanTra(String hanTra) {
        this.hanTra = hanTra;
    }

    public Float getPhiPhatSinh() {
        return phiPhatSinh;
    }

    public void setPhiPhatSinh(Float phiPhatSinh) {
        this.phiPhatSinh = phiPhatSinh;
    }

    public Integer getHienThi() {
        return hienThi;
    }

    public void setHienThi(Integer hienThi) {
        this.hienThi = hienThi;
    }

    public String getIdHopDong() {
        return idHopDong;
    }

    public void setIdHopDong(String idHopDong) {
        this.idHopDong = idHopDong;
    }

    public String getIdHopDongChiTiet() {
        return idHopDongChiTiet;
    }

    public void setIdHopDongChiTiet(String idHopDongChiTiet) {
        this.idHopDongChiTiet = idHopDongChiTiet;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Float getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(Float giaThue) {
        this.giaThue = giaThue;
    }

    public Date getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(Date ngayThue) {
        this.ngayThue = ngayThue;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public Date getNgayHoanThanh() {
        return ngayHoanThanh;
    }

    public void setNgayHoanThanh(Date ngayHoanThanh) {
        this.ngayHoanThanh = ngayHoanThanh;
    }
}
