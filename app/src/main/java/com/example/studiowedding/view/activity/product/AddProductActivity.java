package com.example.studiowedding.view.activity.product;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.studiowedding.R;
import com.example.studiowedding.databinding.ActivityAddProductBinding;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    ProgressDialog pd;
    ActivityAddProductBinding viewBinding;
    Product product = null;
    String imageUrl = "";
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private StorageReference storageReference;

    @Override
    protected void onStart() {
        try{
            FirebaseApp.initializeApp(this);
            Log.d("123", "c22");
        }catch (Exception e){
            Log.d("123", "onStart: "+e.getMessage());
        }
        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        pd.show();
                        Glide.with(this)
                                .load(uri)
                                .into(viewBinding.imgPopProduct);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                        String imageName = sdf.format(new Date());
                        storageReference = FirebaseStorage.getInstance().getReference("/images/" + imageName);
                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.d("123", taskSnapshot.toString());
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadUri) {
                                        // Handle the download URL, for example, display it in a TextView or load it into an ImageView
                                        String imageUrl = downloadUri.toString();
                                        AddProductActivity.this.imageUrl = imageUrl;
                                        pd.hide();
                                    }
                                });
                            }
                        });
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pd = new ProgressDialog(AddProductActivity.this);
        pd.setMessage("loading");
        viewBinding = ActivityAddProductBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        product = (Product) getIntent().getSerializableExtra("product");
        viewBinding.backImageView.setOnClickListener(v->{
            onBackPressed();
        });
        if (product != null) {
            imageUrl = product.getImgUrl();
            viewBinding.edProductName.setText(product.getName());
            viewBinding.edProductPrice.setText(product.getPrice() + "");
            viewBinding.btnAddProduct.setText("Cập nhật");
            if(!product.getImgUrl().equals("")){
                Glide.with( this)
                        .load(product.getImgUrl())
                        .into(viewBinding.imgPopProduct);
            }

            viewBinding.tvToolBarProduct.setText("Cập nhật sản phẩm");
        } else {
            viewBinding.constraintLayout12.setVisibility(View.GONE);
        }
        requestPermission();
        viewBinding.spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setContentView(viewBinding.getRoot());
    }

    void fillSpinnerData(){
        String[] productArray = getResources().getStringArray(R.array.product_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        String[] productArray2 = getResources().getStringArray(R.array.product_type_array);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productArray2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewBinding.spStatus.setAdapter(adapter2);
        viewBinding.spProduct.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        fillSpinnerData();
        viewBinding.btnAddProduct.setOnClickListener(v -> {
            if (imageUrl.equals("")) {
                Snackbar snackbar = Snackbar.make(viewBinding.btnAddProduct, "Vui lòng chọn ảnh sản phẩm", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                // Hiển thị Snackbar
                snackbar.show();
                return;
            }
            if(!isValidProductName(viewBinding.edProductName.getText().toString())){
                Snackbar snackbar = Snackbar.make(viewBinding.btnAddProduct, "Vui lòng nhập tên sản phẩm hợp lệ", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                // Hiển thị Snackbar
                snackbar.show();
                return;
            }
            if(viewBinding.edProductPrice.getText().toString().equals("")){
                Snackbar snackbar = Snackbar.make(viewBinding.btnAddProduct, "Vui lòng nhập giá tiền", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                // Hiển thị Snackbar
                snackbar.show();
                return;
            }
            if (product != null) {

                updateProduct();
                return;
            }
            addProduct();
        });
        viewBinding.cvProduct.setOnClickListener(v -> {
            getImage();
        });
        if(product != null){
            if(product.getStatus().equals("Chưa sẵn sàng")){
                viewBinding.spStatus.setSelection(1);
            }else if(product.getStatus().equals("Sẵn sàng")){
                viewBinding.spStatus.setSelection(0);
            }else {
                viewBinding.spStatus.setSelection(2);
            }
            Log.d("123", "fillSpinnerData: "+product.getType());
            if(product.getType().toLowerCase().equals("cô dâu")){
                viewBinding.spProduct.setSelection(1);
            }else if(product.getType().toLowerCase().equals("chú rể")){
                viewBinding.spProduct.setSelection(0);
            }
        }
        super.onResume();
    }

    void getImage() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(AddProductActivity.this, "Quyền hình ảnh được chấp nhận", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(AddProductActivity.this, "Quyền hình ảnh bị từ chối" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    void addProduct() {
        if (viewBinding.edProductPrice.getText().length() == 0 || viewBinding.edProductName.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng điền đủ trường", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiClient.getClient().create(ApiService.class).addProduct(new Product(
                        viewBinding.edProductName.getText().toString(),
                        Float.parseFloat(viewBinding.edProductPrice.getText().toString()),
                        (String) viewBinding.spStatus.getSelectedItem(),
                        (String) viewBinding.spProduct.getSelectedItem(),
                        imageUrl

                )).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        AddProductActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddProductActivity.this, "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void updateProduct() {
        if (viewBinding.edProductPrice.getText().length() == 0 || viewBinding.edProductName.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng điền đủ trường", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiClient.getClient().create(ApiService.class).updateProduct(product.getId() + "", new Product(
                        viewBinding.edProductName.getText().toString(),
                        Float.parseFloat(viewBinding.edProductPrice.getText().toString()),
                        (String) viewBinding.spStatus.getSelectedItem(),
                        (String) viewBinding.spProduct.getSelectedItem(),
                        imageUrl)).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        AddProductActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddProductActivity.this, "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private  boolean isValidProductName(String productName) {
        try{
            float a = 5 - Float.parseFloat(productName);
            return false;
        }catch (Exception exception){
            return true;
        }
    }
}