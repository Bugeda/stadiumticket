package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sector {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer seatsQuantity;

    public Sector(){}

    public Sector(Integer id, String name, Integer seatsQuantity) {
        this.id = id;
        this.name = name;
        this.seatsQuantity = seatsQuantity;
    }
    
    

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Sector sector = (Sector) o;

        if (name != null ? !name.equals(sector.name) : sector.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
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

    public Integer getSeatsQuantity() {
        return seatsQuantity;
    }

    public void setSeatsQuantity(Integer seatsQuantity) {
        this.seatsQuantity = seatsQuantity;
    }
}
