package com.example.tadje.myapplication.model_test;

import com.example.tadje.myapplication.model.Calc;
import com.example.tadje.myapplication.model.Holiday;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tadje on 24.04.2018.
 */

public class Holiday_test {


    @Before
    public void setup() {
    }

    @Test
    public void testGetFromHoliday() {
        Holiday holiday = new Holiday("22.01.2022", "Feiertagi", "Braunschweig");

        holiday.setDate("22.01.2022");
        Assert.assertTrue(holiday.getDate()=="22.01.2022");

        holiday.setName("Feiertagi");
        Assert.assertTrue(holiday.getName()=="Feiertagi");

        holiday.setPlace("Braunschweig");
        Assert.assertTrue(holiday.getPlace()=="Braunschweig");

        holiday.setId(1);
        Assert.assertTrue(holiday.getId()==1);

        Assert.assertEquals("Das von-Datum stimmt nicht überein", "22.01.2022", holiday.getDate
                ());
        Assert.assertEquals("Der Feiertagsname stimmt nicht überein", "Feiertagi", holiday
                .getName());
        Assert.assertEquals("Der Ort stimmt nicht überein", "Braunschweig", holiday.getPlace());
    }

}
