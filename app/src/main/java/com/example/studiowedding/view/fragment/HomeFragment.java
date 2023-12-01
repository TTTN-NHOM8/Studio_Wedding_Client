package com.example.studiowedding.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import com.example.studiowedding.view.activity.task.UpdateTaskActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment  {
    private RecyclerView mRCV, mRCVToday;
    private List<Task> mList;
    private List<Task> mListToday;
    private TaskAdapter adapterTask;
    private TaskTodayAdapter taskTodayAdapter;
    private ProgressDialog mProgressDialog;
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
        mRCV = view.findViewById(R.id.rcv_job_home);
        mRCVToday = view.findViewById(R.id.rcv_today_job_home);
        onClick();
//        readTasksApi();
    }

    private void onClick() {

    }

//    private void readTasksApi() {
//        ApiClient.getClient().create(ApiService.class).readTask().enqueue(new Callback<ResponseTask>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseTask> call, @NonNull Response<ResponseTask> response) {
//                if (response.isSuccessful()){
//                    assert response.body() != null;
//                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
//                        setAdapter(response.body().getTaskList());
//                        setAdapterToday(response.body().getTaskList());
//                    }else {
//                        Toast.makeText(getContext(), "Call Api Failure", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseTask> call, @NonNull Throwable t) {
//                Log.e("Error", t.toString());
//            }
//        });
//    }

//    private void setAdapter(List<Task> taskList) {
//        adapterTask = new TaskAdapter(taskList);
//        adapterTask.setOnClickItem(this);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRCV.setLayoutManager(layoutManager);
//        mRCV.setAdapter(adapterTask);
//        mList= taskList;
//    }
//
//    private void setAdapterToday(List<Task> taskList) {
//        List<Task> list = new ArrayList<>();
//        for(int i = 0 ; i < taskList.size() ; i ++){
//            if (taskList.get(i).getDateImplement() != null){
//                if (FormatUtils.checkData(taskList.get(i).getDateImplement())){
//                    list.add(taskList.get(i));
//                }
//            }else {
//                if (FormatUtils.checkData(taskList.get(i).getDataLaundry())){
//                    list.add(taskList.get(i));
//                }
//            }
//
//        }
//
//        taskTodayAdapter = new TaskTodayAdapter(list);
//        taskTodayAdapter.setOnClickItem(this);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRCVToday.setLayoutManager(layoutManager);
//        mRCVToday.setAdapter(taskTodayAdapter);
//        mListToday = list;
//    }
//
//    public void deleteTaskApi(Task task, View view){
//        ApiClient.getClient().create(ApiService.class).deleteTaskById(task.getIdTask()).enqueue(new Callback<ResponseTask>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseTask> call, @NonNull Response<ResponseTask> response) {
//                mProgressDialog.dismiss();
//                if (response.isSuccessful()){
//                    assert response.body() != null;
//                    if (AppConstants.STATUS_TASK.equals(response.body().getStatus())){
//                        mList.remove(task);
//                        adapterTask.setList(mList);
//                        if (mListToday.contains(task)){
//                            mListToday.remove(task);
//                            taskTodayAdapter.setList(mListToday);
//                        }
//                        Snackbar.make(view,"Xóa thành công", Snackbar.LENGTH_SHORT).show();
//                    }else {
//                        Snackbar.make(view,"Xóa thất bại", Snackbar.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Snackbar.make(view,"Xóa thất bại", Snackbar.LENGTH_SHORT).show();
//                }
//            }

//            @Override
//            public void onFailure(@NonNull Call<ResponseTask> call, @NonNull Throwable t) {
//
//            }
//        });
////    }
//    @Override
//    public void nextUpdateScreenTask(Task task) {
//        Intent intent = new Intent(getActivity(), UpdateTaskActivity.class);
//        intent.putExtra("task", task);
//        startActivity(intent);
//    }
//    @Override
//    public void showConfirmDelete(Task task, View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Xóa công việc")
//                .setMessage("Bạn chắc chắn muốn xóa công việc này ?")
//                .setPositiveButton("Đồng ý", (dialog, which) -> {
//                    mProgressDialog = ProgressDialog.show(getContext(), "", "Loading...");
//                    deleteTaskApi(task, view);
//                })
//                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        readTasksApi();
//    }
}