package com.example.studiowedding.view.activity.contract;

import static android.icu.util.ULocale.getDisplayLanguage;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;


import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ContractDetailAdapter;
import com.example.studiowedding.model.Contract;
import com.example.studiowedding.model.ContractDetail;
import com.example.studiowedding.model.Customer;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.example.studiowedding.view.activity.detail_contract.AddContractDetailActivity;
import com.example.studiowedding.view.activity.detail_contract.ServerResponse;
import com.example.studiowedding.view.activity.detail_contract.UpdateContractProductActivity;
import com.example.studiowedding.view.activity.detail_contract.UpdateContractServiceActivity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContractActivity extends AppCompatActivity implements View.OnClickListener, ContractDetailAdapter.ItemListener {
    public static final String CONTRACT_DETAIL = "ContractDetail";
    private EditText edIdHD, edPaymentStatus, edPickClient, edDateCreate, edDop, edDeposit, edDiscount, edTotal;
    private TextView tvCreateDetailContract;
    private ImageView imgBack;
    private RecyclerView rcv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btnAdd;
    private List<ContractDetail> contractDetailList = new ArrayList<>();
    private ContractDetailAdapter adapter;

    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
    SimpleDateFormat sdf2= new SimpleDateFormat("dd/MM/yyyy");
    FormatUtils formatUtils=new FormatUtils();

    private int idKH;
    private float totalPrice;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contract);

        imgBack = findViewById(R.id.imgBackFromAddContract);

        edIdHD = findViewById(R.id.edAddIdContract);
        edPaymentStatus = findViewById(R.id.edPaymentStatus);
        edPickClient = findViewById(R.id.edAddClientContract);
        tvCreateDetailContract = findViewById(R.id.tvCreateDetailContract);
        edDateCreate = findViewById(R.id.edAddDateCreateContract);
        edDop = findViewById(R.id.edAddDOPContract);
        edDeposit = findViewById(R.id.edAddDepositContract);
        edDiscount = findViewById(R.id.edAddDiscountContract);
        edTotal = findViewById(R.id.edAddTotalAmmountContract);
        rcv = findViewById(R.id.rcvDetailContractInAddContract);
        swipeRefreshLayout = findViewById(R.id.srlAddContract);
        btnAdd = findViewById(R.id.btnAddNewContract);



        // Khởi tạo adapter và rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);
        adapter = new ContractDetailAdapter(this);
        rcv.setAdapter(adapter);
        rcv.setNestedScrollingEnabled(false);

        
        imgBack.setOnClickListener(this);
        edPaymentStatus.setOnClickListener(this);
        edPickClient.setOnClickListener(this);
        tvCreateDetailContract.setOnClickListener(this);
        edDop.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllDetailContractByIdHDTT();
            }
        });


        // OnBackPressedCallback xử lý xoá HDCT
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                deleteContractDetail();
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(callback);


        autoGenerateIDHD();
        autoGenerateDateCreate(edDateCreate);
        getAllDetailContractByIdHDTT();
        onChangeDiscount();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBackFromAddContract:
                deleteContractDetail();
                finish();
                break;
            case R.id.edPaymentStatus:
                showPopupMenu(view);
                break;
            case R.id.edAddDOPContract:
                try {
                    showDatePicker(edDop);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.edAddClientContract:
                pickCustomerActivityResultLauncher.launch(new Intent(this, PickClientActivity.class));
                break;
            case R.id.tvCreateDetailContract:
                Intent intent=new Intent(AddContractActivity.this,AddContractDetailActivity.class);
                String idHD=edIdHD.getText().toString().trim();
                intent.putExtra("contractID",idHD);
                startActivity(intent);
                break;
            case R.id.btnAddNewContract:
                try {
                    if (validateForm() > 0) {
                        try {
                            saveContract();
                            finish();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    //  Lưu thông tin hợp đồng
    private void saveContract() throws ParseException {
        String idHD = edIdHD.getText().toString();
      
        // format ngày thanh toán có kiểu dd/MM/yyyy thành yyyy-MM-dd
        String formatNgayThanhToan=edDop.getText().toString().isEmpty() ? null: sdf.format(sdf2.parse(edDop.getText().toString().trim()));

        Float tienCoc = edDeposit.getText().toString().trim().isEmpty() ? null : Float.parseFloat(edDeposit.getText().toString().trim());
        Float giamGia = edDiscount.getText().toString().trim().isEmpty() ? null : Float.parseFloat(edDiscount.getText().toString().trim());

        // reverse format tiền vnd
        Float tongTien = formatUtils.reverseCurrencyVietnam(edTotal.getText().toString());

        String trangThaiThanhToan = edPaymentStatus.getText().toString();
        String trangThaiHopDong = "Đang thực hiện";

        Contract contract = new Contract(idHD, formatNgayThanhToan, tienCoc, giamGia, tongTien, trangThaiThanhToan, trangThaiHopDong, idKH);
        insertNewContract(contract);
    }


    //    Popup menu trạng thái thanh toán
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_payment_status_contract, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_paid:
                        edPaymentStatus.setText("Đã thanh toán");
                        autoGenerateDateCreate(edDop);
                        edDop.setEnabled(false);
                        edDeposit.setEnabled(false);
                        edDeposit.setText("0");
                        return true;
                    case R.id.action_unpaid:
                        edPaymentStatus.setText("Chưa thanh toán");
                        edDop.setText("");
                        edDeposit.setText("");
                        edDop.setEnabled(true);
                        edDeposit.setEnabled(true);
                        return true;
                }
                return true;
            }
        });

        popupMenu.show();
    }

    //    Tự động tạo mã HD theo thời gian: HD+yyymmddhhmmss
    private void autoGenerateIDHD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        String contractID = "HD" + currentDateAndTime;
        edIdHD.setText(contractID);
    }

    //    Tự động khởi tạo ngày tạo:dd/MM/yyyy
    private void autoGenerateDateCreate( EditText editText) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDateAnd = sdf.format(new Date());
        editText.setText(currentDateAnd);
    }

    // Set hiển thị tiếng việt cho calender
    public static Locale getLocaleForCalendarInstance(Context context) {
        Locale locale = new Locale("vi","VN");
        LocaleList locales = new LocaleList(locale);
        context.getResources().getConfiguration().setLocales(locales);
        context.getResources().updateConfiguration(context.getResources().getConfiguration(),
                context.getResources().getDisplayMetrics());

        return locale;
    }

    //    Dialog date picker
    private void showDatePicker(EditText editText) throws ParseException {
        Locale locale = new Locale("vi", "VN");
        Locale.setDefault(locale);

        Calendar calendar = Calendar.getInstance(getLocaleForCalendarInstance(this));

        if (editText != null && !TextUtils.isEmpty(editText.getText())) {
            Date currentDate = FormatUtils.parserStringToDate(editText.getText().toString());
            if (currentDate != null) {
                calendar.setTime(currentDate);
            }
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

        // Thay đổi tiêu đề của 2 button trong dialog
        datePickerDialog.setOnShowListener(dialog -> {
            Button positiveButton = datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            if (positiveButton != null) {
                positiveButton.setText("Chọn");
            }

            if (negativeButton != null) {
                negativeButton.setText("Hủy");
            }
        });
        datePickerDialog.show();
    }


    //    Xử lý thay đổi của edittext giảm giá
    private void onChangeDiscount() {
        edDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String text = s.toString();
                try {
                    int value = Integer.parseInt(text);
                    if (value < 0 || value > 100) {
                        edDiscount.setText("");
                    }
                } catch (NumberFormatException e) {
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Gọi lại hàm tính tổng tiền sau khi giá trị của giảm giá bị thay đổi
                totalAmount();
            }
        });
    }

    // Xử lý thay đổi của edittext tiền cọc
