package org.example;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class EventManager {

    private Map<Event, Time> HistoryEvents=new HashMap<>();
    private Map<Event, Time> Upcoming=new HashMap<>();




    private Map<Event, Time> events=new HashMap<>();

    public Map<Event, Time> getEvents() {
        return events;
    }

    public void setEvents(Map<Event, Time> events) {
        this.events = events;
    }

    public Map<Event, Time> getHistoryEvents() {
        return HistoryEvents;
    }

    public void setHistoryEvents(Map<Event, Time> historyEvents) {
        HistoryEvents = historyEvents;
    }

    public Map<Event, Time> getUpcoming() {
        return Upcoming;
    }

    public void setUpcoming(Map<Event, Time> upcoming) {
        Upcoming = upcoming;
    }

    /*public class EventManager {
    private Map<Event, Time> historyEvents = new HashMap<>();
    private Map<Event, Time> upcomingEvents = new HashMap<>();

    // Add to upcoming
    public void scheduleEvent(Event event, Time time) {
        upcomingEvents.put(event, time);
    }

    // Move from upcoming to history when done
    public void markAsCompleted(Event event) {
        Time completionTime = upcomingEvents.remove(event);
        if (completionTime != null) {
            historyEvents.put(event, completionTime);
        }
    }

    // Filter upcoming events (e.g., next 24 hours)
    public Map<Event, Time> getUpcomingInNextDay() {
        return upcomingEvents.entrySet().stream()
            .filter(e -> isWithinNext24Hours(e.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}*/




}
