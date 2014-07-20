package com.dataartschool2.stadiumticket.dreamteam.domain;


public enum SeatStatus {
    Free(0), Booked(1), Sold(2);
    private int code;
    SeatStatus(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
