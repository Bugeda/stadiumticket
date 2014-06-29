package com.dataartschool2.stadiumticket.dreamteam.dao;

import com.dataartschool2.stadiumticket.dreamteam.dao.EventDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* Created by on 24.06.14.
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml"})
@Transactional
public class EventDAOTest{

	@Autowired
    private EventDAO eventDAO;
    

    @Test
    public void entitySaveTest(){
    	Date date = new Date();
        Event expected = new Event(15, "1", date);
        eventDAO.makePersistent(expected);

        Event actual = eventDAO.findById(15);
        Assert.assertEquals(expected, actual);
    }
}