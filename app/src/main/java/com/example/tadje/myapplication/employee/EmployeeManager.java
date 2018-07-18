package com.example.tadje.myapplication.employee;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.holidays.HolidayManager;
import com.example.tadje.myapplication.model.Employee;
import com.example.tadje.myapplication.model.Holiday;

import java.util.ArrayList;

/**
 * Created by tadje on 16.04.2018.
 */

public class EmployeeManager {

        private static EmployeeManager instance = null;

        private long employeeNumber;
        private int employeePosition;

        private EmployeeManager() {

        }
        public static EmployeeManager getInstance() {

            if (instance == null){
                instance = new EmployeeManager();
            }

            return instance;
        }


    public long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public int getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(int employeePosition) {
        this.employeePosition = employeePosition;
    }
}
