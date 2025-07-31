package org.example;

public class ReminderEvents extends Event{

    private String name;

    public ReminderEvents(String name) {
        super(EventType.TASK_REMINDER,name);
        this.name=name;
    }



    @Override
    public String toString() {
        return this.getName()+" this is a reminder event for every min";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
