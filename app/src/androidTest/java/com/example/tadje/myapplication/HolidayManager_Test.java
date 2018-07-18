package com.example.tadje.myapplication;

import android.content.Context;
import android.test.AndroidTestCase;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.holidays.HolidayManager;
import com.example.tadje.myapplication.model.Holiday;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by tadje on 26.04.2018.
 */

public class HolidayManager_Test extends AndroidTestCase {
    @Test
    public void getHolidayManager(){
        Context context = getContext();
        //Initialization of the database otherwise the app crashes
        AppDatabase.getInstance(context);
        ArrayList<Holiday> arrayList = new ArrayList<>();
        Holiday holiday=new Holiday("11.11.2011","Lulu","Hannover");
        arrayList.add(holiday);
        HolidayManager.getInstance().setHolidayList(arrayList);

        Assert.assertEquals("Die ArrayList stimmt nicht überein",arrayList, HolidayManager
                .getInstance().getHolidayList());


    }
}
