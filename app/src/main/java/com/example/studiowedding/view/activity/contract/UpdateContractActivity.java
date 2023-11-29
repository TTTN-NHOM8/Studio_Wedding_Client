package com.example.studiowedding.view.activity.contract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ContractDetailAdapteVer2;
import com.example.studiowedding.adapter.IncurrentAdapter;
import com.example.studiowedding.model.Contract;
import com.example.studiowedding.model.ContractDetail;
import com.example.studiowedding.model.Incurrent;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateContractActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgBack;
    private TextView tvDeposit,tvIDHD,tvDateCreate,tvName,tvPhone,tvAddress,tvShownOncurrent,tvHidden,tvTitle;
    private EditText edPaymentStatus,edDop,edDiscount,edTotal;
    private RecyclerView rcvDetailContract,rcvIncurrent;
    private RelativeLayout relativeLayout;
    private Button btnUpdate;
    private ContractDetailAdapteVer2 adapter;
    private IncurrentAdapter incurrentAdapter;
    private List<ContractDetail>contractDetailList =new ArrayList<>();
    private List<Incurrent>incurrentList=new ArrayList<>();
    FormatUtils formatUtils;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
    DialogDatePicker dialogDatePicker=new DialogDatePicker();
    private float totalPrice;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contract);
        initView();
        fillDataIntoView();

        // Khởi tạo rcv cho HDCT
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(UpdateContractActivity.this,LinearLayoutManager.VERTICAL,false);
        rcvDetailContract.setLayoutManager(linearLayoutManager);
        adapter = new ContractDetailAdapteVer2(contractDetailList,this);
        rcvDetailContract.setAdapter(adapter);
        rcvDetailContract.setNestedScrollingEnabled(false);

        // Khởi tạo rcv cho Phát sinh
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(UpdateContractActivity.this,LinearLayoutManager.VERTICAL,false);
        rcvIncurrent.setLayoutManager(linearLayoutManager2);
        incurrentAdapter = new IncurrentAdapter(incurrentList,this);
        rcvIncurrent.setAdapter(incurrentAdapter);
        rcvIncurrent.setNestedScrollingEnabled(false);

        incurrentAdapter.setOnItemClickListener(position -> {
            Incurrent incurrent=incurrentList.get(position);
            String noiDung=incurrent.getNoiDung();
            if(noiDung!=null){
                showAlertDialog(position,true);
            }else{
                showAlertDialog(position,false);
            }

        });

        setOnClickView();
        getContractDetail();
        getIncurrentList();
        onChangeDiscount();

    }

    private void fillDataIntoView(){
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
        if(getContractInformation().getTrangThaiThanhToan().equalsIgnoreCase("Đã thanh toán")){
            edDop.setEnabled(false);
            edDiscount.setEnabled(false);
            edPaymentStatus.setEnabled(false);
            btnUpdate.setVisibility(View.GONE);
            tvTitle.setText("Chi tiết hợp đồng");
        }
        if(getContractInformation().getTrangThaiThanhToan().equalsIgnoreCase("Chưa thanh toán")){
            edDop.setEnabled(true);
            edDiscount.setEnabled(true);
            edPaymentStatus.setEnabled(true);
            btnUpdate.setVisibility(View.VISIBLE);
        }
        if(getContractInformation().getNgayThanhToan()!=null){
            Date dop;
            try {
                dop=sdf.parse(getContractInformation().getNgayThanhToan());

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            String formateDop=sdf2.format(dop);
            edDop.setText(formateDop);
        }

        float originalDiscount = getContractInformation().getGiamGia();
        int converDiscount = (int) originalDiscount;

        edDiscount.setText(String.valueOf(converDiscount));
        edTotal.setText(formatTotal);
    }
    private void initView(){
        tvTitle=findViewById(R.id.tvTitleUpdateContract);
        imgBack=findViewById(R.id.imgBackFromUpdateContract);
        tvIDHD=findViewById(R.id.tvIdHDUpdateContract);
        tvDeposit=findViewById(R.id.tvDepositUpdateContract);
        tvDateCreate=findViewById(R.id.tvDatecreateUpdateContract);
        tvName=findViewById(R.id.tvNameClientUpdateContract);
        tvPhone=findViewById(R.id.tvPhoneNumberUpdateContract);
        tvAddress=findViewById(R.id.tvAddressUpdateContract);
        tvShownOncurrent=findViewById(R.id.tvShowOncurrent);
        tvHidden=findViewById(R.id.tvIsnShowOncurrent);
        edDop=findViewById(R.id.edUpdateDOPContract);
        edDiscount=findViewById(R.id.edUpdateDiscountContract);
        edTotal=findViewById(R.id.edUpdateTotalAmmountContract);
        edPaymentStatus=findViewById(R.id.edUpdatePaymentStatusContract);
        rcvDetailContract=findViewById(R.id.rcvDetailContractInUpdateContract);
        rcvIncurrent=findViewById(R.id.rcvIncurrentsInUpdateContract);
        relativeLayout=findViewById(R.id.rltOncurrent);
        btnUpdate=findViewById(R.id.btnUpdateOncurrent);
    }
    private void setOnClickView(){
        imgBack.setOnClickListener(this);
        edPaymentStatus.setOnClickListener(this);
        tvShownOncurrent.setOnClickListener(this);
        tvHidden.setOnClickListener(this);
        edDop.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBackFromUpdateContract:
                finish();
                break;
            case R.id.edUpdateDOPContract:
                try {
                    dialogDatePicker.showDatePicker(edDop,UpdateContractActivity.this);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.edUpdatePaymentStatusContract:
                showPopupMenuPayment(view);
                break;
            case R.id.btnUpdateOncurrent:
                try {
                    if(validateContrac()>0){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1000);
                        saveContract();
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.tvShowOncurrent:
                relativeLayout.setVisibility(View.VISIBLE);
                tvShownOncurrent.setVisibility(View.GONE);
                tvHidden.setVisibility(View.VISIBLE);
                break;
            case R.id.tvIsnShowOncurrent:
                relativeLayout.setVisibility(View.GONE);
                tvShownOncurrent.setVisibility(View.VISIBLE);
                tvHidden.setVisibility(View.GONE);
                break;
        }
    }

    //Lưu thông tin hợp đồng
    private void saveContract(){
        String idHD=tvIDHD.getText().toString().trim();
        String formatNgayThanhToan=edDop.getText().toString().isEmpty() ? null: formatUtils.formatStringToStringMySqlFormat(edDop.getText().toString().trim());

        Float giamGia = edDiscount.getText().toString().trim().isEmpty() ? null : Float.parseFloat(edDiscount.getText().toString().trim());
        // reverse format tiền vnd
        Float tongTien = formatUtils.reverseCurrencyVietnam(edTotal.getText().toString());
        String trangThaiThanhToan = edPaymentStatus.getText().toString();

        Contract contract=new Contract(idHD,formatNgayThanhToan,giamGia,tongTien,trangThaiThanhToan);
        updateContract(contract);

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

    // Alert dialog phát sinh
    private void showAlertDialog(int position, boolean isPhatSinh) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateContractActivity.this);
        CharSequence[] options;
        if (isPhatSinh) {
            options = new CharSequence[]{"Chi tiết phát sinh","Xác nhận hết phát sinh"};
        } else {
            options = new CharSequence[]{"Thêm phát sinh"};
        }
        builder.setTitle("Menu")
                .setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent updateIntent = new Intent(UpdateContractActivity.this, UpdateOncurrentActivity.class);
                                updateIntent.putExtra("oncurrentList", incurrentList.get(position));
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

    // Alert comfirm xoá phát sinh
    private void comfirmDeleteDialog(int posititon){
        Incurrent incurrent=incurrentList.get(posititon);
        String name=incurrent.getTenSanPham();

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateContractActivity.this);
        builder.setMessage("Xác nhận không còn phát sinh cho: \n"+ name);
        builder.setNegativeButton("Huỷ", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton("Xác nhận", (dialogInterface, i) -> {
            int idPhatSinh = incurrent.getIdPhatSinh();
            String idSanPham=incurrent.getIdSanPham();
            String idHopDong=incurrent.getIdHopDong();
            String idHopDongChiTiet=incurrent.getIdHopDongChiTiet();
            Incurrent updateValues=new Incurrent(idPhatSinh,null,null,null,idSanPham,idHopDong,idHopDongChiTiet);
            deleteIncurrentById(idPhatSinh,updateValues);
        });
        builder.show();

    }
    //    Popup menu trạng thái thanh toán
    private void showPopupMenuPayment(View v) {
        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenuWindow);
        PopupMenu popupMenu = new PopupMenu(wrapper, v, Gravity.END);
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

    // validate các trường nhập
    private int validateContrac() throws ParseException {
        int check = 1;
        String discount = edDiscount.getText().toString();
        String status = edPaymentStatus.getText().toString();
        String dateOfPay=edDop.getText().toString();
        String dateCreate=tvDateCreate.getText().toString();

        if (check == 1 && status.equalsIgnoreCase("Trạng thái hợp đồng")) {
            showSnackbar("Chưa chọn trạng thái hợp đồng");
            check = -1;
        }

        if(!dateOfPay.isEmpty()){
            Date dop=formatUtils.parserStringToDate(dateOfPay);
            Date doc=formatUtils.parserStringToDate(dateCreate);

            if(check==1 && dop.before(doc)){
                showSnackbar("Ngày thanh toán không hợp lệ");
                check = -1;
            }
        }

        if (check == 1 && discount.isEmpty()) {
            showSnackbar("Chưa nhập giảm giá");
            check = -1;
        }


        return check;
    }

    private void deleteIncurrentById(int idPhatSinh, Incurrent incurrent){
        ApiService apiService= ApiClient.getClient().create(ApiService.class);
        Call<Void>call=apiService.updateIncurrentNone(idPhatSinh,incurrent);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    showSnackbar("Xoá phát sinh thành công");
                    getIncurrentList();
                }else{
                    showSnackbar("Lỗi");

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showSnackbar("Lỗi"+t.getMessage());

            }
        });
    }

    private void updateContract(Contract contract){
        String idHD=tvIDHD.getText().toString().trim();
        ApiService apiService=ApiClient.getClient().create(ApiService.class);
        Call<Void>call=apiService.updateContract(idHD,contract);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    showSnackbar("Cập nhật hợp đồng thành công");
                }else{
                    showSnackbar("Cập nhật hợp đồng thất bại");

                    Log.i("TAG","Lỗi");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("TAG","Lỗi"+t.getMessage());
                showSnackbar("Lỗi"+t.getMessage());

            }
        });
    }

    private void getIncurrentList(){
        String idHD= tvIDHD.getText().toString();
        ApiService apiService= ApiClient.getClient().create(ApiService.class);
        Call<List<Incurrent>>call=apiService.getIncurrentList(idHD);
        call.enqueue(new Callback<List<Incurrent>>() {
            @Override
            public void onResponse(Call<List<Incurrent>> call, Response<List<Incurrent>> response) {
                if(response.isSuccessful()){
                    incurrentList.clear();
                    incurrentList.addAll(response.body());

                    incurrentAdapter.notifyDataSetChanged();
                }else{
                    Log.i("TAG","Lỗi");
                }
            }

            @Override
            public void onFailure(Call<List<Incurrent>> call, Throwable t) {
                Log.i("TAG","Lỗi"+t.getMessage());

            }
        });

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

    private Contract getContractInformation(){
        Contract contract=(Contract) getIntent().getSerializableExtra("contractList");
        return contract;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIncurrentList();
    }

    private void showSnackbar(String message){
        Snackbar.make(edPaymentStatus,message, Snackbar.LENGTH_SHORT)
                .show();
    }
}