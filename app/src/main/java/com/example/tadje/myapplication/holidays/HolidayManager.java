package com.example.tadje.myapplication.holidays;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.model.Holiday;

import java.util.ArrayList;

/**
 * Created by tadje on 06.04.2018.
 */

public class HolidayManager {
    
    private static HolidayManager instance = null;

    private String fileName = "Berlin_2018.json";
    private ArrayList<Holiday> holidayList= (ArrayList<Holiday>) AppDatabase.getInstance()
            .holidaysDao().getAll();
    private int listFrom;

    private HolidayManager() {

    }
    public static  HolidayManager getInstance() {

        if (instance == null){
            instance = new HolidayManager();
        }

         return instance;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setHolidayList(ArrayList<Holiday> holidayList){
        this.holidayList=holidayList;
    }

    public ArrayList<Holiday> getHolidayList() {
        return holidayList;
    }


    public int getListFrom() {
        return listFrom;
    }

    public void setListFrom(int listFrom) {
        this.listFrom = listFrom;
    }
}

