package org.example;

public class ReminderEvents extends Event {



    public ReminderEvents(String name,boolean isUrgent) {

        super(EventType.TASK_REMINDER, name, isUrgent);


    }

    @Override
    public String toString() {
        String msg = this.getMsg();
        return (msg != null ? msg : "null") + "ReminderEvents{}";
    }
}
