package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;

@Repository  
@Transactional  
public class SeatDAOImpl extends GenericDAOImpl<Seat> implements SeatDAO {

	@Override
	public List<Seat> findBySeat(int rowNumber, int seatNumber, Sector sector) {
		Criterion criterion1 = Restrictions.eq("rowNumber", rowNumber);   
		Criterion criterion2 = Restrictions.eq("seatNumber", seatNumber);
		Criterion criterion3 = Restrictions.eq("sector", sector);	
		return findByCriteria(0,1,null,criterion1, criterion2,criterion3);
	}
    

}