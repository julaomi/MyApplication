package com.example.tadje.myapplication;

import android.content.res.AssetManager;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tadje on 16.03.2018.
 */

public class Util {

    public static void copyAssets(AssetManager assetManager) {
        String[] files = null;
        try {
            files = assetManager.list("holidays");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File folder=new File(Environment.getExternalStorageDirectory()+File.separator+"/HolidayData");
        boolean success=true;
        if(!folder.exists()){
            success=folder.mkdir(); // creation of the orderer if he does not exist yet
             }

        //Copy files in the assets foulder to SD Card
        for (String fileListen : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open("holidays/" + fileListen);
                String outDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                File outFile = new File(outDir+"/HolidayData",fileListen);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}
