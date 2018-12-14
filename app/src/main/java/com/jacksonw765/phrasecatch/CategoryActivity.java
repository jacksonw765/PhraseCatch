package com.jacksonw765.phrasecatch;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity {

    private Button buttonPlus, buttonMinus, buttonStart;
    private int pointsToWin = 6;
    private TextView textViewPointsToWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonStart = findViewById(R.id.buttonStart);
        textViewPointsToWin = findViewById(R.id.textPointsToWin);

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

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO START GAME
            }
        });
    }
}
