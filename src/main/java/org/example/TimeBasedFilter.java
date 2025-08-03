package org.example;

import java.time.LocalTime;

// Time-based filter
public class TimeBasedFilter implements EventFilter {
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeBasedFilter(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean matches(Event event, LocalTime currentTime) {
        if (currentTime == null) {
            throw new IllegalArgumentException("Current time cannot be null");
        }

        if (startTime.isBefore(endTime)) {
            // Normal case: start is before end (e.g., 9:00 AM to 5:00 PM)
            return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
        } else {
            // Overnight case: start is after end (e.g., 10:00 PM to 6:00 AM)
            return !currentTime.isBefore(startTime) || !currentTime.isAfter(endTime);
        }
    }


}
