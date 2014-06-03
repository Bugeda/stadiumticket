package com.stadiumticket.domain;

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

@Entity
@Table(name = "SECTORS")
public class Sectors {
	@Id
	@Column(name = "SECTOR_ID")
	@GeneratedValue
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENT_ID", nullable = false)	
	private Events events;
	/*private Integer eid;*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sectors")
	private Set<Tickets> tickets;
	
	@Column(name = "SECTOR_NAME")
	private String name;	
	
	@Column(name = "PRICE_SECTORS")
	private Double priceSectors;
	
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
	
	public Double getPriceSectors() {
		return priceSectors;
	}
	public void setPriceSectors(Double priceSectors) {
		this.priceSectors = priceSectors;
	}
	
	public Events getEvents() {
		return events;
	}
	public void setEvents(Events events) {
		this.events = events;
	}
	/*
	public Integer getEid() {
		return eid;
	}

	public void setEId(Integer eid) {
		this.eid = eid;
	}
*/
	
	public Set<Tickets> getTickets() {
		return tickets;
	}
	public void setTickets(Set<Tickets> tickets) {
		this.tickets = tickets;
	}
}
