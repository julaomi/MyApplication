package com.example.tadje.myapplication.ui;

import android.test.AndroidTestCase;

import com.example.tadje.myapplication.model.Holiday;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by tadje on 26.04.2018.
 */

public class HolidayFragmentTest extends AndroidTestCase {

    @Test
    public void addHolidayTest(){

        HolidayFragment holidayFragment = new HolidayFragment();
        String getEditDateAndName = holidayFragment.addHoliday();
        Assert.assertNotNull(getEditDateAndName);
    }

    @Test
    public void getDataFromFileTest(){

        String fileNameValidTxt = "Braunschweig.txt";
        String fileNameValidJson = "Braunschweig.json";
        String fileNameInvalid = "fakefilename";

        HolidayFragment holidayFragment = new HolidayFragment();
        ArrayList<Holiday> arrayListTxt = holidayFragment.getDataFromFile(fileNameValidTxt);
        Assert.assertTrue("Array Holiday List ist nicht übereinstimmend", arrayListTxt.size() > 0);

        ArrayList<Holiday> arrayListJson = holidayFragment.getDataFromFile(fileNameValidJson);
        Assert.assertTrue("Array Holiday List ist nicht übereinstimmend", arrayListJson.size() > 0);

        ArrayList<Holiday> arrayListFalse = holidayFragment.getDataFromFile(fileNameInvalid);
        Assert.assertFalse("Array Holiday List ist nicht übereinstimmend", arrayListFalse.size() >
                0);
    }





}
