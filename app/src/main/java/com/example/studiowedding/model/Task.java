package com.example.studiowedding.model;

import java.util.List;

public class Task {
    private String id;
    private String date;
    private String statusTask;
    private String name;
    private String address;
    private List<String> employee;

    public Task(String id, String date, String statusTask, String name, String address, List<String> employee) {
        this.id = id;
        this.date = date;
        this.statusTask = statusTask;
        this.name = name;
        this.address = address;
        this.employee = employee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getEmployee() {
        return employee;
    }

    public void setEmployee(List<String> employee) {
        this.employee = employee;
    }
}
