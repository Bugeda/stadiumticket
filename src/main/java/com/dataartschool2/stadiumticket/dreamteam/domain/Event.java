package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Event {

	@Id
    @GeneratedValue
    private int id;

    private String eventName;

    private Timestamp eventDate;

    public Event(){}
   
    public Event(int id, String eventName, Timestamp eventDate) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate;        
    }
    
   
 	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result
				+ ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Event)) 
			return false;
		Event other = (Event) obj;
		if (eventDate == null) {
			if (other.getEventDate() != null)
				return false;
		} else if (!eventDate.equals(other.getEventDate()))
			return false;
		if (eventName == null) {
			if (other.getEventName() != null)
				return false;
		} else if (!eventName.equals(other.getEventName()))
			return false;
		if (id != other.getId())
			return false;
		return true;
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
    
    public Timestamp getEventDate() {
        return eventDate;
    }

    public void setEventDate(Timestamp eventDate) {
        this.eventDate = eventDate;
    }
    
   
   
}
