package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.ArrayList;
import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;


public class SeatServiceImpl implements SeatService {
	
	@Override
	public List<Seat> modifySeatSet(int size, List<Seat> seatSet, List<Sector> sectorSet, List<Ticket> ticketSet){	
		if (seatSet.equals(null)){
			for (int i=1;i<=size;i++){
				seatSet.add(new Seat());
			}
		}		
		if (!sectorSet.equals(null)){
			for (int index=1; index<=size; index++)
			seatSet.get(index).setSector(sectorSet.get(index));
		}
		if (!sectorSet.equals(null)){
			for (int index=1; index<=size; index++)
			seatSet.get(index).setTicket(ticketSet.get(index));
		}
		return seatSet;
		
	}

}
