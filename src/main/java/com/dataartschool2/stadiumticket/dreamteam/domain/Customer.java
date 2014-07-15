package com.dataartschool2.stadiumticket.dreamteam.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 1, message = "error.notNull")
    private String customerName; 

    @OneToMany(cascade= CascadeType.ALL)
    private List<Booking> bookingSet;
    
    public Customer(){}

    public Customer(int id, String customerName, List<Booking> bookingSet) {
        this.id = id;
        this.customerName = customerName;
        this.bookingSet = bookingSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public List<Booking> getBookingSet() {
        return bookingSet;
    }

    public void setBookingSet(List<Booking> bookingSet) {
        this.bookingSet = bookingSet;
    }
}
