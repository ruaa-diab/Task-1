package org.example;

import java.time.LocalTime;

public interface EventFilter {
    boolean matches(Event event, LocalTime currentTime);
    //so here we are implementing Strategy pattern so the subscribers has diffrent behaviours (filtres)
    //and the argument here represent what will filter who gets nf and who doesn't.s
}
