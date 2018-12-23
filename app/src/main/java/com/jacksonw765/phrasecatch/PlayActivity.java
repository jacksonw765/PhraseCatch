package com.jacksonw765.phrasecatch;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    //create UI
    private Button buttonNext, buttonStartStop;
    private TextView textTeamAPoints, textTeamBPoints, textWord;


    //create rest
    private int category;
    private int deckIndex = 0;
    private int deckMaxIndex;
    private ArrayList<String> currentDeck;
    private MediaPlayer countdownSound;
    private int currentPointsA, currentPointsB, pointsToWin = 0;
    private CountDownTimer timer;
    private Boolean isGameActive = false;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        buttonNext = findViewById(R.id.buttonNext);
        buttonStartStop = findViewById(R.id.buttonStop);
        textTeamAPoints = findViewById(R.id.textTeamAPoints);
        textTeamBPoints = findViewById(R.id.textTeamBPoints);
        textWord = findViewById(R.id.textStatement);
        data = new Data(this);
        pointsToWin = data.loadPointsToWin();

        //load current deck
        Intent intent = getIntent();
        category = intent.getIntExtra(CategoryActivity.CATEGORY_TYPE, CategoryActivity.ERROR_VALUE);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textWord.setText(getNextWord());
            }
        });

        buttonStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start game
                if (!isGameActive) {
                    startGame();
                }
                //game is active, so stop game
                else {
                    countdownSound.stop();
                    timer.cancel();
                    endGame();
                }
            }
        });

        int temp = getSelectedTimeInMilli();

        timer = new CountDownTimer(temp, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                endGame();
            }
        };

        //resolve category from previous screen
        switch (category) {
            case CategoryActivity.ERROR_VALUE:
                Toast.makeText(getApplicationContext(), "ERROR! NO DECK SELECTED", Toast.LENGTH_SHORT).show();
            case CategoryActivity.COLLEGE:
                List<String> tempList = Arrays.asList(getResources().getStringArray(R.array.array_college_life));
                currentDeck = new ArrayList<>(tempList);
                Collections.shuffle(currentDeck);
                break;
        }
        textWord.setText("Click Start");
        buttonStartStop.setText("Start");
        buttonStartStop.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_lightblue, null));
    }

    //called when a user hits start
    private void startGame() {
        deckMaxIndex = currentDeck.size();
        countdownSound.start();
        buttonStartStop.setText("Stop");
        textWord.setText(getNextWord());
        buttonStartStop.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_red, null));
        isGameActive = true;
    }

    private void endGame() {
        buttonStartStop.setText("Start");
        buttonStartStop.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_lightblue, null));
        isGameActive = false;
    }

    //gets the users preference for timer length and converts it to audio.
    //this is a really ugly method. Refactor when feeling it
    private int getSelectedTimeInMilli() {
        Random random;
        int retVal = data.loadRadio();
        if (retVal == Data.RANDOM) {
            random = new Random(1234);
            retVal = random.nextInt(3) + 1;
        }
        switch (retVal) {
            case 1:
                retVal = SettingsActivity.SHORT_TIME;
                countdownSound = MediaPlayer.create(getApplicationContext(), R.raw.countdown_short);
                break;
            case 2:
                retVal = SettingsActivity.MID_TIME;
                countdownSound = MediaPlayer.create(getApplicationContext(), R.raw.countdown_mix);
                break;
            case 3:
                retVal = SettingsActivity.LONG_TIME;
                countdownSound = MediaPlayer.create(getApplicationContext(), R.raw.countdown_reg);
        }
        return retVal * 1000;
    }

    private String getNextWord() {
        String nextWord = null;
        if (deckIndex <= deckMaxIndex) {
            nextWord = currentDeck.get(deckIndex);
            deckIndex++;
            return nextWord;
        } else {
            deckIndex = 0;
        }
        return nextWord;
    }

}