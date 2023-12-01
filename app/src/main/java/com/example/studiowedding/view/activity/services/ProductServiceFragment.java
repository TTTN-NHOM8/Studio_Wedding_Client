package com.example.studiowedding.view.activity.services;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ProductAdapter;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Product;

import java.util.List;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_service, container, false);

        ImageView ivProduct = view.findViewById(R.id.iv_product);

        // Attach a click listener to the ImageView (iv_product)
        ivProduct.setOnClickListener(v -> showPopupMenu(ivProduct));

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
                    ((ImageView) view).setColorFilter(getResources().getColor(R.color.yellow));
                    return true;
                case R.id.menu_green:
                    // Xử lý khi chọn màu xanh
                    // Ví dụ: thay đổi màu của ImageView thành màu xanh
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

}