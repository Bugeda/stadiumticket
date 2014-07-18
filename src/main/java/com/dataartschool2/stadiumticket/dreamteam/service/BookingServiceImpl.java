package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.dao.BookingDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        	Seat seat = booking.getSeat();            
            Event event = seat.getTicket().getEvent();           
            Sector sector = seat.getSector();

            if(event.getId().equals(eventId) && sector.getId().equals(sectorId)){
                result.add(booking);
            }
        }
        return result;
    }

    @Scheduled(fixedDelay = 300000) // 5 minutes
    public void cancelBooking(){
        List<Booking> bookings = bookingDAO.findAll();
        for(Booking booking : bookings){
            if(booking.getBookingStatus().equals(BookingStatus.Booked)){
                cancelBookingIfNeeded(booking);

            }
        }
    }

    private void cancelBookingIfNeeded(Booking booking) {
        Event event = booking.getSeat().getTicket().getEvent();
        Date startDate = event.getEventDate();

        int minutes = event.getBookingCanceltime();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, minutes);
        Date fromNow = calendar.getTime();

        if(startDate.before(fromNow)){
            booking.setBookingStatus(BookingStatus.BookingCancelled);
            bookingDAO.updateEntity(booking);
        }
    }
    
    @Override
    public Booking createEmptyBooking() {
        Booking booking= new Booking();
        booking.setBookingStatus(BookingStatus.Free);
        booking.setCustomer(new Customer());
        booking.setSeat(null);        
        return booking;
    }
    
    @Override
    public List<Booking> createEmptyBookingSetForCustomer(Customer customer) {
        Booking booking= new Booking();
        booking.setBookingStatus(BookingStatus.Free);
        booking.setCustomer(customer);
        booking.setSeat(null);
        
        List<Booking> bookingSet= new ArrayList<Booking>();
        bookingSet.add(booking);
        return bookingSet;
    }
}
