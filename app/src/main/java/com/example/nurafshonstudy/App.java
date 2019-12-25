package com.example.nurafshonstudy;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import com.example.nurafshonstudy.config.Key_Values;

import java.io.File;

public class App extends Application {
    //private Settings settings;
    private static App myapp;
    private int testCount;
    private int historyDay;

    public App(){
        myapp=this;
    }

    public static App getInstance(){
        return myapp;
    }


    public File getContextFilesDir(){
        return getApplicationContext().getExternalFilesDir(Environment.getExternalStorageDirectory().getAbsolutePath());
    }
    public String getBaseDirectory(){
        return getContextFilesDir().getAbsolutePath()+File.separator+ Key_Values.BASE_DIRECTORY;
    }

    public String getMAC(){
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        return macAddress;
    }

    public int getTestCount(){
        return testCount;
    }

    public int getHistoryDay() {
        return historyDay;
    }

    public void setHistoryDay(int historyDay) {
        this.historyDay = historyDay;
    }

    public void setTestCount(int testCount) {
        this.testCount = testCount;
    }

    public void saveSettings(){
        SharedPreferences.Editor editor = getSharedPreferences(Key_Values.Shared_PREF_NAME, MODE_PRIVATE).edit();
        editor.putInt(Key_Values.PREF_HISTORY_DAY, historyDay);
        editor.putInt(Key_Values.PREF_TEST_COUNT,testCount);
        editor.apply();
    }

    public void loadSettings(){
        SharedPreferences prefs = getSharedPreferences(Key_Values.Shared_PREF_NAME, MODE_PRIVATE);
        testCount=prefs.getInt(Key_Values.PREF_TEST_COUNT, 30);
        historyDay=prefs.getInt(Key_Values.PREF_HISTORY_DAY, 7);
        if(historyDay<5){
            historyDay=5;
        }
        if(testCount<10){
            testCount=10;
        }
    }
}
