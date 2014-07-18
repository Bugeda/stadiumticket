package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    public Booking(){}

    public Booking(int id, Customer customer, Seat seat, BookingStatus bookingStatus) {
        this.id = id;
        this.customer = customer;
        this.seat = seat;
        this.bookingStatus = bookingStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat ticket) {
        this.seat = ticket;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
