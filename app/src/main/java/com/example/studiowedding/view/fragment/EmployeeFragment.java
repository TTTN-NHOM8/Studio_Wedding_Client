package com.example.studiowedding.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.EmployeeAdapter;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.employee.AddEmployeeActivity;
import com.example.studiowedding.view.activity.employee.ResponseEmployee;
import com.example.studiowedding.view.activity.employee.UpdateEmployeeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeFragment extends Fragment implements OnItemClickListner.EmployeeI {

    private FloatingActionButton floatingActionButton;
    private ImageView ivFilter;
    private RecyclerView rcvEmployee;

    private EmployeeAdapter employeeAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener(view);
        setUpEmployeeRecyclerView();
        getEmployeeList();
    }

    private void setListener(@NonNull View view) {
        rcvEmployee = view.findViewById(R.id.rcv_employee_list);
        floatingActionButton = view.findViewById(R.id.fabContract);
        ivFilter = view.findViewById(R.id.imgFilterContract);
        floatingActionButton.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AddEmployeeActivity.class)));
    }

    public void setUpEmployeeRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvEmployee.setLayoutManager(linearLayoutManager);
        rcvEmployee.setNestedScrollingEnabled(false);
        employeeAdapter = new EmployeeAdapter();
        employeeAdapter.setOnClickItem(this);
        rcvEmployee.setAdapter(employeeAdapter);
    }

    /**
     * Lấy danh sách nhân viên từ API
     */
    private void getEmployeeList(){
        ApiClient.getClient().create(ApiService.class).getEmployees().enqueue(new Callback<ResponseEmployee>() {
            @Override
            public void onResponse(Call<ResponseEmployee> call, Response<ResponseEmployee> response) {
                if (response.isSuccessful()){
                    if (AppConstants.RESPONSE_SUCCESS.equals(response.body().getStatus())){
                        employeeAdapter.setEmployeeList(response.body().getEmployees());
                    }
                }else {
                    Log.e("ERROR", AppConstants.CALL_API_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ResponseEmployee> call, Throwable t) {
                Log.e("ERROR", AppConstants.CALL_API_FAILURE_MESSAGE + t);
            }
        });
    }



    @Override
    public void nextUpdateScreenEmployee(Employee employee) {
        Intent intent = new Intent(getContext(), UpdateEmployeeActivity.class);
        intent.putExtra("employee", employee);
        startActivity(intent);
    }

    @Override
    public void showConfirmDeleteEmployee() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa công việc");
        builder.setMessage("Bạn chắc chắn muốn xóa công việc này ?");

        builder.setPositiveButton("Đồng ý", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        getEmployeeList();
    }
}