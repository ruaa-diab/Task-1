package org.example;
class ReminderEventFactory extends AbstractEventFactory {
    @Override
    public Event createEvent(String message, boolean isUrgent) {
        return new ReminderEvents(message, isUrgent);
    }

    @Override
    public Event createEvent(String message, boolean isUrgent, Object data) {
        return createEvent(message, isUrgent);
    }
}