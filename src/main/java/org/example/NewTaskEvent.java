package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTaskEvent extends Event {

    private Task task;


    public NewTaskEvent(EventType type, Task task) {
        super(EventType.NEW_TASK);
        this.task = task;
    }

    public NewTaskEvent(EventType type) {
        super(type);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }






    }


