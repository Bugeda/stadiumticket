package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sector {
    @Id
    @GeneratedValue
    private int id;

    private String number;

    private int seatsQuantity;

    public Sector(){}

    public Sector(int id, String number, int seatsQuantity) {
        this.id = id;
        this.number = number;
        this.seatsQuantity = seatsQuantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getSeatsQuantity() {
        return seatsQuantity;
    }

    public void setSeatsQuantity(int seatsQuantity) {
        this.seatsQuantity = seatsQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sector sector = (Sector) o;

        if (seatsQuantity != sector.seatsQuantity) return false;
        if (!number.equals(sector.number)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number.hashCode();
        result = 31 * result + seatsQuantity;
        return result;
    }
}
