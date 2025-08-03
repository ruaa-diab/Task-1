package org.example;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Admin {

    private String adiminId;
    private String name;

    private static AtomicInteger counter=new AtomicInteger(0);
    public Admin() {
    }

    public Admin(String adiminId, String name) {
        this.setAdiminId(adiminId);
        this.setName(name);
    }

    public String getAdiminId() {
        return adiminId;
    }

    public void setAdiminId(String adiminId) {
        this.adiminId = adiminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void  creatTask(String title,boolean isUrgent) { //THIS METHOD IS ONLY FOR NEW TASKS
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }
        Task task = new Task("TSK" + counter.incrementAndGet(), title);
        NewTaskEvent NTE = new NewTaskEvent( task,isUrgent); //BECASUE THIS IS A NEW TASK AND IT IS TYPE NEWTASK

       ManagingSubscribers.getInstance().publish(NTE);

        //so the admin get to choose when it has to be done or launched
        //and publish done

    }

}
