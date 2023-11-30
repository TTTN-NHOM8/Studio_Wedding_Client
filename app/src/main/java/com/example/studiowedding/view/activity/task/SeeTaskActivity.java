package com.example.studiowedding.view.activity.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.TaskAdapter;
import com.example.studiowedding.adapter.TaskTodayAdapter;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Task;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeTaskActivity extends AppCompatActivity implements OnItemClickListner.TaskI {
    private TextView tvTitle;
    private ImageView ivBack, ivFilter, ivCancelFilter;
    private RecyclerView mRCV;
    private ProgressDialog mProgressDialog;
    private SearchView searchView;
    private List<Task> mList;
    private List<Task> mListToday;
    private TaskAdapter adapterTask;
    private TaskTodayAdapter taskTodayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_task);
        initUI();
        onClick();
        readTasksApi();
    }

    private void initUI() {
        tvTitle = findViewById(R.id.tv_title_see_job);
        mRCV = findViewById(R.id.rcv_see_job);
        ivBack = findViewById(R.id.iv_back_see_job);
        ivFilter = findViewById(R.id.iv_filter_see_job);
        searchView = findViewById(R.id.et_search_see_job);
        ivCancelFilter = findViewById(R.id.iv_cancel_filter_see_job);
        ivCancelFilter.setVisibility(View.GONE);
    }


    private void onClick() {
        ivBack.setOnClickListener(view -> onBackPressed());

        ivFilter.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    R.style.CustomDatePickerDialog,
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        ivCancelFilter.setVisibility(View.VISIBLE);
                        ivFilter.setVisibility(View.GONE);

                        // Tạo một Calendar object từ ngày được chọn
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

                        List<Task> list = mList;
                        List<Task> filteredTasks = filterTasksByDate(list, selectedCalendar.getTime());
                        adapterTask.setList(filteredTasks);

                    },
                    year,
                    month,
                    dayOfMonth
            );

            datePickerDialog.show();
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (getIntent().getIntExtra("seeJob", 2) == 0){
                    adapterTask.getFilter().filter(s);
                }else {
                    taskTodayAdapter.getFilter().filter(s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (getIntent().getIntExtra("seeJob", 2) == 0){
                    adapterTask.getFilter().filter(s);
                }else {
                    taskTodayAdapter.getFilter().filter(s);
                }
                return true;
            }
        });

        ivCancelFilter.setOnClickListener(view -> {
            ivCancelFilter.setVisibility(View.GONE);
            ivFilter.setVisibility(View.VISIBLE);
            adapterTask.setList(mList);
        });
    }

    // Hàm lọc danh sách theo ngày
    private List<Task> filterTasksByDate(List<Task> taskList, Date selectedDate) {
        return taskList.stream()
                .filter(task -> task.getDateImplement() != null && isSameDay(task.getDateImplement(), selectedDate))
                .collect(Collectors.toList());
    }

    // Hàm kiểm tra xem hai ngày có phải là cùng một ngày hay không
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    private void checkIntent(List<Task> taskList) {
        if (getIntent().getIntExtra("seeJob", 2) == 0){
            setAdapter(taskList);
            tvTitle.setText("Danh sách công việc");
        }else {
            setAdapterToday(taskList);
            tvTitle.setText("Danh sách công việc hôm nay");
            ivFilter.setVisibility(View.GONE);
            ivFilter.setVisibility(View.GONE);
        }
    }

    private void readTasksApi() {
        ApiClient.getClient().create(ApiService.class).readTask().enqueue(new Callback<ResponseTask>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTask> call, @NonNull Response<ResponseTask> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        checkIntent(response.body().getTaskList());
                    }else {
                        Toast.makeText(getApplicationContext(), "Call Api Failure", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTask> call, @NonNull Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    private void setAdapter(List<Task> taskList) {
        adapterTask = new TaskAdapter(taskList, 1);
        adapterTask.setOnClickItem(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRCV.setLayoutManager(layoutManager);
        mRCV.setAdapter(adapterTask);
        mList= taskList;
    }

    private void setAdapterToday(List<Task> taskList) {
        List<Task> list = new ArrayList<>();
        for(int i = 0 ; i < taskList.size() ; i ++){
            if (taskList.get(i).getDateImplement() != null){
                if (FormatUtils.checkData(taskList.get(i).getDateImplement())){
                    list.add(taskList.get(i));
                }
            }
        }

        taskTodayAdapter = new TaskTodayAdapter(list, 1);
        taskTodayAdapter.setOnClickItem(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRCV.setLayoutManager(layoutManager);
        mRCV.setAdapter(taskTodayAdapter);
        mListToday = list;
    }

    @Override
    public void nextUpdateScreenTask(Task task) {
        if (AppConstants.STATUS_TASK_DONE.equals(task.getStatusTask())){
            Snackbar.make(mRCV, "Cập việc đã xong không thể cập nhật", Snackbar.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, UpdateTaskActivity.class);
            intent.putExtra("task", task);
            startActivity(intent);
        }
    }
    @Override
    public void showConfirmDelete(Task task, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa công việc")
                .setMessage("Bạn chắc chắn muốn xóa công việc này ?")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    mProgressDialog = ProgressDialog.show(this, "", "Loading...");
                    deleteTaskApi(task, view);
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void deleteTaskApi(Task task, View view){
        ApiClient.getClient().create(ApiService.class).deleteTaskById(task.getIdTask()).enqueue(new Callback<ResponseTask>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTask> call, @NonNull Response<ResponseTask> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        mList.remove(task);
                        adapterTask.setList(mList);
                        if (mListToday.contains(task)){
                            mListToday.remove(task);
                            taskTodayAdapter.setList(mListToday);
                        }
                        Snackbar.make(view,"Xóa thành công", Snackbar.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(view,"Xóa thất bại", Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(view,"Xóa thất bại", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTask> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        readTasksApi();
    }

}