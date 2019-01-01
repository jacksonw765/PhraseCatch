package com.jacksonw765.phrasecatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Data {

    //define constants
    public final static String RADIO_KEY = "RADIO_KEY";
    public final static String POINTS_KEY = "POINTS_KEY";
    public final static int SHORT = 1;
    public final static int MID = 2;
    public final static int LONG = 3;
    public final static int RANDOM = 4;

    //private variables
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private Context context;

    public Data(Context context) {
        this.context = context;
    }

    public void saveRadio(int status) {
        editor = prefs.edit();
        editor.putInt(RADIO_KEY, status);
        editor.apply();
    }

    public int loadRadio() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
        int soundType = prefs.getInt(RADIO_KEY, -1);
        if(soundType == -1 || soundType == 7) {
            saveRadio(RANDOM);
            soundType = RANDOM;
        }
        editor.apply();
        return soundType;
    }

    public void savePointsToWin(int pointsToWin) {
        editor = prefs.edit();
        editor.putInt(POINTS_KEY, pointsToWin);
        editor.apply();
    }

    public int loadPointsToWin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
        int points = prefs.getInt(POINTS_KEY, -1);
        if(points == -1) {
            points = 7;
            savePointsToWin(points);
        }
        editor.apply();
        return points;
    }
}
