package com.example.tadje.myapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tadje on 29.03.2018.
 */

@Entity(tableName = "holiday")
public class Holiday {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="date")
    private String date;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="place")
    private String place;


    public Holiday(String date, String name, String place){
        this.date = date;
        this.name = name;
        this.place=place;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
