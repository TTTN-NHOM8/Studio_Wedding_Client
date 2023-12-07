package com.example.studiowedding.view.activity.contract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.studiowedding.R;
import com.example.studiowedding.adapter.PickCustomerAdapter;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.PickCustomer;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.customer.AddCustomerActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickClientActivity extends AppCompatActivity implements View.OnClickListener  {

    private ImageView imgBack;
    private TextView tvCreate, tvNoItem;
    private RecyclerView rcv;
    private SearchView searchView;
    private PickCustomerAdapter adapter;
    private List<PickCustomer>customerList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_client);
        initView();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        adapter=new PickCustomerAdapter(customerList,this);
        rcv.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                PickCustomer selectedCustomer = customerList.get(position);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("customer", selectedCustomer);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        imgBack.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
        getAllCustomer();
        setupSearchView();
    }
    private void initView(){
        imgBack=findViewById(R.id.imgBackFromPickClient);
        tvCreate=findViewById(R.id.tvCreateClient);
        tvNoItem=findViewById(R.id.tvNoItemCustomer);
        rcv=findViewById(R.id.rcvPickClient);
        searchView=findViewById(R.id.searcViewPickCustomer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBackFromPickClient:
                finish();
                break;
            case R.id.tvCreateClient:
                startActivity(new Intent(PickClientActivity.this, AddCustomerActivity.class));
                break;
        }
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCustomerList(newText);
                return true;
            }
        });
    }

    private void filterCustomerList(String query) {
        if (query.isEmpty()) {
            getAllCustomer();
        } else {
            List<PickCustomer> filteredList = new ArrayList<>();
            for (PickCustomer customer : customerList) {
                if (customer.getPhone().toLowerCase().startsWith(query.toLowerCase())) {
                    filteredList.add(customer);
                }
            }
            customerList.clear();
            customerList.addAll(filteredList);
            if (filteredList.isEmpty()) {
                tvNoItem.setVisibility(View.VISIBLE);
            } else {
                tvNoItem.setVisibility(View.GONE);
            }
        }
        adapter.notifyDataSetChanged();

    }

    private void getAllCustomer() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<PickCustomer>> call = apiService.getCustomers();
        call.enqueue(new Callback<List<PickCustomer>>() {
            @Override
            public void onResponse(Call<List<PickCustomer>> call, Response<List<PickCustomer>> response) {
                if (response.isSuccessful()) {
                    customerList.clear();
                    customerList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    if (response.body().isEmpty()) {
                        tvNoItem.setVisibility(View.VISIBLE);
                    } else {
                        tvNoItem.setVisibility(View.GONE);
                    }
                } else {
                    Log.i("TAG", "ERRR");
                }
            }

            @Override
            public void onFailure(Call<List<PickCustomer>> call, Throwable t) {
                Log.i("TAG", "ERRR");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllCustomer();
    }

}