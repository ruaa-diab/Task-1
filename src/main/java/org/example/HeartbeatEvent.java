package org.example;

public class HeartbeatEvent extends Event{
    //because those are reminders i didn't think there were a need to put a task id
    private String name;

    public HeartbeatEvent(String name) {
        super(EventType.HEARTBEAT,name);

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
