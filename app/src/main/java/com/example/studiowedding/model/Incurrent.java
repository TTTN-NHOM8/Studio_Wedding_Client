package com.example.studiowedding.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Incurrent {

    @SerializedName("idPhatSinh")
    private int idPhatSinh;
    @SerializedName("noiDung")
    private String noiDung;
    @SerializedName("hanTra")
    private Date hanTra;
    @SerializedName("phiPhatSinh")
    private Float phiPhatSinh;
    @SerializedName("hienThi")
    private Integer hienThi;
    @SerializedName("idHopDong")
    private String idHopDong;

    public Incurrent(int idPhatSinh, String noiDung, Date hanTra, Float phiPhatSinh, Integer hienThi, String idHopDong) {
        this.idPhatSinh = idPhatSinh;
        this.noiDung = noiDung;
        this.hanTra = hanTra;
        this.phiPhatSinh = phiPhatSinh;
        this.hienThi = hienThi;
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

    public Date getHanTra() {
        return hanTra;
    }

    public void setHanTra(Date hanTra) {
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
}
