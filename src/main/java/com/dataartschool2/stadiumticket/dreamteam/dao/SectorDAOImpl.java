package com.dataartschool2.stadiumticket.dreamteam.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;

@Repository  
@Transactional  
public class SectorDAOImpl extends GenericDAOImpl<Sector, Long> implements SectorDAO  {
    

}