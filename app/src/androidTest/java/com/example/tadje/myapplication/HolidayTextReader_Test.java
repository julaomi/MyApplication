package com.example.tadje.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.test.AndroidTestCase;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.model.Holiday;
import com.example.tadje.myapplication.reader.HolidayJsonReader;
import com.example.tadje.myapplication.reader.HolidayTextReader;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tadje on 25.04.2018.
 */

public class HolidayTextReader_Test extends AndroidTestCase {
    String fileNameValid = "Braunschweig_2018.txt";
    String fileNameInvalid= "dasdsa";
    String fileNameInValTyp = "Braunschweig.txt";


    @Test
    public void testHolidayListFromFileExistTxT() {
        Context context = getContext();
        //Initialization of the database otherwise the app crashes
        AppDatabase.getInstance(context);
        HolidayTextReader reader = new HolidayTextReader();
        ArrayList<Holiday> holidayListTestValid = reader.getHolidays(fileNameValid);
        Assert.assertTrue(holidayListTestValid.size() > 0);
        ArrayList<Holiday> holidayListTestInValid = reader.getHolidays(fileNameInvalid);
        Assert.assertFalse(holidayListTestInValid.size() > 0);
        ArrayList<Holiday> holidayListInvalidTyp = reader.getHolidays(fileNameInValTyp);
        Assert.assertFalse(holidayListInvalidTyp.size()>0);
    }



    @Test
    public void createLinesTest(BufferedReader holidayStr) throws IOException {
        HolidayTextReader reader = new HolidayTextReader();
        ArrayList<Holiday> holidayArrayList = reader.createLines(holidayStr);
        Holiday holiday = holidayArrayList.get(1);
        Assert.assertNotNull("Kein Feiertag bekommen", holiday );
        Assert.assertEquals((AppDatabase.getInstance().holidaysDao().loadAllById(1)), holiday);
        }
    }

