package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

//	public List<Booking> getAllBookingsForEventInSector(Integer eventId, Integer sectorId);

	public void updateBooking(Booking booking);

	public List<Booking> getBookingsForEvent(Integer eventId);

	public Boolean[] cancelBookingSet(Integer[] ids);

	public Boolean[] sellBookingSet(Integer[] ids);

	public List<Booking> findLikeCustomerNameInEvent(Integer eventId, String customerName);

	

}
