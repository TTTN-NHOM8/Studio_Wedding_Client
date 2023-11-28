package com.example.studiowedding.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.EmployeeAdapter;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.view.activity.employee.AddEmployeeActivity;
import com.example.studiowedding.view.activity.employee.UpdateEmployeeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmployeeFragment extends Fragment implements OnItemClickListner.EmployeeI {

    private FloatingActionButton floatingActionButton;
    private ImageView ivFilter;
    private RecyclerView rcvEmployee;

    private SearchView searchView;

    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeListold;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_employee, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvEmployee = view.findViewById(R.id.rcv_employee_list);
        floatingActionButton = view.findViewById(R.id.fabContract);
        ivFilter = view.findViewById(R.id.imgFilterContract);
        searchView = view.findViewById(R.id.searchView);
        floatingActionButton.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AddEmployeeActivity.class)));
        setAdapter();
        setSearchView();
        ivFilter.setOnClickListener(v -> showFilterPopupMenu(v));
        }
    private void showFilterPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(requireContext(),view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.filternv, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.quanly:
                    filterByRole("Quản Lý");
                    break;
                case R.id.makeup:
                    filterByRole("Make Up");
                    break;
                case R.id.laixe:
                    filterByRole("Lái Xe");
                    break;
                case R.id.chuphinh:
                    filterByRole("Chụp Hình");
                    break;
                case R.id.haucan:
                    filterByRole("Hậu Cần");
                    break;
                case R.id.loctatca:
                    filterByRole("Lọc tất cả trạng thái");
                    break;
            }
            return true;
        });
        popupMenu.show();
    }
    private void filterByRole (String role){
        if (employeeAdapter != null){
            employeeAdapter.filterByRole(role);
        }
    }


    private void setSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                employeeAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                employeeAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    public void setAdapter(){
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("NV1@gmail.com", "Lê Viết Dũng", "abc", "28/08/2003", "Nam", "0365411154", "Hà Tĩnh", "https://www.pngmart.com/files/21/Admin-Profile-PNG-Clipart.png", "Thợ Ảnh", 1));
        list.add(new Employee("NV1@gmail.com", "Lê Viết Dũng", "abc", "28/08/2004", "Nam", "0365411154", "Hà Tĩnh", "https://www.pngmart.com/files/21/Admin-Profile-PNG-Clipart.png", "Thợ Ảnh", 1));
        list.add(new Employee("NV1@gmail.com", "Lê Viết Dũng", "abc", "28/08/2003", "Nam", "0365411154", "Hà Tĩnh", "https://www.pngmart.com/files/21/Admin-Profile-PNG-Clipart.png", "Thợ Ảnh", 1));
        list.add(new Employee("NV1@gmail.com", "Lê Viết Dũng", "abc", "28/08/2003", "Nam", "0365411154", "Hà Tĩnh", "https://www.pngmart.com/files/21/Admin-Profile-PNG-Clipart.png", "Thợ Ảnh", 1));

        EmployeeAdapter employeeAdapter = new EmployeeAdapter(list,list);
        employeeAdapter.setOnClickItem(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvEmployee.setLayoutManager(linearLayoutManager);
        rcvEmployee.setAdapter(employeeAdapter);


    }


    @Override
    public void nextUpdateScreenEmployee(Employee employee) {
        Intent intent = new Intent(getContext(), UpdateEmployeeActivity.class);
        intent.putExtra("employee", employee);
        startActivity(intent);
    }

    @Override
    public void showConfirmDeleteEmployee() {
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

}