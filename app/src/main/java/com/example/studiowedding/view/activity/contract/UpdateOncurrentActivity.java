package com.example.studiowedding.view.activity.contract;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
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
import com.example.studiowedding.model.Contract;
import com.example.studiowedding.model.Incurrent;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateOncurrentActivity extends AppCompatActivity{
    private ImageView imBack;
    private EditText edNote,edFine,edDor;
    private TextView tvIDHDCT,tvName,tvPrice,tvvDol,tvDor;
    private Button btnUpdate;
    FormatUtils formatUtils;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
    CustomComponent customComponent=new CustomComponent();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_incurrent);
        initView();

        findViewById(R.id.btnUpdateOncurrent).setOnClickListener(view -> {
            if(validateIncurrent()>0){
                saveOncurrent();
                finish();
            }
        });
        fillDataToView();
    }

    private void initView(){
        imBack=findViewById(R.id.imgBackFromUpdateOncurrent);
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
        String formatGiaThue=formatUtils.formatCurrencyVietnam(getIncurentInformation().getGiaThue());
        String formatDol=formatUtils.formatDateToString(getIncurentInformation().getNgayThue());
        String formatDor1=formatUtils.formatDateToString(getIncurentInformation().getNgayTra());
        tvIDHDCT.setText(getIncurentInformation().getIdHopDongChiTiet());
        tvName.setText(getIncurentInformation().getTenSanPham() + "/"+getIncurentInformation().getIdPhatSinh()+"/"+getIncurentInformation().getIdSanPham());
        tvPrice.setText(formatGiaThue);
        tvvDol.setText(formatDol);
        tvDor.setText(formatDor1);

        String note=getIncurentInformation().getNoiDung();
        Float fine=getIncurentInformation().getPhiPhatSinh();
        String dor=getIncurentInformation().getHanTra();

        if(note!=null){
            edNote.setText(note);
            edFine.setVisibility(View.VISIBLE);
            edFine.setText(""+formatUtils.formatCurrencyVietnam(fine));
            edNote.setEnabled(false);
            edFine.setEnabled(false);
            edDor.setEnabled(false);
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

        imBack.setOnClickListener(view -> {
            finish();
        });
        edNote.setOnClickListener(view -> {
            showPopupMenuIncurrentNote(view);
        });

        edDor.setOnClickListener(view -> {
            try {
                customComponent.showDatePicker(edDor,UpdateOncurrentActivity.this);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void saveOncurrent(){
        int idPS=getIncurentInformation().getIdPhatSinh();
        String idSP=getIncurentInformation().getIdSanPham();
        String idHD=getIncurentInformation().getIdHopDong();
        String note=edNote.getText().toString().trim();
        String dor=edDor.getText().toString().isEmpty() ? null: formatUtils.formatStringToStringMySqlFormat(edDor.getText().toString().trim());
        String fine=edFine.getText().toString().trim();
        Float formatFine = fine.isEmpty() ? null : Float.parseFloat(fine);

        Incurrent incurrent=new Incurrent(idPS,note,dor,formatFine,idSP,idHD);
        updateOncurrent(incurrent);

    }

    private int validateIncurrent(){
        int check=1;
        String note=edNote.getText().toString();
        String dor= edDor.getText().toString();
        String fine=edFine.getText().toString();
        String stringOldor=tvDor.getText().toString();


        if(check==1 && note.equalsIgnoreCase("Không có phát sinh") || note.equalsIgnoreCase("Nội dung") || note.isEmpty()){
            Toast.makeText(this, "Chưa chọn nội dung ", Toast.LENGTH_SHORT).show();
            check=-1;
        }

        if(check == 1 && edDor.getVisibility() == View.VISIBLE && dor.isEmpty()){
            Toast.makeText(this, "Chưa chọn hạn trả ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        if(check == 1 && edFine.getVisibility() == View.VISIBLE && fine.isEmpty()){
            Toast.makeText(this, "Chưa nhập tiền phạt ", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if(note.equalsIgnoreCase("Khách trả đồ muộn")){
            try {
                Date oldDor=formatUtils.parserStringToDate(stringOldor);
                Date newDor=formatUtils.parserStringToDate(dor);

                if(check==1 && newDor.before(oldDor)){
                    Toast.makeText(this, "Hạn trả không hợp lệ ", Toast.LENGTH_SHORT).show();
                    check = -1;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return check;
    }

    private void showPopupMenuIncurrentNote(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
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
                    Toast.makeText(UpdateOncurrentActivity.this,"Cập nhật phát sinh thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateOncurrentActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateOncurrentActivity.this,"Lỗi"+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private Incurrent getIncurentInformation(){
        Incurrent incurrent=(Incurrent) getIntent().getSerializableExtra("oncurrentList");
        return incurrent;
    }
}