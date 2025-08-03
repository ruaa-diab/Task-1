package org.example;

public class EventFactoryManager {
    //singletonnnnn
    private static EventFactoryManager instance;
    private ConcreteEventFactory eventFactory;

    private EventFactoryManager() {
        this.eventFactory = new ConcreteEventFactory();
    }

    public static EventFactoryManager getInstance() {
        if (instance == null) {
            synchronized (EventFactoryManager.class) {
                if (instance == null) {
                    instance = new EventFactoryManager();
                }
            }
        }
        return instance;
    }

    public Event createEvent(EventType type, String message, boolean isUrgent) {
        return eventFactory.createEvent(type, message, isUrgent);
    }

    public Event createEvent(EventType type, String message, boolean isUrgent, Object additionalData) {
        return eventFactory.createEvent(type, message, isUrgent, additionalData);
    }

    // Convenience methods
    public NewTaskEvent createTaskEvent(Task task, boolean isUrgent) {
        return eventFactory.createNewTaskEvent(task, isUrgent);
    }

    public ReminderEvents createReminderEvent(String message, boolean isUrgent) {
        return eventFactory.createReminderEvent(message, isUrgent);
    }

    public HeartbeatEvent createHeartbeatEvent(String message, boolean isUrgent) {
        return eventFactory.createHeartbeatEvent(message, isUrgent);
    }
}