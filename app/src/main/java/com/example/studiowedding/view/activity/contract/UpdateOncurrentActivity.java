package com.example.studiowedding.view.activity.contract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.model.Incurrent;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateOncurrentActivity extends AppCompatActivity{
    private ImageView imBack;
    private EditText edNote,edFine,edDor;
    private TextView tvIDHDCT,tvName,tvPrice,tvvDol,tvDor,tvTitle;
    private Button btnUpdate;
    private NestedScrollView nestedScrollView;
    FormatUtils formatUtils;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
    DialogDatePicker dialogDatePicker=new DialogDatePicker();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_incurrent);
        initView();

        fillDataToView();
        onClickView();

    }

    private void initView(){
        nestedScrollView=findViewById(R.id.nscvOncurrent);
        imBack=findViewById(R.id.imgBackFromUpdateOncurrent);
        tvTitle=findViewById(R.id.tvTitleUpdateOncurrent);
        tvIDHDCT=findViewById(R.id.tvIDHDCTOncurrent);
        tvName=findViewById(R.id.tvNameProductOncurrent);
        tvPrice=findViewById(R.id.tvPriceOncurent);
        tvvDol=findViewById(R.id.tvDolOncurent);
        tvDor=findViewById(R.id.tvDorOncurent);
        edNote=findViewById(R.id.edOncurretNote);
        edFine=findViewById(R.id.edOncurretFine);
        edDor=findViewById(R.id.edOncurrentDor);
        btnUpdate=findViewById(R.id.btnUpdateOncurrent);

    }
    private void fillDataToView(){
        tvTitle.setText("Thêm phát sinh");
        String formatGiaThue=formatUtils.formatCurrencyVietnam(getIncurentInformation().getGiaThue());
        String formatDol=formatUtils.formatDateToString(getIncurentInformation().getNgayThue());
        String formatDor1=formatUtils.formatDateToString(getIncurentInformation().getNgayTra());
        tvIDHDCT.setText(getIncurentInformation().getIdHopDongChiTiet());
        tvName.setText(getIncurentInformation().getTenSanPham());
        tvPrice.setText(formatGiaThue);
        tvvDol.setText(formatDol);
        tvDor.setText(formatDor1);

        String note=getIncurentInformation().getNoiDung();
        Float fine=getIncurentInformation().getPhiPhatSinh();
        String dor=getIncurentInformation().getHanTra();


        if(note!=null){
            tvTitle.setText("Chi tiết phát sinh");
            edNote.setText(note);
            edFine.setVisibility(View.VISIBLE);
            edFine.setText(""+formatUtils.formatCurrencyVietnam(fine));
            edNote.setEnabled(false);
            edFine.setEnabled(false);
            edDor.setEnabled(false);
            edNote.setTextColor(ContextCompat.getColor(this,R.color.black));
            edFine.setTextColor(ContextCompat.getColor(this,R.color.black));
            edDor.setTextColor(ContextCompat.getColor(this,R.color.black));
            edNote.setBackground(ContextCompat.getDrawable(this,R.drawable.bgr_edt_disable));
            edFine.setBackground(ContextCompat.getDrawable(this,R.drawable.bgr_edt_disable));
            edDor.setBackground(ContextCompat.getDrawable(this,R.drawable.bgr_edt_disable));
            btnUpdate.setVisibility(View.GONE);

            if(dor!=null){
                String formatDor;
                try {
                    Date parseDor=sdf.parse(dor);
                    formatDor=formatUtils.formatDateToString(parseDor);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                edDor.setText(formatDor);
                edDor.setVisibility(View.VISIBLE);
            }

        }


    }
    private void onClickView(){
        imBack.setOnClickListener(view -> {
            finish();
        });
        edNote.setOnClickListener(view -> {
            showPopupMenuIncurrentNote(view);
        });

        edDor.setOnClickListener(view -> {
            try {
                dialogDatePicker.showDatePicker(edDor,UpdateOncurrentActivity.this);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        findViewById(R.id.btnUpdateOncurrent).setOnClickListener(view -> {
            if(validateIncurrent()>0){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
                saveOncurrent();
            }
        });
    }
    private void saveOncurrent(){
        int idPS=getIncurentInformation().getIdPhatSinh();
        String idSP=getIncurentInformation().getIdSanPham();
        String idHD=getIncurentInformation().getIdHopDong();
        String idHDCT=getIncurentInformation().getIdHopDongChiTiet();
        String note=edNote.getText().toString().trim();
        String dor=edDor.getText().toString().isEmpty() ? null: formatUtils.formatStringToStringMySqlFormat(edDor.getText().toString().trim());
        String fine=edFine.getText().toString().trim();
        Float formatFine = fine.isEmpty() ? null : Float.parseFloat(fine);

        Incurrent incurrent=new Incurrent(idPS,note,dor,formatFine,idSP,idHD,idHDCT);
        updateOncurrent(incurrent);

    }

    private int validateIncurrent(){
        int check=1;
        String note=edNote.getText().toString();
        String dor= edDor.getText().toString();
        String fine=edFine.getText().toString();
        String stringOldor=tvDor.getText().toString();


        if(check==1 && note.equalsIgnoreCase("Không có phát sinh") || note.equalsIgnoreCase("Nội dung") || note.isEmpty()){
            showSnackbar("Chưa chọn nội dung");
            check=-1;
        }

        if(check == 1 && edDor.getVisibility() == View.VISIBLE && dor.isEmpty()){
            showSnackbar("Chưa chọn hạn trả");
            check = -1;
        }
        if(check == 1 && edFine.getVisibility() == View.VISIBLE && fine.isEmpty()){
            showSnackbar("Chưa nhập tiền phạt");
            check = -1;
        }

        if(note.equalsIgnoreCase("Khách trả đồ muộn")){
            try {
                Date oldDor=formatUtils.parserStringToDate(stringOldor);
                Date newDor=formatUtils.parserStringToDate(dor);

                if(check==1 && newDor.before(oldDor)){
                    showSnackbar("Hạn trả không hợp lệ");
                    check = -1;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return check;
    }

    private void showPopupMenuIncurrentNote(View v) {
        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenuWindow);
        PopupMenu popupMenu = new PopupMenu(wrapper, v, Gravity.END);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_incurrent_note, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_damage:
                        edNote.setText("Khách làm hỏng đồ");
                        edFine.setVisibility(View.VISIBLE);
                        edDor.setVisibility(View.GONE);
                        edDor.setText("");
                        edFine.setText("");
                        return true;
                    case R.id.action_is_late:
                        edNote.setText("Khách trả đồ muộn");
                        edDor.setVisibility(View.VISIBLE);
                        edFine.setVisibility(View.VISIBLE);
                        edDor.setText("");
                        edFine.setText("");
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
    private void updateOncurrent(Incurrent incurrent){
        String idPS= String.valueOf(getIncurentInformation().getIdPhatSinh());
        ApiService apiService= ApiClient.getClient().create(ApiService.class);
        Call<Void>call=apiService.updateIncurrent(idPS,incurrent);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    showSnackbar("Cập nhật phát sinh thành công");
                }else{
                    Toast.makeText(UpdateOncurrentActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
                    showSnackbar("Lỗi");

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showSnackbar("Lỗi"+t.getMessage());
            }
        });
    }

    private Incurrent getIncurentInformation(){
        Incurrent incurrent=(Incurrent) getIntent().getSerializableExtra("oncurrentList");
        return incurrent;
    }

    private void showSnackbar(String message){
        Snackbar.make(nestedScrollView,message, Snackbar.LENGTH_SHORT)
                .setAnchorView(btnUpdate)
                .show();
    }
}