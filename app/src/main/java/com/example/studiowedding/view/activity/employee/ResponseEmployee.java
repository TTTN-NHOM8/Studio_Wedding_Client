package com.example.studiowedding.view.activity.employee;

import com.example.studiowedding.constant.AppConstants;
import com.example.studiowedding.model.Employee;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseEmployee {
    private String status;
    @SerializedName("employeeList")
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess(){
        return AppConstants.RESPONSE_SUCCESS.equals(this.status);
    }
}
