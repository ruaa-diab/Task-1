package org.example;
class HeartbeatEventFactory extends AbstractEventFactory {
    @Override
    public Event createEvent(String message, boolean isUrgent) {
        return new HeartbeatEvent(message, isUrgent);
    }

    @Override
    public Event createEvent(String message, boolean isUrgent, Object data) {
        return createEvent(message, isUrgent);
    }
}