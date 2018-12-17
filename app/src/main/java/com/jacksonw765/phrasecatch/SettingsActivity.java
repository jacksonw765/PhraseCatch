package com.jacksonw765.phrasecatch;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonPlus, buttonMinus;
    private int pointsToWin;
    private TextView textViewPointsToWin;
    private RadioGroup radioGroup;

    public final static String SETTINGS_KEY = "SETTINGS_KEY";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus = findViewById(R.id.buttonPlus);
        textViewPointsToWin = findViewById(R.id.textPointsToWin);
        radioGroup = findViewById(R.id.radioGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.radioButtonOne:
                        System.out.println("worked");
                    case R.id.radioButtonTwo:

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
}
