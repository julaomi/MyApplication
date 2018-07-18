package com.example.tadje.myapplication.reader;

import android.os.Environment;
import android.util.JsonReader;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.model.Holiday;
import com.example.tadje.myapplication.holidays.HolidayManager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tadje on 01.03.2018.
 */

public class HolidayJsonReader implements IHolidayReader {

    public String date = null;
    public String holidayname = null;
    private ArrayList<Holiday> holidayList = new ArrayList<>();

    @Override
    public ArrayList<Holiday> getHolidays(String fileName) {
        holidayList.clear();

        String stream = Environment.getExternalStorageDirectory() + File.separator + "/HolidayData/" + fileName;
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(new FileInputStream(stream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Creates a DataInputStream that uses the specified underlying FileInputStream with the
        // specified path.

        JsonReader reader = new JsonReader(new InputStreamReader(dataInputStream));


        try {
            holidayList = readHolidayArray(reader);

            reader.close();
        } catch (IOException io) {
            io.printStackTrace();
        }

        return holidayList;
    }

    //read the arrays and returns the Array with Objects as Array-List to holidayList
    public ArrayList<Holiday> readHolidayArray(JsonReader reader) {
        ArrayList<Holiday> holidayList = new ArrayList<>();

        try {
            reader.beginArray();
            while (reader.hasNext()) {
                Holiday holiday = readHoliday(reader);
                holidayList.add(holiday);
            }
            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return holidayList;
    }

    //read the objects and returns the object to readHolidayArray
    public Holiday readHoliday(JsonReader reader) {
        String name = null;

        try {
            reader.beginObject();
            name = reader.nextName();
            if (name.equals("date")) {
                date = reader.nextString();
            }
            name = reader.nextName();
            if (name.equals("holidayname")) {
                holidayname = reader.nextString();
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String place = HolidayManager.getInstance().getFileName();
        final Holiday holiday = new Holiday(date, holidayname, place);

          if (!isDuplicate(holiday)) {
            addHoliday(holiday);
          }

        return holiday;
    }

    private boolean isDuplicate(Holiday newHoliday) {
        List<Holiday> databaseHolidays= AppDatabase.getInstance().holidaysDao().getAll();

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

