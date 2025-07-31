package org.example;

import java.time.LocalTime;

public class TimeFilter implements EventFilter {
    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    private int startHour;
    private int endHour;

    public TimeFilter() {
        this(8, 17);  // Default work hours
    }

    public TimeFilter(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public boolean matches(Event event, LocalTime currentTime) {
        return currentTime.getHour() >= startHour &&
                currentTime.getHour() <= endHour;
    }
}
