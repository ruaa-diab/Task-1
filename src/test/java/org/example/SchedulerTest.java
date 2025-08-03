package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulerTest {

    private Scheduler scheduler;

    @BeforeEach
    public void setUp() {
        scheduler = new Scheduler();
    }

    @AfterEach
    public void tearDown() {
        if (scheduler != null) {
            scheduler.shutDown();
        }
    }

    // Test Constructor and Initialization
    @Test
    public void testConstructorInitializesSchedulerCorrectly() {
        assertNotNull(scheduler.getScheduler());
        assertFalse(scheduler.getScheduler().isShutdown());
        assertFalse(scheduler.getScheduler().isTerminated());
    }

    @Test
    public void testConstructorCreatesValidScheduledExecutorService() {
        ScheduledExecutorService ses = scheduler.getScheduler();
        assertNotNull(ses);
        assertTrue(ses instanceof ScheduledExecutorService);

        // Test that it can actually schedule tasks
        assertDoesNotThrow(() -> {
            ses.schedule(() -> {}, 1, TimeUnit.MILLISECONDS);
        });
    }

    // Test Scheduler Getter and Setter
    @Test
    public void testGetScheduler() {
        ScheduledExecutorService originalScheduler = scheduler.getScheduler();
        assertNotNull(originalScheduler);
        assertSame(originalScheduler, scheduler.getScheduler());
    }

    @Test
    public void testSetSchedulerWithValidScheduler() {
        ScheduledExecutorService newScheduler = Executors.newScheduledThreadPool(3);

        scheduler.setScheduler(newScheduler);

        assertSame(newScheduler, scheduler.getScheduler());
        newScheduler.shutdown();
    }

    @Test
    public void testSetSchedulerWithNullThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduler.setScheduler(null)
        );
        assertEquals("Scheduler cannot be null", exception.getMessage());
    }


    // Test startReminderEvents - Input Validation
    @Test
    public void testStartReminderEventsWithNullMessage() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduler.startReminderEvents(5, null, false)
        );
        assertEquals("Message cannot be null", exception.getMessage());
    }

    @Test
    public void testStartReminderEventsWithEmptyMessage() {
        // Empty string should be allowed (not null)
        assertDoesNotThrow(() -> scheduler.startReminderEvents(5, "", false));
    }

    @Test
    public void testStartReminderEventsWithZeroInterval() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduler.startReminderEvents(0, "Test message", false)
        );
        assertEquals("Interval must be positive", exception.getMessage());
    }

    @Test
    public void testStartReminderEventsWithNegativeInterval() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduler.startReminderEvents(-5, "Test message", false)
        );
        assertEquals("Interval must be positive", exception.getMessage());
    }

    @Test
    public void testStartReminderEventsWithShutdownScheduler() {
        scheduler.shutDown();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> scheduler.startReminderEvents(5, "Test message", false)
        );
        assertEquals("Scheduler is already shut down", exception.getMessage());
    }

    @Test
    public void testStartReminderEventsWithValidParameters() {
        assertDoesNotThrow(() -> {
            scheduler.startReminderEvents(1, "Test reminder", false);
        });

        // Verify scheduler is still running
        assertFalse(scheduler.getScheduler().isShutdown());
    }

    @Test
    public void testStartReminderEventsWithUrgentFlag() {
        assertDoesNotThrow(() -> {
            scheduler.startReminderEvents(2, "Urgent reminder", true);
        });

        // Verify scheduler is still running
        assertFalse(scheduler.getScheduler().isShutdown());
    }

    @Test
    public void testStartReminderEventsWithLargeInterval() {
        assertDoesNotThrow(() -> {
            scheduler.startReminderEvents(3600, "Hourly reminder", false);
        });
    }

    // Test startEventsLikeHeartBeats - Input Validation
    @Test
    public void testStartHeartbeatEventsWithNullMessage() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduler.startEventsLikeHeartBeats(5, null, false)
        );
        assertEquals("Message cannot be null", exception.getMessage());
    }

    @Test
    public void testStartHeartbeatEventsWithEmptyMessage() {
        // Empty string should be allowed (not null)
        assertDoesNotThrow(() -> scheduler.startEventsLikeHeartBeats(5, "", false));
    }

    @Test
    public void testStartHeartbeatEventsWithZeroInterval() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduler.startEventsLikeHeartBeats(0, "Test message", false)
        );
        assertEquals("Interval must be positive", exception.getMessage());
    }

    @Test
    public void testStartHeartbeatEventsWithNegativeInterval() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduler.startEventsLikeHeartBeats(-3, "Test message", false)
        );
        assertEquals("Interval must be positive", exception.getMessage());
    }



    @Test
    public void testStartHeartbeatEventsWithValidParameters() {
        assertDoesNotThrow(() -> {
            scheduler.startEventsLikeHeartBeats(1, "Test heartbeat", false);
        });

        // Verify scheduler is still running
        assertFalse(scheduler.getScheduler().isShutdown());
    }

    @Test
    public void testStartHeartbeatEventsWithUrgentFlag() {
        assertDoesNotThrow(() -> {
            scheduler.startEventsLikeHeartBeats(2, "Urgent heartbeat", true);
        });

        assertFalse(scheduler.getScheduler().isShutdown());
    }

    @Test
    public void testStartHeartbeatEventsWithLargeInterval() {
        assertDoesNotThrow(() -> {
            scheduler.startEventsLikeHeartBeats(7200, "2-hour heartbeat", false);
        });
    }


    @Test
    public void testShutDownGracefulShutdown() throws InterruptedException {
        // Start some events
        scheduler.startReminderEvents(10, "Test reminder", false);
        scheduler.startEventsLikeHeartBeats(15, "Test heartbeat", false);

        // Shutdown
        scheduler.shutDown();

        // Verify shutdown
        assertTrue(scheduler.getScheduler().isShutdown());
    }



    @Test
    public void testShutDownWithReplacedScheduler() {
        ScheduledExecutorService newScheduler = Executors.newScheduledThreadPool(1);
        scheduler.setScheduler(newScheduler);

        scheduler.shutDown();

        assertTrue(newScheduler.isShutdown());
    }

    @Test
    public void testMultipleReminderEvents() throws InterruptedException {
        scheduler.startReminderEvents(1, "Reminder 1", false);
        scheduler.startReminderEvents(1, "Reminder 2", true);
        scheduler.startReminderEvents(2, "Reminder 3", false);

        // Let events run briefly
        Thread.sleep(100);

        // Verify scheduler is still running
        assertFalse(scheduler.getScheduler().isShutdown());
    }

    @Test
    public void testMultipleHeartbeatEvents() throws InterruptedException {
        scheduler.startEventsLikeHeartBeats(1, "Heartbeat 1", false);
        scheduler.startEventsLikeHeartBeats(1, "Heartbeat 2", true);
        scheduler.startEventsLikeHeartBeats(2, "Heartbeat 3", false);

        // Let events run briefly
        Thread.sleep(100);

        // Verify scheduler is still running
        assertFalse(scheduler.getScheduler().isShutdown());
    }

    @Test
    public void testMixedEventTypes() throws InterruptedException {
        scheduler.startReminderEvents(1, "Test reminder", false);
        scheduler.startEventsLikeHeartBeats(1, "Test heartbeat", true);
        scheduler.startReminderEvents(2, "Another reminder", true);
        scheduler.startEventsLikeHeartBeats(2, "Another heartbeat", false);

        // Let events run briefly
        Thread.sleep(100);

        // Verify scheduler is still running
        assertFalse(scheduler.getScheduler().isShutdown());
    }

    // Test thread safety with concurrent operations
    @Test
    public void testConcurrentReminderEventCreation() throws InterruptedException {
        int numberOfThreads = 5;
        int eventsPerThread = 3;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < eventsPerThread; j++) {
                        scheduler.startReminderEvents(
                                (threadId + 1) * (j + 1),
                                "Reminder " + threadId + "_" + j,
                                j % 2 == 0
                        );
                        successCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    // Event creation failed
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();

        // Verify all event creations were successful
        assertEquals(numberOfThreads * eventsPerThread, successCount.get());

        // Verify scheduler is still running
        assertFalse(scheduler.getScheduler().isShutdown());
    }





    // Test edge cases
    @Test
    public void testSchedulerStateAfterException() {
        // Cause an exception
        assertThrows(IllegalArgumentException.class, () -> {
            scheduler.startReminderEvents(-1, "Test", false);
        });

        // Verify scheduler is still in good state
        assertFalse(scheduler.getScheduler().isShutdown());

        // Should still be able to schedule valid events
        assertDoesNotThrow(() -> {
            scheduler.startReminderEvents(1, "Valid reminder", false);
        });
    }


    @Test
    public void testEmptyMessageHandling() {
        // Empty messages should be allowed
        assertDoesNotThrow(() -> {
            scheduler.startReminderEvents(5, "", false);
            scheduler.startEventsLikeHeartBeats(5, "", true);
        });
    }

    @Test
    public void testBooleanFlagVariations() {
        // Test different combinations of urgent flags
        assertDoesNotThrow(() -> {
            scheduler.startReminderEvents(10, "Not urgent reminder", false);
            scheduler.startReminderEvents(10, "Urgent reminder", true);
            scheduler.startEventsLikeHeartBeats(10, "Not urgent heartbeat", false);
            scheduler.startEventsLikeHeartBeats(10, "Urgent heartbeat", true);
        });
    }
}