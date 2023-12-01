package com.example.studiowedding.view.activity.account;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.AccountAdapter;
import com.example.studiowedding.model.Account;
import com.example.studiowedding.view.activity.account.AccountResponse;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInformation extends AppCompatActivity {
    RadioButton rdGioitinhnam, rdNu;
    ImageView imgBack;
    Spinner spinnerRoles;
    EditText edMaNv, edHoTen, edDienThoai, edDiaChi;
    TextView tvNgaySinh;
    Button btnLuu;

    private ApiService apiService;
    private String selectedRole;
    private String selectedRoleBeforeChange;
    private String mySqlFormattedDate;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
    FormatUtils formatUtils = new FormatUtils();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        apiService = ApiClient.getApiService();

        edMaNv = findViewById(R.id.edMaNv);
        edMaNv.setEnabled(false);
        edHoTen = findViewById(R.id.edHoten);
        edDienThoai = findViewById(R.id.edSdt);
        edDiaChi = findViewById(R.id.edDiachi);
        btnLuu = findViewById(R.id.btnLuu);
        tvNgaySinh = findViewById(R.id.tvNgaySing);
        rdGioitinhnam = findViewById(R.id.radioButtonMale);
        rdNu = findViewById(R.id.radioButtonFemale);
        spinnerRoles = findViewById(R.id.spVaitro);

        Intent intent = getIntent();
        String idNhanVien1 = intent.getStringExtra("idNhanVien");
        Log.d("thuid", idNhanVien1);
        getAll(idNhanVien1);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Xử lý Spinner Vai tro
        List<String> rolesList = Arrays.asList("Quan li", "Lai xe", "Chup hinh", "Make up");
        String[] rolesArray = rolesList.toArray(new String[0]);
        AccountAdapter adapter = new AccountAdapter(EditInformation.this, R.layout.item_account, rolesArray);
        adapter.setDropDownViewResource(R.layout.item_account);
        spinnerRoles.setAdapter(adapter);

        spinnerRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedRole = (String) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        // Store the initial role value when the activity is created
        selectedRoleBeforeChange = selectedRole;

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MaNv = edMaNv.getText().toString();
                String HoTen = edHoTen.getText().toString();
                String DienThoai = edDienThoai.getText().toString();
                String DiaChi = edDiaChi.getText().toString();

                try {
                    String formatNgayThanhToan = tvNgaySinh.getText().toString().isEmpty() ? null : sdf.format(sdf2.parse(tvNgaySinh.getText().toString().trim()));
                    String GioiTinh = rdGioitinhnam.isChecked() ? "Nam" : "Nữ";
                    if (MaNv.isEmpty() || HoTen.isEmpty() || DienThoai.isEmpty() || DiaChi.isEmpty()) {
                        Toast.makeText(EditInformation.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        // Check if the role has changed
                            // Role hasn't changed, proceed with updating information
                            updateEmployeeInfo(MaNv, HoTen, formatNgayThanhToan, GioiTinh, DienThoai, DiaChi, selectedRole);
                            Toast.makeText(EditInformation.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            if (!selectedRole.equals(selectedRoleBeforeChange)) {
                            logout();

                           }
                            finish();
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        tvNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        int year = 0, month = 0, day = 0;

        String[] dateParts = tvNgaySinh.getText().toString().split("/");
        if (dateParts.length == 3) {
            day = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]) - 1;
            year = Integer.parseInt(dateParts[2]);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String formattedDate = (selectedDay < 10 ? "0" : "") + selectedDay + "/"
                                + ((selectedMonth + 1) < 10 ? "0" : "") + (selectedMonth + 1) + "/" + selectedYear;
                        tvNgaySinh.setText(formattedDate);
                        try {
                            mySqlFormattedDate = FormatUtils.formatStringToStringMySqlFormat(formattedDate);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(EditInformation.this, "Lỗi định dạng ngày", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateEmployeeInfo(String idNhanVien, String hoVaTen, String ngaySinh, String gioiTinh, String dienThoai, String diaChi, String vaiTro) {
        Call<AccountResponse> call = apiService.updateEmployeeInfo(idNhanVien, hoVaTen, ngaySinh, gioiTinh, dienThoai, diaChi, vaiTro);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("EditInformation", "Cập nhật thông tin thành công");
                } else {
                    Log.e("EditInformation", "Cập nhật thông tin thất bại");
                    Toast.makeText(EditInformation.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.e("EditInformation", "Lỗi kết nối", t);
                Toast.makeText(EditInformation.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("LuuIdNhanvien", MODE_PRIVATE);
        preferences.edit().clear().apply();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private void getAll(String idNhanVien) {
        Call<AccountResponse> call = apiService.getEmployeeInfo(idNhanVien);

        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        Account accountResponse = response.body().getAllthongtin();
                        Account account = accountResponse;
                        Log.d("se: ", account.toString());

                        Date date = inputDateFormat.parse(account.getNgaySinh());
                        String outputDateStr = sdf2.format(date);

                        if (account != null) {
                            edMaNv.setText(account.getIdNhanVien());
                            edHoTen.setText(account.getHoVaTen());
                            edDienThoai.setText(account.getDienThoai());
                            edDiaChi.setText(account.getDiaChi());
                            tvNgaySinh.setText(outputDateStr);

                            selectedRoleBeforeChange = account.getVaiTro();

                            List<String> rolesList = Arrays.asList("Quan li", "Lai xe", "Chup hinh", "Make up");
                            String[] rolesArray = rolesList.toArray(new String[0]);

                            if (!"Quan li".equals(account.getVaiTro())) {
                                spinnerRoles.setEnabled(false);
                            } else {
                                spinnerRoles.setEnabled(true);
                            }
                            AccountAdapter adapter = new AccountAdapter(EditInformation.this, R.layout.item_account, rolesArray);
                            adapter.setDropDownViewResource(R.layout.item_account);
                            spinnerRoles.setAdapter(adapter);

                            int roleIndex = Arrays.asList(rolesArray).indexOf(account.getVaiTro());
                            if (roleIndex != -1) {
                                spinnerRoles.setSelection(roleIndex);
                            }

                            if ("Nam".equals(account.getGioiTinh())) {
                                rdGioitinhnam.setChecked(true);
                            } else if ("Nữ".equals(account.getGioiTinh())) {
                                rdNu.setChecked(true);
                            } else {
                                Toast.makeText(EditInformation.this, "Dữ liệu nhân viên không hợp lệ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EditInformation.this, "Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(EditInformation.this, "loi:" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(EditInformation.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.d("onFailure: ", t.getMessage());
            }
        });
    }

    public void thu() {

    }
}
