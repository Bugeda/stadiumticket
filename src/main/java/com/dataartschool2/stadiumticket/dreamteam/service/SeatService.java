package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;


public interface SeatService {
	
	public void updateSeat(Seat seat);

	public List<Seat> findBySeat(Integer rowNumber, Integer seatNumber, Sector sector);

	public List<Seat> findByAll();

}
