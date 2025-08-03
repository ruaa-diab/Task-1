package org.example;

import java.time.LocalDateTime;

public class Task {
    private String taskId;
    private String title;

    public Task(String taskId, String title) {
        this.setTaskId(taskId);
        this.setTitle( title);
    }


    public Task() {
    }

    public Task(String title) {
        this.title = title;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }
        this.taskId = taskId;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }
        this.title = title;
    }
}
