package com.dataartschool2.stadiumticket.dreamteam.domain;

import org.hibernate.validator.constraints.NotBlank;
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
	
	@NotBlank(message = "error.notNull")
	@NotNull(message = "error.notNull")
	@Size(min = 1, max = 50, message = "error.wrongLength")
	private String eventName;

    @NotNull(message = "error.notNullDate")
    @Future(message = "error.futureDate")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date eventDate;

    @NotNull(message = "error.notNull")
    @Min(value = 1, message = "error.notPositivePrice")
    private Integer bookingCanceltime;

    private boolean isDelete;

    @OneToMany(cascade = CascadeType.ALL)
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

        if (eventDate != null ? !eventDate.equals(event.eventDate) : event.eventDate != null) {
            return false;
        }
        if (eventName != null ? !eventName.equals(event.eventName) : event.eventName != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventName != null ? eventName.hashCode() : 0;
        result = 31 * result + (eventDate != null ? eventDate.hashCode() : 0);
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

	public Integer getBookingCanceltime() {
		return bookingCanceltime;
	}

	public void setBookingCanceltime(Integer bookingCanceltime) {
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
