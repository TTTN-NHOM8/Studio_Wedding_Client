package com.example.studiowedding.network;

import com.example.studiowedding.auth.test;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.view.activity.employee.ResponseEmployee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    //Employee
    @GET(ManagerUrl.URL_GET_EMPLOYEE)
        Call<ResponseEmployee> getEmployees();

    @FormUrlEncoded
    @POST(ManagerUrl.URL_ADD_EMPLOYEE)
    Call<ResponseEmployee> addEmployee(
            @Field("idNhanVien") String id,
            @Field("hoVaTen") String hoTen,
            @Field("ngaySinh") String ngaySinh,
            @Field("gioiTinh") String gioiTinh,
            @Field("dienThoai") String dienThoai,
            @Field("diaChi") String diaChi,
            @Field("anhDaiDien") String anh,
            @Field("vaiTro") String vaiTro
    );

    @PUT(ManagerUrl.URL_UPDATE_EMPLOYEE)
    Call<ResponseEmployee> updateEmployee(
            @Field("idNhanVien") String id,
            @Field("hoVaTen") String hoTen,
            @Field("matKhau") String matKhau,
            @Field("ngaySinh") String ngaySinh,
            @Field("gioiTinh") String gioiTinh,
            @Field("dienThoai") String dienThoai,
            @Field("diaChi") String diaChi,
            @Field("anhDaiDien") String anh,
            @Field("vaiTro") String vaiTro
    );
}
