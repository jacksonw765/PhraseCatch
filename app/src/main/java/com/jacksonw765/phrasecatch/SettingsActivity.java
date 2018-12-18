package com.jacksonw765.phrasecatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    //define UI
    private Button buttonPlus, buttonMinus;
    private TextView textViewPointsToWin;
    private RadioGroup radioGroup;
    private RadioButton radioShort, radioMid, radioLong, radioRandom;

    //define variables
    private int pointsToWin;
    private static SharedPreferences.Editor editor;
    private static SharedPreferences prefs;

    public static final int SHORT_TIME = 30;
    public static final int MID_TIME = 47;
    public static final int LONG_TIME = 53;

   //define constants
    public final static String RADIO_KEY = "RADIO_KEY";
    public final static String POINTS_KEY = "RADIO_KEY";
    public final static int SHORT = 1;
    public final static int MID = 2;
    public final static int LONG = 3;
    public final static int RANDOM = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //instantiate UI
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus = findViewById(R.id.buttonPlus);
        textViewPointsToWin = findViewById(R.id.textPointsToWin);
        radioGroup = findViewById(R.id.radioGroup);
        radioShort = findViewById(R.id.radioButtonOne);
        radioMid = findViewById(R.id.radioButtonTwo);
        radioLong = findViewById(R.id.radioButtonThree);
        radioRandom = findViewById(R.id.radioButtonFour);

        //instantiate other elements
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pointsToWin = loadPointsToWin(getApplicationContext());
        textViewPointsToWin.setText(""+pointsToWin);

        //load previous radio from SP
        switch (loadRadio(getApplicationContext())) {
            case 1:
                radioShort.toggle();
                break;
            case 2:
                radioMid.toggle();
                break;
            case 3:
                radioLong.toggle();
                break;
            case 4:
                radioRandom.toggle();
        }

        //onClick listeners
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButtonOne:
                        saveRadio(SHORT);
                        break;
                    case R.id.radioButtonTwo:
                        saveRadio(MID);
                        break;
                    case R.id.radioButtonThree:
                        saveRadio(LONG);
                        break;
                    case R.id.radioButtonFour:
                        saveRadio(RANDOM);
                }
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pointsToWin > 0) {
                    try {
                        final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.type);
                        mp.start();
                        pointsToWin--;
                        textViewPointsToWin.setText("" + pointsToWin);
                        savePointsToWin(pointsToWin);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Min score", Toast.LENGTH_SHORT);
                }
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pointsToWin < 10) {
                    try {
                        final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.type);
                        mp.start();
                        pointsToWin++;
                        textViewPointsToWin.setText("" + pointsToWin);
                        savePointsToWin(pointsToWin);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Max score", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    //getters and setters for SP
    private static void saveRadio(int status) {
        editor = prefs.edit();
        editor.putInt(RADIO_KEY, status);
        editor.apply();
    }

    public static int loadRadio(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
        int soundType = prefs.getInt(RADIO_KEY, -1);
        if(soundType == -1) {
            saveRadio(RANDOM);
            soundType = RANDOM;
        }
        return soundType;
    }

    private static void savePointsToWin(int pointsToWin) {
        editor = prefs.edit();
        editor.putInt(POINTS_KEY, pointsToWin);
        editor.apply();
    }

    public static int loadPointsToWin(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
        int points = prefs.getInt(POINTS_KEY, -1);
        if(points == -1) {
            points = 7;
            savePointsToWin(points);
        }
        return points;
    }
}
