package org.example;

import java.sql.Time;

public class Notification {

    private String eventType;
    private String msg;
    private long timeStamp;

    public Notification() {
    }


    public Notification(String eventType,String msg,long timeStamp) {
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
