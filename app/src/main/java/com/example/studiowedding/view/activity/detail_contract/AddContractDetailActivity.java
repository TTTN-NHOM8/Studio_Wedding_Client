package com.example.studiowedding.view.activity.detail_contract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.studiowedding.R;
import com.example.studiowedding.interfaces.utils.FormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddContractDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText
            contractIdEditText,
            productSelectEditText,
            serviceSelectEditText,
            priceEditText,
            dateOfHireEditText,
            dateOfReturnEditText,
            locationEditText,
            dateOfPerformEditText;
    private RelativeLayout productButton, serviceButton, addButton;
    private ImageView backImageView;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contract_detail);

        initView();
        setListeners();
        displayProductUI();
        generateContractDetailCode();
    }

    private void initView() {
        contractIdEditText = findViewById(R.id.contractIdEditText);
        productSelectEditText = findViewById(R.id.productSelectEditText);
        serviceSelectEditText = findViewById(R.id.serviceSelectEditText);
        priceEditText = findViewById(R.id.priceEditText);
        dateOfHireEditText = findViewById(R.id.dateOfHireEditText);
        dateOfReturnEditText = findViewById(R.id.dateOfReturnEditText);
        locationEditText = findViewById(R.id.locationEditText);
        dateOfPerformEditText = findViewById(R.id.dateOfPerformEditText);
        productButton = findViewById(R.id.productButton);
        serviceButton = findViewById(R.id.serviceButton);
        addButton = findViewById(R.id.addButton);
        backImageView = findViewById(R.id.backImageView);
    }

    private void setListeners() {
        backImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        productButton.setOnClickListener(view -> displayProductUI());
        serviceButton.setOnClickListener(view -> displayServiceUI());
        productSelectEditText.setOnClickListener(view -> launcherProductSelectActivity());
        serviceSelectEditText.setOnClickListener(view -> launcherServieSelectActivity());
        dateOfHireEditText.setOnClickListener(view -> showDatePicker(dateOfHireEditText));
        dateOfReturnEditText.setOnClickListener(view -> showDatePicker(dateOfReturnEditText));
        dateOfPerformEditText.setOnClickListener(view -> showDatePicker(dateOfPerformEditText));
    }

    private void displayProductUI() {
        // Hiển thị view thông tin sản phẩm
        productSelectEditText.setVisibility(View.VISIBLE);
        dateOfHireEditText.setVisibility(View.VISIBLE);
        dateOfReturnEditText.setVisibility(View.VISIBLE);

        // Ẩn view thông tin dịch vụ
        serviceSelectEditText.setVisibility(View.GONE);
        locationEditText.setVisibility(View.GONE);
        dateOfPerformEditText.setVisibility(View.GONE);

        // Gán background cho button tương ứng
        productButton.setBackgroundResource(R.drawable.button_bgr_primary);
        serviceButton.setBackgroundResource(R.drawable.button_bgr_linear);
        generateContractDetailCode();
    }

    private void displayServiceUI() {
        // Hiển thị view thông tin dịch vụ
        serviceSelectEditText.setVisibility(View.VISIBLE);
        locationEditText.setVisibility(View.VISIBLE);
        dateOfPerformEditText.setVisibility(View.VISIBLE);

        // Ẩn view thông tin sản phẩm
        productSelectEditText.setVisibility(View.GONE);
        dateOfHireEditText.setVisibility(View.GONE);
        dateOfReturnEditText.setVisibility(View.GONE);

        // Gán background cho button tương ứng
        productButton.setBackgroundResource(R.drawable.button_bgr_linear);
        serviceButton.setBackgroundResource(R.drawable.button_bgr_primary);
        generateContractDetailCode();
    }

    private void launcherProductSelectActivity() {
        startActivity(new Intent(this, ProductSelectActivity.class));
    }

    private void launcherServieSelectActivity() {
        startActivity(new Intent(this, ServiceSelectActivity.class));
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

    // Tạo mã hợp đồng chi tiết: HDCT + ngày tháng năm giờ hiện tại
    private void generateContractDetailCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        String contractID = "HDCT" + currentDateAndTime;
        contractIdEditText.setText(contractID);
    }
}