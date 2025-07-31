package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagingSubscribers implements Subject<Subscriber>{
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
    //so all clases and all objects will have the same version

    public List<Subscriber> getSubscribers(EventType t) {
        return this.manage.get(t);
        //and by that we get subscribers
    }


    //we cant have the task to publish it self so we move it t here to manage publishing an d
    //subscribers
    public void publish(Event event  ) {
        //so we can publush all kinds using same method
        Notification notification = new Notification(
                event.getMsg(), event,
                "task alert");
        this.notifyAllSubscribers(notification,event.getType());


    }

    @Override
    public void Subscribe(Subscriber o, EventType type) {
        if(this.getSubscribers(type)==null){
            this.manage.put(type,new ArrayList<>());



        }
        this.getSubscribers(type).add(o);
    }
//add here to make the subscribers subscribe but not the same thing
    //subscribe method for users
    @Override
    public void Unsubscribe(Subscriber o, EventType type) {
        if(this.getSubscribers(type)!=null){

        this.getSubscribers(type).remove(o);

    }}

    @Override
    public void notifyAllSubscribers(Notification nf,EventType type) {
        if(this.getSubscribers(type)!=null) {

            for (Subscriber s : this.getSubscribers(type)) {
                s.update(nf);
            }
        }

    }





}

