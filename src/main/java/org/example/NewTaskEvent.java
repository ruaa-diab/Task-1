package org.example;

public class NewTaskEvent extends Event{

    private Task task;
   // private Admin admin;

    public NewTaskEvent(EventType type,Task task) {
        super(type);
        this.getTask(task);

    }






    public Task getTask(Task task) {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }




}
