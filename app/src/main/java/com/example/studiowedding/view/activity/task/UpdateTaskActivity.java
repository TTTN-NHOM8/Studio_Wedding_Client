package com.example.studiowedding.view.activity.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.studiowedding.R;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.model.Task;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTaskActivity extends AppCompatActivity {
    private EditText etName, etDate, etAddress, etNote;
    private ImageView ivSelect, ivBack;
    private LinearLayout btnSave;
    private Task mTask;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        mTask = (Task) getIntent().getSerializableExtra("task");
        initUI();
        setValue();
        onClick();
    }

    private void onClick() {
        ivSelect.setOnClickListener(view -> openPopupMenu());
        ivBack.setOnClickListener(view -> onBackPressed());
        btnSave.setOnClickListener(view -> {
            mProgressDialog = ProgressDialog.show(this, "", "Loading...");
            saveTask();
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
    }

    private void setValue() {
        if (mTask.getDateImplement() != null){
            etName.setText(mTask.getNameService());
            etDate.setText(FormatUtils.formatDateToString(mTask.getDateImplement()));
            etAddress.setText(mTask.getAddress());
            etNote.setText(mTask.getStatusTask());
        }else {
            etName.setText(AppConstants.NAME_TASK);
            etDate.setText(FormatUtils.formatDateToString(mTask.getDataLaundry()));
            etAddress.setText(AppConstants.ADDRESS_TASK);
            etNote.setText(mTask.getStatusTask());
        }

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
}