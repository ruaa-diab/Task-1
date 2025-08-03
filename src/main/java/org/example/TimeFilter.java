package org.example;

import java.time.LocalTime;

public class TimeFilter implements EventFilter {
    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        if (startHour > this.endHour) {
            throw new IllegalArgumentException("Start hour cannot be greater than end hour");
        }
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        if (endHour < 0 || endHour > 23) {
            throw new IllegalArgumentException("End hour must be between 0 and 23");
        }
        if (this.startHour > endHour) {
            throw new IllegalArgumentException("End hour cannot be less than start hour");
        }
        this.endHour = endHour;
    }

    private int startHour;
    private int endHour;

    public TimeFilter() {
        this(8, 17);  // Default work hours
    }

    public TimeFilter(int startHour, int endHour) {
        this.setStartHour(startHour);
        this.setEndHour(endHour);
    }

    @Override
    public boolean matches(Event event, LocalTime currentTime) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (currentTime == null) {
            throw new IllegalArgumentException("Current time cannot be null");
        }

        return currentTime.getHour() >= startHour &&
                currentTime.getHour() <= endHour;
    }
}
