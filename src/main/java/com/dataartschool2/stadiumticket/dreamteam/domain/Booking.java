package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.*;
import java.awt.print.Book;

@Entity
public class Booking {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    public Booking(){}

    public Booking(int id, Customer customer, Ticket ticket, BookingStatus bookingStatus) {
        this.id = id;
        this.customer = customer;
        this.ticket = ticket;
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

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
