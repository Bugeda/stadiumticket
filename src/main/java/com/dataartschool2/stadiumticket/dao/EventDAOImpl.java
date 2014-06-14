package com.dataartschool2.stadiumticket.dao;

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
public class EventDAOImpl {
	@Id
	@Column(name = "TICKET_ID")
	@GeneratedValue
	private Integer id;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECTOR_ID", nullable = false)
	private CustomerDAO sectors;
	/*private Integer sid;*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENT_ID", nullable = false)
	private SectorPriceDAO events;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "tickets", cascade = CascadeType.ALL)
	private SectorDAO bookingTicket;	
	
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

	public CustomerDAO getSectors() {
		return sectors;
	}
	public void setSectors(CustomerDAO sectors) {
		this.sectors = sectors;
	}
	
	public SectorPriceDAO getEvents() {
		return events;
	}
	public void setEvents(SectorPriceDAO events) {
		this.events = events;
	}

	public SectorDAO getBookingTicket() {
		return bookingTicket;
	}
	public void setBookingTicket(SectorDAO bookingTicket) {
		this.bookingTicket = bookingTicket;
	}
}
