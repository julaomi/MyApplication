package com.example.tadje.myapplication.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.myapplication.model.Calc;

import java.util.List;

/**
 * Created by tadje on 10.04.2018.
 */
@Dao
public interface CalcDao {
    @Query("SELECT * FROM calc")
    List<Calc> getAll();

    @Query("SELECT * FROM calc WHERE id IN (:calcIds)")
    List<Calc> loadAllByIds(int[] calcIds);

    @Query("SELECT * FROM calc WHERE fromDate LIKE :fromDate AND " + " toDate LIKE :toDate AND "+
            "interval " +
            "LIKE" +
            " :interval")
    Calc findByName(String fromDate, String toDate, double interval );


    @Insert
    void insertAll(Calc... calcs);

    @Delete
    void delete(Calc calc);


}

