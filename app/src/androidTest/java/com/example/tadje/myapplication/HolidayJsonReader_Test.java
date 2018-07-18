package com.example.tadje.myapplication;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.JsonReader;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.model.Holiday;
import com.example.tadje.myapplication.reader.HolidayJsonReader;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tadje on 25.04.2018.
 */

public class HolidayJsonReader_Test extends AndroidTestCase {
    String fileNameValid = "Braunschweig_2018.json";
    String fileNameInvalid = "dasdsa";
    String fileNameInValTyp = "Braunschweig.txt";


    @Test
    public void testHolidayListFromFileExist() {
        Context context = getContext();

        //Initialization of the database otherwise the app crashes
        AppDatabase.getInstance(context);
        HolidayJsonReader reader = new HolidayJsonReader();
        ArrayList<Holiday> holidayListValid = reader.getHolidays(fileNameValid);
        Assert.assertTrue(holidayListValid.size() > 0);
        ArrayList<Holiday> holidayListInvalid=reader.getHolidays(fileNameInvalid);
        Assert.assertFalse(holidayListInvalid.size() >0);
        ArrayList<Holiday> holidayListInvalidTyp = reader.getHolidays(fileNameInValTyp);
        Assert.assertFalse(holidayListInvalidTyp.size()>0);
    }

}


