package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;

public interface SectorPriceDAO extends GenericDAO<SectorPrice>{

	List<SectorPrice> getPricesSectorsOfEvent(Event ev);

}
