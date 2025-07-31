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

    @Override
    public boolean matches(Event event, LocalTime currentTime) {
        return event.isUrgent()==this.isProiorityHigh();
    }
}