//    private void onChangeDeposit(){
////        edDeposit.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable editable) {
////                String input = editable.toString().trim();
////
////                // Loại bỏ ký tự "đ" và dấu phân cách
////                String formattedInput = input.replace("đ", "").replaceAll(",", "");
////
////                if (formattedInput.isEmpty()) {
////                    edDeposit.setText("");
////                    return;
////                }
////
////                try {
////                    float depositValue = Float.parseFloat(formattedInput);
////
////                    // Định dạng số tiền thành chuỗi tiền tệ
////                    DecimalFormat formatter = new DecimalFormat("#,###");
////                    String formattedDeposit = formatter.format(depositValue);
////
////                    // Hiển thị giá trị đã định dạng trở lại vào EditText
////                    edDeposit.removeTextChangedListener(this);
////                    edDeposit.setText(formattedDeposit + "đ");
////                    edDeposit.setSelection(formattedDeposit.length());
////                    edDeposit.addTextChangedListener(this);
////                } catch (NumberFormatException e) {
////                    e.printStackTrace();
////                }
////
////            }
////        });
//    }


    //    Tính tổng tiền = tổng tiền sản phẩm* giam giá
    private void totalAmount() {
        float discountPercentage = 0;

        String discountText = edDiscount.getText().toString().trim();
        if (!discountText.isEmpty()) {

            // chuyển đổi giá trị discount từ định dạng phần trăm sang số thập phân
            discountPercentage = Float.parseFloat(discountText);

            // chia cho 100 để có giá trị phần trăm
            discountPercentage /= 100;
        }

        // tính giá trị giảm giá dưới dạng phần trăm
        float discountAmount = totalPrice * discountPercentage;

        // tính tổng tiền sau khi giảm giá
        float totalAmount = totalPrice - discountAmount;

        //format tiền vnd
        String totalAmountFormat=formatUtils.formatCurrencyVietnam(totalAmount);

        edTotal.setText(totalAmountFormat);

    }

    //    Tính tổng tiền sản phẩm, dịch vụ
    private float totalPrice(List<ContractDetail> details) {
        float total = 0;
        for (ContractDetail contractDetail : details) {
            total += contractDetail.getProductPrice() + contractDetail.getServicePrice();

        }
        return total;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    //  Validate các các trường nhập, chọn
    private int validateForm() throws ParseException {
        int check = 1;
        String customerName = edPickClient.getText().toString();
        String deposit = edDeposit.getText().toString();
        String discount = edDiscount.getText().toString();
        String status = edPaymentStatus.getText().toString();
        String dateOfPay=edDop.getText().toString();
        Date dop=formatUtils.parserStringToDate(dateOfPay);
        LocalDate currentDate = LocalDate.now();
        LocalDate paymentDate = dop.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        if (check == 1 && customerName.equalsIgnoreCase("Chọn KH")) {
            Toast.makeText(this, "Chưa chọn khách hàng", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (check == 1 && contractDetailList.isEmpty()) {
            Toast.makeText(this, "Chọn hợp đồng chi tiết", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (check == 1 && status.equalsIgnoreCase("Trạng thái hợp đồng")) {
            Toast.makeText(this, "Chưa chọn trạng thái hợp đồng", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if(check==1 && paymentDate.isBefore(currentDate)){
            Toast.makeText(this, "Ngày thanh toán không hợp lệ ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        if (check == 1 && deposit.isEmpty()) {
            Toast.makeText(this, "Chưa nhập tiền cọc ", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (check == 1 && discount.isEmpty()) {
            Toast.makeText(this, "Chưa nhập giảm giá ", Toast.LENGTH_SHORT).show();
            check = -1;
        }



        return check;
    }


    // Hàm gọi API thêm  hợp đồng mới
    private void insertNewContract(Contract contract){
        ApiService apiService=ApiClient.getClient().create(ApiService.class);
        Call<Void> call=apiService.insertContract(contract);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    updateIdContractForContractDetail();
                    Toast.makeText(AddContractActivity.this,"Thêm hợp đồng thành công",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddContractActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Tag", "Lỗi" + t.getMessage());
            }
        });

    }


    //  Hàm gọi API Lấy toàn danh sách HDCT có idHDTamThoi=idHD
    private void getAllDetailContractByIdHDTT(){
        String idHDTT=edIdHD.getText().toString();
        ApiService apiService= ApiClient.getClient().create(ApiService.class);
        Call<List<ContractDetail>> call=apiService.getAllDetailContractByIdHDTT(idHDTT);
        call.enqueue(new Callback<List<ContractDetail>>() {
            @Override
            public void onResponse(Call<List<ContractDetail>> call, Response<List<ContractDetail>> response) {
                if (response.isSuccessful()) {
                    contractDetailList.clear();
                    contractDetailList.addAll(response.body());
                    adapter.setContractDetails(contractDetailList);
                    totalPrice = totalPrice(contractDetailList);
                    totalAmount();
                    swipeRefreshLayout.setRefreshing(false);

                } else {
                    Log.i("Tag", "Lỗi");
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<List<ContractDetail>> call, Throwable t) {
                Log.i("Tag", "Lỗi" + t.getMessage());
                swipeRefreshLayout.setRefreshing(false);


            }
        });
    }


    // Hàm gọi API cập nhật idHD cho HDCT
    private void updateIdContractForContractDetail(){
        String contractIDTemporary=edIdHD.getText().toString().trim();
        ContractDetail contractDetail=new ContractDetail(contractIDTemporary);
        ApiService apiService=ApiClient.getClient().create(ApiService.class);
        Call<Void>call=apiService.updateContractIDContractDetail(contractIDTemporary,contractDetail);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.i("TAG","Update idHD HDCT Thành công");
                }else{
                    Log.i("TAG","Lỗi Update idHD HDCT");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("TAG","Lỗi"+t.getMessage());
            }
        });
    }

    //  Hàm gọi API  Xoá HDCT
    private void deleteContractDetail(){
        String idHDTT=edIdHD.getText().toString().trim();
        ApiService apiService=ApiClient.getClient().create(ApiService.class);
        Call<Void> call=apiService.deleteHDCT(idHDTT);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddContractActivity.this,"Xoá HDCTS thành công",Toast.LENGTH_SHORT);
                    Log.i("TAG","Xoá HDCTS thành công");
                }else{
                    Log.i("TAG","Xoá HDCT thất bại");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("TAG","Lỗi xoá HDCT"+t.getMessage());

            }
        });
    }

    private ActivityResultLauncher<Intent> pickCustomerActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Customer selectedCustomer = data.getParcelableExtra("customer");

                        if (selectedCustomer != null) {
                            edPickClient.setText(selectedCustomer.getName() + " - " + selectedCustomer.getPhone());
                            idKH = selectedCustomer.getId();
                        } else {
                            Toast.makeText(AddContractActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            });

    private ActivityResultLauncher<Intent> addDetailContractResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                }
            });



    @Override
    protected void onResume() {
        super.onResume();
        getAllDetailContractByIdHDTT();
    }



    @Override
    public void startUpdateContractDetailActivity(ContractDetail contractDetail) {
        if (contractDetail.getLocation() != null) {
            // HĐCT với dịch
            Intent intent = new Intent(this, UpdateContractServiceActivity.class);
            intent.putExtra(CONTRACT_DETAIL, contractDetail);
            startActivity(intent);
        } else {
            // HĐCT với sản phẩm
            Intent intent = new Intent(this, UpdateContractProductActivity.class);
            intent.putExtra(CONTRACT_DETAIL, contractDetail);
            startActivity(intent);
        }
    }

    // Hiển thị hộp thoại xác nhận trước khi xoá HĐCT
    @Override
    public void showConfirmDeleteContractDetail(ContractDetail contractDetail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Xác nhận xoá HĐCT: " + contractDetail.getId());
        builder.setNegativeButton("Huỷ", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton("Xoá", (dialogInterface, i) -> {
            // Thực hiện xoá HĐCT theo mã HĐCT
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ServerResponse> call = apiService.deleteContractDetailByContractDetailID(contractDetail.getId());
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        ServerResponse serverResponse = response.body();
                        if (serverResponse.isSuccess()) {
                            Toast.makeText(AddContractActivity.this, "Xoá HĐCT thành công", Toast.LENGTH_SHORT).show();
                            getAllDetailContractByIdHDTT();
                        } else {
                            Toast.makeText(AddContractActivity.this, "Xoá HĐCT có lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {

                }
            });
        });
        builder.show();
    }
}