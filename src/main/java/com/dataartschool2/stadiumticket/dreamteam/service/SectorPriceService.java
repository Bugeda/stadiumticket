package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;


public interface SectorPriceService {

	void updateSectorPrice(SectorPrice SectorPrice);
	public List<SectorPrice> getPricesSectorsOfEvent(Event ev);
}
