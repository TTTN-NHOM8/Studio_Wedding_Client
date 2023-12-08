package com.example.studiowedding.view.activity.detail_contract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateContractProductActivity extends AppCompatActivity {
    private ConstraintLayout container;
    private EditText
            contractIdEditText,
            productSelectEditText,
            priceEditText,
            dateOfHireEditText,
            dateOfReturnEditText;
    private RelativeLayout updateButton;
    private ImageView backImageView;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contract_product);

        initView();
        setListeners();

        calendar = Calendar.getInstance();
        contractIdEditText.setText(getContractDetailFromIntent().getId());
        productSelectEditText.setText(getContractDetailFromIntent().getProductName());
        priceEditText.setText(FormatUtils.formatCurrencyVietnam(getContractDetailFromIntent().getProductPrice()));
        dateOfHireEditText.setText(getContractDetailFromIntent().getDateOfHire());
        dateOfReturnEditText.setText(getContractDetailFromIntent().getDateOfReturn());
    }

    public ContractDetail getContractDetailFromIntent() {
        return (ContractDetail) getIntent().getSerializableExtra(AddContractActivity.CONTRACT_DETAIL);
    }

    private void initView() {
        contractIdEditText = findViewById(R.id.contractIdEditText);
        productSelectEditText = findViewById(R.id.productSelectEditText);
        priceEditText = findViewById(R.id.priceEditText);
        dateOfHireEditText = findViewById(R.id.dateOfHireEditText);
        dateOfReturnEditText = findViewById(R.id.dateOfReturnEditText);
        updateButton = findViewById(R.id.updateButton);
        backImageView = findViewById(R.id.backImageView);
        updateButton = findViewById(R.id.updateButton);
        container = findViewById(R.id.container);
    }

    private void setListeners() {
        backImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        dateOfHireEditText.setOnClickListener(view -> showDatePicker(dateOfHireEditText));
        dateOfReturnEditText.setOnClickListener(view -> showDatePicker(dateOfReturnEditText));
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

    // Cập nhật HĐCT với gói sản phẩm
    private void performUpdateContractDetail() {
        String dateOfHireInput = dateOfHireEditText.getText().toString().trim();
        String dateOfReturnInput = dateOfReturnEditText.getText().toString().trim();
        if (isValidDataInput(dateOfHireInput, dateOfReturnInput)) {
            try {
                ContractDetail currentContractDetail = getContractDetailFromIntent();
                currentContractDetail.setDateOfHire(FormatUtils.parserStringToDate(dateOfHireInput));
                currentContractDetail.setDateOfReturn(FormatUtils.parserStringToDate(dateOfReturnInput));

                // Gọi Api cập nhật HĐCT với sản phẩm
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<ServerResponse> call = apiService.updateContractDetailWithProduct(
                        currentContractDetail.getId(),
                        FormatUtils.formatStringToStringMySqlFormat(currentContractDetail.getDateOfHire()),
                        FormatUtils.formatStringToStringMySqlFormat(currentContractDetail.getDateOfReturn()),
                        currentContractDetail.getProductID()
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

    private boolean isValidDataInput(String dateOfHireStr, String dateOfReturnStr) {
        try {
            Date dateOfHire = FormatUtils.parserStringToDate(dateOfHireStr);
            Date dateOfReturn = FormatUtils.parserStringToDate(dateOfReturnStr);

            if (dateOfReturn.before(dateOfHire)) {
                showSnackbar("Ngày trả sản phẩm không hợp lệ");
                return false;
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void showSnackbar(String message) {
        Snackbar.make(container,message, Snackbar.LENGTH_SHORT).setAnchorView(updateButton).show();
    }
}