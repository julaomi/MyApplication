package com.example.tadje.myapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tadje on 18.04.2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Employee.class, parentColumns = "empNumb",
        childColumns = "empNumb"), tableName = "employeeholiday")
public class EmployeeHoliday {

       @PrimaryKey (autoGenerate = true)
        private int id;

        @ColumnInfo(name="empNumb")
        private long empNumb;

        @ColumnInfo(name = "holidaydate")
        private String holidaydate;


        public EmployeeHoliday(long empNumb, String  holidaydate ){
            this.empNumb=empNumb;
            this.holidaydate=holidaydate;
        }

    public long getEmpNumb() {
        return empNumb;
    }

    public void setEmpNumb(long empNumb) {
        this.empNumb = empNumb;
    }

    public String getHolidaydate() {
        return holidaydate;
    }

    public void setHolidaydate(String holidaydate) {
        this.holidaydate = holidaydate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
