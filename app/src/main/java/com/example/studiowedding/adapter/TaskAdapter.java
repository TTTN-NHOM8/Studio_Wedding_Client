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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final List<Task> mList;
    private OnItemClickListner.TaskI mOnClickItem;
    public TaskAdapter(List<Task> mList) {
        this.mList = mList;
    }

    public void setOnClickItem(OnItemClickListner.TaskI mOnClickItem){
        this.mOnClickItem = mOnClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mList.get(position);
        holder.bind(task);
        holder.ivBtn.setOnClickListener(view -> showPopupEdit(holder, task));

    }

    @SuppressLint("NonConstantResourceId")
    private void showPopupEdit(@NonNull ViewHolder holder, Task task){
        PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.ivBtn);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu_options, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.action_update:
                    mOnClickItem.nextUpdateScreenTask(task);
                    return true;
                case R.id.action_delete:
                    mOnClickItem.showConfirmDelete();
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvId, tvDate, tvStatus, tvName, tvAddress, tvEmployee;
        private final ImageView ivBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id_hd_job);
            tvDate = itemView.findViewById(R.id.tv_date_job);
            tvStatus = itemView.findViewById(R.id.tv_status_job);
            tvName = itemView.findViewById(R.id.tv_name_job);
            tvAddress = itemView.findViewById(R.id.tv_address_job);
            tvEmployee = itemView.findViewById(R.id.tv_employee_job);
            ivBtn = itemView.findViewById(R.id.iv_menu_job);
        }

        public void bind(Task task){
            tvId.setText(task.getId());
            tvDate.setText(task.getDate());
            switch (task.getStatusTask()){
                case "Đang thực hiện" :
                    tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.yellow));
                    tvStatus.setText(task.getStatusTask());
                    break;
                case "Đã xong" :
                    tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.dark_green));
                    tvStatus.setText(task.getStatusTask());
                    break;
            }
            tvName.setText(task.getName());
            tvAddress.setText(task.getAddress());
            if (task.getEmployee() != null && !task.getEmployee().isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String str : task.getEmployee()) {
                    stringBuilder.append(str).append(", ");
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 2);

                tvEmployee.setText(stringBuilder.toString());
            } else {
                tvEmployee.setText("");
            }
        }
    }
}
