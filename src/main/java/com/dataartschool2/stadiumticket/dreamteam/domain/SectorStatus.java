package com.dataartschool2.stadiumticket.dreamteam.domain;

import java.util.List;

public class SectorStatus {

    private String name;
    private List<List<SeatStatus>> rows;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<List<SeatStatus>> getRows() {
        return rows;
    }

    public void setRows(List<List<SeatStatus>> rows) {
        this.rows = rows;
    }

}
