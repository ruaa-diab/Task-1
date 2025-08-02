package org.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Event Management System Test ===\n");

        // Using singleton instances directly

        // Create users
        User user1 = new User("Alice");
        User user2 = new User("Bob");
        User user3 = new User("Charlie");

        System.out.println("Created users: " + user1.getName() + ", " + user2.getName() + ", " + user3.getName());

        // Subscribe users to NEW_TASK events (using your EventType)
        Subscriber subscriber1 = ManagingSubscribers.getInstance().Subscribe(user1, EventType.NEW_TASK);
        Subscriber subscriber2 = ManagingSubscribers.getInstance().Subscribe(user2, EventType.NEW_TASK);
        Subscriber subscriber3 = ManagingSubscribers.getInstance().Subscribe(user3, EventType.NEW_TASK);

        System.out.println("- " + subscriber1.getName() + " (ID: " + subscriber1.getId() + ")");
        System.out.println("- " + subscriber2.getName() + " (ID: " + subscriber2.getId() + ")");
        System.out.println("- " + subscriber3.getName() + " (ID: " + subscriber3.getId() + ")");


        // Add priority filter to Alice
        EventFilter priorityFilter = new PriorityFilter(true);
        subscriber1.addFilter(priorityFilter);
        System.out.println("Added priority filter (urgent only) to " + subscriber1.getName());

        // Add time filter to Bob
        EventFilter timeFilter = new TimeFilter(9, 17);
        subscriber2.addFilter(timeFilter);
        System.out.println("Added time filter (9 AM - 5 PM) to " + subscriber2.getName());

        // Charlie has no filters
        System.out.println(subscriber3.getName() + " has no filters");

        System.out.println("\n=== Creating and Publishing Events ===");

        // Create tasks (I'll use simple constructor - adjust based on your Task class)
        // Since I don't have your Task class, I'll create a simple one
        Task task1 = new Task("Complete project report");
        Task task2 = new Task("Review code");
        Task task3 = new Task("Fix critical bug");

        // Create events using your NewTaskEvent class
        NewTaskEvent event1 = new NewTaskEvent(task1, true);  // Urgent
        NewTaskEvent event2 = new NewTaskEvent(task2, false); // Not urgent
        NewTaskEvent event3 = new NewTaskEvent(task3, true);  // Urgent

        System.out.println("Publishing events...\n");

        // Publish events
        System.out.println("1. Publishing urgent task: " + task1.getTitle());
        ManagingSubscribers.getInstance().publish(event1);

        System.out.println("\n2. Publishing non-urgent task: " + task2.getTitle());
        ManagingSubscribers.getInstance().publish(event2);

        System.out.println("\n3. Publishing urgent task: " + task3.getTitle());
        ManagingSubscribers.getInstance().publish(event3);

        System.out.println("\n=== Testing Event History ===");

        // Get events from last hour
        List<Event> recentEvents = EventHistory.getInstance().getEventsFromLastHour();
        Map<Event, LocalDateTime> meme=EventHistory.getInstance().getHistoryEvents();
        System.out.println(meme.size());

        System.out.println("Total events from last hour: " + recentEvents.size());

        // Get NEW_TASK events from last hour
        List<Event> recentTaskEvents = EventHistory.getInstance().getEventsFromLastHour(EventType.NEW_TASK);
        System.out.println("NEW_TASK events from last hour: " + recentTaskEvents.size());

        System.out.println("\n=== Testing Subscription Management ===");

        // Try to subscribe Alice again (should show "already subscribed" message)
        System.out.println("Trying to subscribe Alice again to NEW_TASK:");
        ManagingSubscribers.getInstance().reSubscribe(subscriber1, EventType.NEW_TASK);



        // Unsubscribe Bob
        System.out.println("\nUnsubscribing Bob from NEW_TASK events:");
        ManagingSubscribers.getInstance().Unsubscribe(subscriber2, EventType.NEW_TASK);

        // Publish another event to see that Bob doesn't receive it
        System.out.println("\nPublishing another event after Bob unsubscribed:");
        Task task4 = new Task("New assignment");
        NewTaskEvent event4 = new NewTaskEvent(task4, false);
        ManagingSubscribers.getInstance().publish(event4);

        System.out.println("\n=== Test Complete ===");
    }
}
