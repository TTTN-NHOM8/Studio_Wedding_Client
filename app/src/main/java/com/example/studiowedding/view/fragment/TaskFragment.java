package com.example.studiowedding.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.TaskAdapter;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Task;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.task.ResponseTask;
import com.example.studiowedding.view.activity.task.UpdateTaskActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskFragment extends Fragment implements OnItemClickListner.TaskI {
    private SearchView searchView;
    private RecyclerView mRCV;
    private ImageView ivFilter, ivCancelFilter;
    private ProgressDialog mProgressDialog;
    private List<Task> mList;
    private TaskAdapter adapterTask;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        onClick();
        readTasksApi();
    }

    private void initUI(View view) {
        mRCV = view.findViewById(R.id.rcv_task);
        ivFilter = view.findViewById(R.id.iv_filter_task);
        searchView = view.findViewById(R.id.et_search_task);
        ivCancelFilter = view.findViewById(R.id.iv_cancel_filter_update_job);
        ivCancelFilter.setVisibility(View.GONE);
    }

    private void onClick() {
        ivFilter.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    R.style.CustomDatePickerDialog,
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        ivCancelFilter.setVisibility(View.VISIBLE);
                        ivFilter.setVisibility(View.GONE);

                        // Tạo một Calendar object từ ngày được chọn
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

                        // Lọc danh sách theo ngày được chọn
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
                adapterTask.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterTask.getFilter().filter(s);
                return true;
            }
        });
        ivCancelFilter.setOnClickListener(view -> {
            ivCancelFilter.setVisibility(View.GONE);
            ivFilter.setVisibility(View.VISIBLE);
            adapterTask.setList(mList);
        });
    }

    private void setAdapter(List<Task> taskList) {
        adapterTask = new TaskAdapter(taskList, 1);
        adapterTask.setOnClickItem(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRCV.setLayoutManager(layoutManager);
        mRCV.setAdapter(adapterTask);
        mList = taskList;
    }
//
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

    public void readTasksApi() {
        ApiClient.getClient().create(ApiService.class).readTask().enqueue(new Callback<ResponseTask>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTask> call, @NonNull Response<ResponseTask> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        setAdapter(response.body().getTaskList());
                    }else {
                        Toast.makeText(getContext(), "Call Api Failure", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTask> call, @NonNull Throwable t) {
                Log.e("Error", t.toString());
            }
        });
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
    public void nextUpdateScreenTask(Task task) {
        Intent intent = new Intent(getActivity(), UpdateTaskActivity.class);
        intent.putExtra("task", task);
        startActivity(intent);
    }

    @Override
    public void showConfirmDelete(Task task, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa công việc")
                .setMessage("Bạn chắc chắn muốn xóa công việc này ?")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    mProgressDialog = ProgressDialog.show(getContext(), "", "Loading...");
                    deleteTaskApi(task, view);
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onStart() {
        super.onStart();
        readTasksApi();
    }
}