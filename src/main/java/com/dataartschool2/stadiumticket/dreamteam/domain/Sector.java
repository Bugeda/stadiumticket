package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * Created by dshchelkonogov on 14.06.2014.
 */
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
}
