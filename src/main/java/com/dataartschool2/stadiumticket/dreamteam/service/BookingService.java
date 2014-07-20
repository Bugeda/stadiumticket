package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

	public List<Booking> getBookingsForEventInSector(Integer eventId, Integer sectorId);

	public List<Booking> getBookingsForEvent(Integer id);

	public boolean BookingSeat(Seat seat);

}
