package com.example.tadje.myapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by tadje on 10.04.2018.
 */

@Entity(tableName = "calc")
public class Calc {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "fromDate")
    private long fromDate;

    @ColumnInfo(name = "toDate")
    private long toDate;

    @ColumnInfo(name = "interval")
    private double interval;

    public Calc(long fromDate, long toDate, double interval) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.interval = interval;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
