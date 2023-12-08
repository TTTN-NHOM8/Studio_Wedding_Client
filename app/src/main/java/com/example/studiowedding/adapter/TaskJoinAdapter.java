package com.example.studiowedding.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studiowedding.R;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.model.Task;

import java.util.List;

public class TaskJoinAdapter extends RecyclerView.Adapter<TaskJoinAdapter.ViewHolder> {
    private  List<Employee> mList;
    private final OnItemClickListner.TaskJoinI mOnClickItem;
    private boolean isInteractionEnabled = true;
    public TaskJoinAdapter(List<Employee> mList, OnItemClickListner.TaskJoinI mOnClickItem) {
        this.mList = mList;
        this.mOnClickItem = mOnClickItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Employee> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPopupMenu(boolean enabled){
        this.isInteractionEnabled = enabled;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee_join, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = mList.get(position);
        if (employee == null){
            return;
        }
        holder.bind(employee);
        if (isInteractionEnabled) {
            holder.ivMenu.setOnClickListener(view -> showPopupEdit(holder, employee));
        } else {
            holder.ivMenu.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @SuppressLint("NonConstantResourceId")
    private void showPopupEdit(@NonNull TaskJoinAdapter.ViewHolder holder, Employee employee){
        PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.ivMenu);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu_options, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.action_update:
                    mOnClickItem.nextScreen(employee);
                    return true;
                case R.id.action_delete:
                    mOnClickItem.showConfirmDelete(employee, holder.itemView);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, role;
        private final ImageView ivMenu, ivAvt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_employee_join_name);
            role = itemView.findViewById(R.id.tv_employee_join_role);
            ivMenu = itemView.findViewById(R.id.iv_employee_join_menu);
            ivAvt = itemView.findViewById(R.id.iv_employee_join_avt);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Employee employee){
            Glide.with(itemView.getContext()).load(employee.getAnh()).placeholder(R.drawable.avt_demo_employee).into(ivAvt);
           name.setText(employee.getHoTen());
           role.setText(employee.getVaiTro());
        }
    }
}
