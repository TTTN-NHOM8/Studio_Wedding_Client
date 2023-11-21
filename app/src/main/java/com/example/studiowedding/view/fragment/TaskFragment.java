package com.example.studiowedding.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.TaskAdapter;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Task;
import com.example.studiowedding.view.activity.task.UpdateTaskActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskFragment extends Fragment implements OnItemClickListner.TaskI {

    private RecyclerView mRCV;
    private ImageView ivFilter;
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
        mRCV = view.findViewById(R.id.rcv_task);
        ivFilter = view.findViewById(R.id.iv_filter_task);
        setAdapter();
        onClick();
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
                    (DatePickerDialog.OnDateSetListener) (datePicker, selectedYear, selectedMonth, selectedDay) -> {

                    },
                    year,
                    month,
                    dayOfMonth
            );

            // Hiển thị DatePickerDialog
            datePickerDialog.show();
        });
    }

    private void setAdapter() {
        List<Task> list = new ArrayList<>();
        List<String> listEmployee = new ArrayList<>();
        listEmployee.add("AnhNN");
        listEmployee.add("NamNN");

        list.add(new Task("HD001", "12/12/2023","Đang thực hiện", "Chụp hình cưới", "Sơn Trà - Đà Nẵng", listEmployee));
        list.add(new Task("HD002", "12/12/2023","Đã xong", "Chụp hình cưới", "Sơn Trà - Đà Nẵng", listEmployee));
        list.add(new Task("HD003", "12/12/2023","Đang thực hiện", "Chụp hình cưới", "Sơn Trà - Đà Nẵng", listEmployee));

        TaskAdapter adapterTask = new TaskAdapter(list);
        adapterTask.setOnClickItem(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRCV.setLayoutManager(layoutManager);
        mRCV.setAdapter(adapterTask);
    }

    @Override
    public void nextUpdateScreenTask(Task task) {
        startActivity(new Intent(getActivity(), UpdateTaskActivity.class));
    }

    @Override
    public void showConfirmDelete() {
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
}