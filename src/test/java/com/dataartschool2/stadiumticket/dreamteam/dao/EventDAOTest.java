package com.dataartschool2.stadiumticket.dreamteam.dao;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml"})
@Transactional
public class EventDAOTest{

	@Autowired
    private EventDAO eventDAO;

	
	@Test
    public void findByIdTest() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = sdf.parse("2014-05-16 17:00:00");
		Timestamp stamp = new Timestamp(d.getTime());		

        Event expected = new Event(100, "new test event", stamp, 30, new ArrayList<SectorPrice>());
        eventDAO.updateEntity(new Event(100, "new test event", stamp, 30, new ArrayList<SectorPrice>()));
        Event actual = eventDAO.findById(100);
        assertNotNull(actual);
        assertEquals(expected, actual);             
	}
		   
    @Test
    public void entityCreateReadTest(){
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
        //create    	
    	Event expected = new Event(15, "1", stamp, 30, new ArrayList<SectorPrice>());
    	eventDAO.updateEntity(new Event(15, "1", stamp, 30, new ArrayList<SectorPrice>()));
        //read
    	Event actual = eventDAO.findById(15);     	
    	assertEquals(actual,expected);
    	assertNotNull(actual);
    }
    
    @Test
    public void entityUpdateTest(){
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
  
        stamp.setYear(10);
        stamp.setMonth(8);
        stamp.setDate(25);
        Event ev = new Event(15, "newname", stamp,30, new ArrayList<SectorPrice>());
    	eventDAO.updateEntity(ev);    	
        Event actual = eventDAO.findById(15);	
  
        assertEquals(actual,ev);
        assertNotNull(actual);     
    } 
    
    @Test
    public void entityDeleteTest(){
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
    	Event ev = new Event(15, "newname", stamp,30, new ArrayList<SectorPrice>());
    	eventDAO.updateEntity(ev);    	
    	eventDAO.deleteEntity(ev);
        Event actual = eventDAO.findById(15);	
        assertNull(actual);   
    }    
    
  /*  @Test
    public void entityListTest() throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Event ev1=new Event(1, "Черноморец - Карпаты", new Timestamp(sdf.parse("2014-05-18 15:00:00").getTime()), 30, new ArrayList<SectorPrice>());
    	Event ev2=new Event(2, "Говерла - Черноморец", new Timestamp(sdf.parse("2014-05-20 16:00:00").getTime()), 30, new ArrayList<SectorPrice>());
    	Event ev3=new Event(3, "Черноморец - Карпаты", new Timestamp(sdf.parse("2014-05-23 11:00:00").getTime()), 30, new ArrayList<SectorPrice>());
    	Event ev4=new Event(4, "Говерла - Черноморец", new Timestamp(sdf.parse("2014-05-23 19:00:00").getTime()), 30, new ArrayList<SectorPrice>());
    	Event ev5=new Event(5, "Черноморец - Карпаты", new Timestamp(sdf.parse("2014-05-16 17:00:00").getTime()), 30, new ArrayList<SectorPrice>());
    	Event ev6=new Event(6, "Шахтер - Волынь", new Timestamp(sdf.parse("2014-05-18 14:00:00").getTime()), 30, new ArrayList<SectorPrice>());
    	Event ev7=new Event(7, "Металлист - Карпаты", new Timestamp(sdf.parse("2014-05-18 12:00:00").getTime()), 30, new ArrayList<SectorPrice>());
    	Event ev8=new Event(8, "Говерла - Черноморец", new Timestamp(sdf.parse("2014-05-20 14:00:00").getTime()), 30, new ArrayList<SectorPrice>());
    	List<Event> expecteds = Arrays.asList(ev1,ev2,ev3,ev4,ev5,ev6,ev7,ev8);
    			
        List<Event> actuals = eventDAO.findAll();
        int cnt=eventDAO.countAll();
      //  assertEquals(8, cnt);
        assertEquals(ev1, actuals.get(0));
        assertEquals(ev2, actuals.get(1));
        assertEquals(ev3, actuals.get(2));
        assertEquals(ev4, actuals.get(3));
        assertEquals(ev5, actuals.get(4));
        assertEquals(ev6, actuals.get(5));
        assertEquals(ev7, actuals.get(6));
        assertEquals(ev8, actuals.get(7));
	}  */
}
