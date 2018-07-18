package com.example.tadje.myapplication.writer;

import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.FileNameMap;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * Created by tadje on 19.03.2018.
 */


public class HolidayJsonWriter implements IHolidayWriter {

    String fileName;
    public static JSONArray array;

    public HolidayJsonWriter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void write(String editDateStr, String editNameStr) {
        File fileJson = new File(Environment.getExternalStorageDirectory() + File.separator + "/HolidayData/" + fileName);


        String strFileJson = null;
        try {
            strFileJson = getStringFromFile(fileJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //previous objects in new object as string and the array is filled with the object
        JSONArray array = null;
        try {
            array = new JSONArray(strFileJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // new object is added to the array with entered strings
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("date", editDateStr);
            jsonObject.put("holidayname", editNameStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        array.put(jsonObject);

        JSONObject newJsonObject = new JSONObject();
        try {
            newJsonObject.put("", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        writeJsonFile(fileJson, array.toString());

    }

    public static String getStringFromFile(String filePath) throws Exception {
        File jsonfile = new File(filePath);
        FileInputStream fin = new FileInputStream(jsonfile);
        String ret = convertSteamToStream(fin);
        fin.close();
        return ret;
    }

    public static String convertSteamToStream(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private void writeJsonFile(File file, String json) {
        BufferedWriter bufferedWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void delete(ArrayList<Integer> positionList, FloatingActionButton deleteButton) {

        int i = 0;
        String strFileJson = null;
        File fileJson = new File(Environment.getExternalStorageDirectory() + File.separator + "/HolidayData/" + fileName);

        try {
            String fileJsonStr = fileJson.toString();
            strFileJson = getStringFromFile(fileJsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //previous objects in new object as string and the array is filled with the object
        array = null;


        ArrayList<String> listdata = new ArrayList<String>();
        JSONArray array = null;
        try {
            array = new JSONArray(strFileJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (array != null) {
            for (i=0;i<array.length();i++){
                try {
                    listdata.add(array.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ArrayList<String> stringsToBeDeleted = new ArrayList<>();

        //copy the Strings with the positions from the positionList in the stringsToBeDeleted-
        // List, remove the stringToBeDeleted-List from lines-List
        for (int position : positionList) {
            stringsToBeDeleted.add(listdata.get(position));
        }
        listdata.removeAll(stringsToBeDeleted);


        writeJsonFile(fileJson, listdata.toString());
        deleteButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

}
