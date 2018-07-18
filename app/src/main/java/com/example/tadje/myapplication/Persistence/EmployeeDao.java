package com.example.tadje.myapplication.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tadje.myapplication.model.Employee;

import java.util.List;

/**
 * Created by tadje on 13.04.2018.
 */

@Dao
public interface EmployeeDao {
    @Query("SELECT * FROM employee")
    List<Employee> getAll();


    @Query("SELECT * FROM employee WHERE empNumb LIKE :empNumb AND " + "lastname LIKE " +
            ":lastname" +
            " AND " +
            "firstname" +
            " LIKE :firstname AND " + "workingTime LIKE :workingTime AND " + "role LIKE :role AND "
            +"image LIKE :image")
    Employee findByName(long empNumb, String lastname, String firstname, double workingTime, String
            role, byte[] image );


    @Query("SELECT * FROM employee WHERE empNumb = :empNumb")
    Employee getAllFromEmpNumb(long empNumb);

    @Query("SELECT empNumb FROM employee")
    long[] getEmpNumbers();

    @Query("SELECT lastname FROM employee")
    String [] getEmplastname();

    @Query("SELECT firstname FROM employee")
    String [] getEmpFirstname();

    @Query("SELECT workingTime FROM employee WHERE empNumb LIKE :empNumb")
    double getWorkingTime(long empNumb);

    @Update
    void update(Employee employee);

    @Insert
    void insertAll(Employee... employee);


    @Delete
    void delete(Employee employee);
}

