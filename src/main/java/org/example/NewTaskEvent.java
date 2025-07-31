package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTaskEvent extends Event { //implements Subject<Subscriber> {

    private Task task;
    // private Admin admin;


    public NewTaskEvent(EventType type, Task task) {
        super(EventType.NEW_TASK);
        this.task = task;
    }

    public NewTaskEvent(EventType type) {
        super(type);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }






    }


   /* @Override
    //HEREEEEEEEEEEEEEEEEEEE CHECKK CHECK CHECK
    public void ADD(Subscriber o) {
        ManagingSubscribers.getInstance().getSubscribers(EventType.NEW_TASK).add(o);

    }

    @Override
    public void Remove(Subscriber o) {
        ManagingSubscribers.getInstance().getSubscribers(EventType.NEW_TASK).remove(o);

    }

    @Override
    public void notifyAllSubscribers(Notification nf) {
        for(Subscriber s:  ManagingSubscribers.getInstance().getSubscribers(this.getType())){
            s.update(nf);
        }

    }
}*/