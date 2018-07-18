package com.example.tadje.myapplication.writer;

import android.support.design.widget.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tadje on 26.03.2018.
 */

public interface IHolidayWriter {

    void write(String editDateStr, String editNameStr);

    void delete(ArrayList<Integer> positionList, FloatingActionButton deleteButton);
}

