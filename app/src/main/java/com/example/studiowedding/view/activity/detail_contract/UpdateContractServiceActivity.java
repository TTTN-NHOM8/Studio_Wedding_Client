package com.example.studiowedding.view.activity.detail_contract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.model.ContractDetail;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.example.studiowedding.view.activity.contract.AddContractActivity;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateContractServiceActivity extends AppCompatActivity {
    private EditText
            contractIdEditText,
            serviceSelectEditText,
            priceEditText,
            locationEditText,
            dateOfPerformEditText;
    private RelativeLayout updateButton;
    private ImageView backImageView;
    private Calendar calendar;
    private ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contract_service);
        calendar = Calendar.getInstance();
        initView();
        setListeners();

        contractIdEditText.setText(getContractDetailFromIntent().getId());
        serviceSelectEditText.setText(getContractDetailFromIntent().getServiceName());
        priceEditText.setText(FormatUtils.formatCurrencyVietnam(getContractDetailFromIntent().getServicePrice()));
        dateOfPerformEditText.setText(getContractDetailFromIntent().getDateOfPerform());
        locationEditText.setText(getContractDetailFromIntent().getLocation());
    }

    public ContractDetail getContractDetailFromIntent() {
        return (ContractDetail) getIntent().getSerializableExtra(AddContractActivity.CONTRACT_DETAIL);
    }

    private void initView() {
        contractIdEditText = findViewById(R.id.contractIdEditText);
        serviceSelectEditText = findViewById(R.id.serviceSelectEditText);
        priceEditText = findViewById(R.id.priceEditText);
        locationEditText = findViewById(R.id.locationEditText);
        dateOfPerformEditText = findViewById(R.id.dateOfPerformEditText);
        updateButton = findViewById(R.id.updateButton);
        backImageView = findViewById(R.id.backImageView);
        updateButton = findViewById(R.id.updateButton);
        container = findViewById(R.id.container);
    }

    private void setListeners() {
        backImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        dateOfPerformEditText.setOnClickListener(view -> showDatePicker(dateOfPerformEditText));
        updateButton.setOnClickListener(view -> performUpdateContractDetail());
    }

    /**
     * Hiển thị hộp thoại chọn ngày
     *
     * @param editText input khi nhấn
     */
    private void showDatePicker(EditText editText) {
        try {
            if (editText.getText().toString().isEmpty()) {
                calendar = Calendar.getInstance();
            } else {
                Date date = FormatUtils.parserStringToDate(editText.getText().toString());
                calendar.setTime(date);
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String selectedDate = FormatUtils.formatDateToString(calendar.getTime());
                        editText.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Thực hiện cập nhật HĐCT với gói dịch vụ
    private void performUpdateContractDetail() {
        String location = locationEditText.getText().toString().trim();
        String dateOfPerformInput = dateOfPerformEditText.getText().toString().trim();

        if (isValidDataInputService(location, dateOfPerformInput)) {
            try {
                ContractDetail currentContractDetail = getContractDetailFromIntent();
                currentContractDetail.setDateOfPerform(FormatUtils.parserStringToDate(dateOfPerformInput));
                currentContractDetail.setLocation(location);

                // Gọi Api cập nhật HĐCT với dịch vụ
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<ServerResponse> call = apiService.updateContractDetailWithService(
                        currentContractDetail.getId(),
                        currentContractDetail.getLocation(),
                        FormatUtils.formatStringToStringMySqlFormat(currentContractDetail.getDateOfPerform()),
                        currentContractDetail.getServiceID()
                );

                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if (response.isSuccessful()) {
                            ServerResponse serverResponse = response.body();
                            if (serverResponse == null) return;
                            if (serverResponse.isSuccess()) {
                                showSnackbar("Cập nhật thành công");
                                new Handler().postDelayed(() -> finish(), 1000);
                            } else if (serverResponse.isFailure()) {
                                showSnackbar("Không có cập nhật nào");
                                new Handler().postDelayed(() -> finish(), 1000);
                            } else {
                                showSnackbar("Xảy ra lỗi khi cập nhật");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {

                    }
                });
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // Kiểm tra tính hợp lệ của dữ liệu đầu vào với dịch vụ
    public boolean isValidDataInputService(String location, String dateOfPerform) {
        if (location.isEmpty()) {
            showSnackbar("Vui lòng nhập địa điểm");
            return false;
        }

        if (!isValidLocation(location)) {
            showSnackbar("Địa điểm không hợp lệ");
            return false;
        }

        if (dateOfPerform.isEmpty()) {
            showSnackbar("Vui lòng chọn thực hiện");
            return false;
        }

        return true;
    }

    /**
     * Xác thực nội dung địa điểm có hợp lệ hay không
     *
     * @param location nôi dụng địa điểm cần kiểm tra
     * @return true nếu không chưa ký tự đặt biệt, flase chưa ký tự đặt biệt
     */
    public boolean isValidLocation(String location) {
        // Định dạng cho phép chứa ký tự chữ hoặc số
        final Pattern VALID_LOCATION_PATTERN = Pattern.compile("^[\\w\\s\\u0100-\\u1FFF,.-]+$");
        return VALID_LOCATION_PATTERN.matcher(location).matches();
    }

    private void showSnackbar(String message) {
        Snackbar.make(container,message, Snackbar.LENGTH_SHORT).show();
    }
}