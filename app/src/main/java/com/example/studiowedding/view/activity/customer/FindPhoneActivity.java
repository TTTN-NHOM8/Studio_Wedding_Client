package com.example.studiowedding.view.activity.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ListcustomerAdapter;
import com.example.studiowedding.model.Customer;

import java.util.ArrayList;

public class FindPhoneActivity extends AppCompatActivity {
    private ImageView imgBack;
    private RecyclerView recyclerViewCustomersList;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_phone);
        imgBack = findViewById(R.id.imgback);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCustomersList = findViewById(R.id.RVCustomers);
        recyclerViewCustomersList.setLayoutManager(linearLayoutManager);
        ArrayList<Customer> listCustomer = new ArrayList<>();
        listCustomer.add(new Customer("Cuong", "+ 888888888", "Đà nẵng"));
        listCustomer.add(new Customer("Cuong1", "+ 888888888", "Đà nẵng"));
        listCustomer.add(new Customer("Cuong2", "+ 888888888", "Đà nẵng"));
        adapter = new ListcustomerAdapter(listCustomer);
        recyclerViewCustomersList.setAdapter(adapter);



    }
}