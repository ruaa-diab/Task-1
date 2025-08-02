package org.example;

public class HeartbeatEvent extends Event{
    //because those are reminders i didn't think there were a need to put a task id

    public HeartbeatEvent(String name,boolean isUrgent) {
        super(EventType.HEARTBEAT,name,isUrgent);

    }




}
