package org.example;

import java.sql.Time;
import java.util.ArrayList;

public class Event implements Subject<Subscriber> { //this way i get to put Subscribr instead of observer and still
            //keep the observer design
    private EventType type;
    private Time time;
    private boolean isUrgent;
    private ArrayList<Task> tasks;

    private ArrayList<Subscriber> notified;//as in notified people about this certain event
    public Event(EventType type,Time time,boolean isUrgent,ArrayList<Task>tasks,ArrayList<Subscriber>notified){
        this.setType(type);
        this.setTime(time);
        this.setUrgent(isUrgent);
        this.setTasks(tasks);
        this.setNotified(notified);
    }


    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public Event(){

    }
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        if(tasks.isEmpty()){
            throw new NullPointerException();
        }else{
        this.tasks = tasks;
    }}


    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public ArrayList<Subscriber> getNotified() {
        return notified;
    }

    public void setNotified(ArrayList<Subscriber> notified) {
        this.notified = notified;
    }

    @Override
    public void ADD(Subscriber o) {
        this.getNotified().add( o);//
    }

    @Override
    public void Remove(Subscriber o) {
        this.getNotified().remove( o);

    }

    @Override
    public void notifyAllSubscribers(Notification nf) {
        for (Subscriber a:this.getNotified()){
            a.update(nf);
        }

    }



    public void fireEvent(){//to be continud with schedualer

    }

    /*  // Fire an event but schedule the notification for later
    public void fireEvent(String eventType, String message, long delayMs) {
        Notification notification = new Notification(eventType, message);
        scheduler.schedule(
            () -> notifyObservers(notification),
            delayMs,
            TimeUnit.MILLISECONDS
        );
    }

    // Shutdown the scheduler when done
    public void shutdown() {
        scheduler.shutdown();
    }
}*/
}
