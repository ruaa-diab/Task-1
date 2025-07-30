package org.example;

import java.util.HashMap;
import java.util.Map;

public static class ManagingSubscribers {

    public Map<Subscriber, EventType> getManage() {
        return manage;
    }

    public void setManage(Map<Subscriber, EventType> manage) {
        this.manage = manage;
    }

    private Map<Subscriber,EventType> manage=new HashMap<>();

}
