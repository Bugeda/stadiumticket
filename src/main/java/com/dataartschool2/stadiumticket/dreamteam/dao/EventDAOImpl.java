package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;

@Repository  
@Transactional  
public class EventDAOImpl extends GenericDAOImpl<Event> implements EventDAO {

	@Override
	public List<Event> findFutureEvents() {
		Date date= new Date();
		Criterion criterion = Restrictions.lt("eventDate", date);      
		List<Event> events=findByCriteria(criterion);
		return events;
	}
	
	@Override
	public List<Event> findPastEvents() {
		Date date= new Date();
		Criterion criterion = Restrictions.lt("eventDate", date);      		
		List<Event> events=findByCriteria(criterion); 
		return events;
	}

}