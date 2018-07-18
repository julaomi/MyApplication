package com.example.tadje.myapplication.Persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.tadje.myapplication.model.Calc;
import com.example.tadje.myapplication.model.Employee;
import com.example.tadje.myapplication.model.EmployeeHoliday;
import com.example.tadje.myapplication.model.Holiday;

/**
 * Created by tadje on 10.04.2018.
 */

@Database(entities = {Calc.class, Holiday.class, Employee.class, EmployeeHoliday.class} ,  version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase Instance = null;

    public static AppDatabase getInstance() {
        if (Instance == null) {
            throw new IllegalStateException("AppDatabase not initialized yet.");
        }
        return Instance;
    }

    public static AppDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context,
                    AppDatabase.class, "calc_your_holidays.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }
    public abstract HolidaysDao holidaysDao();

    public abstract CalcDao CalcDao();

    public abstract EmployeeDao EmployeeDao();

    public abstract EmployeeHolidaysDao EmployeeHolidaysDao();
}
