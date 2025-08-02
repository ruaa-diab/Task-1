package org.example;


import java.util.Objects;

public abstract class Event {

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return isUrgent == event.isUrgent && Objects.equals(msg, event.msg) && type == event.type;
    }
            //we have to include them all other wise the key will be the same

    @Override
    public int hashCode() {
        return Objects.hash(msg, type, isUrgent);
    }
}
