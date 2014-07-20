package com.dataartschool2.stadiumticket.dreamteam.domain;


public enum SeatStatus {
    vacant(0), booked(1), occupied(2);
    private int code;
    SeatStatus(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
