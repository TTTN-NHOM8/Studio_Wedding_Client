package com.example.studiowedding.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.TaskAdapter;
import com.example.studiowedding.model.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView mRCV, mRCVToday;

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
        setAdapter();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRCV.setLayoutManager(layoutManager);
        mRCV.setAdapter(adapterTask);

        TaskAdapter adapterTask1 = new TaskAdapter(list);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        mRCVToday.setLayoutManager(layoutManager1);
        mRCVToday.setAdapter(adapterTask1);
    }
}