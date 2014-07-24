package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;

public interface BookingDAO extends GenericDAO<Booking>{

	public List<Booking> findByTicket(Ticket ticket);
	
	public List<Booking> findAllBooked();
	
	public Boolean cancelBookingInTime(Booking booking);
	
	public Boolean cancelBooking(Booking booking);

	public Boolean sellBooking(Booking findById);
}
