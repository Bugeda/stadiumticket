package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorStatus;


public interface SectorService {

	public SectorStatus getSectorStatus(Integer eventId, Integer sectorId);

    public void updateSector(Sector sector);

	public Sector findById(Integer id);

	public List<Sector> findAll();

	public List<Sector> createSectorsListFromNums(List<Integer> listSectoriD);

}
