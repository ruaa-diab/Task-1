package org.example;

public interface Subject<T extends Observer> {


    void Subscribe(T o,EventType type);
    void Unsubscribe(T O,EventType type);
    void notifyAllSubscribers(Notification nf,EventType type);
}
