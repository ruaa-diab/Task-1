package org.example;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        System.out.println(" Creating filters using FilterFactory:");

        EventFilter priorityFilter = FilterFactory.createPriorityFilter(true);
        System.out.println("Priority Filter created: " + priorityFilter.getDescription());

        EventFilter typeFilter = FilterFactory.createEventTypeFilter(EventType.NEW_TASK);
        System.out.println("Type Filter created: " + typeFilter.getDescription());

        EventFilter timeFilter = FilterFactory.createTimeBasedFilter(
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );
        System.out.println("Time Filter created: " + timeFilter.getDescription());

        System.out.println(" Creating subscriber and adding filters:");

        Subscriber subscriber = new Subscriber("sub1", "Test Subscriber");
        subscriber.addFilter(priorityFilter);
        subscriber.addFilter(typeFilter);

        System.out.println("Subscriber created: " + subscriber.getName());
        System.out.println("Number of filters: " + subscriber.getFilters().size());


        System.out.println(" Testing EventFactoryManager:");

        EventFactoryManager manager = EventFactoryManager.getInstance();
        System.out.println("EventFactoryManager instance created");


        EventFactoryManager manager2 = EventFactoryManager.getInstance();
        System.out.println("Same instance? " + (manager == manager2));

        // Test 4: Test dynamic filter creation
        System.out.println(". Testing dynamic filter creation:");

        try {
            EventFilter dynamicFilter = FilterFactory.createFilter("priority", true);
            System.out.println("Dynamic filter created: " + dynamicFilter.getDescription());
        } catch (Exception e) {
            System.out.println("Error creating dynamic filter: " + e.getMessage());
        }

        // Test 5: Test subscriber methods
        System.out.println("\n5. Testing subscriber filter methods:");

        // Test backward compatibility methods
        subscriber.setEventFilter(priorityFilter);
        EventFilter retrievedFilter = subscriber.getEventFilter();
        System.out.println("Set/Get single filter works: " + (retrievedFilter != null));

        // Test multiple filters
        subscriber.addFilters(typeFilter, timeFilter);
        System.out.println("Total filters after adding multiple: " + subscriber.getFilters().size());

        // Test 6: Test equals method
        System.out.println("\n6. Testing subscriber equality:");

        Subscriber subscriber2 = new Subscriber("sub1", "Different Name");
        System.out.println("Subscribers with same ID are equal: " + subscriber.equals(subscriber2));

        Subscriber subscriber3 = new Subscriber("sub2", "Test Subscriber");
        System.out.println("Subscribers with different ID are equal: " + subscriber.equals(subscriber3));

        System.out.println("\n=== Test Complete ===");
    }
}
