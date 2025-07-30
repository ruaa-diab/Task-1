package org.example;

public interface Subject<T extends Observer> {


    void ADD(T o);
    void Remove(T O);
    void notifyAllSubscribers(Notification nf);
}
