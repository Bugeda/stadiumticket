package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.dao.BookingDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDAO bookingDAO;

    @Override
    public List<Booking> getBookingsForEventInSector(Integer eventId, Integer sectorId) {
        List<Booking> bookings = bookingDAO.findAll();
        List<Booking> result = new ArrayList<Booking>();

        for(Booking booking : bookings){
            Ticket ticket = booking.getTicket();
            Event event = ticket.getEvent();
            Seat seat = ticket.getSeat();
            Sector sector = seat.getSector();

            if(event.getId().equals(eventId) && sector.getId().equals(sectorId)){
                result.add(booking);
            }
        }
        return result;
    }
}
