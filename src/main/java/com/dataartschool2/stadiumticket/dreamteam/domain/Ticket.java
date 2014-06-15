package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by dshchelkonogov on 14.06.2014.
 */
@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private Seat seat;

    private String ticketNumber;

    public Ticket(){}

    public Ticket(int id, Seat seat, String ticketNumber) {
        this.id = id;
        this.seat = seat;
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
}
