package org.example;

class TaskEventFactory extends AbstractEventFactory {
    @Override
    public Event createEvent(String message, boolean isUrgent) {
        throw new IllegalArgumentException("Task events require a Task object");
    }

    @Override
    public Event createEvent(String message, boolean isUrgent, Object data) {
        if (!(data instanceof Task)) {
            throw new IllegalArgumentException("Task events require a Task object");
        }
        return new NewTaskEvent((Task) data, isUrgent);
    }
}