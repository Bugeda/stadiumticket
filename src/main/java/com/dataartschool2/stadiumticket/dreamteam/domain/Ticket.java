package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private Seat seat;

    @ManyToOne
    private Event event;

    private String ticketNumber;

    public Ticket(){}

    public Ticket(int id, Seat seat, Event event, String ticketNumber) {
        this.id = id;
        this.seat = seat;
        this.event = event;
        this.ticketNumber = ticketNumber;
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
}
