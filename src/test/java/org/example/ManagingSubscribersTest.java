package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ManagingSubscribersTest {

    private ManagingSubscribers managingSubscribers;
    private User testUser1;
    private User testUser2;
    private Subscriber testSubscriber1;
    private Subscriber testSubscriber2;
    private Event testEvent;
    private Event reminderEvent;

    @BeforeEach
    public void setUp() {
        managingSubscribers = ManagingSubscribers.getInstance();

        // Create test users
        testUser1 = new User("Alice","1294");
        testUser2 = new User("Bob","14567");

        // Create test subscribers
        testSubscriber1 = new Subscriber("SUB001", "Alice");
        testSubscriber1.setSubscriberId("22334455");

        testSubscriber2 = new Subscriber("SUB002", "Bob");
        testSubscriber2.setSubscriberId("3r456");

        // Create test events
        testEvent = new NewTaskEvent(new Task("TSK001", "Test Task"), true);
        reminderEvent = new ReminderEvents("Test Reminder", false);
    }

    @AfterEach
    public void tearDown() {
        // Clear event history
        EventHistory.getInstance().getHistoryEvents().clear();

        // Note: ManagingSubscribers state persists between tests (singleton)
        // This might cause tests to interfere with each other
        managingSubscribers.getNotifiedOdOfEvent().clear();
    }

    // Test singleton behavior
    @Test
    public void testSingletonInstance() {
        ManagingSubscribers instance1 = ManagingSubscribers.getInstance();
        ManagingSubscribers instance2 = ManagingSubscribers.getInstance();

        assertSame(instance1, instance2);
        assertSame(managingSubscribers, instance1);
    }

    // Test Subscribe method (User → Subscriber)
    @Test
    public void testSubscribeUser() {
        // Create a unique user for this test instead of reusing testUser1
        User uniqueUser = new User("Alice", "UNIQUE_ID_" + System.currentTimeMillis());

        Subscriber subscriber = managingSubscribers.Subscribe(uniqueUser, EventType.NEW_TASK);

        assertNotNull(subscriber, "Subscriber should not be null - check if user was already subscribed");
        assertEquals("Alice", subscriber.getName());
        assertTrue(subscriber.getId().startsWith("sub"));

        // Verify subscriber was added to the list
        List<Subscriber> subscribers = managingSubscribers.getSubscribers(EventType.NEW_TASK);
        assertNotNull(subscribers);
        assertTrue(subscribers.contains(subscriber));
    }

    // Test Subscribe exception handling
    @Test
    public void testSubscribeNullUser() {
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.Subscribe(null, EventType.NEW_TASK);
        });
    }

    @Test
    public void testSubscribeNullEventType() {
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.Subscribe(testUser1, null);
        });
    }

    @Test
    public void testSubscribeUserWithNullName() {
        User userWithNullName = new User();
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.Subscribe(userWithNullName, EventType.NEW_TASK);
        });
    }

    // Test reSubscribe method
    @Test
    public void testReSubscribeNewSubscriber() {
        managingSubscribers.reSubscribe(testSubscriber1, EventType.NEW_TASK);

        List<Subscriber> subscribers = managingSubscribers.getSubscribers(EventType.NEW_TASK);
        assertNotNull(subscribers);
        assertTrue(subscribers.contains(testSubscriber1));
    }


    @Test
    public void testReSubscribeNullSubscriber() {
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.reSubscribe(null, EventType.NEW_TASK);
        });
    }

    @Test
    public void testReSubscribeNullEventType() {
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.reSubscribe(testSubscriber1, null);
        });
    }

    // Test Unsubscribe method
    @Test
    public void testUnsubscribe() {
        // First subscribe
        managingSubscribers.reSubscribe(testSubscriber1, EventType.NEW_TASK);
        assertTrue(managingSubscribers.getSubscribers(EventType.NEW_TASK).contains(testSubscriber1));

        // Then unsubscribe
        managingSubscribers.Unsubscribe(testSubscriber1, EventType.NEW_TASK);
        assertFalse(managingSubscribers.getSubscribers(EventType.NEW_TASK).contains(testSubscriber1));
    }

    @Test
    public void testUnsubscribeFromNonExistentType() {
        // Should not throw exception
        assertDoesNotThrow(() -> {
            managingSubscribers.Unsubscribe(testSubscriber1, EventType.HEARTBEAT);
        });
    }

    @Test
    public void testUnsubscribeNullSubscriber() {
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.Unsubscribe(null, EventType.NEW_TASK);
        });
    }

    @Test
    public void testUnsubscribeNullEventType() {
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.Unsubscribe(testSubscriber1, null);
        });
    }

    // Test publish method
    @Test
    public void testPublishEvent() {
        // Subscribe a user first
        Subscriber subscriber = managingSubscribers.Subscribe(testUser1, EventType.NEW_TASK);

        // Publish event
        managingSubscribers.publish(testEvent);

        // Verify event was recorded in history
        assertFalse(EventHistory.getInstance().getHistoryEvents().isEmpty());
        assertTrue(EventHistory.getInstance().getHistoryEvents().containsKey(testEvent));

        // Verify subscriber was notified
        assertFalse(managingSubscribers.getNotifiedOdOfEvent().isEmpty());
    }

    @Test
    public void testPublishNullEvent() {
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.publish(null);
        });
    }


    // Test notifyAllSubscribers method
    @Test
    public void testNotifyAllSubscribers() {
        // Clear any existing notifications first
        managingSubscribers.getNotifiedOdOfEvent().clear();

        // Use unique user to avoid subscription conflicts
        User uniqueUser = new User("NotifyTestUser", "NOTIFY_TEST_" + System.currentTimeMillis());

        // Subscribe a user
        Subscriber subscriber = managingSubscribers.Subscribe(uniqueUser, EventType.NEW_TASK);
        assertNotNull(subscriber, "Subscription should succeed before testing notifications");

        // Create notification
        Notification notification = new Notification(testEvent.getMsg(), testEvent, "Test Alert");

        // Notify subscribers
        managingSubscribers.notifyAllSubscribers(notification, EventType.NEW_TASK);

        // Verify notification was recorded
        assertTrue(managingSubscribers.getNotifiedOdOfEvent().containsKey(testEvent),
                "Event should be recorded in notification history");
        List<Subscriber> notified = managingSubscribers.getNotifiedOdOfEvent().get(testEvent);
        assertNotNull(notified, "Notified subscribers list should not be null");
        assertTrue(notified.contains(subscriber), "Subscriber should be in the notified list");
    }
    @Test
    public void testNotifyAllSubscribersNullNotification() {
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.notifyAllSubscribers(null, EventType.NEW_TASK);
        });
    }

    @Test
    public void testNotifyAllSubscribersNullEventType() {
        Notification notification = new Notification("Test", testEvent, "Alert");
        assertThrows(IllegalArgumentException.class, () -> {
            managingSubscribers.notifyAllSubscribers(notification, null);
        });
    }

    // Test AtomicInteger counter for subscriber IDs
    @Test
    public void testSubscriberIdGeneration() {
        Subscriber sub1 = managingSubscribers.Subscribe(new User("ruaa","2556677"), EventType.NEW_TASK);
        Subscriber sub2 = managingSubscribers.Subscribe(new User("mh","22eerr"), EventType.NEW_TASK);

        assertNotEquals(sub1.getId(), sub2.getId());
        assertTrue(sub1.getId().startsWith("sub"));
        assertTrue(sub2.getId().startsWith("sub"));
    }

    // Test thread safety - concurrent subscriptions
    @Test
    public void testConcurrentSubscriptions() throws InterruptedException {
        int numberOfThreads = 10;
        int subscriptionsPerThread = 5;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < subscriptionsPerThread; j++) {
                        // FIX: Give each user a unique ID
                        User user = new User("User" + threadId + "_" + j, "ID_" + threadId + "_" + j);
                        //
                        //                                                 Now each user has unique ID!

                        Subscriber subscriber = managingSubscribers.Subscribe(user, EventType.NEW_TASK);
                        if (subscriber != null) {
                            successCount.incrementAndGet();
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();

        // Verify all subscriptions were successful
        assertEquals(numberOfThreads * subscriptionsPerThread, successCount.get());

        // Verify subscribers were added to the list
        List<Subscriber> subscribers = managingSubscribers.getSubscribers(EventType.NEW_TASK);
        assertNotNull(subscribers);
        assertTrue(subscribers.size() >= numberOfThreads * subscriptionsPerThread);
    }
    // Test thread safety - concurrent publishing
    @Test
    public void testConcurrentPublishing() throws InterruptedException {
        // First, subscribe some users
        managingSubscribers.Subscribe(testUser1, EventType.NEW_TASK);
        managingSubscribers.Subscribe(testUser2, EventType.TASK_REMINDER);

        int numberOfThreads = 5;
        int eventsPerThread = 3;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        //to make sure we are waiting for all of them to finish
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < eventsPerThread; j++) {
                        if (j % 2 == 0) {
                            Event event = new NewTaskEvent(new Task("TSK" + threadId + "_" + j, "Task " + threadId + "_" + j), true);
                            managingSubscribers.publish(event);
                        } else {
                            Event event = new ReminderEvents("Reminder " + threadId + "_" + j, false);
                            managingSubscribers.publish(event);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        // we are waiting 5 seconds
        executor.shutdown();

        // Verify events were recorded
        assertTrue(EventHistory.getInstance().getHistoryEvents().size() >= numberOfThreads * eventsPerThread);
        //checking if all of them did happen and were published correctly.
    }


    // Test edge cases
    @Test
    public void testGetSubscribersForNonExistentType() {
        List<Subscriber> subscribers = managingSubscribers.getSubscribers(EventType.HEARTBEAT);
        assertNull(subscribers); // Should return null for non-existent types
    }

    @Test
    public void testEmptyNotificationList() {
        // Subscribe user but don't publish anything
        managingSubscribers.Subscribe(testUser1, EventType.NEW_TASK);

        assertTrue(managingSubscribers.getNotifiedOdOfEvent().isEmpty());
    }
}