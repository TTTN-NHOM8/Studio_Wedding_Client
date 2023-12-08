package com.example.studiowedding.network;

import com.example.studiowedding.model.Customer;
import com.example.studiowedding.model.test;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/abc")
    Call<test> TEST_CALL();

    @FormUrlEncoded
    @POST("insertCustomer")
    Call<Customer>insertCustomer(
            @Field("tenKhachHang") String name,
            @Field("dienThoai") String phone,
            @Field("diaChi") String address
    );

//    @DELETE("/api/customers/{customerId}")
//    Call<Void> deleteCustomer(@Path("customerId") int customerId);

}
