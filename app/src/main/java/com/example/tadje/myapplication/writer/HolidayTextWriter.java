package com.example.tadje.myapplication.writer;

import android.content.Context;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tadje on 19.03.2018.
 */

public class HolidayTextWriter implements IHolidayWriter {

    private FloatingActionButton deleteButton;
    private String fileName;

    public HolidayTextWriter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void write(String editDateStr, String editNameStr) {

        try {
            String stream = Environment
                    .getExternalStorageDirectory() + File.separator + "/HolidayData/" + fileName;
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(stream, true), "UTF-8");

            BufferedWriter fbw = new BufferedWriter(writer);
            fbw.newLine();
            fbw.write(editDateStr + ";" + editNameStr);
            fbw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ArrayList<Integer> positionList, FloatingActionButton deleteButton) {


        ArrayList<String> lines = new ArrayList<String>();
        String in = null;
        String stream = Environment
                .getExternalStorageDirectory() + File.separator + "/HolidayData/" + fileName;
        BufferedReader r = null;
        FileWriter fw;


        try {
            r = new BufferedReader(new FileReader(stream));
            while ((in = r.readLine()) != null)
                lines.add(in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        ArrayList<String> stringsToBeDeleted = new ArrayList<>();

        //copy the Strings with the positions from the positionList in the stringsToBeDeleted-
       // List, remove the stringToBeDeleted-List from lines-List
        for (int position : positionList) {
            stringsToBeDeleted.add(lines.get(position));
        }
        lines.removeAll(stringsToBeDeleted);




        try {
            r.close();
            fw = new FileWriter(stream);
            boolean isFirst = true;

            for (String line : lines) {

                if (!isFirst) {
                    fw.append("\n");
                } else {
                    isFirst = false;
                }

                fw.append(line);
            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.INVISIBLE);

    }
}




