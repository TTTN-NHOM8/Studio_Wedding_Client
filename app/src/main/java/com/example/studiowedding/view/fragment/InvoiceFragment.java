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

import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        adapter=new ContractAdapter(contractList,getContext());
        rcvContract.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Contract contract=contractList.get(position);
                String idHD=contract.getIdHopDong();
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
        getAllContracts();

        return view;
    }


    private void showAlertDialog(int position) {
        final CharSequence[] options = {"Cập nhật", "Xoá"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Menu")
                .setItems(options, new DialogInterface.OnClickListener() {
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
                    contractList.clear();
                    contractList.addAll(response.body());
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

    private void deleteContract( String idHD){
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