package com.example.studiowedding.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ContractAdapter;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Contract;
import com.example.studiowedding.view.activity.contract.AddContractActivity;
import com.example.studiowedding.view.activity.contract.FilterContractActivity;
import com.example.studiowedding.view.activity.contract.UpdateContractActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceFragment extends Fragment {


    private FloatingActionButton fab;
    private RecyclerView rcvContract;
    private List<Contract> contractList=new ArrayList<>();
    private ContractAdapter adapter;
    private ImageView imgFilter;

    public InvoiceFragment() {
        // Required empty public constructor
    }


    public static InvoiceFragment newInstance(String param1, String param2) {
        InvoiceFragment fragment = new InvoiceFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_invoice, container, false);

        fab=view.findViewById(R.id.fabContract);
        rcvContract=view.findViewById(R.id.rcvContract);
        imgFilter=view.findViewById(R.id.imgFilterContract);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcvContract.setLayoutManager(linearLayoutManager);
        contractList = generateSampleData();
        adapter=new ContractAdapter(contractList,getContext());
        rcvContract.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                showAlertDialog(position);
            }
        });

        fab.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), AddContractActivity.class));
        });

        imgFilter.setOnClickListener(view1 -> {
            FilterContractActivity dialog=new FilterContractActivity(getContext());
            dialog.show();

        });

        return view;
    }

    public static List<Contract> generateSampleData() {
        List<Contract> contractList = new ArrayList<>();

        Date currentDate = new Date();

        for (int i = 1; i <= 20; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            Date randomDate = calendar.getTime();

            Contract contract = new Contract(
                    "HD20231112192" + i,
                    randomDate,
                    currentDate,
                    1000.0f,
                    200.0f,
                    5000.0f,
                    (i % 2 == 0) ? "Đã thanh toán" : "Chưa thanh toán",
                    (i % 3 == 0) ? "Đã hoàn thành" : "Đang thực hiện",
                    (i % 4 == 0) ? "Có phát sinh" : "Không có phát sinh",
                    1,
                    i,
                    "Khách hàng " + i
            );

            contractList.add(contract);
        }

        return contractList;
    }


    private void showAlertDialog(int position) {
        final CharSequence[] options = {"Cập nhật", "Xoá"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Menu")
                .setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                startActivity(new Intent(getActivity(), UpdateContractActivity.class));
                                break;
                            case 1:
                                Toast.makeText(getContext(), "Xoá"+position, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    @SuppressLint("RestrictedApi")
//    private void showPopupMenu(View v, int position) {
//        PopupMenu popupMenu = new PopupMenu(getContext(), v);
//        MenuInflater inflater = popupMenu.getMenuInflater();
//        inflater.inflate(R.menu.popup_menu_options, popupMenu.getMenu());
//
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_update:
//                        startActivity(new Intent(getActivity(), UpdateContractActivity.class));
//                        return true;
//                    case R.id.action_delete:
//                        Toast.makeText(getContext(), "Xoá"+position, Toast.LENGTH_SHORT).show();
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        });
//        popupMenu.show();
//
//    }

}