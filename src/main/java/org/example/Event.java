package org.example;



public abstract class Event{

//checkkkkkkkkkk


    private String msg;

    private EventType type;

    public Event(EventType type,String msg) {

        this.type = type;
        this.msg=msg;
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
}
