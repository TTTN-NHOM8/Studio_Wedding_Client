package com.example.studiowedding.view.activity.employee;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toolbar;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeActivity extends AppCompatActivity {
    private ImageView ivBack, ivSpinnerRole;
    private CircleImageView civEmployee;
    private EditText etEmail, etName, etDob, etPhone, etLocation, etRole;
    private RadioGroup radioGender;
    private LinearLayout linearAdd;
    private ProgressDialog loadingDialog;
    private RadioButton radioMale, radioFemale;

    private String photoUrlPiker;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        setMapping();
        setListener();
    }

    private void setMapping() {
        ivBack = findViewById(R.id.iv_back_add_employee);
        civEmployee = findViewById(R.id.civ_add_img_employee);
        etEmail = findViewById(R.id.et_add_email_employee);
        etName = findViewById(R.id.et_add_name_employee);
        etDob = findViewById(R.id.et_add_dob_employee);
        etPhone = findViewById(R.id.et_add_phone_employee);
        etLocation = findViewById(R.id.et_add_address_employee);
        etRole = findViewById(R.id.et_add_role_employee);
        radioGender = findViewById(R.id.radioGender);
        linearAdd = findViewById(R.id.linearAdd);
        ivSpinnerRole = findViewById(R.id.ivSpinnerRole);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
    }

    private void setListener(){
        ivBack.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        civEmployee.setOnClickListener(view -> requestPermission());
        etDob.setOnClickListener(view -> showDatePicker());
        ivSpinnerRole.setOnClickListener(view -> showRoleOptions());
        linearAdd.setOnClickListener(view -> startAddEmployeeProcess());
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
                Toast.makeText(AddEmployeeActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
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
                .addOnFailureListener(e -> Toast.makeText(AddEmployeeActivity.this, "Failed to upload image." + e, Toast.LENGTH_SHORT).show());
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

    private void startAddEmployeeProcess(){
        String id = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String location = etLocation. getText().toString().trim();
        String gender = "";
        if (radioMale.isChecked()){
            gender = radioMale.getText().toString().trim();
        }else {
            gender = radioFemale.getText().toString().trim();
        }
        String role = etRole.getText().toString().trim();

        Employee employee = new Employee(id,name,dob,gender,phone,location,photoUrlPiker,role);
        if (!isValidDataInput(employee)){
            return;
        }
        performAddEmployee(employee);
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

//        if (!FormatUtils.isValidDate(employee.getNgaySinh())) {
//            showSnackbar(AppConstants.DATE_OF_BIRTH_INVALID_MESSAGE);
//            return false;
//        }

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

    private void showSnackbar(String message) {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        UIutils.showSnackbar(rootView, message);
    }

    /**
     * Thêm nhân viên thông qua gọi API
     */
    private void performAddEmployee(Employee employee){
        showLoadingDialog(true);
        ApiClient.getClient().create(ApiService.class).addEmployee(
                employee.getIdNhanVien(),
                employee.getHoTen(),
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
    private void handleAddEmployeeResponse(ResponseEmployee addEmployeeResponse) {
        if (addEmployeeResponse != null && addEmployeeResponse.isSuccess()) {
            showSnackbar(AppConstants.ADD_EMPLOYEE_SUCCESS_MESSAGE);
            refreshUI();
        } else {
            showSnackbar(AppConstants.EMPLOYEE_EXIST);
        }
    }
    private void refreshUI() {
        photoUrlPiker = null;
        UIutils.clearTextFields(
                etEmail,
                etName,
                etDob,
                etPhone,
                etLocation,
                etRole
        );
        civEmployee.setImageResource(R.drawable.pick_image);
    }

    private void showLoadingDialog(boolean show) {
        if (loadingDialog == null) {
            initLoadingDialog();
        }

        if (show) {
            loadingDialog.show();
        } else {
            loadingDialog.dismiss();
        }
    }

    private void initLoadingDialog() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(AppConstants.LOADING_MESSAGE);
        loadingDialog.setCancelable(false);
    }

}