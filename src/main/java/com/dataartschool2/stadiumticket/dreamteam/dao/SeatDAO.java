package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;

public interface SeatDAO extends GenericDAO<Seat>{

	List<Seat> findBySeat(int rowNumber, int seatNumber, Sector sector);

}
