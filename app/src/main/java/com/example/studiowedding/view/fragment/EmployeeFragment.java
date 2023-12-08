package com.example.studiowedding.view.fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.EmployeeAdapter;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.UIutils;
import com.example.studiowedding.view.activity.employee.AddEmployeeActivity;
import com.example.studiowedding.view.activity.employee.ResponseEmployee;
import com.example.studiowedding.view.activity.employee.UpdateEmployeeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeFragment extends Fragment implements OnItemClickListner.EmployeeI {

    private FloatingActionButton floatingActionButton;
    private ImageView ivFilter;
    private RecyclerView rcvEmployee;
    private List<Employee> employeeList = new ArrayList<>();

    private TextView tvNotification;


    private ProgressDialog mProgressDialog;


    private SearchView searchView;

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
        tvNotification = view.findViewById(R.id.tvNotifiicationEmployee);
        rcvEmployee = view.findViewById(R.id.rcv_employee_list);
        floatingActionButton = view.findViewById(R.id.fabContract);
        ivFilter = view.findViewById(R.id.imgFilterContract);
        searchView = view.findViewById(R.id.searchView);
        tvNotification.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AddEmployeeActivity.class)));
        setAdapter();
        setSearchView();
        ivFilter.setOnClickListener(v -> showFilterPopupMenu(v));
    }
    private void showFilterPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(requireContext(),view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.filter_employee_task, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.make_up:
                    filterByRole("Make Up");
                    break;
                case R.id.lai_xe:
                    filterByRole("Lái Xe");
                    break;
                case R.id.chup_hinh:
                    filterByRole("Chụp Hình");
                    break;
                case R.id.loc_tat_ca:
                    filterByRole("Lọc tất cả trạng thái");
                    break;
            }
            return true;
        });
        popupMenu.show();
    }
    private void filterByRole (String role){
        if (employeeAdapter != null){
            employeeAdapter.filterByRole(role);
        }
    }


    private void setSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()) {
                    getEmployeeList();
                } else {
                    employeeAdapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(newText.isEmpty()) {
                    getEmployeeList();
                } else {
                    employeeAdapter.getFilter().filter(newText);
                }
                updateVisible();
                return true;
            }
        });
    }

    private void updateVisible(){
        if(employeeList.isEmpty()){
            tvNotification.setVisibility(View.VISIBLE);
        }else{
            tvNotification.setVisibility(View.GONE);
        }
    }

    public void setAdapter(){
        List<Employee> list = new ArrayList<>();
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(list);
        employeeAdapter.setOnClickItem(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvEmployee.setLayoutManager(linearLayoutManager);
        rcvEmployee.setAdapter(employeeAdapter);
    }

    public void setUpEmployeeRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvEmployee.setLayoutManager(linearLayoutManager);
        rcvEmployee.setNestedScrollingEnabled(false);
        employeeAdapter = new EmployeeAdapter(employeeList);
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
    /**
     * Xóa nhân viên
     */
    private void deleteEmployee(Employee employee, View view){
        ApiClient.getClient().create(ApiService.class).deleteEmployee(employee.getIdNhanVien()).enqueue(new Callback<ResponseEmployee>() {
            @Override
            public void onResponse(Call<ResponseEmployee> call, Response<ResponseEmployee> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()){
                    if (response.body() != null && AppConstants.STATUS_EMPLOYEE.equals(response.body().getStatus())){
                        Snackbar.make(view, AppConstants.DELETE_EMPLOYEE_SUCCESS_MESSAGE, Snackbar.LENGTH_SHORT).show();
                        getEmployeeList();
                    }else {
                        Snackbar.make(view, AppConstants.DELETE_EMPLOYEE_FAILED_MESSAGE, Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Log.e(AppConstants.EMPLOYEE_OBJECT, AppConstants.CALL_API_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ResponseEmployee> call, Throwable t) {
                Log.e(AppConstants.EMPLOYEE_OBJECT, AppConstants.CALL_API_FAILURE_MESSAGE + t);
            }
        });
    }

    private void handleDeleteEmployeeResponse(ResponseEmployee deleteEmployee){
        if (deleteEmployee != null && deleteEmployee.isSuccess()){

        }
    }




    @Override
    public void nextUpdateScreenEmployee(Employee employee) {
        Intent intent = new Intent(getContext(), UpdateEmployeeActivity.class);
        intent.putExtra("employee", employee);
        startActivity(intent);
    }

    @Override
    public void showConfirmDeleteEmployee(Employee employee, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa nhân viên");
        builder.setMessage("Bạn chắc chắn muốn xóa nhân viên này ?");

        builder.setPositiveButton("Đồng ý", (dialog, which) -> {
            mProgressDialog = ProgressDialog.show(getContext(), "", "Loading...");
            deleteEmployee(employee, view);
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