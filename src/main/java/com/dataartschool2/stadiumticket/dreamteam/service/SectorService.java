package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;


public interface SectorService {

	public void updateSector(Sector sector);

	public void deleteSector(Sector sector);

	public Sector findById(Integer id);

}
