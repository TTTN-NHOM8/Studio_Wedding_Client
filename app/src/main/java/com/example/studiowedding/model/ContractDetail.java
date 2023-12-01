package com.example.studiowedding.model;

import com.example.studiowedding.utils.FormatUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class ContractDetail implements Serializable {
    @SerializedName("idHopDongChiTiet")
    private String id;
    @SerializedName("diaDiem")
    private String location;
    @SerializedName("ngayThucHien")
    private Date dateOfPerform;
    @SerializedName("ngayThue")
    private Date dateOfHire;
    @SerializedName("ngayTra")
    private Date dateOfReturn;
    @SerializedName("idDichVu")
    private int serviceID;
    @SerializedName("idSanPham")
    private int productID;
    @SerializedName("idHopDong")
    private String contractID;
    @SerializedName("idHDTamThoi")
    private String contractIDTemporary;

    // Thông tin dịch vụ
    @SerializedName("tenDichVu")
    private String serviceName;
    @SerializedName("giaThueDichVu")
    private float servicePrice;

    // Thông tin sản phẩm
    @SerializedName("tenSanPham")
    private String productName;
    @SerializedName("giaThueSanPham")
    private float productPrice;

    public ContractDetail() {
    }

    public ContractDetail(String id, String location, Date dateOfPerform, Date dateOfHire, Date dateOfReturn, int serviceID, int productID, String contractID) {
        this.id = id;
        this.location = location;
        this.dateOfPerform = dateOfPerform;
        this.dateOfHire = dateOfHire;
        this.dateOfReturn = dateOfReturn;
        this.serviceID = serviceID;
        this.productID = productID;
        this.contractID = contractID;
    }

    public ContractDetail(String contractID) {
        this.contractID = contractID;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateOfPerform() {
        return FormatUtils.formatDateToString(dateOfPerform);
    }

    public void setDateOfPerform(Date dateOfPerform) {
        this.dateOfPerform = dateOfPerform;
    }

    public String getDateOfHire() {
        return FormatUtils.formatDateToString(dateOfHire);
    }

    public void setDateOfHire(Date dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public String getDateOfReturn() {
        return FormatUtils.formatDateToString(dateOfReturn);
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public float getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getContractIDTemporary() {
        return contractIDTemporary;
    }

    public void setContractIDTemporary(String contractIDTemporary) {
        this.contractIDTemporary = contractIDTemporary;
    }

    @Override
    public String toString() {
        return "ContractDetail{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", dateOfPerform=" + dateOfPerform +
                ", dateOfHire=" + dateOfHire +
                ", dateOfReturn=" + dateOfReturn +
                ", serviceID=" + serviceID +
                ", productID=" + productID +
                ", contractID='" + contractID + '\'' +
                ", contractIDTemporary='" + contractIDTemporary + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", servicePrice=" + servicePrice +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }
}
