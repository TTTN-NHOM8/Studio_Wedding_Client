package com.example.studiowedding.view.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.TaskAdapter;
import com.example.studiowedding.adapter.TaskTodayAdapter;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.model.Task;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.example.studiowedding.view.activity.task.ResponseTask;
import com.example.studiowedding.view.activity.task.SeeTaskActivity;
import com.example.studiowedding.view.activity.task.UpdateTaskActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnItemClickListner.TaskI {
    private RecyclerView mRCV, mRCVToday;
    private TextView tvSeeTask, tvSeeTaskToday, tvShowToday, tvShow;
    private List<Task> mList;
    private List<Task> mListToday;
    private TaskAdapter adapterTask;
    private TaskTodayAdapter taskTodayAdapter;
    private ProgressDialog mProgressDialog;
    private String role, idEmployee;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        onClick();
    }

    private void checkRoleCallApi() {
        SharedPreferences preferences = getActivity().getSharedPreferences("LuuIdNhanvien", MODE_PRIVATE);
         role = preferences.getString("Vaitro", "");
         idEmployee = preferences.getString("IdNhanvien", "");

        if (AppConstants.ROLE.equals(role)){
            readTasksApi();
        }else {
            readTasksByIdEmployeeApi(idEmployee);
        }
    }

    private void initUI(View view) {
        tvShow = view.findViewById(R.id.tv_show_home_fragment);
        tvShowToday = view.findViewById(R.id.tv_show_today_home_fragment);
        mRCV = view.findViewById(R.id.rcv_job_home);
        mRCVToday = view.findViewById(R.id.rcv_today_job_home);
        tvSeeTask = view.findViewById(R.id.tv_see_all_job);
        tvSeeTaskToday = view.findViewById(R.id.tv_see_all_job_today);
    }

    private void onClick() {
        tvSeeTask.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SeeTaskActivity.class);
            intent.putExtra("seeJob", 0);
            startActivity(intent);
        });
        tvSeeTaskToday.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SeeTaskActivity.class);
            intent.putExtra("seeJob", 1);
            startActivity(intent);
        });
    }

    private void readTasksApi() {
        ApiClient.getClient().create(ApiService.class).readTask().enqueue(new Callback<ResponseTask>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTask> call, @NonNull Response<ResponseTask> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        setAdapter(response.body().getTaskList());
                        setAdapterToday(response.body().getTaskList());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTask> call, @NonNull Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    private void readTasksByIdEmployeeApi(String idEmployee) {
        ApiClient.getClient().create(ApiService.class).readTaskByIdEmployee(idEmployee).enqueue(new Callback<ResponseTask>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTask> call, @NonNull Response<ResponseTask> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
                        setAdapter(response.body().getTaskList());
                        setAdapterToday(response.body().getTaskList());
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
        Collections.reverse(taskList);
        adapterTask = new TaskAdapter(taskList, 0, role);
        adapterTask.setOnClickItem(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRCV.setLayoutManager(layoutManager);
        mRCV.setAdapter(adapterTask);
        mList= taskList;
        tvShow.setVisibility(View.GONE);
    }

    private void setAdapterToday(List<Task> taskList) {
        List<Task> list = new ArrayList<>();
        int check = 0;
        for(int i = 0 ; i < taskList.size() ; i ++){
            if (taskList.get(i).getDateImplement() != null){
                if (FormatUtils.checkData(taskList.get(i).getDateImplement())){
                    list.add(taskList.get(i));
                    check++;
                }
            }
        }
        if (check > 0){
            tvShowToday.setVisibility(View.GONE);
        }
        taskTodayAdapter = new TaskTodayAdapter(list, 0, role);
        taskTodayAdapter.setOnClickItem(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRCVToday.setLayoutManager(layoutManager);
        mRCVToday.setAdapter(taskTodayAdapter);
        mListToday = list;
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
    public void nextUpdateScreenTask(Task task, String role) {
        Intent intent = new Intent(getActivity(), UpdateTaskActivity.class);
        intent.putExtra("task", task);
        intent.putExtra("role", role);
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
        checkRoleCallApi();
    }
}