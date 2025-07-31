package org.example;

import java.time.LocalDateTime;

public class Admin {

    private String adiminId;
    private String name;
    static int c = 0;

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
        c = c + 1;
        Task task = new Task("TSK" + c, title);
        NewTaskEvent NTE = new NewTaskEvent(EventType.NEW_TASK, task,isUrgent); //BECASUE THIS IS A NEW TASK AND IT IS TYPE NEWTASK

       ManagingSubscribers.getInstance().publish(NTE);

        //so the admin get to choose when it has to be done or launched
        //and publish done

    }

}
