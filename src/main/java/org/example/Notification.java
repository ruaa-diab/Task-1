package org.example;

import java.sql.Time;
import java.time.LocalDateTime;

public class Notification {

    private String title;
    private String message;
    private Event relatedTask;

    public Notification() {
    }

    public Notification(String message, Event relatedTask, String title) {
        this.message = message;
        this.relatedTask = relatedTask;
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Event getRelatedTask() {
        return relatedTask;
    }

    public void setRelatedTask(Event relatedTask) {
        this.relatedTask = relatedTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message + '\'' +
                ", title='" + title + '\'' +
                ", Task=" + this.getRelatedTask().getMsg() +
                '}';
    }
}
