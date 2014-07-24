package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.dao.BookingDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.*;

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
    
    @Override
	@Transactional
    public List<Booking> getAllBookingsForEventInSector(Integer eventId, Integer sectorId) {
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

	@Scheduled(fixedDelay = 60000) // 1 minute
    public void cancelBooking(){
        List<Booking> bookings = bookingDAO.findAllBooked();
        for(Booking booking : bookings){
                cancelBookingIfNeeded(booking);
        }
    }

    private void cancelBookingIfNeeded(Booking booking) {
        Event event = booking.getTicket().getEvent();
        Date startDate = event.getEventDate();

        int minutes = event.getBookingCanceltime();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, minutes);
        Date fromNow = calendar.getTime();

        if(startDate.before(fromNow)){
        	bookingDAO.cancelBookingInTime(booking);
            bookingDAO.updateEntity(booking);
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
