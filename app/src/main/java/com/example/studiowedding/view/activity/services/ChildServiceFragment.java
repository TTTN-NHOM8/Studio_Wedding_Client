package com.example.studiowedding.view.activity.services;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ProductAdapter;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildServiceFragment extends Fragment implements OnItemClickListner.Child {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnItemClickListner.TaskI mOnClickItem;
    private String mParam2;

    private List<Product> dataList;
    private ProductAdapter adapter;

    public ChildServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildServiceFragment newInstance(String param1, String param2) {
        ChildServiceFragment fragment = new ChildServiceFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_child_service, container, false);


        return view;
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

    @Override
    public void onDeleteButtonClick(int position) {

    }

}