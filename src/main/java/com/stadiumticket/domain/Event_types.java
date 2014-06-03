package com.stadiumticket.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT_TYPES")
public class Event_types {
	@Id
	@Column(name = "EVENT_TYPE_ID")
	@GeneratedValue
	private Integer id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "event_types")
	private Set<Events> events;
		
	@Column(name = "EVENT_TYPE_NAME")
	private String name;

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
	
	public Set<Events> getEvents() {
		return events;
	}

	public void setEvents(Set<Events> events) {
		this.events = events;
	}

	
}
