package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;

@Repository  
@Transactional  
public class SectorPriceDAOImpl extends GenericDAOImpl<SectorPrice> implements SectorPriceDAO {

	@Override
	public List<SectorPrice> getPricesSectorsOfEvent(Event ev) {
		Criterion criterion = Restrictions.eq("event", ev);
		Order order=Order.asc("id");
		List<SectorPrice> sectorPrices=findByCriteria(0,0,true,order,criterion);
		return sectorPrices;
	}
    

}