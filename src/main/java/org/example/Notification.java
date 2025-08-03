package org.example;


import java.time.LocalDateTime;

public class Notification {

    private String title;
    private String message;
    private Event relatedTask;

    public Notification() {
    }

    public Notification(String message, Event relatedTask, String title) {
        this.setMessage(message);
        this.setRelatedTask(relatedTask);
        this.setTitle( title);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        this.message = message;
    }

    public Event getRelatedTask() {
        return relatedTask;
    }

    public void setRelatedTask(Event relatedTask) {
        if (relatedTask == null) {
            throw new IllegalArgumentException("Related task cannot be null");
        }
        this.relatedTask = relatedTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        this.title = title;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message + '\'' +
                ", title='" + title + '\'' +
                ", Task=" + (relatedTask != null ? relatedTask.getMsg() : "null") +
                '}';
    }
}
