package com.example.studiowedding.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studiowedding.R;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;

import java.util.List;

public class TaskJoinAdapter extends RecyclerView.Adapter<TaskJoinAdapter.ViewHolder> {
    private final List<Employee> mList;
    private OnItemClickListner.TaskJoinI mOnClickItem;

    public TaskJoinAdapter(List<Employee> mList, OnItemClickListner.TaskJoinI mOnClickItem) {
        this.mList = mList;
        this.mOnClickItem = mOnClickItem;
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
        holder.ivMenu.setOnClickListener(view ->  showPopupEdit(holder, employee));
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
        private final ImageView ivMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_employee_join_name);
            role = itemView.findViewById(R.id.tv_employee_join_role);
            ivMenu = itemView.findViewById(R.id.iv_employee_join_menu);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Employee employee){
           name.setText(employee.getHoTen());
           role.setText(employee.getVaiTro());
        }
    }
}
