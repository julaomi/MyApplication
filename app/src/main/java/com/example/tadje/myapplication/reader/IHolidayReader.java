package com.example.tadje.myapplication.reader;

import com.example.tadje.myapplication.model.Holiday;

import java.util.ArrayList;

/**
 * Created by tadje on 01.03.2018.
 */

public interface IHolidayReader {

    ArrayList<Holiday> getHolidays(String fileName);

}
