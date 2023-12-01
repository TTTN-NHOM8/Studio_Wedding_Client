package com.example.studiowedding.view.activity.contract;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ContractAdapter;
import com.example.studiowedding.adapter.ContractDetailAdapteVer2;
import com.example.studiowedding.adapter.ContractDetailAdapter;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Contract;
import com.example.studiowedding.model.ContractDetail;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateContractActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgBack;
    private TextView tvDeposit,tvIDHD,tvDateCreate,tvName,tvPhone,tvAddress;
    private EditText edPaymentStatus,edIncurrentStatus,edIncurrentNote,edFine,edDor,edDop,edDiscount,edTotal;
    private RecyclerView rcv;
    private ContractDetailAdapteVer2 adapter;
    private List<ContractDetail>contractDetailList =new ArrayList<>();
    FormatUtils formatUtils;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");

    private float totalPrice;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contract);
        imgBack=findViewById(R.id.imgBackFromUpdateContract);
        tvIDHD=findViewById(R.id.tvIdHDUpdateContract);
        tvDeposit=findViewById(R.id.tvDepositUpdateContract);
        tvDateCreate=findViewById(R.id.tvDatecreateUpdateContract);
        tvName=findViewById(R.id.tvNameClientUpdateContract);
        tvPhone=findViewById(R.id.tvPhoneNumberUpdateContract);
        tvAddress=findViewById(R.id.tvAddressUpdateContract);
        edDop=findViewById(R.id.edUpdateDOPContract);
        edDiscount=findViewById(R.id.edUpdateDiscountContract);
        edTotal=findViewById(R.id.edUpdateTotalAmmountContract);
        edPaymentStatus=findViewById(R.id.edUpdatePaymentStatusContract);
        edIncurrentStatus=findViewById(R.id.edUpdateIncurrentStatusContract);
        edIncurrentNote=findViewById(R.id.edUpdateIncurrentNoteContract);
        edFine=findViewById(R.id.edUpdateIncurrentFineContract);
        edDor=findViewById(R.id.edUpdateIncurrentDorContract);
        rcv=findViewById(R.id.rcvDetailContractInUpdateContract);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(UpdateContractActivity.this,LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        adapter = new ContractDetailAdapteVer2(contractDetailList,this);
        rcv.setAdapter(adapter);
        rcv.setNestedScrollingEnabled(false);




        String formatDeposit=formatUtils.formatCurrencyVietnam(getContractInformation().getTienCoc());
        String formatDateCreate=formatUtils.formatDateToString(getContractInformation().getNgayTao());
        String formatTotal=formatUtils.formatCurrencyVietnam(getContractInformation().getTongTien());

        tvIDHD.setText(getContractInformation().getIdHopDong());
        tvDeposit.setText(formatDeposit);
        tvDateCreate.setText(formatDateCreate);
        tvName.setText(getContractInformation().getTenKH());
        tvPhone.setText(getContractInformation().getDienThoai());
        tvAddress.setText(getContractInformation().getDiaChi());
        edPaymentStatus.setText(getContractInformation().getTrangThaiThanhToan());

        if(getContractInformation().getNgayThanhToan()!=null){
            edPaymentStatus.setEnabled(false);
            Date dop=null;

            try {
                dop=sdf.parse(getContractInformation().getNgayThanhToan());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String formateDop=formatUtils.formatDateToString(dop);
            edDop.setText(formateDop);
            edDop.setEnabled(false);

        }


        edDiscount.setText(String.valueOf(getContractInformation().getGiamGia()));
        edTotal.setText(formatTotal);

        imgBack.setOnClickListener(this);
        edPaymentStatus.setOnClickListener(this);
        edIncurrentStatus.setOnClickListener(this);
        edIncurrentNote.setOnClickListener(this);
        edDop.setOnClickListener(this);

        getContractDetail();
        onChangeDiscount();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBackFromUpdateContract:
                finish();
                break;
            case R.id.edUpdateDOPContract:
                try {
                    showDatePicker(edDop);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.edUpdatePaymentStatusContract:
                showPopupMenuPayment(view);
                break;
            case R.id.edUpdateIncurrentStatusContract:
                showPopupMenuIncurrent(view);
                break;
            case R.id.edUpdateIncurrentNoteContract:
                showPopupMenuIncurrentNote(view);
                break;
        }
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
    //    Tính tổng tiền = tổng tiền sản phẩm + dịch vụ - giảm giá
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

    //    Popup menu trạng thái thanh toán
    private void showPopupMenuPayment(View v) {
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
                        return true;
                    case R.id.action_unpaid:
                        edPaymentStatus.setText("Chưa thanh toán");
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
    //    Popup menu phát sinh
    private void showPopupMenuIncurrent(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_incurrent, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_incurent:
                        edIncurrentStatus.setText("Có phát sinh");
                        edIncurrentNote.setVisibility(View.VISIBLE);
                        edDor.setVisibility(View.GONE);
                        edFine.setVisibility(View.GONE);
                        return true;
                    case R.id.action_is_incurrent:
                        edIncurrentStatus.setText("Không có phát sinh");
                        edIncurrentNote.setVisibility(View.GONE);
                        edDor.setVisibility(View.GONE);
                        edFine.setVisibility(View.GONE);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
    //    Popup menu nội dung phát sinh
    private void showPopupMenuIncurrentNote(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_incurrent_note, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_damage:
                        edIncurrentNote.setText("Khách làm hỏng đồ");
                        edFine.setVisibility(View.VISIBLE);
                        edDor.setVisibility(View.GONE);
                        return true;
                    case R.id.action_is_late:
                        edIncurrentNote.setText("Khách trả đồ muộn");
                        edDor.setVisibility(View.VISIBLE);
                        edFine.setVisibility(View.VISIBLE);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
    //

    // validate các trường nhập
    @RequiresApi(api = Build.VERSION_CODES.O)
    private int validateForm() throws ParseException {
        int check=1;
        String dateOfPay=edDop.getText().toString();
        Date dop=formatUtils.parserStringToDate(dateOfPay);
        LocalDate currentDate = LocalDate.now();
        LocalDate paymentDate = dop.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if(check==1 && paymentDate.isBefore(currentDate)){
            Toast.makeText(this, "Ngày thanh toán không hợp lệ ", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        return check;
    }
    private Contract getContractInformation(){
        Contract contract=(Contract) getIntent().getSerializableExtra("contractList");
        return contract;
    }


    private void getContractDetail(){
        String idHD= tvIDHD.getText().toString();
        ApiService apiService= ApiClient.getClient().create(ApiService.class);
        Call<List<ContractDetail>>call=apiService.getContractDetailByIdContract(idHD);
        call.enqueue(new Callback<List<ContractDetail>>() {
            @Override
            public void onResponse(Call<List<ContractDetail>> call, Response<List<ContractDetail>> response) {
                if(response.isSuccessful()){
                    contractDetailList.clear();
                    contractDetailList.addAll(response.body());
                    totalPrice = totalPrice(contractDetailList);
                    totalAmount();
                    adapter.notifyDataSetChanged();

                }else{
                    Log.i("TAG","Lỗi");
                }
            }

            @Override
            public void onFailure(Call<List<ContractDetail>> call, Throwable t) {
                Log.i("TAG","Lỗi"+t.getMessage());

            }
        });
    }



}