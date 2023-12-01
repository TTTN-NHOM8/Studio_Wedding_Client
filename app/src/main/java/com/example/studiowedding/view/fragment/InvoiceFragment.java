package com.example.studiowedding.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ContractAdapter;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Contract;
import com.example.studiowedding.model.Customer;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.contract.AddContractActivity;
import com.example.studiowedding.view.activity.contract.FilterContractActivity;
import com.example.studiowedding.view.activity.contract.UpdateContractActivity;
import com.example.studiowedding.view.activity.detail_contract.AddContractDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceFragment extends Fragment {


    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;
    private RecyclerView rcvContract;
    private EditText edSearch;
    private TextView tvNotification;
    private List<Contract> contractList=new ArrayList<>();
    private List<Contract> originalContractList;
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_invoice, container, false);

        coordinatorLayout=view.findViewById(R.id.cdlContract);
        fab=view.findViewById(R.id.fabContract);
        rcvContract=view.findViewById(R.id.rcvContract);
        imgFilter=view.findViewById(R.id.imgFilterContract);
        edSearch=view.findViewById(R.id.edSearchContract);
        tvNotification=view.findViewById(R.id.tvNotifiicationContract);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcvContract.setLayoutManager(linearLayoutManager);
        adapter=new ContractAdapter(contractList,getContext());
        rcvContract.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Contract contract=contractList.get(position);
                String idHD=contract.getIdHopDong();
                String trangThai=contract.getTrangThaiThanhToan();

                if ("Đã thanh toán".equals(trangThai)) {
                    showAlertDialog(position, true);
                } else {
                    showAlertDialog(position, false);
                }
            }
        });

        fab.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), AddContractActivity.class));
        });

        imgFilter.setOnClickListener(view1 -> {
            showFilterDialog();

        });
        getAllContracts();
        onchangeEditTextSearch();

        return view;
    }


    private void showFilterDialog() {
        FilterContractActivity dialog = new FilterContractActivity(getContext());

        dialog.setFilterSelectedListener(selectedStatus -> {
            try {
                filterContractsByStatus(selectedStatus);
            }catch (Exception e){
                Log.i("TAG","Lỗi"+e+selectedStatus);
            }
        });

        dialog.show();
    }
    private void filterContractsByStatus(String selectedStatus) {
        if ("tất cả".equalsIgnoreCase(selectedStatus)) {
            contractList.clear();
            contractList.addAll(originalContractList);
        } else {
            List<Contract> filteredList = new ArrayList<>();

            for (Contract contract : originalContractList) {
                if (contract != null &&
                        (contract.getTrangThaiPhatSinh() != null && contract.getTrangThaiPhatSinh().equalsIgnoreCase(selectedStatus)) ||
                        (contract.getTrangThaiThanhToan() != null && contract.getTrangThaiThanhToan().equalsIgnoreCase(selectedStatus)) ||
                        (contract.getTrangThaiHopDong() != null && contract.getTrangThaiHopDong().equalsIgnoreCase(selectedStatus))) {
                    filteredList.add(contract);
                }
            }

            contractList.clear();
            contractList.addAll(filteredList);
        }

        adapter.notifyDataSetChanged();
    }
    private void onchangeEditTextSearch(){
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchTerm = charSequence.toString().toLowerCase();
                List<Contract> filteredList = new ArrayList<>();

                if (searchTerm.isEmpty()) {
                    getAllContracts();
                } else {
                    for (Contract contract : originalContractList) {
                        if (contract.getIdHopDong().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                contract.getTenKH().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                contract.getTrangThaiHopDong().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                contract.getTrangThaiPhatSinh().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                contract.getTrangThaiThanhToan().toLowerCase().contains(searchTerm.toLowerCase())) {
                            filteredList.add(contract);
                        }
                    }

                    contractList.clear();
                    contractList.addAll(filteredList);

                    if (filteredList.isEmpty()) {
                        tvNotification.setVisibility(View.VISIBLE);
                    } else {
                        tvNotification.setVisibility(View.GONE);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showAlertDialog(int position, boolean isDaThanhToan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Menu");

        CharSequence[] options;
        if (isDaThanhToan) {
            options = new CharSequence[]{"Chi tiết"};
        } else {
            options = new CharSequence[]{"Cập nhật"};
        }
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent updateIntent = new Intent(getActivity(), UpdateContractActivity.class);
                        updateIntent.putExtra("contractList", contractList.get(position));
                        startActivity(updateIntent);
                        break;
                    case 1:
                        comfirmDeleteDialog(position);
                        break;
                    default:
                        break;
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void comfirmDeleteDialog(int posititon){
        Contract contract=contractList.get(posititon);
        String idHD =contract.getIdHopDong();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Xác nhận xoá HĐCT: " + idHD);
        builder.setNegativeButton("Huỷ", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton("Xoá", (dialogInterface, i) -> {
            deleteContract(idHD);
        });
        builder.show();

    }
    private void getAllContracts(){
        ApiService apiService=ApiClient.getClient().create(ApiService.class);
        Call<List<Contract>>call=apiService.getContracts();

        call.enqueue(new Callback<List<Contract>>() {
            @Override
            public void onResponse(Call<List<Contract>> call, Response<List<Contract>> response) {
                if(response.isSuccessful()){
                    originalContractList = new ArrayList<>(response.body());
                    contractList.clear();
                    contractList.addAll(originalContractList);
                    adapter.notifyDataSetChanged();
                }else{
                    Log.i("TAG","Lỗi");
                }
            }

            @Override
            public void onFailure(Call<List<Contract>> call, Throwable t) {
                Log.i("TAG","Lỗi" +t.getMessage());
            }
        });
    }

    private void deleteContract(String idHD){
        ApiService apiService=ApiClient.getClient().create(ApiService.class);
        Call<Void>call=apiService.deleteContract(idHD);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    getAllContracts();
                    Toast.makeText(getContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                Log.i("TAG", "Lỗi"+t.getMessage());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllContracts();
    }
}