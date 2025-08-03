package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Subscriber implements Observer {
    private String name;
    private String id;
    private List<EventFilter> filters;
    private String SubscriberId;

    public Subscriber() {
    }

    public Subscriber(String id, String name) {
        this.setId(id);
        this.setName(name);
        this.filters = new ArrayList<>();
    }

    // ADD THESE METHODS FOR BACKWARD COMPATIBILITY:
    public EventFilter getEventFilter() {
        // Return the first filter if exists, null otherwise
        return filters.isEmpty() ? null : filters.get(0);
    }

    public void setEventFilter(EventFilter filter) {
        // Clear existing filters and add the new one
        filters.clear();
        if (filter != null) {
            filters.add(filter);
        }
    }

    public List<EventFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<EventFilter> filters) {
        if (filters == null) {
            throw new IllegalArgumentException("Filters list cannot be null");
        }
        this.filters = filters;
    }

    public String getId() {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubscriberId() {
        return SubscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        if (subscriberId == null) {
            throw new IllegalArgumentException("subscriberids cannot be null");
        }
        SubscriberId = subscriberId;
    }

    public void addFilter(EventFilter filter) {
        this.getFilters().add(filter);
    }

    // ADD THIS METHOD for factory pattern compatibility:
    public void addFilters(EventFilter... filters) {
        for (EventFilter filter : filters) {
            if (filter != null) {
                this.filters.add(filter);
            }
        }
    }

    private boolean passTheFilters(Event e) {
        if (e == null) {
            return false;
        }
        LocalTime currentTime = LocalTime.now();
        if (this.getFilters().isEmpty()) {
            return true;
        }

        for (EventFilter f : this.getFilters()) {
            if (!f.matches(e, currentTime)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean update(Notification nf) {
        if (nf == null) {
            System.err.println("Subscriber [" + this.name + "] received null notification");
            return false;
        }

        if (this.passTheFilters((Event) nf.getRelatedTask())) {
            System.out.println("[" + this.name + "] received: " + nf.toString());
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "filters=" + filters +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return this.getId().trim().compareToIgnoreCase(((Subscriber) o).getId().trim()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, filters);
    }
}