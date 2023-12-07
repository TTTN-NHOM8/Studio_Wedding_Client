package com.example.studiowedding.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studiowedding.R;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employeeList;

    private OnItemClickListner.EmployeeI itemClickListner;

    public EmployeeAdapter(List<Employee> employeeList) {

        this.employeeList = employeeList;
    }


    public void setOnClickItem(OnItemClickListner.EmployeeI itemClickListner){
        this.itemClickListner = itemClickListner;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        if (employee == null){
            return;
        }

//
//        Log.e("img", employee.getAnh() + "abc");
        Glide.with(holder.itemView.getContext())
                .load(employee.getAnh())
                .centerCrop()
                .placeholder(R.drawable.error_image)
                .into(holder.circleImageView);
        holder.tvNameEmployee.setText(employee.getHoTen());
        holder.tvRoleEmployee.setText(employee.getVaiTro());
        holder.ivMenu.setOnClickListener(view -> showPopupEdit(holder, employee));
    }

    @SuppressLint("NonConstantResourceId")
    private void showPopupEdit(@NonNull EmployeeAdapter.EmployeeViewHolder holder, Employee employee){
        PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.ivMenu);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu_options, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.action_update:
                    itemClickListner.nextUpdateScreenEmployee(employee);
                    return true;
                case R.id.action_delete:
                    itemClickListner.showConfirmDeleteEmployee();
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return employeeList == null ? 0 : employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView circleImageView;
        public TextView tvNameEmployee, tvRoleEmployee;
        public ImageView ivMenu;

        public ImageView ivFiler;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.civ_avt_employee);
            tvNameEmployee = itemView.findViewById(R.id.tv_name_employee);
            tvRoleEmployee = itemView.findViewById(R.id.tv_role_employee);
            ivMenu = itemView.findViewById(R.id.iv_menu_employee);
            ivFiler = itemView.findViewById(R.id.imgFilterContract);
        }

        public void setListener(Employee employee) {
            tvNameEmployee.setText(employee.getHoTen());
            tvRoleEmployee.setText(employee.getVaiTro());
        }

    }
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    employeeList = new ArrayList<>();
                } else {
                    List<Employee> list = new ArrayList<>();
                    for (Employee employee : employeeList) {
                        if (employee.getHoTen().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(employee);
                        }
                    }
                    employeeList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = employeeList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                employeeList = (List<Employee>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
    public void filterByRole (String role) {
        List<Employee> filterList = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee.getVaiTro().equals(role)) {
                filterList.add(employee);
            }
        }
        employeeList.clear();
        employeeList = filterList;
        notifyDataSetChanged();
    }
}