package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Seat seat;

    @ManyToOne(cascade = CascadeType.ALL)
    private Event event;

    private String ticketNumber;
    
    private SeatStatus seatStatus;
    
    public Ticket(){}

    public Ticket(int id, Seat seat, Event event, String ticketNumber, SeatStatus seatStatus) {
        this.id = id;
        this.seat = seat;
        this.event = event;
        this.ticketNumber = ticketNumber;
    	this.seatStatus = seatStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}
}
