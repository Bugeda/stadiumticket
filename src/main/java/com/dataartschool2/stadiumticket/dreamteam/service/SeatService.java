package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;


public interface SeatService {
	
	public List<Seat> modifySeatSet(int size, List<Seat> seatSet, List<Sector> sectorSet, List<Ticket> ticketSet);

	public void updateSeat(Seat seat);
}
