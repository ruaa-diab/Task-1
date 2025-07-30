package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagingSubscribers {
    // for tis class i chose the design singleton to use only one instance
            //so i can keep the list of subscribers from losing any data
    private static ManagingSubscribers instance=new ManagingSubscribers();
    private Map<EventType ,List<Subscriber>> manage=new HashMap<>();


    //PRIVATE constructor so no one makes another instance
    private ManagingSubscribers(){}
    public static ManagingSubscribers getInstance(){
        return instance;
    }
    //so allclases and all objects will have the same version

    public List<Subscriber> getSubscribers(EventType t){
        return this.manage.get(t);
        //and by that we get subscribers
    }


    }

