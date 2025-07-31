package org.example;



public abstract class Event{

//checkkkkkkkkkk


    private String msg;

    private EventType type;

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    private boolean isUrgent;

    public Event(EventType type, String msg, boolean isUrgent) {

        this.type = type;
        this.msg=msg;
        this.isUrgent = isUrgent;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isUrgent() {
        return isUrgent;
    }
}
