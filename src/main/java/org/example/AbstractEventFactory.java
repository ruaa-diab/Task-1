package org.example;

// Abstract factory for different types of event factories
public abstract class AbstractEventFactory {

    public abstract Event createEvent(String message, boolean isUrgent);
    public abstract Event createEvent(String message, boolean isUrgent, Object data);

    // Static factory method to get specific factory
    public static AbstractEventFactory getFactory(EventType type) {
        switch (type) {
            case NEW_TASK:
                return new TaskEventFactory();
            case TASK_REMINDER:
                return new ReminderEventFactory();
            case HEARTBEAT:
                return new HeartbeatEventFactory();
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
}