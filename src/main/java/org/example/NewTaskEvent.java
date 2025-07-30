package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTaskEvent extends Event {

    private Task task;
    // private Admin admin;


    public NewTaskEvent(EventType type) {
        super(type);
    }

    public NewTaskEvent(EventType type, Task task) {
        super(type);
        this.getTask(task);

    }

    public Task getTask(Task task) {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }


    @Override
    public String toString() {
        return "NewTaskEvent{" +
                "task=" + task.getTaskId() +
                '}';
    }


    public void publish(NewTaskEvent task) {
        //WE ALSO HAVE TO RECORD THEM

        ManagingSubscribers ms=ManagingSubscribers.getInstance();

        List<Subscriber> interestedPeople = ManagingSubscribers.getInstance().getSubscribers(this.getType());

        for (Subscriber person : interestedPeople) {
            person.notify();  // Deliver the message
        }




    }




}
