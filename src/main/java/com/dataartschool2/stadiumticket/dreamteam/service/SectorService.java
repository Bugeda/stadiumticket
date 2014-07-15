package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorStatus;


public interface SectorService {

    SectorStatus getSectorStatus(Integer eventId, Integer sectorId);

    public void updateSector(Sector sector);

	public Sector findById(Integer id);

}
