package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.dao.BookingDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
        	Seat seat = booking.getSeat().getTicket().getSeat();            
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
    
	@Override
	public boolean BookingSeat(Seat seat) {
		
		return bookingDAO.findBySeat(seat);
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

    public List<Booking> getBookingsForEvent(Integer id){    	
    	List<Booking> bookings = bookingDAO.findAll();
    	List<Booking> result = new ArrayList<Booking>();
		return bookings;
    }



    
}
