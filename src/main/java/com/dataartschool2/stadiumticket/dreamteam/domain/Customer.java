package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private int id;

	@NotBlank(message = "error.notNull")
    @NotNull(message = "error.notNull")
    @Size(min = 1, max = 50, message = "error.wrongLength")
    private String customerName;
    
    public Customer(){}

    public Customer(int id, String customerName) {
        this.id = id;
        this.customerName = customerName;
   
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



}
