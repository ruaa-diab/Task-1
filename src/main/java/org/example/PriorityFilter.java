package org.example;

import java.time.LocalTime;

public class PriorityFilter implements EventFilter{
private boolean proiorityHigh;

    public boolean isProiorityHigh() {
        return proiorityHigh;
    }

    public void setProiorityHigh(boolean proiorityHigh) {
        this.proiorityHigh = proiorityHigh;
    }

    public PriorityFilter(boolean proiorityHigh) {
        this.proiorityHigh = proiorityHigh;
    }

    public boolean matches(Event event, LocalTime currentTime) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (currentTime == null) {
            throw new IllegalArgumentException("Current time cannot be null");
        }
        boolean result = event.isUrgent() == this.isProiorityHigh();
        System.out.println("PriorityFilter Debug: event.isUrgent()=" + event.isUrgent() +
                ", this.isProiorityHigh()=" + this.isProiorityHigh() +
                ", result=" + result);
        return result;
    }
}
