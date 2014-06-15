package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;


@Entity
public class Event {

    @Id
    @GeneratedValue
    private int id;

    private String eventName;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar eventTime;

    public Event(){}

    public Event(int id, String eventName, Calendar eventTime) {
        this.id = id;
        this.eventName = eventName;
        this.eventTime = eventTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Calendar getEventTime() {
        return eventTime;
    }

    public void setEventTime(Calendar eventTime) {
        this.eventTime = eventTime;
    }
}
