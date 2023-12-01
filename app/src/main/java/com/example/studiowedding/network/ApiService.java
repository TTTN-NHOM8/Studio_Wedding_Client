package com.example.studiowedding.network;

import com.example.studiowedding.model.Account;
import com.example.studiowedding.view.activity.task.ResponseTask;

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

    @GET(ManagerUrl.ACCOUNT_ALL)
    Call<AccountResponse> getEmployeeInfo(@Path("idNhanVien") String idNhanVien);
    @FormUrlEncoded
    @POST(ManagerUrl.ACCOUNT_CHANGEPASSWORK)
    Call<AccountResponse> changePassword(@Field("idNhanVien") String idNhanVien,
                                                @Field("matKhauCu") String matKhauCu,
                                                @Field("matKhauMoi") String matKhauMoi);

    @FormUrlEncoded
    @POST(ManagerUrl.ACCOUNT_UPDATE_EMPLOYEE_INFO)
    Call<AccountResponse> updateEmployeeInfo(
            @Field("idNhanVien") String idNhanVien,
            @Field("hoVaTen") String hoVaTen,
            @Field("ngaySinh") String ngaySinh,
            @Field("gioiTinh") String gioiTinh,
            @Field("dienThoai") String dienThoai,
            @Field("diaChi") String diaChi,
            @Field("vaiTro") String vaiTro

    );
    @GET(ManagerUrl.ACCOUNT_DOANHTHU)
    Call<AccountResponse> getDailyRevenue(@Path("ngay") String ngay);
    @GET(ManagerUrl.ACCOUNT_DOANHTHUMONTH)
    Call<AccountResponse> getDailyRevenuemonth(@Path("thang") String thang);

    @GET(ManagerUrl.ACCOUNT_DOANHTHUYERT)
    Call<AccountResponse> getDailyRevenueyert(@Path("nam") String nam);


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
}

