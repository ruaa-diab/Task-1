package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTaskEvent extends Event  implements Subject<Subscriber> {

    private Task task;
    // private Admin admin;


    public NewTaskEvent(EventType type, Task task) {
        super(type);
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

    public void publish() {
        //WE ALSO HAVE TO RECORD THEM

        ManagingSubscribers ms=ManagingSubscribers.getInstance();

        List<Subscriber> interestedPeople = ManagingSubscribers.getInstance().getSubscribers(this.getType());
        Notification notification = new Notification(
                "New Task Created",this,
                "Task: " + this.getTask().getTaskId()+ " has been created"
        );
        // Pass the actual task object
        //since Notification takes Event and newTaskEVENT EXTENDS FROM EVENT
        //NOW WE CAn pass any type of event

        for (Subscriber person : interestedPeople) {
            person.update(notification );  // Deliver the message
        }




    }


    @Override
    //HEREEEEEEEEEEEEEEEEEEE CHECKK CHECK CHECK
    public void ADD(Subscriber o) {
        ManagingSubscribers.getInstance().getSubscribers(this.getType()).add(o);

    }

    @Override
    public void Remove(Subscriber o) {
        ManagingSubscribers.getInstance().getSubscribers(this.getType()).remove(o);

    }

    @Override
    public void notifyAllSubscribers(Notification nf) {
        for(Subscriber s:  ManagingSubscribers.getInstance().getSubscribers(this.getType())){
            s.update(nf);
        }

    }
}
