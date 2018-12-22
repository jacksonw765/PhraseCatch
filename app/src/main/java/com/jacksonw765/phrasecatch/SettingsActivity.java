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
    private Data data;

    //define variables
    private int pointsToWin;

    public static final int SHORT_TIME = 30;
    public static final int MID_TIME = 47;
    public static final int LONG_TIME = 53;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        data = new Data(this);

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
        pointsToWin = data.loadPointsToWin();
        textViewPointsToWin.setText(""+pointsToWin);

        //load previous radio from SP
        switch (data.loadRadio()) {
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
                        data.saveRadio(Data.SHORT);
                        break;
                    case R.id.radioButtonTwo:
                        data.saveRadio(Data.MID);
                        break;
                    case R.id.radioButtonThree:
                        data.saveRadio(Data.LONG);
                        break;
                    case R.id.radioButtonFour:
                        data.saveRadio(Data.RANDOM);
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
                        data.savePointsToWin(pointsToWin);
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
                        data.savePointsToWin(pointsToWin);
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

}
