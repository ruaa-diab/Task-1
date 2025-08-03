package org.example;

import java.time.LocalTime;

public class EventTypeFilter implements EventFilter {
    private EventType targetType;

    public EventTypeFilter(EventType targetType) {
        this.targetType = targetType;
    }

    @Override
    public boolean matches(Event event, LocalTime currentTime) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        return event.getType() == targetType;
    }

    public EventType getTargetType() {
        return targetType;
    }

    @Override
    public String getDescription() {
        return "EventTypeFilter{" + targetType + "}";
    }
}