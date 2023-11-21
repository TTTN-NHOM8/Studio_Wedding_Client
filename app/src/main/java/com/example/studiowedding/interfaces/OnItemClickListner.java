package com.example.studiowedding.interfaces;

import com.example.studiowedding.model.Employee;
import com.example.studiowedding.model.Task;

public interface OnItemClickListner {
    void onItemClick(int position);

    interface TaskI{
      void nextUpdateScreenTask(Task task);
      void showConfirmDelete();
    }

    interface EmployeeI{
        void nextUpdateScreenEmployee(Employee employee);
        void showConfirmDeleteEmployee();
    }

}
