package com.dataartschool2.stadiumticket.service;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "TICKETS")
public class EventServiceImpl {
	@Id
	@Column(name = "TICKET_ID")
	@GeneratedValue
	private Integer id;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECTOR_ID", nullable = false)
	private CustomerService sectors;
	/*private Integer sid;*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENT_ID", nullable = false)
	private SectorPriceService events;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "tickets", cascade = CascadeType.ALL)
	private SectorService bookingTicket;	
	
	@Column(name = "TICKET_ROW_ID")
	private int ticketRowId;

	@Column(name = "TICKET_PLACE")
	private int ticketPlace;
	
	@Column(name = "STATE")
	private int state;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getTicketRowId() {
		return ticketRowId;
	}
	public void setTicketRowId(Integer ticketRowId) {
		this.ticketRowId = ticketRowId;
	}
	
	public Integer getTicketPlace() {
		return ticketPlace;
	}
	public void setTicketPlace(Integer ticketPlace) {
		this.ticketPlace = ticketPlace;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public CustomerService getSectors() {
		return sectors;
	}
	public void setSectors(CustomerService sectors) {
		this.sectors = sectors;
	}
	
	public SectorPriceService getEvents() {
		return events;
	}
	public void setEvents(SectorPriceService events) {
		this.events = events;
	}

	public SectorService getBookingTicket() {
		return bookingTicket;
	}
	public void setBookingTicket(SectorService bookingTicket) {
		this.bookingTicket = bookingTicket;
	}
}
