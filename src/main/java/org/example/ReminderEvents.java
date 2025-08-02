package org.example;

public class ReminderEvents extends Event {



    public ReminderEvents(String name,boolean isUrgent) {
        super(EventType.TASK_REMINDER, name, isUrgent);


    }

    @Override
    public String toString() {
        return this.getMsg() + "ReminderEvents{}";
    }
}
