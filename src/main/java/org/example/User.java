package org.example;

import java.util.List;
import java.util.Objects;

public class User {

    private List<EventType> preferences; //then here we eill make the user choose betweeten
    //his or her preferences by checking or putting a circle so later we can suggest events to him.
    private String name;



    private String userId;
    //or it could be the user email.
    //or id number
    // or anything that has a unique value

    public User(){

    }

    public User(String name,String userId) {
        this.setName(name);
        this.setUserId(userId);
    }

    public User(List<EventType> preferences, String name,String userId){
        this.setPreferences(preferences);
        this.setName(name);
        this.setUserId(userId);
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
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("user id cannot be null");
        }
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.getUserId().trim().compareToIgnoreCase(((User) o).getUserId().trim())==0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
