package com.example.studiowedding.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ListcustomerAdapter;
import com.example.studiowedding.model.Customer;

import java.util.ArrayList;


public class CustomerFragment extends Fragment {

    private RecyclerView recyclerViewCustomersList;
    private RecyclerView.Adapter adapter;



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
        recyclerViewCustomersList.setLayoutManager(linearLayoutManager);
        ArrayList<Customer> listCustomer = new ArrayList<>();
        listCustomer.add(new Customer("Cuong", "+ 888888888", "Đà nẵng"));
        listCustomer.add(new Customer("Cuong1", "+ 888888888", "Đà nẵng"));
        listCustomer.add(new Customer("Cuong2", "+ 888888888", "Đà nẵng"));
        adapter = new ListcustomerAdapter(listCustomer);
        recyclerViewCustomersList.setAdapter(adapter);

        return view;

    }
}