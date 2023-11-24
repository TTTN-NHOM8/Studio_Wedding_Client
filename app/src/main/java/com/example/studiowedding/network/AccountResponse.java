package com.example.studiowedding.network;

import com.example.studiowedding.model.Account;

public class AccountResponse {
    private String status;
    private Account userAccount;

    public String getStatus() {
        return status;
    }

    public Account getUserAccount() {
        return userAccount;
    }

}
