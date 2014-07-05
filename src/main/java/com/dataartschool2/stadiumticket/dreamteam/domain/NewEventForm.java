package com.dataartschool2.stadiumticket.dreamteam.domain;

public class NewEventForm {
	
	private	int id;
	
	private	String eventName;
	
	private String[] sectorPrice;
		
	private	String eventDate;
	
	private String bookingCanceltime;
	
	public  int getId() {
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
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	
	public String[] getSectorPrice() {
		return sectorPrice;
	}
	public void setSectorPrice(String[] sectorPrice) {
		this.sectorPrice = sectorPrice;
	}
	public String getBookingCanceltime() {
		return bookingCanceltime;
	}
	public void setBookingCanceltime(String bookingCanceltime) {
		this.bookingCanceltime = bookingCanceltime;
	}
	

}
