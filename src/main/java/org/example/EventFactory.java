package org.example;



public interface EventFactory {
    Event createEvent(EventType type, String message, boolean isUrgent);
    Event createEvent(EventType type, String message, boolean isUrgent, Object additionalData);
}
