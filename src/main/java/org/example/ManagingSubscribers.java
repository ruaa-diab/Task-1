package org.example;

import java.util.HashMap;
import java.util.Map;

public static class ManagingSubscribers {
    public ManagingSubscribers() {
    }

    private static Map<Subscriber,EventType> manage=new HashMap<>();

    public static Map<Subscriber, EventType> getManage() {
        return manage;
    }

    public static void setManage(Map<Subscriber, EventType> manage) {
        ManagingSubscribers.manage = manage;
    }
}
