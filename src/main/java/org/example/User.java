package org.example;

import java.util.List;

public class User {

    private List<EventType> preferences; //then here we eill make the user choose betweeten
    //his or her preferences by checking or putting a circle so later we can suggest events to him.
    private String name;

    public User(){

    }

    public User(String name) {
        this.setName(name);
    }

    public User(List<EventType> preferences, String name){
        this.setPreferences(preferences);
        this.setName(name);
    }

    public List<EventType> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<EventType> preferences) {
        if (preferences == null) {
            throw new IllegalArgumentException("Preferences list cannot be null");
        }
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }
}
