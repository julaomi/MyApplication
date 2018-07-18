package com.example.tadje.myapplication.model_test;

import com.example.tadje.myapplication.model.Calc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tadje on 24.04.2018.
 */

public class Calc_test {


    @Before
        public void setup() {
        }

    @Test
        public void testGetFromCalc() {
        Calc calc=new Calc(0055, 4142, 0.0d);

        calc.setFromDate(0055);
        Assert.assertTrue(calc.getFromDate()==0055);

        calc.setToDate(4142);
        Assert.assertTrue(calc.getToDate()==4142);

        calc.setInterval(0.0d);
        Assert.assertTrue(calc.getInterval()==0.0);

        Assert.assertEquals("Das von-Datum stimmt nicht überein", 0055, calc.getFromDate());
        Assert.assertEquals("Das bis-Datum stimmt nicht überein", 4142, calc.getToDate());
        Assert.assertEquals("Das Interval stimmt nicht überein", 0.0, calc.getInterval(),0.001);

        calc.setId(1);
        Assert.assertTrue(calc.getId()==1);
    }


}
