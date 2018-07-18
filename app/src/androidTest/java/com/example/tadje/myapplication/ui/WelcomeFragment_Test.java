package com.example.tadje.myapplication.ui;

import android.content.Context;
import android.test.AndroidTestCase;

import com.example.tadje.myapplication.Persistence.AppDatabase;

import org.junit.Test;

/**
 * Created by tadje on 26.04.2018.
 */

public class WelcomeFragment_Test extends AndroidTestCase {


    public WelcomeFragment_Test(){
       Context context = getContext();

    //Initialization of the database otherwise the app crashes
        AppDatabase.getInstance(context);
}


}
