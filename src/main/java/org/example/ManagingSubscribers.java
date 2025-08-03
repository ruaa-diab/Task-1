package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ManagingSubscribers implements Subject<Subscriber>{
    // for tis class i chose the design singleton to use only one instance
    //so i can keep the list of subscribers from losing any data
    private static ManagingSubscribers instance = new ManagingSubscribers();
    private Map<EventType, List<Subscriber>> manage = new ConcurrentHashMap<>();
    private Map<Event, List<Subscriber>> notifiedOdOfEvent = new ConcurrentHashMap<>();
    private static AtomicInteger  counter= new AtomicInteger(0);
    //here changing the normal counter to atomic so it can be thread safe
    //in case 2 objects were made at the same time.


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

    public Map<Event, List<Subscriber>> getNotifiedOdOfEvent() {
        return notifiedOdOfEvent;
    }

    //we cant have the task to publish it self so we move it t here to manage publishing an d
    //subscribers
    public synchronized void publish(Event event  ) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        // so if more than event published at the same time one of them waits
        //so we can publush all kinds using same method
        Notification notification = new Notification(
                event.getMsg(), event,
                "task alert");
        this.notifyAllSubscribers(notification,event.getType());
        EventHistory.getInstance().recordEvent(event);


    }

    @Override
    public synchronized void reSubscribe(Subscriber o, EventType type) {
        if (o == null) {
            throw new IllegalArgumentException("Subscriber cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("EventType cannot be null");
        }
        if(this.getSubscribers(type)==null){
            this.manage.put(type, Collections.synchronizedList(new ArrayList<>()));
            //since it is one list it is easy to just synchronise it
            this.getSubscribers(type).add(o);



        }else{
            if(this.getSubscribers(type).contains(o)){
                System.out.println("you are already subscribed");
                        //we can make a notification and send this massage as a nf
            }else{
                this.getSubscribers(type).add(o);
                System.out.println("you have jut supscribed to a new type");
            }
        }

    }
//add here to make the subscribers subscribe but not the same thing
    //subscribe method for users
    @Override
    public synchronized void Unsubscribe(Subscriber o, EventType type) {
        if (o == null) {
            throw new IllegalArgumentException("Subscriber cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("EventType cannot be null");
        }

        if (this.getSubscribers(type) != null) {
            this.getSubscribers(type).remove(o);
        }
    }


    @Override
    public synchronized void notifyAllSubscribers(Notification nf,EventType type) {
        if (nf == null) {
            throw new IllegalArgumentException("Notification cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("EventType cannot be null");
        }
        if(this.getSubscribers(type)!=null) {
            List<Subscriber> notified=new ArrayList<>();

            for (Subscriber s : this.getSubscribers(type)) {
               if( s.update(nf)) {
                   notified.add(s);
               }

            }
            if(!notified.isEmpty()){
                this.getNotifiedOdOfEvent().put(nf.getRelatedTask(),notified);
            }
        }

    }

    //now the subscribe method for the user

//check
    public synchronized Subscriber Subscribe(User o, EventType type) {
        //this methd should only be available to subscribers onlyyy.
        if (o == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("EventType cannot be null");
        }
        if (o.getName() == null) {
            throw new IllegalArgumentException("User name cannot be null");
        }
        if(this.getSubscribers(type)==null){
            this.manage.put(type,new ArrayList<>());}

        for (Map.Entry<EventType, List<Subscriber>> entry : this.manage.entrySet()) {
            List<Subscriber> subscribers = entry.getValue();
            if (subscribers != null && !subscribers.isEmpty()) {
                for (Subscriber subscriber : subscribers) {
                    if (subscriber.equals(o)) {
                        System.out.println("You are already subscribed to " + entry.getKey() +
                                ". Users can only subscribe once.");
                        return null; // User already exists, cannot subscribe again
                    }
                }
            }
        }
        Subscriber newSubscriber=new Subscriber("sub"+counter.incrementAndGet(),o.getName());
                //here i will put counter instead of using the ready method to save time;
        this.getSubscribers(type).add(newSubscriber);
        return newSubscriber;
    }









}

