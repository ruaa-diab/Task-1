package org.example;

import java.time.LocalDateTime;

public class Admin {

    private String adiminId;
    private String name;
    static int c=0;

    public Admin() {
    }

    public Admin(String adiminId,String name) {
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


    public NewTaskEvent creatTask(String title, LocalDateTime time ,EventType t){
        c=c+1;
        Task task=new Task(this,false,"TSK"+c,time,title);
        NewTaskEvent NTE=new NewTaskEvent(t,task);






        return NTE;

        //so the admin get to choose when it has to be done or launched

    }
}
