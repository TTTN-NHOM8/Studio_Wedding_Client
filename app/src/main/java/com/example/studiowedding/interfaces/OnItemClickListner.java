package com.example.studiowedding.interfaces;

import android.view.View;

import com.example.studiowedding.model.Employee;
import com.example.studiowedding.model.Task;

public interface OnItemClickListner {
    void onItemClick(int position);

    interface TaskI{
      void nextUpdateScreenTask(Task task);
      void showConfirmDelete(Task task, View view);
    }

    interface EmployeeI{
        void nextUpdateScreenEmployee(Employee employee);
        void showConfirmDeleteEmployee();
    }
  
    interface Child {
        void showConfirmDelete();

        void onDeleteButtonClick(int position);
    }
    }

