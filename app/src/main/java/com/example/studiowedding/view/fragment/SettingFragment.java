package com.example.studiowedding.view.fragment;

import static android.app.ProgressDialog.show;
import static android.content.Context.MODE_PRIVATE;
import static android.media.CamcorderProfile.getAll;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.AccountAdapter;
import com.example.studiowedding.model.Account;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.account.AccountResponse;
import com.example.studiowedding.view.activity.account.ChangePassword;
import com.example.studiowedding.view.activity.account.EditInformation;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingFragment extends Fragment {
    Button btntaikhoan, btndoimatkhau;
    TextView tvVaitro, tvhoten;
    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        tvVaitro = view.findViewById(R.id.tvVaitro);
        tvhoten = view.findViewById(R.id.tvaccounthoten);
        apiService = ApiClient.getApiService();


        SharedPreferences preferences = view.getContext().getSharedPreferences("LuuIdNhanvien", MODE_PRIVATE);
        String vtro = preferences.getString("Vaitro", "");
        String idnv = preferences.getString("IdNhanvien", "");
        getAll1(idnv);

        tvVaitro.setText(vtro);
        Log.d("vaitro", vtro);


        btndoimatkhau = view.findViewById(R.id.btnDoimatkhau);
        btndoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        btntaikhoan = view.findViewById(R.id.btnTaikhoan);
        btntaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditInformation.class);
                intent.putExtra("idNhanVien", idnv);
                startActivity(intent);
            }
        });
        return view;
    }

    public void getAll1(String idNhanVien) {
        Call<AccountResponse> call = apiService.getEmployeeInfo(idNhanVien);

        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Account accountResponse = response.body().getAllthongtin();
                    Account account = accountResponse;

                    if (account != null) {
                        tvhoten.setText(account.getHoVaTen());
                        tvVaitro.setText(account.getVaiTro());
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {

            }
        });
    }

}