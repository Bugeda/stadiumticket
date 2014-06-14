package com.dataartschool2.stadiumticket.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dataartschool2.stadiumticket.domain.Tickets;



@Entity
@Table(name = "EVENTS")
public class Events {
	@Id
	@Column(name = "EVENT_ID")
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENT_TYPE_ID", nullable = false)
	private Event_types eventTypes;
	/*private Integer etid;*/	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "events")
	private Set<Sectors> sectors;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "events")
	private Set<Tickets> tickets;
	
	@Column(name = "EVENT_NAME")
	private String name;	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EVENT_DATE")
	private Date eventDate;
	
	@Column(name = "BOOKING_TIME")
	private int bookingTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Event_types getEventTypes() {
		return eventTypes;
	}
	public void setEventTypes(Event_types eventTypes) {
		this.eventTypes = eventTypes;
	}
	/*
	public Integer getEtid() {
		return etid;
	}

	public void setEtId(Integer etid) {
		this.etid = etid;
	}*/
	
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	public Integer getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(Integer bookingTime) {
		this.bookingTime = bookingTime;
	}

	public Set<Sectors> getSectors() {
		return sectors;
	}
	public void setSectors(Set<Sectors> sectors) {
		this.sectors = sectors;
	}
	
	public Set<Tickets> getTickets() {
		return tickets;
	}
	public void setTickets(Set<Tickets> tickets) {
		this.tickets = tickets;
	}
}
