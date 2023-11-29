package com.example.studiowedding.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.TabServiceAdapter;
import com.example.studiowedding.model.Services;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductAndServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductAndServicesFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_product_and_services, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.vp_service);


//        mAdapter = new MyRecyclerViewAdapter(dataSanPham, null); // Ban đầu hiển thị dữ liệu của "Sản Phẩm"
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(mAdapter);

        TabServiceAdapter adapter = new TabServiceAdapter(getActivity());
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
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
}