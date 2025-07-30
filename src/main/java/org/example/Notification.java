package org.example;

import java.sql.Time;
import java.time.LocalDateTime;

public class Notification {

    private String eventType;
    private String msg;
    private LocalDateTime timeStamp;

    public Notification() {
    }


    public Notification(String eventType,String msg,LocalDateTime timeStamp) {
        this.setEventType(eventType);
        this.setMsg(msg);
        this.setTimeStamp(timeStamp);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "eventType='" + eventType + '\'' +
                ", msg='" + msg + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
