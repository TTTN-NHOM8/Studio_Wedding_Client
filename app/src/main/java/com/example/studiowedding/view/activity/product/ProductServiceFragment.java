package com.example.studiowedding.view.activity.services;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ProductAdapter;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.product.AddProductActivity;
import com.example.studiowedding.view.activity.task.ResponseTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductServiceFragment extends Fragment implements OnItemClickListner.Child {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnItemClickListner.TaskI mOnClickItem;
    private String mParam2;

    private List<Product> dataList;
    private ProductAdapter adapter;
    public ProductServiceFragment() {
        // Required empty public constructor
    }

    RecyclerView rcvProducts;
    EditText searchEditext ;
    FloatingActionButton fabAddProduct ;
    public static ProductServiceFragment newInstance(String param1, String param2) {
        ProductServiceFragment fragment = new ProductServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onPause() {
        searchEditext.setText("");
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_service, container, false);

        ImageView ivProduct = view.findViewById(R.id.iv_product);
        // Attach a click listener to the ImageView (iv_product)
        ivProduct.setOnClickListener(v -> showPopupMenu(ivProduct));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        rcvProducts = view.findViewById(R.id.rcvProduct);
        rcvProducts.setLayoutManager(linearLayoutManager);
        fabAddProduct = view.findViewById(R.id.fabService);
        searchEditext = view.findViewById(R.id.et_search_task);
        searchEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Trước khi văn bản thay đổi
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Trong quá trình văn bản thay đổi
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString();
                ApiClient.getClient().create(ApiService.class).getProductsByName(searchText).enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.body()!=null){
                            dataList = response.body();
                            fillData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
            }
        });
        fabAddProduct.setOnClickListener(v->{
            startActivity(new Intent(getContext(), AddProductActivity.class));
        });
        return view;
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.inflate(R.menu.pop_menu_product_arrange); // Đặt menu của bạn ở đây

        // Bắt sự kiện khi một mục trong menu được chọn
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_yellow:
                    // Xử lý khi chọn màu vàng
                    // Ví dụ: thay đổi màu của ImageView thành màu vàng
                    fillData(filterByStatus("Chưa sẵn sàng"));
                    ((ImageView) view).setColorFilter(getResources().getColor(R.color.yellow));
                    return true;
                case R.id.menu_green:
                    fillData(filterByStatus("Sẵn sàng"));
                    ((ImageView) view).setColorFilter(getResources().getColor(R.color.green));
                    return true;
                case R.id.menu_red:
                    // Xử lý khi chọn màu đỏ
                    // Ví dụ: thay đổi màu của ImageView thành màu đỏ
                    ((ImageView) view).setColorFilter(getResources().getColor(R.color.red));
                    return true;
                default:
                    return false;
            }
        });

        // Hiển thị PopupMenu
        popupMenu.show();
    }

    @Override
    public void onResume() {

        super.onResume();
        getProducts();

    }

    @Override
    public void showConfirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa công việc");
        builder.setMessage("Bạn chắc chắn muốn xóa công việc này ?");

        builder.setPositiveButton("Đồng ý", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onDeleteButtonClick(int position) {

    }
    private void getProducts() {
        ApiClient.getClient().create(ApiService.class).getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.body().size()>0){
                    dataList = response.body();
                    fillData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    private void fillData(List<Product> listProduct){
        ProductAdapter productAdapter = new ProductAdapter(listProduct);
        rcvProducts.setAdapter(productAdapter);
    }
    private  List<Product> filterByStatus(String targetStatus) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : dataList) {
            if (targetStatus.equals(product.getStatus())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}