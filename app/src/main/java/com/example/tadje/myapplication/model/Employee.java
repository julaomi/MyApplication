package com.example.tadje.myapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by tadje on 13.04.2018.
 */

@Entity(tableName = "employee")
public class Employee {

    @PrimaryKey @ColumnInfo(name="empNumb")
    private long empNumb;

    @ColumnInfo(name = "lastname")@NonNull
    private String lastname;

    @ColumnInfo(name = "firstname")@NonNull
    private String firstname;

    @ColumnInfo(name="workingTime")@NonNull
    private double workingTime;

    @ColumnInfo(name = "role")@NonNull
    private String role;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name="image")
    private byte[] image;



    public Employee(long empNumb, String lastname, String firstname, double workingTime, String
            role, byte[] image ){
        this.empNumb=empNumb;
        this.lastname=lastname;
        this.firstname=firstname;
        this.workingTime=workingTime;
        this.role=role;
        this.image=image;
    }



    public long getEmpNumb() {
        return empNumb;
    }

    public void setEmpNumb(long empNumb) {
        this.empNumb = empNumb;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public double getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(double workingTime) {
        this.workingTime = workingTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
