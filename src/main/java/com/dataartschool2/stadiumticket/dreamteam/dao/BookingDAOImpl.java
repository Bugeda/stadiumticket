package com.dataartschool2.stadiumticket.dreamteam.dao;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.BookingStatus;
import com.dataartschool2.stadiumticket.dreamteam.domain.SeatStatus;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository  
@Transactional  
public class BookingDAOImpl extends GenericDAOImpl<Booking> implements BookingDAO {

	@Override
	public List<Booking> findByTicket(Ticket ticket) {
		Criterion criterion = Restrictions.eq("ticket", ticket);  
		return findByCriteria(criterion);
	}	
	
	@Override
	public List<Booking> findAllBooked() {
		Criterion criterion = Restrictions.eq("bookingStatus", BookingStatus.Booked);  
		return findByCriteria(criterion);
	}

	@Override
	public Boolean cancelBooking(Booking booking) {
	    return changeBookingState(booking,BookingStatus.BookingCancelled,SeatStatus.vacant);
	}

	@Override
	public Boolean cancelBookingInTime(Booking booking) {
	    return changeBookingState(booking,BookingStatus.BookingTimeIsOver,SeatStatus.vacant);
	}
	
	@Override
	public Boolean sellBooking(Booking booking) {
	    return changeBookingState(booking,BookingStatus.BookingRedeemed,SeatStatus.occupied);
	}

	private Boolean changeBookingState(Booking booking, BookingStatus bookingStatus,SeatStatus seatStatus){
		BookingStatus backBookingStatus = booking.getBookingStatus();
		SeatStatus backSeatStatus = booking.getTicket().getSeatStatus();
		booking.setBookingStatus(bookingStatus);
		booking.getTicket().setSeatStatus(seatStatus);
		updateEntity(booking);
		return true;
	}


	

}