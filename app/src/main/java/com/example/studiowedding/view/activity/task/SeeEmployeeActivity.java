package com.example.studiowedding.view.activity.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.EmployeeAdapter;
import com.example.studiowedding.adapter.TaskJoinAdapter;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeEmployeeActivity extends AppCompatActivity {
    private TextView tvShowMessage;
    private RecyclerView mRCV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_employee);
        readEmployeeByRoleApi(getIntent().getStringExtra("role"));
        initUI();
    }
    private void initUI() {
        mRCV = findViewById(R.id.rcv_see_employee);
        tvShowMessage = findViewById(R.id.tv_show_see_employee);
    }
    private void readEmployeeByRoleApi(String role) {
        ApiClient.getClient().create(ApiService.class).readEmployeeByRole(role).enqueue(new Callback<ResponseEmployeeJoin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseEmployeeJoin> call, @NonNull Response<ResponseEmployeeJoin> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        if (response.body().getEmployeeList().get(0).getIdNhanVien() != null){
                            setAdapter(response.body().getEmployeeList());
                            tvShowMessage.setVisibility(View.GONE);
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseEmployeeJoin> call, @NonNull Throwable t) {

            }
        });
    }

    private void setAdapter(List<Employee> employeeList) {
        EmployeeAdapter adapter = new EmployeeAdapter(employeeList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRCV.setLayoutManager(layoutManager);
        mRCV.setAdapter(adapter);
    }
}