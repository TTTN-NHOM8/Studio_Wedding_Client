package com.example.studiowedding.view.activity.task;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.TaskJoinAdapter;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.model.Task;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.example.studiowedding.view.fragment.EmployeeFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTaskActivity extends AppCompatActivity  implements OnItemClickListner.TaskJoinI {
    private EditText etName, etDate, etAddress, etNote;
    private ImageView ivSelect, ivBack;
    private LinearLayout btnSave;
    private Task mTask;
    private TextView tvAddEmployee, tvShowMessage;
    private RecyclerView mRCV;
    private ProgressDialog mProgressDialog;
    private List<Employee> mListEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        mTask = (Task) getIntent().getSerializableExtra("task");
        initUI();
        setValue();
        onClick();
        readEmployeeJoinApi();
    }

    private void onClick() {
        ivSelect.setOnClickListener(view -> openPopupMenu());
        ivBack.setOnClickListener(view -> onBackPressed());
        btnSave.setOnClickListener(view -> {
            mProgressDialog = ProgressDialog.show(this, "", "Loading...");
            saveTask();
        });
        tvAddEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(this, SeeEmployeeActivity.class);
            if (mListEmployee != null){
                intent.putExtra("role", mListEmployee.get(0).getVaiTro());
            }else {
                intent.putExtra("role", "");
            }
            mLauncher.launch(intent);
            startActivity(intent);
        });
    }

    @SuppressLint("WrongViewCast")
    private void initUI() {
        etName = findViewById(R.id.et_name_update_job);
        etDate = findViewById(R.id.et_date_update_job);
        etAddress = findViewById(R.id.et_address_update_job);
        etNote = findViewById(R.id.et_note_update_job);
        ivSelect = findViewById(R.id.iv_select_update_job);
        btnSave = findViewById(R.id.btn_update_job);
        ivBack = findViewById(R.id.iv_back_update_job);
        mRCV = findViewById(R.id.rcv_employee_update_job);
        tvAddEmployee = findViewById(R.id.tv_add_update_job);
        tvShowMessage = findViewById(R.id.tv_show_update_job);
    }

    private void setValue() {
        etName.setText(mTask.getNameService());
        etDate.setText(FormatUtils.formatDateToString(mTask.getDateImplement()));
        etAddress.setText(mTask.getAddress());
        etNote.setText(mTask.getStatusTask());
    }

    private void readEmployeeJoinApi() {
        ApiClient.getClient().create(ApiService.class).readEmployee(mTask.getIdDetailContract()).enqueue(new Callback<ResponseEmployeeJoin>() {
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
        TaskJoinAdapter taskJoinAdapter = new TaskJoinAdapter(employeeList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRCV.setLayoutManager(layoutManager);
        mRCV.setAdapter(taskJoinAdapter);
        mListEmployee = employeeList;
    }

    @SuppressLint("NonConstantResourceId")
    private void openPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, ivSelect);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu_status_task, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.action_status_worked:
                case R.id.action_status_done:
                    etNote.setText(menuItem.getTitle());
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    private void saveTask() {
        ApiClient.getClient().create(ApiService.class).updateTaskById(mTask.getIdTask(), etNote.getText().toString()).enqueue(new Callback<ResponseTask>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTask> call, @NonNull Response<ResponseTask> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        Snackbar.make(findViewById(R.id.btn_update_job),"Cập nhật thành công", Snackbar.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(findViewById(R.id.btn_update_job),"Cập nhật thất bại", Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(findViewById(R.id.btn_update_job),"Cập nhật thất bại", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTask> call, @NonNull Throwable t) {

            }
        });
    }

    private final ActivityResultLauncher<Intent> mLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK){
                            Intent intent = result.getData();
                            assert intent != null;
                        }

                    });

    @Override
    public void nextScreen(Employee employee) {

    }

    @Override
    public void showConfirmDelete(Employee employee, View view) {

    }
}