package com.example.studiowedding.network;


import com.example.studiowedding.view.activity.task.ResponseEmployeeJoin;
import com.example.studiowedding.view.activity.task.ResponseJoin;
import com.example.studiowedding.view.activity.services.ServiceResponse;
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
import com.example.studiowedding.model.Product;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.view.activity.detail_contract.ServerResponse;


public interface ApiService {

    @GET(ManagerUrl.CONTRACT_DETAILS_URL)
    Call<List<ContractDetail>> getContractDetails();

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

    @POST(ManagerUrl.ADD_CONTRACT)
    Call<Void> insertContract(@Body Contract newContract);

    @PUT(ManagerUrl.CONTRACT_UPDATE)
    Call<Void> updateContract(@Path("idHopDong") String idHopDong, @Body Contract updatedContract);

    @PUT(ManagerUrl.CONTRACT_DELETE)
    Call<Void> deleteContract(@Path("idHopDong") String idHopDong);

    @DELETE(ManagerUrl.CONTRACT_DELETE_TASK)
    Call<Void>deleteTaskByTemporaryID(@Path("idHDTamThoi")String idHDTamThoi);

    @GET(ManagerUrl.INCURRENT_LIST)
    Call<List<Incurrent>>getIncurrentList(@Path("idHopDong")String idHDTamThoi);

    @PUT(ManagerUrl.INCURREN_UPDATE)
    Call<Void>updateIncurrent(@Path("idPhatSinh") String idPhatSinh,@Body Incurrent incurrent);
    @PUT(ManagerUrl.INCURREN_UPDATE_NONE)
    Call<Void>updateIncurrentNone(@Path("idPhatSinh")int idPhatSinh,@Body Incurrent incurrent);



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



    // task , join
    @GET(ManagerUrl.READ_TASKS)
    Call<ResponseTask> readTask();

    @GET(ManagerUrl.READ_TASK_EMPLOYEE)
    Call<ResponseEmployeeJoin> readTaskEmployee();

    @GET(ManagerUrl.READ_EMPLOYEE)
    Call<ResponseEmployeeJoin> readEmployee(@Path("idHDCT") String idDetailContract);
    @POST(ManagerUrl.READ_EMPLOYEE_ID_TASK)
    @FormUrlEncoded
    Call<ResponseEmployeeJoin> readEmployeeByIdTask(@Field("idTask") int idTask);
    @POST(ManagerUrl.INSERT_EMPLOYEE)
    @FormUrlEncoded
    Call<ResponseJoin> insertEmployee(@Field("idTask") int idTask,
                                      @Field("idEmployee") String idEmployee);

    @DELETE(ManagerUrl.DELETE_EMPLOYEE)
    Call<ResponseTask> deleteEmployeeJoin(@Path("idJoin") int idJoin);

    @GET(ManagerUrl.READ_TASKS_ROLE)
    Call<ResponseTask> readTaskByRole(@Query("vaiTro") String role);

    @PUT(ManagerUrl.UPDATE_TASKS)
    @FormUrlEncoded
    Call<ResponseTask> updateTaskById(@Path("id") int id,
                                      @Field("statusTask") String statusTask);

    @DELETE(ManagerUrl.DELETE_TASKS)
    Call<ResponseTask> deleteTaskById(@Path("id") int id);

    // Service
    @GET(ManagerUrl.GET_SERVICES_URL)
    Call<List<Service>> getServices();

    @FormUrlEncoded
    @POST(ManagerUrl.INSERT_SERVICE_URL)
    Call<ServiceResponse> insertService(@Field("serviceName") String serviceName, @Field("servicePrice") float servicePrice);

    @FormUrlEncoded
    @PUT(ManagerUrl.UPDATE_SERVICE_URL)
    Call<ServiceResponse> updateService(
            @Path("serviceID") int serviceID,
            @Field("serviceName") String serviceName,
            @Field("servicePrice") float servicePrice
    );

    @PUT(ManagerUrl.REMOVE_SERVICE_URL)
    Call<ServiceResponse> removeService(@Path("serviceID") int serviceID);

}

