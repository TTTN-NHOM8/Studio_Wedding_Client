package com.example.studiowedding.network;

import com.example.studiowedding.auth.test;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/abc")
    Call<test> TEST_CALL();
}
