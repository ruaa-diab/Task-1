package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTaskEvent extends Event {

    private Task task;

    @Override
    public boolean isUrgent() {
        return isUrgent;
    }

    @Override
    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    private boolean isUrgent;


    public NewTaskEvent(EventType type, Task task,boolean isUrgent) {
        super(EventType.NEW_TASK,task.getTitle(),isUrgent);
        this.task = task;
    }



    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }






    }


