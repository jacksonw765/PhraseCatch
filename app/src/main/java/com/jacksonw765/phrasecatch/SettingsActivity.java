package com.jacksonw765.phrasecatch;

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

    private Button buttonPlus, buttonMinus;
    private int pointsToWin = 6;
    private TextView textViewPointsToWin;
    private RadioGroup radioGroup;

    private RadioButton radioShort, radioMid, radioLong, radioRandom;

    public final static String RADIO_KEY = "RADIO_KEY";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    private final int SHORT = 1;
    private final int MID = 2;
    private final int LONG = 3;
    private final int RANDOM = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus = findViewById(R.id.buttonPlus);
        textViewPointsToWin = findViewById(R.id.textPointsToWin);
        radioGroup = findViewById(R.id.radioGroup);
        radioShort = findViewById(R.id.radioButtonOne);
        radioMid = findViewById(R.id.radioButtonTwo);
        radioLong = findViewById(R.id.radioButtonThree);
        radioRandom = findViewById(R.id.radioButtonFour);

        textViewPointsToWin.setTextIsSelectable(false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch (loadRadio()) {
            case -1:
                saveRadio(RANDOM);
                if(!radioRandom.isChecked())
                    radioRandom.toggle();
                break;
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
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Max score", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void saveRadio(int status) {
        editor = prefs.edit();
        editor.putInt(RADIO_KEY, status);
        editor.apply();
    }

    public int loadRadio() {
        return prefs.getInt(RADIO_KEY, -1);
    }
}
