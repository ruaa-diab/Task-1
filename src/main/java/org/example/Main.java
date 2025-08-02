package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Event wow=new NewTaskEvent(new Task("tt","123"),false);

        Subscriber s=new Subscriber("ruaa","ruru");
        s.addFilter(new PriorityFilter(false));
      //  ManagingSubscribers.getInstance().Subscribe(s,EventType.NEW_TASK);
      ManagingSubscribers.getInstance().publish(wow);







    }
}