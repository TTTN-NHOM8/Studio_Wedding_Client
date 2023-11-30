package com.example.studiowedding.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studiowedding.R;
import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Employee;
import com.example.studiowedding.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TaskEmployeeJoinAdapter extends RecyclerView.Adapter<TaskEmployeeJoinAdapter.ViewHolder> implements Filterable {

    private List<Employee> mList;
    private final List<Employee> mListTemp;
    private OnItemClickListner.TaskEmployeeJoinI itemClickListener;

    public TaskEmployeeJoinAdapter(List<Employee> employeeList) {
        this.mList = employeeList;
        this.mListTemp = employeeList;
    }

    public void setOnClickItem(OnItemClickListner.TaskEmployeeJoinI itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_employee_join, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = mList.get(position);
        if (employee == null){
            return;
        }
        holder.bind(employee);
        holder.layout.setOnClickListener(view -> itemClickListener.nextScreen(employee));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            // loc du lieu theo dk
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString().toLowerCase(Locale.getDefault());
                ArrayList<Employee> listTask = new ArrayList<>();

                if (search.isEmpty()){
                    listTask.addAll(mListTemp);
                }else {
                    for (Employee employee : mListTemp ) {
                        if (employee.getHoTen().toLowerCase(Locale.getDefault()).contains(search.toLowerCase())){
                            listTask.add(employee);
                        }
                    }
                }

                FilterResults  filterResults = new FilterResults();
                filterResults.values = listTask;
                return filterResults;
            }

            // lay ket qua loc
            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mList = (List<Employee>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ConstraintLayout layout;
        public CircleImageView circleImageView;
        public TextView tvNameEmployee, tvRoleEmployee;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_employee);
            circleImageView = itemView.findViewById(R.id.civ_avt_employee_join);
            tvNameEmployee = itemView.findViewById(R.id.tv_name_employee_join);
            tvRoleEmployee = itemView.findViewById(R.id.tv_role_employee_join);
        }

        public void bind(Employee employee){
            Glide.with(itemView.getContext()).load(employee.getAnh()).placeholder(R.drawable.avt_demo_employee).into(circleImageView);
            tvNameEmployee.setText(employee.getHoTen());
            tvRoleEmployee.setText(employee.getVaiTro());
        }
    }
}
