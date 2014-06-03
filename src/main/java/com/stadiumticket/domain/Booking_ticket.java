package com.stadiumticket.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "BOOKING_TICKET")
public class Booking_ticket {
	@Id
	@Column(name = "BOOKING_ID")
	@GeneratedValue
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TICKET_ID")
	private Tickets tickets;
	
	@Column(name = "USER_FIRSTNAME")
	private String uFirstName;	
	
	@Column(name = "USER_SECONDNAME")
	private String uSecondName;
	
	
	@Column(name = "PRICE_SECTORS")
	private Double priceSectors;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	/*
	public Integer getEid() {
		return eid;
	}

	public void setEId(Integer eid) {
		this.eid = eid;
	}
*/
	public String getFirstName() {
		return uFirstName;
	}

	public void setFirstName(String uFirstName) {
		this.uFirstName = uFirstName;
	}

	public String getSecondName() {
		return uSecondName;
	}

	public void setSecondName(String uSecondName) {
		this.uSecondName = uSecondName;
	}
	
	public Double getPriceSectors() {
		return priceSectors;
	}

	public void setPriceSectors(Double priceSectors) {
		this.priceSectors = priceSectors;
	}	
}
