package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Event {

	@Id
    @GeneratedValue  
    private int id;

	private String eventName;

    private Timestamp eventDate;

    private int bookingCanceltime;

    private boolean isDelete;
    public Event(){}
   
    public Event(int id, String eventName, Timestamp eventDate, int bookingCanceltime) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate; 
        this.bookingCanceltime = bookingCanceltime;        
    }
    
       
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bookingCanceltime;
		result = prime * result
				+ ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result
				+ ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + id;
		result = prime * result + (isDelete ? 1231 : 1237);
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
		if (bookingCanceltime != other.bookingCanceltime)
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (id != other.id)
			return false;
		if (isDelete != other.isDelete)
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

	public int getBookingCanceltime() {
		return bookingCanceltime;
	}

	public void setBookingCanceltime(int bookingCanceltime) {
		this.bookingCanceltime = bookingCanceltime;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	} 
   
}
