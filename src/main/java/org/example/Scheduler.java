package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private ScheduledExecutorService scheduler;
    private ManagingSubscribers eventManager;

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public void setScheduler(ScheduledExecutorService scheduler) {
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
        public void startReminderEvents(int interval,String msg){
            scheduler.scheduleAtFixedRate(()->{

                ReminderEvents re=new ReminderEvents(msg);
                eventManager.publish(re);
                System.out.println(" a new reminder was made"+msg +"at "+java.time.LocalTime.now());





            },0,                    // Initial delay (0 = start immediately)
                    interval,      // Period between executions
                    TimeUnit.SECONDS);



        }

        public void startEventsLikeHeartBeats(int interval,String msg){
        //to be made to have interval time diffrent than the reminder part
            scheduler.scheduleAtFixedRate(()->{
                HeartbeatEvent heartbeat =new HeartbeatEvent(msg);
                eventManager.publish(heartbeat);
                System.out.println(" a new hear tbeat was made at"+java.time.LocalTime.now());



            },0,interval,TimeUnit.SECONDS);

        }
        public void shutDown(){
            scheduler.shutdown();
        }












}
