package com.example.tadje.myapplication.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.myapplication.model.Holiday;

import java.util.List;

/**
 * Created by tadje on 11.04.2018.
 */

@Dao
public interface HolidaysDao {
    @Query("SELECT * FROM holiday")
    List<Holiday> getAll();

    @Query("SELECT * FROM holiday WHERE id IN (:holidayIds)")
    List<Holiday> loadAllByIds(int[] holidayIds);

    @Query("SELECT * FROM holiday WHERE id LIKE (:id)")
    Holiday loadAllById(int id);


    @Query("SELECT * FROM holiday WHERE date LIKE :date AND " + "name LIKE " +
            ":name" +
            " AND " +
            "place" +
            " LIKE :place")
    Holiday findByName(String date, String name, String place);

    @Query("SELECT * FROM holiday WHERE place LIKE :place")
    Holiday findByPlace(String place);


    @Insert
    void insertAll(Holiday... holidays);


    @Delete
    void delete(Holiday holidays);
}