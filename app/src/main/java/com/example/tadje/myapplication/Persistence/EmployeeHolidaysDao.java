package com.example.tadje.myapplication.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tadje.myapplication.model.EmployeeHoliday;
import com.example.tadje.myapplication.model.Holiday;

import java.util.List;

/**
 * Created by tadje on 18.04.2018.
 */

@Dao
public interface EmployeeHolidaysDao {

    @Query("SELECT * FROM employeeholiday")
    List<EmployeeHoliday> getAll();

    @Query("SELECT holidaydate FROM employeeholiday WHERE empNumb IN (:empNumb)")
    List<String> getHolidaydate(long empNumb);

    @Query("SELECT * FROM employeeholiday WHERE empNumb LIKE :empNumb")
    List <EmployeeHoliday> findByEmpNumb(long empNumb);


    @Query("SELECT * FROM employeeholiday WHERE holidaydate LIKE :holidaydate")
    EmployeeHoliday findByName(String holidaydate);

    @Query("SELECT empNumb FROM employeeholiday")
    long[] getFK();


    @Insert
    void insertAll(EmployeeHoliday... employeeHolidays);

    @Update
    void update(EmployeeHoliday... employeeHolidays);

    @Delete
    void delete(EmployeeHoliday employeeHoliday);
}
