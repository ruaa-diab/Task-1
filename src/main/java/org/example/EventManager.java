package org.example;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class EventManager {

    private Map<Event, Time> HistoryEvents=new HashMap<>();
    // a map to store events with time that it happend in (fired a notification)
    private Map<Event, Time> Upcoming=new HashMap<>();
    // a map to store events with time so when the time comes it will fire and notifications can start

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

    public void markAsCompleted(Event event) {
        //we call this method as soon as the event is published to store the event and the time it exactly happend
        Time scheduledTime = this.getUpcoming().remove(event);
        if (scheduledTime != null) {
            Time actualPublishTime = new Time(System.currentTimeMillis()); // Right now!
            this.getHistoryEvents().put(event, actualPublishTime);
        }
    }
    /*public class EventManager {


    // Add to upcoming
    public void scheduleEvent(Event event, Time time) {
        upcomingEvents.put(event, time);
    }


}*/




}
