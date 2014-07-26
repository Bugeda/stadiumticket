package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SeatStatus;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;

@Repository  
@Transactional  
public class TicketDAOImpl extends GenericDAOImpl<Ticket> implements TicketDAO {

	@Override
	public List<Ticket> findByEvent(Event event) {
		Criterion criterion = Restrictions.eq("event", event);
		return findByCriteria(criterion);
	}

	@Override
	public List<Ticket> findByNumber(String ticketNumber) {
		Criterion criterion = Restrictions.eq("ticketNumber", ticketNumber);
		return  findByCriteria(criterion);
	}

	@Override
	public List<Ticket> findSoldTickets() {
		Criterion criterion = Restrictions.eq("seatStatus", SeatStatus.occupied);
		return findByCriteria(criterion);
	}

	@Override
	public List<Ticket> findBookedTickets() {
		Criterion criterion = Restrictions.eq("seatStatus", SeatStatus.booked);
		return findByCriteria(criterion);
	}
}	