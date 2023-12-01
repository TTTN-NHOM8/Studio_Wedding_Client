package com.example.studiowedding.view.activity.detail_contract;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContractDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText
            contractIdEditText,
            productSelectEditText,
            serviceSelectEditText,
            productPriceEditText,
            dateOfHireEditText,
            dateOfReturnEditText,
            locationEditText,
            dateOfPerformEditText,
            servicePriceEditText;
    private RadioButton productButton, serviceButton;
    private RelativeLayout addButton;
    private ImageView backImageView;
    private Calendar calendar;
    private Service serviceSeleted;
    private Product productSeleted;
    private final ActivityResultLauncher<Intent> productSelectResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Nhận dữ liệu từ ProductSelectActivity.java
                    Intent intent = result.getData();
                    productSeleted= (Product) intent.getSerializableExtra(ProductSelectActivity.PRODUCT_SELECTED_EXTRA);
                    productSelectEditText.setText(productSeleted.getName());
                    productPriceEditText.setText(FormatUtils.formatCurrencyVietnam(productSeleted.getPrice()));
                }
            });

    private final ActivityResultLauncher<Intent> serviceSelectResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Nhận dữ liệu từ ServiceSelectActivity.java
                    Intent intent = result.getData();
                    serviceSeleted= (Service) intent.getSerializableExtra(ServiceSelectActivity.SERVICE_SELECTED_EXTRA);
                    serviceSelectEditText.setText(serviceSeleted.getName());
                    servicePriceEditText.setText(FormatUtils.formatCurrencyVietnam(serviceSeleted.getPrice()));
                }
            });

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
        productPriceEditText = findViewById(R.id.productPriceEditText);
        dateOfHireEditText = findViewById(R.id.dateOfHireEditText);
        dateOfReturnEditText = findViewById(R.id.dateOfReturnEditText);
        locationEditText = findViewById(R.id.locationEditText);
        dateOfPerformEditText = findViewById(R.id.dateOfPerformEditText);
        productButton = findViewById(R.id.productButton);
        serviceButton = findViewById(R.id.serviceButton);
        addButton = findViewById(R.id.addButton);
        backImageView = findViewById(R.id.backImageView);
        servicePriceEditText = findViewById(R.id.servicePriceEditText);
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
        addButton.setOnClickListener(view -> performAddContractDetail());
    }

    private void displayProductUI() {
        // Hiển thị view thông tin sản phẩm
        productSelectEditText.setVisibility(View.VISIBLE);
        dateOfHireEditText.setVisibility(View.VISIBLE);
        dateOfReturnEditText.setVisibility(View.VISIBLE);
        productPriceEditText.setVisibility(View.VISIBLE);

        // Ẩn view thông tin dịch vụ
        serviceSelectEditText.setVisibility(View.GONE);
        locationEditText.setVisibility(View.GONE);
        dateOfPerformEditText.setVisibility(View.GONE);
        servicePriceEditText.setVisibility(View.GONE);

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
        servicePriceEditText.setVisibility(View.VISIBLE);

        // Ẩn view thông tin sản phẩm
        productSelectEditText.setVisibility(View.GONE);
        dateOfHireEditText.setVisibility(View.GONE);
        dateOfReturnEditText.setVisibility(View.GONE);
        productPriceEditText.setVisibility(View.GONE);

        // Gán background cho button tương ứng
        productButton.setBackgroundResource(R.drawable.button_bgr_linear);
        serviceButton.setBackgroundResource(R.drawable.button_bgr_primary);
        generateContractDetailCode();
    }

    private void launcherProductSelectActivity() {
        Intent intent = new Intent(this, ProductSelectActivity.class);
        productSelectResult.launch(intent);
    }

    private void launcherServieSelectActivity() {
        Intent intent = new Intent(this, ServiceSelectActivity.class);
        serviceSelectResult.launch(intent);
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

            // Thiết lập giới hạn ngày tối thiểu là ngày hiện tại
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void performAddContractDetail() {
        if (productButton.isChecked()) {
            String contractIDTemporary = getIntent().getStringExtra("contractID");
            if (contractIDTemporary.isEmpty()) {
                return;
            }
            String contractDetailID = contractIdEditText.getText().toString().trim();
            String dateOfHire = dateOfHireEditText.getText().toString().trim();
            String dateOfReturn = dateOfReturnEditText.getText().toString().trim();

            if (isValidDataInputProduct(dateOfHire, dateOfReturn)) {
                // Chuyển định dạng ngày về hợp lệ với database MySQL mới có thể thêm vào database
                dateOfHire = FormatUtils.formatStringToStringMySqlFormat(dateOfHire);
                dateOfReturn = FormatUtils.formatStringToStringMySqlFormat(dateOfReturn);

                // Gọi API thêm HĐCT với sản phẩm
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<ServerResponse> call = apiService.insertContractDetailWithProduct(
                        contractDetailID,
                        dateOfHire,
                        dateOfReturn,
                        productSeleted.getId(),
                        contractIDTemporary
                );
                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if (response.isSuccessful()) {
                            // Xử lý dữ liệu trả về từ server
                            ServerResponse serverResponse = response.body();
                            if (serverResponse != null) {
                                if (serverResponse.isSuccess()) {
                                    // Làm mới EditText
                                    productSelectEditText.setText(null);
                                    productPriceEditText.setText(null);
                                    dateOfHireEditText.setText(null);
                                    dateOfReturnEditText.setText(null);
                                    generateContractDetailCode();
                                    Toast.makeText(AddContractDetailActivity.this, "Thêm HĐCT thành công", Toast.LENGTH_SHORT).show();
                                    // TODO: Cập nhật lại trạng thái sản phẩm "Đã thuê"
                                    // TODO: Thêm công việc mặt định giặt ủi
                                } else {
                                    Toast.makeText(AddContractDetailActivity.this, "Thêm HĐCT có lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {

                    }
                });
            }
        } else {
            String contractIDTemporary = getIntent().getStringExtra("contractID");
            if (contractIDTemporary.isEmpty()) {
                return;
            }
            String contractDetailID = contractIdEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();
            String dateOfPerform = dateOfPerformEditText.getText().toString().trim();

            if (isValidDataInputService(location, dateOfPerform)) {
                // Chuyển định dạng ngày về hợp lệ với database MySQL mới có thể thêm vào database
                dateOfPerform = FormatUtils.formatStringToStringMySqlFormat(dateOfPerform);

                // Gọi API thêm HĐCT với dịch vụ
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<ServerResponse> call = apiService.insertContractDetailWithService(
                        contractDetailID,
                        location,
                        dateOfPerform,
                        serviceSeleted.getId(),
                        contractIDTemporary
                );
                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if (response.isSuccessful()) {
                            // Xử lý dữ liệu trả về từ server
                            ServerResponse serverResponse = response.body();
                            if (serverResponse != null) {
                                if (serverResponse.isSuccess()) {
                                    // Làm mới EditText
                                    serviceSelectEditText.setText(null);
                                    servicePriceEditText.setText(null);
                                    locationEditText.setText(null);
                                    dateOfPerformEditText.setText(null);
                                    generateContractDetailCode();
                                    Toast.makeText(AddContractDetailActivity.this, "Thêm HĐCT thành công", Toast.LENGTH_SHORT).show();
                                    // TODO: Thêm công việc
                                } else {
                                    Toast.makeText(AddContractDetailActivity.this, "Thêm HĐCT có lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {

                    }
                });
            }
        }
    }

    // Kiểm tra tính hợp lệ của dữ liệu đầu vào với sản phẩm
    public boolean isValidDataInputProduct(String dateOfHireStr, String dateOfReturnStr) {
        if (productSeleted == null) {
            Toast.makeText(this, "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateOfHireStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày thuê", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateOfReturnStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày trả", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Date dateOfHire = FormatUtils.parserStringToDate(dateOfHireStr);
            Date dateOfReturn = FormatUtils.parserStringToDate(dateOfReturnStr);

            if (dateOfReturn.before(dateOfHire)) {
                Toast.makeText(this, "Ngày trả sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
                return false;
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    // Kiểm tra tính hợp lệ của dữ liệu đầu vào với dịch vụ
    public boolean isValidDataInputService(String location, String dateOfPerform) {
        if (serviceSeleted == null) {
            Toast.makeText(this, "Vui lòng chọn dịch vụ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (location.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa điểm", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidLocation(location)) {
            Toast.makeText(this, "Địa điểm không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateOfPerform.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn thực hiện", Toast.LENGTH_SHORT).show();
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

    // Tạo mã hợp đồng chi tiết: HDCT + ngày tháng năm giờ hiện tại
    private void generateContractDetailCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        String contractID = "HDCT" + currentDateAndTime;
        contractIdEditText.setText(contractID);
    }
}