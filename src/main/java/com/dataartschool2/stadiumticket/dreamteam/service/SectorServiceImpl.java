package com.dataartschool2.stadiumticket.dreamteam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.dao.SectorDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;

@Service
public class SectorServiceImpl implements SectorService {
	
	@Autowired
	private SectorDAO sectorDAO;

	@Override
	@Transactional
	public void updateSector(Sector sector){
		sectorDAO.updateEntity(sector);
	}
	
	@Override
	@Transactional
	public Sector findById(Integer id){
		return sectorDAO.findById(id);	 
	}

	
}
