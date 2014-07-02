package com.dataartschool2.stadiumticket.dreamteam.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.util.AutoPopulatingList;

@Entity
public class SectorPrice {
	
   @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private Sector sector;

    @OneToOne
    private Event event;

    private double price;

    public SectorPrice(){}

    public SectorPrice(int id, Sector sector, Event event, double price) {
        this.id = id;
        this.sector = sector;
        this.event = event;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
