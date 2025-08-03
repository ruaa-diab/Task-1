package org.example;

import java.time.LocalTime;

// Factory for creating different types of filters
//only for the factory pattern
public class FilterFactory {

    public static EventFilter createPriorityFilter(boolean priorityHigh) {
        return new PriorityFilter(priorityHigh);
    }

    public static EventFilter createEventTypeFilter(EventType type) {
        return new EventTypeFilter(type);
    }

    public static EventFilter createTimeBasedFilter(LocalTime startTime, LocalTime endTime) {
        return new TimeBasedFilter(startTime, endTime);
    }

    public static EventFilter createCompositeFilter(boolean requireAll, EventFilter... filters) {
        return new CompositeFilter(requireAll, filters);
    }

    // Method to create filter by string description
    public static EventFilter createFilter(String filterType, Object... params) {
        switch (filterType.toLowerCase()) {
            case "priority":
                if (params.length > 0 && params[0] instanceof Boolean) {
                    return createPriorityFilter((Boolean) params[0]);
                }
                break;
            case "eventtype":
                if (params.length > 0 && params[0] instanceof EventType) {
                    return createEventTypeFilter((EventType) params[0]);
                }
                break;
            case "time":
                if (params.length >= 2 && params[0] instanceof LocalTime && params[1] instanceof LocalTime) {
                    return createTimeBasedFilter((LocalTime) params[0], (LocalTime) params[1]);
                }
                break;
        }
        throw new IllegalArgumentException("Invalid filter type or parameters");
    }
}