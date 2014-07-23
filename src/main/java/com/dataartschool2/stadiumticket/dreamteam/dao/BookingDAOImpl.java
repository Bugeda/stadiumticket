package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;

@Repository  
@Transactional  
public class BookingDAOImpl extends GenericDAOImpl<Booking> implements BookingDAO {

	@Override
	public Boolean findBySeat(Seat seat) {
		Criterion criterion = Restrictions.eq("seat", seat);   
		List<Booking> bookingSet=findByCriteria(criterion);
		return (bookingSet.equals(null));
	}

	@Override
	public List<Booking> findByTicket(Ticket ticket) {
		//System.out.println(ticketId);
		Criterion criterion = Restrictions.eq("ticket", ticket);  
		return findByCriteria(criterion);
	}

	

}