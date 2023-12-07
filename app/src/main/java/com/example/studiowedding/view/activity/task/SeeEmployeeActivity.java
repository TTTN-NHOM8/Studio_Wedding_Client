package com.example.studiowedding.view.activity.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import com.example.studiowedding.R;
import com.example.studiowedding.adapter.TaskEmployeeJoinAdapter;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeEmployeeActivity extends AppCompatActivity implements OnItemClickListner.TaskEmployeeJoinI {
    private TextView tvShowMessage;
    private SearchView searchView;
    private ImageView  ivBack;
    private RecyclerView mRCV;
    private ImageView ivFilter, ivCancelFilter;
    private ProgressDialog mProgressDialog;
    private TaskEmployeeJoinAdapter adapter;
    private List<Employee> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_employee);
        mProgressDialog = ProgressDialog.show(this, "", "Loading...");
        checkRoleCallApi();
        initUI();
        onClick();
        readEmployeeByIdTaskApi(getIntent().getIntExtra("task", 0));
    }

    private void checkRoleCallApi() {
//      String role = getIntent().getStringExtra("role");
    }

    @SuppressLint("NonConstantResourceId")
    private void onClick() {
        ivBack.setOnClickListener(view -> onBackPressed());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });

        ivFilter.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, ivFilter);
            MenuInflater menuInflater = popupMenu.getMenuInflater();
            menuInflater.inflate(R.menu.filter_employee_task, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.make_up:
                    case R.id.lai_xe:
                    case R.id.chup_hinh:
                        ivCancelFilter.setVisibility(View.VISIBLE);
                        ivFilter.setVisibility(View.GONE);
                        adapter.setList(filterByRole((String) menuItem.getTitle()));
                        return true;
                    case R.id.loctatca:
                        adapter.setList(mList);
                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
        });
        ivCancelFilter.setOnClickListener(view -> {
            ivCancelFilter.setVisibility(View.GONE);
            ivFilter.setVisibility(View.VISIBLE);
            adapter.setList(mList);
        });
    }

    private void initUI() {
        searchView = findViewById(R.id.sv_see_employee);
        mRCV = findViewById(R.id.rcv_see_employee);
        tvShowMessage = findViewById(R.id.tv_show_see_employee);
        ivBack = findViewById(R.id.iv_back_see_employee);
        ivFilter = findViewById(R.id.iv_filter_see_employee);
        ivCancelFilter = findViewById(R.id.iv_cancel_filter_see_employee);
        ivCancelFilter.setVisibility(View.GONE);
    }

    public List<Employee> filterByRole(String role){
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : mList){
            if (role.equals(employee.getVaiTro())){
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    private void readEmployeeByIdTaskApi(int idTask) {
        ApiClient.getClient().create(ApiService.class).readEmployeeByIdTask(idTask).enqueue(new Callback<ResponseEmployeeJoin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseEmployeeJoin> call, @NonNull Response<ResponseEmployeeJoin> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        setAdapter(response.body().getEmployeeList());
                        mList = response.body().getEmployeeList();
                        tvShowMessage.setVisibility(View.GONE);
                        mProgressDialog.dismiss();
                    }else {
                        mProgressDialog.dismiss();
                    }
                }else {
                    mProgressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseEmployeeJoin> call, @NonNull Throwable t) {

            }
        });
    }

    private void readEmployeeApi() {
        ApiClient.getClient().create(ApiService.class).readTaskEmployee().enqueue(new Callback<ResponseEmployeeJoin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseEmployeeJoin> call, @NonNull Response<ResponseEmployeeJoin> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        setAdapter(response.body().getEmployeeList());
                        tvShowMessage.setVisibility(View.GONE);
                        mProgressDialog.dismiss();
                    }else {
                        mProgressDialog.dismiss();
                    }
                }else {
                    mProgressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseEmployeeJoin> call, @NonNull Throwable t) {

            }
        });
    }

    private void setAdapter(List<Employee> employeeList) {
        adapter = new TaskEmployeeJoinAdapter(employeeList);
        adapter.setOnClickItem(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRCV.setLayoutManager(layoutManager);
        mRCV.setAdapter(adapter);
    }


    public void addEmployeeJoin(Employee employee, int idTask){
        ApiClient.getClient().create(ApiService.class).insertEmployee(idTask, employee.getIdNhanVien()).enqueue(new Callback<ResponseJoin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJoin> call, @NonNull Response<ResponseJoin> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        Intent intent = new Intent(getApplicationContext(), UpdateTaskActivity.class);
                        intent.putExtra("idJoin", response.body().getIdTask());
                        setResult(RESULT_OK, intent);
                        finish();
                    }else {
                        Snackbar.make(mRCV, "Thêm nhân viên thất bại", Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(mRCV, "Thêm nhân viên thất bại", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJoin> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void nextScreen(Employee employee) {
        addEmployeeJoin(employee, getIntent().getIntExtra("task", 0));
    }
}