package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.*;

import java.util.Date;


@Entity
public class Event {

    @Id
    @GeneratedValue
    private int id;

    private String eventName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;

    public Event(){}

    public Event(int id, String eventName, Date eventDate) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate;
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
    
    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
