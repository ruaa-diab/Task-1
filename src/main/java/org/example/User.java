package org.example;

import java.util.List;

public class User {

    private List<EventType> preferences; //then here we eill make the user choose betweeten
    //his or her preferences by checking or putting a circle so later we can suggest events to him.
    private String name;

    public User(){

    }
    public User(List<EventType> preferences,String name){
        this.preferences=preferences;
        this.name=name;
    }

    public List<EventType> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<EventType> preferences) {
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
