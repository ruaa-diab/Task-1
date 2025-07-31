package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Subscriber implements Observer{
    private String name;
    private String id;
    private List<EventFilter> filters =new ArrayList<>();



    public Subscriber() {
    }

    public Subscriber(String id,String name) {
        this.id = id;
        this.name=name;
    }

    public Subscriber(List<EventFilter> filters, String id, String name) {
        this.filters = filters;
        this.id = id;
        this.name = name;
    }

    public List<EventFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<EventFilter> filters) {
        this.filters = filters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void addFilter(EventFilter filter){
        if(filter!=null){
            this.getFilters().add(filter);
        }else{
            throw new NullPointerException();
        }
    }
    // so supscriber can add filter any time he/she wants

    private boolean passTheFilters(Event e){
        LocalTime currentTime=LocalTime.now();
        if(this.getFilters().isEmpty()){
            return true;
        }

        for(EventFilter f:this.getFilters()){

            if(!f.matches(e,currentTime)){
                return false;

            }
        }
        return true;
    }

    @Override
    public void update(Notification nf) {
        if(this.passTheFilters(nf.getRelatedTask())){
            System.out.println(nf.toString());
            //or we can send an email
            //or show a pop up on the device

        }
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
}
