package com.example.studiowedding.network;

import com.example.studiowedding.view.activity.task.ResponseTask;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.view.activity.employee.ResponseEmployee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;
import com.example.studiowedding.model.Contract;
import com.example.studiowedding.model.ContractDetail;
import com.example.studiowedding.model.Customer;
import com.example.studiowedding.model.Incurrent;
import com.example.studiowedding.view.activity.account.AccountResponse;
import com.example.studiowedding.view.activity.task.ResponseTask;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.view.activity.detail_contract.ServerResponse;


public interface ApiService {

    @GET(ManagerUrl.CONTRACT_DETAILS_URL)
    Call<List<ContractDetail>> getContractDetails();

    // task
    @GET(ManagerUrl.READ_TASKS)
    Call<ResponseTask> readTask();

    @GET(ManagerUrl.READ_TASKS_ROLE)
    Call<ResponseTask> readTaskByRole(@Query("vaiTro") String role);

    @PUT(ManagerUrl.UPDATE_TASKS)
    @FormUrlEncoded
    Call<ResponseTask> updateTaskById(@Path("id") int id,
                                      @Field("statusTask") String statusTask);

    @DELETE(ManagerUrl.DELETE_TASKS)
    Call<ResponseTask> deleteTaskById(@Path("id") int id);

    // Account
    @FormUrlEncoded
    @POST(ManagerUrl.ACCOUNT)
    Call<AccountResponse> loginAccount(@Field("idNhanVien") String idNhanVien, @Field("matKhau") String matKhau);


    // CONTRACT
    @GET(ManagerUrl.CONTRACTS)
    Call<List<Contract>>getContracts();
    @GET(ManagerUrl.CONTRACT_CLIENTS)
    Call<List<Customer>>getCustomers();
    @GET(ManagerUrl.CONTRACT_DETAIL_CONTRACT)
    Call<List<ContractDetail>>getAllDetailContractByIdHDTT(@Path("idHDTamThoi") String idHDTamThoi);
    @GET(ManagerUrl.CONTRACTS_ID)
    Call<Contract> getContractById(@Path("idHopDong") String idHopDong);

    @GET(ManagerUrl.CONTRACTS_PAYMENT)
    Call<List<Contract>> getContractsByPayment();

    @GET(ManagerUrl.CONTRACTS_PROGESS)
    Call<List<Contract>> getContractsByProgess();

    @GET(ManagerUrl.CONTRACTS_INCURRENT)
    Call<List<Contract>> getContractsByIncurrent();

    @GET(ManagerUrl.INCURRENT)
    Call<List<Incurrent>> getIncurrent();

    @POST(ManagerUrl.ADD_CONTRACT)
    Call<Void> insertContract(@Body Contract newContract);

    @POST(ManagerUrl.INCURRENT_ADD)
    Call<Void> insertIncurrent(@Body Incurrent newIncurrent);

    @PUT(ManagerUrl.CONTRACT_UPDATE)
    Call<Void> updateContract(@Path("idHopDong") String idHopDong, @Body Contract updatedContract);

    @PUT(ManagerUrl.CONTRACT_DELETE)
    Call<Void> deleteContract(@Path("idHopDong") String idHopDong);

    @PUT(ManagerUrl.INCURRENT_DELETE)
    Call<Void> deleteIncurrent(@Path("idPhatSinh") String idPhatSinh);

  // Detail contract
    @FormUrlEncoded
    @POST(ManagerUrl.INSERT_CONTRACT_DETAIL_PRODUCT)
    Call<ServerResponse> insertContractDetailWithProduct(
            @Field("contractDetailID") String contractDetailID,
            @Field("dateOfHire") String dateOfHire,
            @Field("dateOfReturn") String dateOfReturn,
            @Field("productID") int productID,
            @Field("contractIDTemporary") String contractIDTemporary
    );

    @FormUrlEncoded
    @POST(ManagerUrl.INSERT_CONTRACT_DETAIL_SERVICE)
    Call<ServerResponse> insertContractDetailWithService(
            @Field("contractDetailID") String contractDetailID,
            @Field("location") String location,
            @Field("dateOfPerform") String dateOfPerform,
            @Field("serviceID") int serviceID,
            @Field("contractIDTemporary") String contractIDTemporary
    );

    @GET(ManagerUrl.CONTRACT_DETAIL_SERVICES)
    Call<List<Service>> getSelectServices();

    @GET(ManagerUrl.CONTRACT_DETAIL_PRODUCTS)
    Call<List<Product>> getProductsByStatusReady();

    @DELETE(ManagerUrl.DELETE_CONTRACT_DETAIL_BY_CONTRACT_DETAIL_ID)
    Call<ServerResponse> deleteContractDetailByContractDetailID(@Path("contractDetailID") String contractDetailID);

    @FormUrlEncoded
    @PUT(ManagerUrl.UPDATE_CONTRACT_DETAIL_PRODUCT)
    Call<ServerResponse> updateContractDetailWithProduct(
            @Path("contractDetailID") String contractDetailID,
            @Field("dateOfHire") String dateOfHire,
            @Field("dateOfReturn") String dateOfReturn,
            @Field("productID") int productID
    );

    @FormUrlEncoded
    @PUT(ManagerUrl.UPDATE_CONTRACT_DETAIL_SERVICE)
    Call<ServerResponse> updateContractDetailWithService(
            @Path("contractDetailID") String contractDetailID,
            @Field("location") String location,
            @Field("dateOfPerform") String dateOfPerform,
            @Field("serviceID") int serviceID
    );
    @PUT(ManagerUrl.CONTRACT_DETAIL_IDHD)
    Call<Void> updateContractIDContractDetail(@Path("contractIDTemporary") String contractIDTemporary, @Body ContractDetail contractID);
    @DELETE(ManagerUrl.CONTRACT_DETAIL_REMOVE)
    Call<Void>deleteHDCT(@Path("contractIDTemporary") String idHDTT);

    @GET(ManagerUrl.CONTRACT_DETAIL_IDCONTRACT)
    Call<List<ContractDetail>>getContractDetailByIdContract(@Path("contractID") String idContract);

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

    @FormUrlEncoded
    @PUT(ManagerUrl.URL_UPDATE_EMPLOYEE)
    Call<ResponseEmployee> updateEmployee(
            @Path("idNhanVien") String id,
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
