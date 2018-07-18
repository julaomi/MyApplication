package com.example.tadje.myapplication.model_test;

import com.example.tadje.myapplication.model.Calc;
import com.example.tadje.myapplication.model.EmployeeHoliday;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tadje on 24.04.2018.
 */

public class EmployeeHoliday_test {

    @Before
    public void setup() {
    }

    @Test
    public void testGetFromEmployeeHoliday() {
        EmployeeHoliday employeeHoliday=new EmployeeHoliday(1111, "11.05.2020");

        employeeHoliday.setEmpNumb(1111);
        Assert.assertTrue(employeeHoliday.getEmpNumb()==1111);

        employeeHoliday.setHolidaydate("11.05.2020");
        Assert.assertTrue(employeeHoliday.getHolidaydate()=="11.05.2020");

        employeeHoliday.setId(1);
        Assert.assertTrue(employeeHoliday.getId()==1);


        Assert.assertEquals("Die EmployeeNumber stimmt nicht überein", 1111, employeeHoliday.getEmpNumb());
        Assert.assertEquals("Das Datum stimmt nicht überein", "11.05.2020", employeeHoliday
                .getHolidaydate());

    }
}
