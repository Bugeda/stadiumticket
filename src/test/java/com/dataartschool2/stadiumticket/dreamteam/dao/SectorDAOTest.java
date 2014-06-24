package com.dataartschool2.stadiumticket.dreamteam.dao;

import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by on 24.06.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/data.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class SectorDAOTest{

    @Resource(name="sectorDAOImpl")
    private SectorDAO sectorDAO;

    @Test
    public void entitySaveTest(){
        Sector expected = new Sector(1, "1", 2);
        sectorDAO.save(expected);

        Sector actual = sectorDAO.findById(1);

        Assert.assertEquals(expected, actual);
    }
}
