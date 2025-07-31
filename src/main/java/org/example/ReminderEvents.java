package org.example;

public class ReminderEvents extends Event{



    public ReminderEvents(EventType type) {
        super(type);
    }





    /*public class EventScheduler {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startReminderEvents() {
        scheduler.scheduleAtFixedRate(() -> {
            ReminderEvents event = new ReminderEvents();
            ManagingSubscribers.getInstance().publish(EventType.REMINDER, event);
        }, 0, 10, TimeUnit.SECONDS);
    }
}*/
}
