package org.example;

import java.time.LocalTime;

public class CompositeFilter implements EventFilter {
    private EventFilter[] filters;
    private boolean requireAll;

    public CompositeFilter(boolean requireAll, EventFilter... filters) {
        this.requireAll = requireAll;
        this.filters = filters;
    }

    @Override
    public boolean matches(Event event, LocalTime currentTime) {
        if (filters == null || filters.length == 0) {
            return true;
        }

        for (EventFilter filter : filters) {
            boolean matches = filter.matches(event, currentTime);
            if (requireAll && !matches) {
                return false;
            }
            if (!requireAll && matches) {
                return true;
            }
        }

        return requireAll;
    }


}