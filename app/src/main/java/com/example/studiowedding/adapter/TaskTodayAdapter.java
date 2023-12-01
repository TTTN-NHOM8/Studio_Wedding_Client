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
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Task;
import com.example.studiowedding.utils.FormatUtils;

import java.util.List;

public class TaskTodayAdapter extends RecyclerView.Adapter<TaskTodayAdapter.ViewHolder> {

    private  List<Task> mList;
    private OnItemClickListner.TaskI mOnClickItem;
    public TaskTodayAdapter(List<Task> mList) {
        this.mList = mList;
    }

    public void setOnClickItem(OnItemClickListner.TaskI mOnClickItem){
        this.mOnClickItem = mOnClickItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Task> mList){
        this.mList = mList;
        notifyDataSetChanged();
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
        if (task == null){
            return;
        }
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
                    mOnClickItem.showConfirmDelete(task, holder.view);
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
        private final View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id_hd_job);
            tvDate = itemView.findViewById(R.id.tv_date_job);
            tvStatus = itemView.findViewById(R.id.tv_status_job);
            tvName = itemView.findViewById(R.id.tv_name_job);
            tvAddress = itemView.findViewById(R.id.tv_address_job);
            tvEmployee = itemView.findViewById(R.id.tv_employee_job);
            ivBtn = itemView.findViewById(R.id.iv_menu_job);
            view = itemView.findViewById(R.id.layout_task);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Task task){
            tvId.setText(task.getIdContract());
            if (task.getDateImplement() == null){
                tvDate.setText(FormatUtils.formatDateToString(task.getDataLaundry()));
                tvName.setText(AppConstants.NAME_TASK);
                tvAddress.setText(AppConstants.ADDRESS_TASK);
            }else {
                tvDate.setText(FormatUtils.formatDateToString(task.getDateImplement()));
                tvName.setText(task.getNameService());
                tvAddress.setText(task.getAddress());
            }
            switch (task.getStatusTask()){
                case AppConstants.STATUS_TASK_IM   :
                    tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.yellow));
                    tvStatus.setText(task.getStatusTask());
                    break;
                case AppConstants.STATUS_TASK_DONE :
                    tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.dark_green));
                    tvStatus.setText(task.getStatusTask());
                    break;
            }
            if (task.getEmployee() != null){
                tvEmployee.setText(task.getEmployee());
            }else {
                tvEmployee.setText(AppConstants.EMPLOYEE_TASK);
            }
        }
    }
}
