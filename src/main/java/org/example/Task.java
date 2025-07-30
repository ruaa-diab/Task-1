package org.example;

import java.time.LocalDateTime;

public class Task {
    private String taskId;
    private String title;
    private Admin admin;
    private boolean isCompleted;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Task(Admin admin, boolean isCompleted, String taskId,  String title) {
        //this constructor is for making tasks and publishing tem as soon as the made
        this.admin = admin;
        this.isCompleted = isCompleted;
        this.taskId = taskId;

        this.title = title;
    }


    @Override
    public String toString() {
        return "Task{" +
                "admin=" + admin +
                ", taskId='" + taskId + '\'' +
                ", title='" + title + '\'' +

                ", isCompleted=" + isCompleted +
                '}';
    }

    public Task() {
    }

}
