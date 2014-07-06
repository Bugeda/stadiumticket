package com.dataartschool2.stadiumticket.dreamteam.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
public class Event {

	@Id
    @GeneratedValue  
    private Integer id;

    @NotNull(message = "Must be filled.")
    @Size(min = 1, message = "Must be filled.")
	private String eventName;

    @Future(message = "Event date must be in the future.")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date eventDate;

    @NotNull(message = "Must be filled")
    @Min(value = 1, message = "Should be greater than zero.")
    private Integer bookingCanceltime;

    private boolean isDelete;

    @OneToMany(cascade= CascadeType.ALL)
    private List<SectorPrice> sectorPriceSet;

    public Event(){}
   
    public Event(int id, String eventName, Timestamp eventDate, int bookingCanceltime, List<SectorPrice> sectorPriceSet) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate; 
        this.bookingCanceltime = bookingCanceltime;
        this.sectorPriceSet = sectorPriceSet;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;

        if (bookingCanceltime != event.bookingCanceltime) {
            return false;
        }
        if (isDelete != event.isDelete) {
            return false;
        }
        if (!eventDate.equals(event.eventDate)) {
            return false;
        }
        if (!eventName.equals(event.eventName)) {
            return false;
        }
        if (id != null ? !id.equals(event.id) : event.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + eventName.hashCode();
        result = 31 * result + eventDate.hashCode();
        result = 31 * result + bookingCanceltime;
        result = 31 * result + (isDelete ? 1 : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<SectorPrice> getSectorPriceSet() {
        return sectorPriceSet;
    }

    public void setSectorPriceSet(List<SectorPrice> sectorPriceSet) {
        this.sectorPriceSet = sectorPriceSet;
    }
}
