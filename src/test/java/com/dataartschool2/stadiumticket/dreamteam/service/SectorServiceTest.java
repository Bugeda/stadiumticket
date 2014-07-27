package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 27.07.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml"})
@Transactional
public class SectorServiceTest {

    @Autowired
    private SectorService sectorService;

    @Test
    public void findAllTest(){

        List<Sector> actualSectors = sectorService.findAll();
        int actualSize = actualSectors.size();
        int expectedSize = 27;

        Assert.assertEquals(expectedSize, actualSize);


        int expectedGeneralSeatsQuantity = 1000;
        for(int i = 0; i < expectedSize - 2; ++i){

            Sector sector = actualSectors.get(i);
            Assert.assertEquals(Integer.toString(i + 1), sector.getName());

            int actualSeatsQuantity = sector.getSeatsQuantity();
            Assert.assertEquals(expectedGeneralSeatsQuantity, actualSeatsQuantity);
        }

        List<String> expectedVipNames = new ArrayList<String>();
        expectedVipNames.add("VIP A");
        expectedVipNames.add("VIP D");

        int expectedVipSeatsQuantity = 200;
        for(int i = 0; i < 2; ++i){

            Sector sector = actualSectors.get(expectedSize - 2 + i);
            Assert.assertEquals(expectedVipNames.get(i), sector.getName());

            int actualSeatsQuantity = sector.getSeatsQuantity();
            Assert.assertEquals(expectedVipSeatsQuantity, actualSeatsQuantity);

        }
    }
}
