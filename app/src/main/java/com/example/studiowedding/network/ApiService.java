package com.example.studiowedding.network;

import com.example.studiowedding.auth.test;
import com.example.studiowedding.model.Contract;
import com.example.studiowedding.model.ContractDetail;
import com.example.studiowedding.model.Customer;
import com.example.studiowedding.model.Incurrent;

import com.example.studiowedding.model.Product;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.view.activity.detail_contract.ServerResponse;

import java.net.IDN;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {
    @GET("/abc")
    Call<test> TEST_CALL();

    @GET(ManagerUrl.CONTRACT_DETAILS_URL)
    Call<List<ContractDetail>> getContractDetails();


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
    @PUT(ManagerUrl.CONTRACT_DETAIL_IDHD)
    Call<Void> updateContractIDContractDetail(@Path("contractIDTemporary") String contractIDTemporary, @Body ContractDetail contractID);
    @DELETE(ManagerUrl.CONTRACT_DETAIL_REMOVE)
    Call<Void>deleteHDCT(@Path("contractIDTemporary") String idHDTT);
}