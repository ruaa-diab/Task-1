package org.example;

public class HeartbeatEvent extends Event{
    //because those are reminders i didn't think there were a need to put a task id

    private String name;
    private boolean isUrgent;

    @Override
    public boolean isUrgent() {
        return isUrgent;
    }

    @Override
    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeartbeatEvent(String name,boolean isUrgent) {
        super(EventType.HEARTBEAT,name,isUrgent);

    }




}
