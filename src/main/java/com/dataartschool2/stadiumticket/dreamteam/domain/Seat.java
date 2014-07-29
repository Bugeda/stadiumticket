package com.dataartschool2.stadiumticket.dreamteam.domain;

import javax.persistence.*;


@Entity
public class Seat {
    @Id
    @GeneratedValue
    private int id;

    private int seatNumber;

    private int rowNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    private Sector sector;

    public Seat(){}

    public Seat(int id, int seatNumber, int rowNumber, Sector sector) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.sector = sector;
    }

    
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rowNumber;
		result = prime * result + seatNumber;
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Seat))
			return false;
		Seat other = (Seat) obj;
		if (rowNumber != other.rowNumber)
			return false;
		if (seatNumber != other.seatNumber)
			return false;
		if (sector == null) {
			if (other.sector != null)
				return false;
		} else if (!sector.equals(other.sector))
			return false;
		return true;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

}
