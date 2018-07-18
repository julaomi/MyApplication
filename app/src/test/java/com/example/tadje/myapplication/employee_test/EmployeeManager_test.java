package com.example.tadje.myapplication.employee_test;

import com.example.tadje.myapplication.employee.EmployeeManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tadje on 24.04.2018.
 */

public class EmployeeManager_test {

    @Before
    public void setup() {
    }

    @Test
    public void getEmployeeManagerSingltons () {

        EmployeeManager.getInstance().setEmployeePosition(2);
        Assert.assertTrue(EmployeeManager.getInstance().getEmployeePosition()==2);

        EmployeeManager.getInstance().setEmployeeNumber(4);
        Assert.assertTrue(EmployeeManager.getInstance().getEmployeeNumber()==4);

    }


}
