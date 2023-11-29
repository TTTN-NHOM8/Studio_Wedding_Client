package com.example.studiowedding.view.activity.task;

import com.example.studiowedding.model.Employee;

import java.util.List;

public class ResponseEmployeeJoin  {
    private String status;
    private List<Employee> employeeList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
