package com.example.studiowedding.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Task implements Serializable {
    @SerializedName("idCongViec")
    private int idTask;
    @SerializedName("idHopDong")
    private String idContract;
    @SerializedName("idHopDongChiTiet")
    private String idDetailContract;
    @SerializedName("ngayThucHien")
    private Date dateImplement;
    @SerializedName("trangThaiCongViec")
    private String statusTask;
    @SerializedName("tenDichVu")
    private String nameService;
    @SerializedName("diaDiem")
    private String address;
    @SerializedName("hoVaTen")
    private String employee;
    @SerializedName("vaiTro")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdDetailContract() {
        return idDetailContract;
    }

    public void setIdDetailContract(String idDetailContract) {
        this.idDetailContract = idDetailContract;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getIdContract() {
        return idContract;
    }

    public void setIdContract(String idContract) {
        this.idContract = idContract;
    }

    public Date getDateImplement() {
        return dateImplement;
    }

    public void setDateImplement(Date dateImplement) {
        this.dateImplement = dateImplement;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask='" + idTask + '\'' +
                ", idContract='" + idContract + '\'' +
                ", dateImplement=" + dateImplement +
                ", statusTask='" + statusTask + '\'' +
                ", nameService='" + nameService + '\'' +
                ", address='" + address + '\'' +
                ", employee='" + employee + '\'' +
                '}';
    }
}
