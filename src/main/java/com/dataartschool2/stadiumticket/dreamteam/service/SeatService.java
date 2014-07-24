package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;


public interface SeatService {
	
	public List<Seat> modifySeatSet(int size, List<Seat> seatSet, List<Sector> sectorSet);

	public void updateSeat(Seat seat);
}
