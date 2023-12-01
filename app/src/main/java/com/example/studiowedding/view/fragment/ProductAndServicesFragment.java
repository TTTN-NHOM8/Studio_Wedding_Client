package com.example.studiowedding.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.TabServiceAdapter;
import com.example.studiowedding.model.Services;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductAndServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductAndServicesFragment extends Fragment {

    private ImageView iv_product;

    private TabLayout tabLayout;
    private ViewPager2 vp_service;

    List<Services> list = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductAndServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductAndServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductAndServicesFragment newInstance(String param1, String param2) {
        ProductAndServicesFragment fragment = new ProductAndServicesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_product_and_services, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        vp_service = view.findViewById(R.id.vp_service);


//        mAdapter = new MyRecyclerViewAdapter(dataSanPham, null); // Ban đầu hiển thị dữ liệu của "Sản Phẩm"
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(mAdapter);

        TabServiceAdapter adapter = new TabServiceAdapter(getActivity());
        vp_service.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, vp_service, (tab, position) -> {
            switch (position){
                case 0 :
                    tab.setText("Dịch vụ");
                    break;
                case 1 :
                    tab.setText("Sản phẩm");
                    break;

            }
        }).attach();

        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vp_service = view.findViewById(R.id.vp_service);
        iv_product = view.findViewById(R.id.iv_product);

        onClick();
    }
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
    private void onClick() {
        iv_product.setOnClickListener(view -> {
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
    public void onDeleteButtonClick(int position) {

    }
}