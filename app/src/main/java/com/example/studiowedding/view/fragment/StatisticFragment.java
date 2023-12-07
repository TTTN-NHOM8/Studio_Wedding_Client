package com.example.studiowedding.view.fragment;



import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.example.studiowedding.R;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.account.AccountResponse;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class StatisticFragment extends Fragment {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

    ConstraintLayout buttonPickDate, buttonPickMonth, btnNam;
    private ApiService apiService;

    private TextView textViewResult, tvgia, tvDon, tvthang, tvtienthang, tvdtnam1, tvnama, tvngaychon, namchon;




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        apiService = ApiClient.getApiService();

        textViewResult = view.findViewById(R.id.tvngaydoanhthu);
        buttonPickDate = view.findViewById(R.id.CsChonngay);
        tvgia = view.findViewById(R.id.tvGiatien);
        tvthang = view.findViewById(R.id.tvtThang);
        tvtienthang = view.findViewById(R.id.tvtienthang);
        tvDon = view.findViewById(R.id.thangdt);
        btnNam = view.findViewById(R.id.csnam);
        tvdtnam1 = view.findViewById(R.id.tvdtnam);
        tvnama = view.findViewById(R.id.tvnam0);
        tvngaychon = view.findViewById(R.id.tvSoluongdon);
        namchon = view.findViewById(R.id.namdt);
        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickeryert();

            }
        });

        buttonPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        buttonPickMonth = view.findViewById(R.id.thang);
        buttonPickMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerMonth();

            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String selectedDate = formatDate(selectedDay, selectedMonth + 1, selectedYear);
                        textViewResult.setText(selectedDate);
                        tvngaychon.setText(String.valueOf(selectedDay));
                        getRevenueByDate(String.valueOf(selectedDate));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
    private void getRevenueByDate(String selectedDate) {
        Call<AccountResponse> call = apiService.getDailyRevenue(selectedDate);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse accountResponse = response.body();
                    String status = accountResponse.getStatus();
                    if (response.body() != null) {
                        String revenue = String.valueOf(accountResponse.getTotalRevenue());
                        String don = String.valueOf(accountResponse.getSoluong());
                        Log.d("ngaytien", revenue);
                        tvgia.setText(revenue);

                    } else {
                        tvgia.setText("Dữ liệu trả về từ server là null");
                    }
                } else {
                    tvgia.setText("Có lỗi khi gọi API: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.e("API Error", "Call failed: " + t.getMessage());
                textViewResult.setText("Có lỗi khi gọi API");
            }
        });
    }


    private void showDatePickerMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String selectedDate = formatMonth(selectedDay, selectedMonth + 1, selectedYear);
                        tvthang.setText(selectedDate);
                        // Assuming tvDon is your TextView for displaying the month
                        tvDon.setText(String.valueOf(selectedMonth + 1));

                        getDailyMonth(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }


    private void showDatePickeryert() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String selectedDate = formatMonth(selectedDay, selectedMonth + 1, selectedYear);
                        tvnama.setText(selectedDate);
                        namchon.setText(String.valueOf(selectedYear));
                        getDailyNam(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private String formatMonth(int day, int month, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set( year, month - 1, day);
        return sdf.format(calendar.getTime());
    }




    private void getDailyMonth(String selectedMonth) {
        Call<AccountResponse> call = apiService.getDailyRevenuemonth(selectedMonth);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse accountResponse1 = response.body();
                    if (response.body() != null) {
                        String doanhthuthang = String.valueOf(accountResponse1.getDoanhthuthang());
                        Log.d("ngaytien1", doanhthuthang);
                        tvtienthang.setText(doanhthuthang);
                    } else {
                        tvtienthang.setText("Dữ liệu trả về từ server là null");
                    }
                } else {
                    tvtienthang.setText("Có lỗi khi gọi API: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.e("API Error", "Call failed: " + t.getMessage());
            }
        });
    }


    private String formatDate(int day, int month, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return sdf.format(calendar.getTime());
    }


    private void getDailyNam(String selectedyert) {
        Call<AccountResponse> call = apiService.getDailyRevenueyert(selectedyert);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse accountResponse1 = response.body();
                    if (response.body() != null) {
                        String doanhthunam = String.valueOf(accountResponse1.getDoanhthunam());
                        tvdtnam1.setText(doanhthunam);
                    } else {
                        tvdtnam1.setText("Dữ liệu trả về từ server là null");
                    }
                } else {
                    tvdtnam1.setText("Có lỗi khi gọi API: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.e("API Error", "Call failed: " + t.getMessage());
            }
        });
    }




}
