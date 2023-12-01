package com.example.studiowedding.view.activity.task;

import com.example.studiowedding.model.Task;

import java.util.List;

public class ResponseTask {
    private String status;
    private List<Task> taskList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
