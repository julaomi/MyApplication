 package com.example.tadje.myapplication.holidays_tests;

import android.test.AndroidTestCase;

import com.example.tadje.myapplication.holidays.HolidayManager;
import com.example.tadje.myapplication.model.Holiday;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by tadje on 24.04.2018.
 */

public class HolidayManager_test extends AndroidTestCase {

    String fileNameValid = "Braunschweig_2018.txt";
    String fileNameInvalid= "dasdsa";

    int listFromValid = 1;

    @Test
    public void getHolidayManager(){

        ArrayList<Holiday> arrayList = new ArrayList<>();
        Holiday holiday=new Holiday("11.11.2011","Lulu","Honnover");

        arrayList.add(holiday);
        HolidayManager.getInstance().setHolidayList(arrayList);

        Assert.assertEquals("Die ArrayList stimmt nicht überein",arrayList, HolidayManager
                .getInstance().getHolidayList());



        HolidayManager.getInstance().setFileName(fileNameValid);
        Assert.assertEquals("Der Filename stimmt nicht überein", fileNameValid, HolidayManager
                .getInstance().getFileName());

        HolidayManager.getInstance().setFileName(fileNameInvalid);
        Assert.assertEquals("Der Filename stimmt nicht überein", fileNameInvalid, HolidayManager
                .getInstance().getFileName());



        HolidayManager.getInstance().setListFrom(listFromValid);
        Assert.assertEquals("Die Nummer der Listenposition stimmt nicht überein", listFromValid,
                HolidayManager.getInstance().getListFrom());

    }
}

