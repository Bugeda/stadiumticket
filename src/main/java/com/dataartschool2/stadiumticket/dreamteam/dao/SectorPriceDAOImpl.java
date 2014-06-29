package com.dataartschool2.stadiumticket.dreamteam.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;

@Repository  
@Transactional  
public class SectorPriceDAOImpl extends GenericDAOImpl<SectorPrice> implements SectorPriceDAO {
    

}