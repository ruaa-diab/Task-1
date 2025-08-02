package org.example;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventHistory {
    
    // i chose singleton because i want this list to be accessable without making onject 
    //so it can be saved and unchanged
    //and i can access to it from ny class


    private static EventHistory instance=new EventHistory();

    private Map<Event, LocalDateTime> HistoryEvents=new HashMap<>();

    private  EventHistory(){}
    
    public static EventHistory getInstance(){
        return instance;
    }



    public void recordEvent(Event event){
        HistoryEvents.put(event, LocalDateTime.now());
    }


    public List<Event> getEventsFromLastHour(){
        LocalDateTime anHourAgo=LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        List<Event> result=new ArrayList<>();
        for(Map.Entry<Event,LocalDateTime> oneEntry:HistoryEvents.entrySet()){
            if(oneEntry.getValue().isAfter(anHourAgo)){
                result.add(oneEntry.getKey());
            }
        }
        return result;
    }



    public List<Event> getEventsFromLastHour(EventType type){

        List<Event> result=new ArrayList<>();
        for(Map.Entry<Event,LocalDateTime> oneEntry:HistoryEvents.entrySet()){
            if(oneEntry.getKey().getType().equals(type)){
                result.add(oneEntry.getKey());
            }
        }
        return result;
    }






}
