package com.example.studiowedding.view.activity.account;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.studiowedding.R;
import com.example.studiowedding.adapter.AccountAdapter;

public class EditInformation extends AppCompatActivity {

    ImageView imgback;
    Spinner spinnerRoles;
    EditText edManv, edHoten, edDienthoai, edDiachi;
    Button btnluu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        edManv = findViewById(R.id.edMaNv);
        edHoten = findViewById(R.id.edHoten);
        edDienthoai = findViewById(R.id.edSdt);
        edDiachi = findViewById(R.id.edDiachi);
        btnluu = findViewById(R.id.btnLuu);
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Manv = edManv.getText().toString();
                String Hoten = edHoten.getText().toString();
                String Sdt = edDienthoai.getText().toString();
                String Diachi = edDiachi.getText().toString();

                if (Manv.isEmpty() || Sdt.isEmpty() || Hoten.isEmpty() || Diachi.isEmpty() || Diachi == null) {
                    Toast.makeText(EditInformation.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditInformation.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                }

            }
        });


        TextView tvNgay;
        tvNgay = findViewById(R.id.tvNgaySing);

        int year = 0, month = 0, day = 0;
        DatePickerDialog datePickerDialog;
        DatePickerDialog.OnDateSetListener dateSetListener;


        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                tvNgay.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        };
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);


        tvNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        imgback = findViewById(R.id.imgBack);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spinnerRoles = findViewById(R.id.spVaitro);
        String[] rolesArray = {"Chọn vai trò","1", "2"};
        AccountAdapter adapter = new AccountAdapter(this, R.layout.item_account, rolesArray);
        adapter.setDropDownViewResource(R.layout.item_account);
        spinnerRoles.setAdapter(adapter);

        // Optional: Set up a listener for item selection
        spinnerRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item
                String selectedRole = (String) parentView.getItemAtPosition(position);
                // You can perform any action based on the selected role
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }
}
