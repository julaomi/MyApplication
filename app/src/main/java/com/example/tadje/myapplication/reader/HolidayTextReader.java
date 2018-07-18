package com.example.tadje.myapplication.reader;

import android.os.Environment;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.model.Holiday;
import com.example.tadje.myapplication.holidays.HolidayManager;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tadje on 01.03.2018.
 */

public class HolidayTextReader implements IHolidayReader {

    private ArrayList<Holiday> holidayList = new ArrayList<>();

    @Override
    public ArrayList<Holiday> getHolidays(String fileName) {
        holidayList.clear();
        try {
            String stream = Environment.getExternalStorageDirectory() + File.separator + "/HolidayData/" + fileName;
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(stream));

            BufferedReader holidayStr = new BufferedReader(new InputStreamReader(dataInputStream));

            createLines(holidayStr);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return holidayList;
    }

    public ArrayList<Holiday> createLines(BufferedReader holidayStr) {

        String line;

        Holiday holiday = null;
        try {
            while ((line = holidayStr.readLine()) != null) {
                String[] splitLine = line.trim().split(";");
                String date = splitLine[0];
                String name = splitLine[1];
                String place = HolidayManager.getInstance().getFileName();
                holiday = new Holiday(date, name, place);

                if (!isDuplicate(holiday)) {
                    addHoliday(holiday);
                }
                holidayList.add(holiday);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return holidayList;
    }


    private boolean isDuplicate(Holiday newHoliday) {
        List<Holiday> databaseHolidays = AppDatabase.getInstance().holidaysDao().getAll();

        for (Holiday current : databaseHolidays) {
            if (newHoliday.getDate().equals(current.getDate()) &&
                    newHoliday.getName().equals(current.getName()) &&
                    newHoliday.getPlace().equals(current.getPlace())) {
                return true;
            }
        }
        return false;
    }

    private Holiday addHoliday(Holiday holiday) {

        AppDatabase.getInstance().holidaysDao().insertAll(holiday);

        return holiday;
    }
}


