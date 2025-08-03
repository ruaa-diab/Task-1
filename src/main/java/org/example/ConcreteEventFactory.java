package org.example;

public class ConcreteEventFactory implements EventFactory {

    @Override
    public Event createEvent(EventType type, String message, boolean isUrgent) {
        return createEvent(type, message, isUrgent, null);
    }

    @Override
    public Event createEvent(EventType type, String message, boolean isUrgent, Object additionalData) {
        switch (type) {
            case NEW_TASK:
                if (additionalData instanceof Task) {
                    return new NewTaskEvent((Task) additionalData, isUrgent);
                } else {
                    throw new IllegalArgumentException("NEW_TASK events require a Task object as additional data");
                }

            case TASK_REMINDER:
                return new ReminderEvents(message, isUrgent);

            case HEARTBEAT:
                return new HeartbeatEvent(message, isUrgent);

            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }

    // Convenience methods for specific event types
    public NewTaskEvent createNewTaskEvent(Task task, boolean isUrgent) {
        return (NewTaskEvent) createEvent(EventType.NEW_TASK, task.getTitle(), isUrgent, task);
    }

    public ReminderEvents createReminderEvent(String message, boolean isUrgent) {
        return (ReminderEvents) createEvent(EventType.TASK_REMINDER, message, isUrgent);
    }

    public HeartbeatEvent createHeartbeatEvent(String message, boolean isUrgent) {
        return (HeartbeatEvent) createEvent(EventType.HEARTBEAT, message, isUrgent);
    }
}