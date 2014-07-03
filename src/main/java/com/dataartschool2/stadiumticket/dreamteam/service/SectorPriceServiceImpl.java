package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.dao.EventDAO;
import com.dataartschool2.stadiumticket.dreamteam.dao.SectorPriceDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;

@Service
public class SectorPriceServiceImpl implements SectorPriceService {
	
	@Autowired
	private SectorPriceDAO sectorPriceDAO;
	
	
	@Override
	@Transactional
	public void updateSectorPrice(SectorPrice SectorPrice){
		sectorPriceDAO.updateEntity(SectorPrice);
	}


	@Override
	@Transactional
	public List<SectorPrice> getPricesSectorsOfEvent(Event ev) {
		return sectorPriceDAO.getPricesSectorsOfEvent(ev) ;
	}
	
}
