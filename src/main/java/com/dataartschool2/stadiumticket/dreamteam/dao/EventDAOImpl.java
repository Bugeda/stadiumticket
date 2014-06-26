package com.dataartschool2.stadiumticket.dreamteam.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;

@Repository  
@Transactional  
public class EventDAOImpl extends GenericDAOImpl<Event, Long> implements EventDAO {
    

}