package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagingSubscribers {
    // for tis class i chose the design singleton to use only one instance
    //so i can keep the list of subscribers from losing any data
    private static ManagingSubscribers instance = new ManagingSubscribers();
    private Map<EventType, List<Subscriber>> manage = new HashMap<>();


    //PRIVATE constructor so no one makes another instance
    private ManagingSubscribers() {
    }

    public static ManagingSubscribers getInstance() {
        return instance;
    }
    //so allclases and all objects will have the same version

    public List<Subscriber> getSubscribers(EventType t) {
        return this.manage.get(t);
        //and by that we get subscribers
    }

    //subscribe method to add
    //unsubscribe method to add

    //we cant have the task to publish it self so we move it t here to manage publishing an d
    //subscribers
    public void publish(NewTaskEvent newTaskEvent) {
        //WE ALSO HAVE TO RECORD THEM

        ManagingSubscribers ms = ManagingSubscribers.getInstance();

        List<Subscriber> interestedPeople = ManagingSubscribers.getInstance().getSubscribers(EventType.NEW_TASK);
        Notification notification = new Notification(
                "New Task Created", newTaskEvent,
                "Task: " + newTaskEvent.getTask().getTaskId() + " has been created"
        );
        // Pass the actual task object
        //since Notification takes Event and newTaskEVENT EXTENDS FROM EVENT
        //NOW WE CAn pass any type of event

        for (Subscriber person : interestedPeople) {
            person.update(notification);  // Deliver the message
        }


    }
}

