package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EventHistoryTest {

    private EventHistory eventHistory;
    private Event testEvent1;
    private Event testEvent2;
    private Event reminderEvent;

    @BeforeEach
    public void setUp() {
        eventHistory = EventHistory.getInstance();

        // Create test events
        testEvent1 = new NewTaskEvent(new Task("TSK001", "Test Task 1"), true);
        testEvent2 = new NewTaskEvent(new Task("TSK002", "Test Task 2"), false);
        reminderEvent = new ReminderEvents("Reminder Test", true);
    }

    @AfterEach
    public void tearDown() {
        // Clear event history after each test
        eventHistory.getHistoryEvents().clear();
    }

    // Test singleton behavior
    @Test
    public void testSingletonInstance() {
        EventHistory instance1 = EventHistory.getInstance();
        EventHistory instance2 = EventHistory.getInstance();

        assertSame(instance1, instance2);
        assertSame(eventHistory, instance1);
    }

    // Test basic event recording
    @Test
    public void testRecordEvent() {
        assertTrue(eventHistory.getHistoryEvents().isEmpty());

        eventHistory.recordEvent(testEvent1);

        assertEquals(1, eventHistory.getHistoryEvents().size());
        assertTrue(eventHistory.getHistoryEvents().containsKey(testEvent1));

        // Verify timestamp is recent (within last minute)
        LocalDateTime recordedTime = eventHistory.getHistoryEvents().get(testEvent1);
        LocalDateTime now = LocalDateTime.now();
        assertTrue(recordedTime.isAfter(now.minus(1, ChronoUnit.MINUTES)));
        assertTrue(recordedTime.isBefore(now.plus(1, ChronoUnit.MINUTES)));
    }

    // Test multiple event recording
    @Test
    public void testRecordMultipleEvents() {
        eventHistory.recordEvent(testEvent1);
        eventHistory.recordEvent(testEvent2);
        eventHistory.recordEvent(reminderEvent);

        assertEquals(3, eventHistory.getHistoryEvents().size());
        assertTrue(eventHistory.getHistoryEvents().containsKey(testEvent1));
        assertTrue(eventHistory.getHistoryEvents().containsKey(testEvent2));
        assertTrue(eventHistory.getHistoryEvents().containsKey(reminderEvent));
    }

    // Test exception handling
    @Test
    public void testRecordNullEvent() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventHistory.recordEvent(null);
        });

        // Verify no event was recorded
        assertTrue(eventHistory.getHistoryEvents().isEmpty());
    }

    // Test getEventsFromLastHour() - basic functionality
    @Test
    public void testGetEventsFromLastHour() {
        // Record some events
        eventHistory.recordEvent(testEvent1);
        eventHistory.recordEvent(testEvent2);

        List<Event> recentEvents = eventHistory.getEventsFromLastHour();

        assertEquals(2, recentEvents.size());
        assertTrue(recentEvents.contains(testEvent1));
        assertTrue(recentEvents.contains(testEvent2));
    }

    // Test getEventsFromLastHour() - empty history
    @Test
    public void testGetEventsFromLastHourEmpty() {
        List<Event> recentEvents = eventHistory.getEventsFromLastHour();

        assertTrue(recentEvents.isEmpty());
    }

    // Test getEventsFromLastHour(EventType) - basic functionality
    @Test
    public void testGetEventsFromLastHourByType() {
        // Record different types of events
        eventHistory.recordEvent(testEvent1);     // NEW_TASK
        eventHistory.recordEvent(testEvent2);     // NEW_TASK
        eventHistory.recordEvent(reminderEvent);  // TASK_REMINDER

        List<Event> taskEvents = eventHistory.getEventsFromLastHour(EventType.NEW_TASK);
        List<Event> reminderEvents = eventHistory.getEventsFromLastHour(EventType.TASK_REMINDER);

        assertEquals(2, taskEvents.size());
        assertEquals(1, reminderEvents.size());

        assertTrue(taskEvents.contains(testEvent1));
        assertTrue(taskEvents.contains(testEvent2));
        assertTrue(reminderEvents.contains(reminderEvent));
    }

    // Test getEventsFromLastHour(EventType) - null parameter
    @Test
    public void testGetEventsFromLastHourByTypeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventHistory.getEventsFromLastHour(null);
        });
    }

    // Test getEventsFromLastHour(EventType) - no matching events
    @Test
    public void testGetEventsFromLastHourByTypeNoMatches() {
        eventHistory.recordEvent(testEvent1); // NEW_TASK

        List<Event> heartbeatEvents = eventHistory.getEventsFromLastHour(EventType.HEARTBEAT);

        assertTrue(heartbeatEvents.isEmpty());
    }



    // Test ConcurrentHashMap behavior
    @Test
    public void testConcurrentMapBehavior() {
        assertNotNull(eventHistory.getHistoryEvents());

        // Verify we can safely iterate while other operations happen
        eventHistory.recordEvent(testEvent1);

        // This should not throw
        assertDoesNotThrow(() -> {
            for (Event event : eventHistory.getHistoryEvents().keySet()) {
                eventHistory.recordEvent(new ReminderEvents("During iteration", false));
                break; // Just test one iteration
            }
        });
    }

    // Test edge cases
    @Test
    public void testSameEventMultipleTimes() {
        // Record the same event multiple times
        eventHistory.recordEvent(testEvent1);
        eventHistory.recordEvent(testEvent1); // Same event again

        // Should only have one entry (Map behavior)
        assertEquals(1, eventHistory.getHistoryEvents().size());

        // But timestamp should be updatedDDDDD
        LocalDateTime timestamp = eventHistory.getHistoryEvents().get(testEvent1);
        assertNotNull(timestamp);
    }


    @Test
    public void testLargeNumberOfEvents() {
        int numberOfEvents = 1000;

        for (int i = 0; i < numberOfEvents; i++) {
            Event event = new NewTaskEvent(new Task("TSK" + i, "Task " + i), i % 2 == 0);
            eventHistory.recordEvent(event);
        }

        assertEquals(numberOfEvents, eventHistory.getHistoryEvents().size());


        List<Event> recentEvents = eventHistory.getEventsFromLastHour();
        assertEquals(numberOfEvents, recentEvents.size());
    }
}