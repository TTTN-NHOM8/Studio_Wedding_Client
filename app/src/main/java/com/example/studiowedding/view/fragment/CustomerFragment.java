package com.example.studiowedding.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.CustomerAdapter;
import com.example.studiowedding.adapter.ProductAdapter;
import com.example.studiowedding.model.Customer;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.model.Task;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.customer.UpdateCustomerActivity;
import com.example.studiowedding.view.activity.task.UpdateTaskActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerFragment extends Fragment  {

    private RecyclerView recyclerViewCustomersList;
    private RecyclerView.Adapter adapter;
    private SearchView searchVieww;
    private List<Customer> customerList;


    private CustomerAdapter customerAdapter;



    public CustomerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }


            @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewCustomersList = view.findViewById(R.id.RVCustomers);
        searchVieww = view.findViewById(R.id.et_search_customer);
        recyclerViewCustomersList.setLayoutManager(linearLayoutManager);
        customerList = new ArrayList<>();
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerViewCustomersList.addItemDecoration(itemDecoration);

        setupSearchView();
        return view;

    }

    @Override
    public void onResume() {
        getCustomerData();
        super.onResume();
    }

    //Lây danh sách thông tin khách hàng
    private void getCustomerData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Customer>> call = apiService.getListCustomer(1);

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    List<Customer> customerList = response.body();
                    customerAdapter = new CustomerAdapter(customerList);
                    recyclerViewCustomersList.setAdapter(customerAdapter);
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                // Handle failure
                Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

}
//Tìm kiếm bằng sdt
private void setupSearchView() {
    searchVieww.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            customerAdapter.getFilter().filter(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            customerAdapter.getFilter().filter(newText);
            return true;
        }
    });



}




//


}