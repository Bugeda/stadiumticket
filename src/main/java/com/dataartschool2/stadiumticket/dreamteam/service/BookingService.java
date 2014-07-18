package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    List<Booking> getBookingsForEventInSector(Integer eventId, Integer sectorId);

	Booking createEmptyBooking();

	List<Booking> createEmptyBookingSetForCustomer(Customer customer);
}
