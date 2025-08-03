package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Subscriber implements Observer{
    private String name;
    private String id;
    //given from the system
    private List<EventFilter> filters;


    private String SubscriberId;



    public Subscriber() {
    }

    public Subscriber(String id,String name) {
        this.setId( id);
        this.setName(name);
        this.filters=new ArrayList<>();
    }


    public List<EventFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<EventFilter> filters) {
        if (filters == null) {
            throw new IllegalArgumentException("Filters list cannot be null");
        }
        this.filters = filters;
    }

    public String getId() {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubscriberId() {
        return SubscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        if(subscriberId==null){
            throw new IllegalArgumentException("subscriberids cannot be null");


        }
        SubscriberId = subscriberId;
    }


    public void addFilter(EventFilter filter){

        this.getFilters().add(filter);
    }
    //no need for exception handling because it cant be null /the size will be 0.
    // so supscriber can add filter any time he/she wants

    private boolean passTheFilters(Event e){
        if (e == null) {
            return false; // or throw exception, depending on your preference
        }
        LocalTime currentTime=LocalTime.now();
        if(this.getFilters().isEmpty()){
            return true;
        }

        for(EventFilter f:this.getFilters()){
            if(!f.matches(e,currentTime)){
               // System.out.println("Filter " + f.getClass().getSimpleName() + " rejected event: " + e.getMsg());
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean update(Notification nf) {
        if (nf == null) {
            System.err.println("Subscriber [" + this.name + "] received null notification");
            return false;
        }

        if(this.passTheFilters(nf.getRelatedTask())){
            System.out.println("[" + this.name + "] received: " + nf.toString());
            // editing this to boolean so i can log the event eith notified subscribers
            //or we can send an email
            //or show a pop up on the device
            return true;

        }
        return false;
//to conclude publish calls notify notify calls update and update calls pass and pass calles matches if there is
        //a match publich if not then no.
    }


    @Override
    public String toString() {
        return "Subscriber{" +
                "filters=" + filters +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return this.getId().trim().compareToIgnoreCase(((Subscriber) o).getId().trim())==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, filters);
    }
}
