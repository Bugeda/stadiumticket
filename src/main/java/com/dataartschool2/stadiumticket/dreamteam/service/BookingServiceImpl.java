package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.dao.BookingDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDAO bookingDAO;
   
    @Autowired
    private EventService eventService;
    
    @Autowired
    private TicketService ticketService;

	@Scheduled(cron = "0 * * * * *") // every minute
    @Transactional
    public void cancelBooking(){
        System.out.println("Auto cancelling. Time: " + new Date());
        List<Booking> bookings = bookingDAO.findAllBooked();
        for(Booking booking : bookings){
                cancelBookingIfNeeded(booking);
        }
    }

    private void cancelBookingIfNeeded(Booking booking) {
        System.out.println("checking booking with id" + booking.getId());
        Event event = booking.getTicket().getEvent();
        Date startDate = event.getEventDate();

        int minutes = event.getBookingCancelTime();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, minutes);
        Date fromNow = calendar.getTime();
        System.out.println("Start date " + startDate);
        System.out.println("From now " + fromNow);
        if(startDate.before(fromNow)){
        	System.out.println("Canceling");
        	bookingDAO.cancelBookingInTime(booking);
        }
    }

    @Override
	@Transactional
	public void updateBooking(Booking booking) {
		bookingDAO.updateEntity(booking);
		
	}
	
    @Override
	@Transactional
	public List<Booking> getBookingsForEvent(Integer eventId){
		List<Booking> bookingSet = new ArrayList<Booking>();	
		List<Booking> bookingsAllSet = bookingDAO.findAllBooked();

		for(Booking booking : bookingsAllSet){
			if(booking.getTicket().getEvent().getId().equals(eventId)){
				bookingSet.add(booking);
				}
			}
		return bookingSet;
	}


    @Override
	@Transactional
	public Boolean[] cancelBookingSet(Integer[] ids) {
		Boolean[] result = new Boolean[ids.length];
		for (int i=0; i<ids.length; i++){
			result[i]=bookingDAO.cancelBooking(bookingDAO.findById(ids[i]));
		}
		return result;
	}


    @Override
	@Transactional
	public Boolean[] sellBookingSet(Integer[] ids) {
		Boolean[] result = new Boolean[ids.length];
		for (int i=0; i<ids.length; i++){
			result[i]=bookingDAO.sellBooking(bookingDAO.findById(ids[i]));
		}
		return result;
	}

    
}
