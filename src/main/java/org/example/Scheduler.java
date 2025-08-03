package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private static final int defaultRemainderInterval=10;
    private static final int defaultHeartBeatInterval=5;
    //i added those cause those reminders will be set to fire by this default time till changed by admin.


    private ScheduledExecutorService scheduler;
    private ManagingSubscribers eventManager;

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public void setScheduler(ScheduledExecutorService scheduler) {
        if (scheduler == null) {
            throw new IllegalArgumentException("Scheduler cannot be null");
        }
        this.scheduler = scheduler;
    }



    public Scheduler() {
        this.scheduler = Executors.newScheduledThreadPool(2);
        //here i chose the number 2 because i have 2 types of threads to handles 2 eventtypes
        //sp one for reminder events and one for heartbeat events.
        this.eventManager=ManagingSubscribers.getInstance();

    }


    //in these methods i didn't include the nullpointerexceptionmethod
    //because i considered that the system is connected to data base or souce that gets these reminders from
    //so
        public void startReminderEvents(int interval,String msg,boolean isUrgent){
        //set to falult if null

            if (msg == null) {
                throw new IllegalArgumentException("Message cannot be null");
            }
            if (interval <= 0) {
                throw new IllegalArgumentException("Interval must be positive");
            }
            if (scheduler.isShutdown()) {
                throw new IllegalStateException("Scheduler is already shut down");
            }
            scheduler.scheduleAtFixedRate(()->{

                ReminderEvents re=new ReminderEvents(msg,isUrgent);
                eventManager.publish(re);
                System.out.println(" a new reminder was made"+msg +"at "+java.time.LocalTime.now());





            },0,                    // Initial delay (0 = start immediately)
                    interval,      // Period between executions
                    TimeUnit.SECONDS);



        }

        public void startEventsLikeHeartBeats(int interval,String msg,boolean isUrgent){
        //to be made to have interval time diffrent than the reminder part
            //by msg we mean the title or description to this event
            //if isUrgent is null it is set to fault
            //and when the system takes the data from anywhere it will make it Boolean ...
            if (msg == null) {
                throw new IllegalArgumentException("Message cannot be null");
            }
            if (interval <= 0) {
                throw new IllegalArgumentException("Interval must be positive");
            }
            if (scheduler.isShutdown()) {
                throw new IllegalStateException("Scheduler is already shut down");
            }

            scheduler.scheduleAtFixedRate(()->{
                HeartbeatEvent heartbeat =new HeartbeatEvent(msg,isUrgent);
                eventManager.publish(heartbeat);
                System.out.println(" a new hear tbeat was made at"+java.time.LocalTime.now());



            },0,interval,TimeUnit.SECONDS);

        }
    public void shutDown() {
        if (scheduler != null && !scheduler.isShutdown()) {
            try {
                scheduler.shutdown();
                // Wait for existing tasks to complete
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow(); // Force shutdown
                    System.out.println("Scheduler was forcefully shut down");
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
                System.err.println("Shutdown interrupted: " + e.getMessage());
            }
        }
    }




    //and the admin can update and change it
    // i will make sure that they dint have the same time











}
