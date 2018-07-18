package com.example.tadje.myapplication.model_test;

import com.example.tadje.myapplication.model.Employee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tadje on 24.04.2018.
 */

public class Employee_test {

    @Before
    public void setup() {
    }

    @Test
    public void testGetFromEmployee() {
        byte[] image = new byte[0];
        Employee employee = new Employee(256L, "Test", "Herbert", 40.0d, "Pustekuchen", image);


        employee.setEmpNumb(256L);
        Assert.assertTrue(employee.getEmpNumb()==256);

        employee.setLastname("Test");
        Assert.assertTrue(employee.getLastname()=="Test");

        employee.setFirstname("Herbert");
        Assert.assertTrue(employee.getFirstname()=="Herbert");

        employee.setWorkingTime(40.0d);
        Assert.assertTrue(employee.getWorkingTime()==40.0);

        employee.setRole("Pustekuchen");
        Assert.assertTrue(employee.getRole()=="Pustekuchen");

        employee.setImage(image);
        Assert.assertTrue(employee.getImage()==image);

        Assert.assertEquals("Die EmployeeNumber stimmt nicht überein!", 256L, employee
                .getEmpNumb());
        Assert.assertEquals("Der Nachname stimmt nicht überein", "Test",employee.getLastname());
        Assert.assertEquals("Der Vorname stimmt nicht überein", "Herbert",employee.getFirstname());
        Assert.assertEquals("Die Arbeitsstunden stimmen nicht überein", 40.0, employee
                .getWorkingTime(), 0.001);
        Assert.assertEquals("Die Rolle stimmt nicht überein", "Pustekuchen", employee.getRole());
        Assert.assertEquals("Das Bild stimmt nicht überein", image, employee.getImage());
    }
}
