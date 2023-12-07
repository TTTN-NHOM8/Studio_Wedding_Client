package com.example.studiowedding.view.activity.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.studiowedding.R;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.example.studiowedding.utils.UIutils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmployeeActivity extends AppCompatActivity {
    private ProgressDialog loadingDialog;
    private ImageView ivBack, ivSpinnerRole, ivHidingPass;
    private CircleImageView civEmployee;
    private EditText etEmail, etPass, etName, etDob, etPhone, etLocation, etRole;
    private RadioGroup radioGender;
    private RadioButton radioMale, radioFemale;
    private LinearLayout btnUpdate;
    private String photoUrlPiker;
    private Employee mEmployee;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        loadingDialog = new ProgressDialog(this);
        setMapping();
        mEmployee = (Employee) getIntent().getSerializableExtra("employee");
        updateUI(mEmployee);
        setListener();
    }

    private void setMapping(){
        ivBack = findViewById(R.id.iv_back_update_employee);
        civEmployee = findViewById(R.id.civ_update_img_employee);
        etEmail = findViewById(R.id.et_update_email_employee);
        etPass = findViewById(R.id.et_update_pass_employee);
        etName = findViewById(R.id.et_update_name_employee);
        etDob = findViewById(R.id.et_update_dob_employee);
        etPhone = findViewById(R.id.et_update_phone_employee);
        etLocation = findViewById(R.id.et_update_address_employee);
        etRole = findViewById(R.id.et_update_role_employee);
        radioGender = findViewById(R.id.radioGenderUpdate);
        ivSpinnerRole = findViewById(R.id.ivSpinnerUpdateRole);
        btnUpdate = findViewById(R.id.btnUpdateEmployee);
        radioMale = findViewById(R.id.radioButtonUpdateMale);
        radioFemale = findViewById(R.id.radioButtonUpdateFemale);
        ivHidingPass = findViewById(R.id.iv_hiding_pass);
    }

    /**
     * Lấy thông tin của người dùng hiện tại từ API.
     */
    private void updateUI(Employee employee){
        if (employee != null){
            Glide.with(this)
                    .load(employee.getAnh())
                    .centerCrop()
                    .placeholder(R.drawable.error_image)
                    .into(civEmployee);
            etEmail.setText(employee.getIdNhanVien());
            etName.setText(employee.getHoTen());
            etPass.setText(employee.getMatKhau());
            String dobOrigin = getCustomDob(employee);
            etDob.setText(dobOrigin);
            etLocation.setText(employee.getDiaChi());
            etPhone.setText(employee.getDienThoai());
            etRole.setText(employee.getVaiTro());
        }
    }

    @NonNull
    private String getCustomDob(Employee employee) {
        String dobOrigin;
        try {
            dobOrigin = FormatUtils.formatDateToStringEmployee(sdf.parse(employee.getNgaySinh()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (employee.getGioiTinh().equals("Nam")){
            radioMale.setChecked(true);
        }else {
            radioFemale.setChecked(true);
        }
        return dobOrigin;
    }

    private void showSnackbar(String message) {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        UIutils.showSnackbar(rootView, message);
    }

    private void setListener(){
        ivBack.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        civEmployee.setOnClickListener(view -> requestPermission());
        etDob.setOnClickListener(view -> showDatePicker());
        ivSpinnerRole.setOnClickListener(view -> showRoleOptions());
        btnUpdate.setOnClickListener(view -> startUpdateEmployee());
        ivHidingPass.setOnClickListener(view -> onPasswordToggleImageClick());
    }

    /**
     * Kiểm tra người dùng đã cấp quyền truy cập vào thư viện ảnh
     * <p>
     * Nếu chưa thì sẽ hiển thị hộp thoại đẻ cấp quyền
     * <p>
     * Nếu đã được phép thì mở thư viện ảnh
     */

    private void requestPermission(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(UpdateEmployeeActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, AppConstants.REQUEST_IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            civEmployee.setImageURI(selectedImageUri);
            uploadImageToFirebaseFireStorage(selectedImageUri);
        }
    }

    private void uploadImageToFirebaseFireStorage(Uri selectedImageUri) {
        showLoadingDialog(true);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads");
        StorageReference imageRef = storageRef.child("profile_images/" + UUID.randomUUID().toString() + ".jpg");
        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    showLoadingDialog(false);
                    photoUrlPiker = uri.toString();

                }))
                .addOnFailureListener(e -> Toast.makeText(UpdateEmployeeActivity.this, "Failed to upload image." + e, Toast.LENGTH_SHORT).show());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.CustomDatePickerDialog,
                (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    String formattedDate = sdf.format(calendar.getTime());
                    etDob.setText(formattedDate);
                },
                year,
                month,
                dayOfMonth
        );

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private void showRoleOptions(){
        PopupMenu popupMenu = new PopupMenu(this, ivSpinnerRole);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_role_employee, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            String selectedRole = menuItem.getTitle().toString();
            etRole.setText(selectedRole);
            return true;
        });
        popupMenu.show();
    }

    private void startUpdateEmployee(){
        String id = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String location = etLocation. getText().toString().trim();
        String gender = "";
        if (radioMale.isChecked()){
            gender = radioMale.getText().toString().trim();
        }else {
            gender = radioFemale.getText().toString().trim();
        }
        String role = etRole.getText().toString().trim();
        Employee employee;
        if (photoUrlPiker != null){
            employee = new Employee(id,name,password,dob,gender,phone,location,photoUrlPiker,role);
        }else {
            employee = new Employee(id,name,password,dob,gender,phone,location,mEmployee.getAnh(),role);
        }
        if (!isValidDataInput(employee)){
            return;
        }
        performUpdateEmployee(employee);
    }

    private boolean isValidDataInput(Employee employee) {
        if (employee.getAnh() == null) {
            showSnackbar(AppConstants.PHOTO_URL_EMPTY_MESSAGE);
            return false;
        }

        if (FormatUtils.isDataInputEmpty(
                employee.getIdNhanVien(),
                employee.getHoTen(),
                employee.getNgaySinh(),
                employee.getGioiTinh(),
                employee.getDienThoai(),
                employee.getDiaChi(),
                employee.getVaiTro(),
                employee.getAnh())) {
            showSnackbar(AppConstants.DATA_INPUT_EMPTY_MESSAGE);
            return false;
        }

        if (!FormatUtils.isDataInputString(employee.getHoTen())) {
            showSnackbar(AppConstants.NAME_INVALID_MESSAGE);
            return false;
        }

        if (!FormatUtils.isValidDate(employee.getNgaySinh())) {
            showSnackbar(AppConstants.DATE_OF_BIRTH_INVALID_MESSAGE);
            return false;
        }

        if (!FormatUtils.isEmailValid(employee.getIdNhanVien())) {
            showSnackbar(AppConstants.EMAIL_INVALID_MESSAGE);
            return false;
        }

        if (!FormatUtils.isDataInputNumber(employee.getDienThoai())){
            showSnackbar(AppConstants.PHONE_INVALID_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Chỉnh sửa nhân viên thông qua gọi API
     */
    private void performUpdateEmployee(Employee employee){
        showLoadingDialog(true);
        ApiClient.getClient().create(ApiService.class).updateEmployee(
                employee.getIdNhanVien(),
                employee.getHoTen(),
                employee.getMatKhau(),
                employee.getNgaySinh(),
                employee.getGioiTinh(),
                employee.getDienThoai(),
                employee.getDiaChi(),
                employee.getAnh(),
                employee.getVaiTro()

        ).enqueue(new Callback<ResponseEmployee>() {
            @Override
            public void onResponse(Call<ResponseEmployee> call, Response<ResponseEmployee> response) {
                showLoadingDialog(false);
                if (response.isSuccessful()){
                    handleAddEmployeeResponse(response.body());
                    finish();
                }else {
                    Log.e("ERROR", AppConstants.CALL_API_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ResponseEmployee> call, Throwable t) {
                Log.e("FAILURE", AppConstants.CALL_API_FAILURE_MESSAGE + t);
            }
        });
    }

    private void handleAddEmployeeResponse(ResponseEmployee updateEmployeeResponse) {
        if (updateEmployeeResponse != null && updateEmployeeResponse.isSuccess()) {
            showSnackbar(AppConstants.UPDATE_EMPLOYEE_SUCCESS_MESSAGE);
        } else {
            showSnackbar(AppConstants.UPDATE_EMPLOYEE_FAILED_MESSAGE);
        }
    }

    public void onPasswordToggleImageClick(){
        UIutils.togglePasswordVisibleWithImage(etPass, ivHidingPass);
    }

    private void showLoadingDialog(boolean show) {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setMessage(AppConstants.LOADING_MESSAGE);
            loadingDialog.setCancelable(false);
        }
        if (show) {
            loadingDialog.show();
        } else {
            loadingDialog.dismiss();
        }
    }
}