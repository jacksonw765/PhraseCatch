package com.jacksonw765.phrasecatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    //create UI
    private Button buttonNext, buttonStartStop;
    private TextView textTeamAPoints, textTeamBPoints, textWord;
    private AdView adview;
    private AdRequest adRequest;
    private InterstitialAd interstitialAd;

    //create rest
    private int category;
    private int deckIndex = 0;
    private int deckMaxIndex;
    private ArrayList currentDeck;
    private MediaPlayer countdownSound;
    private int currentPointsA, currentPointsB, pointsToWin = 0;
    private CountDownTimer timer;
    private Boolean isGameActive = false;
    private Data data;
    private int currentTime;
    private long timeLeft = -1;
    private int countDownSoundURI;
    private Context context;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        try {
            MobileAds.initialize(this, "ca-app-pub-7225811178915318~7129168296");
            interstitialAd = new InterstitialAd(this);
            adview = findViewById(R.id.adView);
            adRequest = new AdRequest.Builder().addTestDevice("F1143FF789A53A2B4E5A2837BEE09ABB").build();
            adview.loadAd(adRequest);
            interstitialAd.setAdUnitId("ca-app-pub-7225811178915318/8513120893");
            interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("F1143FF789A53A2B4E5A2837BEE09ABB").build());
        }

        //call base exception to catch every error
        catch (Exception e) {
            e.printStackTrace();
        }



        buttonNext = findViewById(R.id.buttonNext);
        buttonStartStop = findViewById(R.id.buttonStop);
        textTeamAPoints = findViewById(R.id.textTeamAPoints);
        textTeamBPoints = findViewById(R.id.textTeamBPoints);
        textWord = findViewById(R.id.textStatement);
        data = new Data(this);
        pointsToWin = data.loadPointsToWin();
        textTeamAPoints = findViewById(R.id.textTeamAPoints);
        textTeamBPoints = findViewById(R.id.textTeamBPoints);
        adview = findViewById(R.id.adView);
        context = getApplicationContext();
        random = new Random();

        //load current deck
        Intent intent = getIntent();
        category = intent.getIntExtra(CategoryActivity.CATEGORY_TYPE, CategoryActivity.ERROR_VALUE);

        //loads current time from the data class
        currentTime = getSelectedTimeInMilli();

        //updates UI elements
        updateButtonNext();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textWord.setText(getNextWord());
            }
        });

        textTeamAPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isGameActive) {
                    MediaPlayer scorePoints = MediaPlayer.create(view.getContext(), R.raw.type);
                    currentPointsA++;
                    textTeamAPoints.setText("" + currentPointsA);
                    scorePoints.start();
                    if (currentPointsA == pointsToWin) {
                        MediaPlayer mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.win_sound);
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Team A Wins!")
                                .setMessage("Team A has won! Congratulations")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        mediaPlayer.start();
                    }
                }
            }
        });

        textTeamBPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isGameActive) {
                    MediaPlayer scorePoints = MediaPlayer.create(view.getContext(), R.raw.type);
                    currentPointsB++;
                    textTeamBPoints.setText("" + currentPointsB);
                    scorePoints.start();
                    if (currentPointsB == pointsToWin) {
                        MediaPlayer mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.win_sound);
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Team B Wins!")
                                .setMessage("Team B has won! Congratulations")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        mediaPlayer.start();
                    }
                }
            }
        });

        buttonStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start game
                if (!isGameActive) {
                    startGame();
                }
                //game is active, stop game
                else {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Stop Round?")
                            .setMessage("Are you sure you would like to stop the round?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (timeLeft > 0) {
                                        timer.cancel();
                                        endGame();
                                    }
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            }
        });

        //create new timer
        timer = new CountDownTimer(currentTime, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
            }

            @Override
            public void onFinish() {
                if (!isFinishing()) {
                    timerUp();
                }
                System.gc();
            }
        };

        //resolve category from previous screen
        switch (category) {
            case CategoryActivity.ERROR_VALUE:
                Toast.makeText(getApplicationContext(), "ERROR! NO DECK SELECTED", Toast.LENGTH_SHORT).show();
            case CategoryActivity.EVERYTHING:
                List<String> college_a = Arrays.asList(getResources().getStringArray(R.array.array_college_life));
                List<String> entertainment_a = Arrays.asList(getResources().getStringArray(R.array.array_entertainment));
                List<String> animals_a = Arrays.asList(getResources().getStringArray(R.array.array_animals));
                List<String> places_a = Arrays.asList(getResources().getStringArray(R.array.array_places));
                List<String> science_a = Arrays.asList(getResources().getStringArray(R.array.array_science));
                List<String> sports_a = Arrays.asList(getResources().getStringArray(R.array.array_sports));
                List<String> food_a = Arrays.asList(getResources().getStringArray(R.array.array_food));
                ArrayList everything = new ArrayList<String>();
                everything.addAll(entertainment_a);
                everything.addAll(college_a);
                everything.addAll(animals_a);
                everything.addAll(places_a);
                everything.addAll(science_a);
                everything.addAll(sports_a);
                everything.addAll(food_a);
                currentDeck = everything;
                Collections.shuffle(currentDeck);
                break;
            case CategoryActivity.COLLEGE:
                List<String> college = Arrays.asList(getResources().getStringArray(R.array.array_college_life));
                currentDeck = new ArrayList(college);
                Collections.shuffle(currentDeck);
                break;
            case CategoryActivity.ANIMAL:
                List<String> animals = Arrays.asList(getResources().getStringArray(R.array.array_animals));
                currentDeck = new ArrayList(animals);
                Collections.shuffle(currentDeck);
                break;
            case CategoryActivity.PLACES:
                List<String> places = Arrays.asList(getResources().getStringArray(R.array.array_places));
                currentDeck = new ArrayList(places);
                Collections.shuffle(currentDeck);
                break;
            case CategoryActivity.SCIENCE:
                List<String> science = Arrays.asList(getResources().getStringArray(R.array.array_science));
                currentDeck = new ArrayList(science);
                Collections.shuffle(currentDeck);
                break;
            case CategoryActivity.ENTERTAINMENT:
                List<String> entertainment = Arrays.asList(getResources().getStringArray(R.array.array_entertainment));
                currentDeck = new ArrayList(entertainment);
                Collections.shuffle(currentDeck);
                break;
            case CategoryActivity.SPORTS:
                List<String> sports = Arrays.asList(getResources().getStringArray(R.array.array_sports));
                currentDeck = new ArrayList(sports);
                Collections.shuffle(currentDeck);
                break;
            case CategoryActivity.FOOD:
                List<String> food = Arrays.asList(getResources().getStringArray(R.array.array_food));
                currentDeck = new ArrayList(food);
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
        textWord.setText(capitalize(getNextWord()));
        buttonStartStop.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_red, null));
        isGameActive = true;
        timer.start();
        updateButtonNext();
    }

    private void endGame() {
        buttonStartStop.setText("Start");
        buttonStartStop.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_lightblue, null));
        isGameActive = false;
        resetSound(countdownSound);
        updateButtonNext();
    }

    private void resetSound(MediaPlayer mp) {
        try {
            mp.stop();
            mp.prepare();
            mp.setDataSource(getResources().getResourceName(countDownSoundURI));
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //gets the users preference for timer length and converts it to audio.
    //this is a really ugly method. Refactor when feeling it, which is prolly never lol.
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
                countDownSoundURI = R.raw.countdown_short;
                countdownSound = MediaPlayer.create(getApplicationContext(), countDownSoundURI);
                break;
            case 2:
                retVal = SettingsActivity.MID_TIME;
                countDownSoundURI = R.raw.countdown_mix;
                countdownSound = MediaPlayer.create(getApplicationContext(), countDownSoundURI);
                break;
            case 3:
                retVal = SettingsActivity.LONG_TIME;
                countDownSoundURI = R.raw.countdown_reg;
                countdownSound = MediaPlayer.create(getApplicationContext(), countDownSoundURI);
        }
        return retVal * 1000;
    }

    private String getNextWord() {
        String nextWord = null;
        if (deckIndex <= deckMaxIndex) {
            try {
                nextWord = (String) currentDeck.get(deckIndex);
                deckIndex++;
                return nextWord;
            } catch (IndexOutOfBoundsException index) {
                deckIndex = 0;
                nextWord = (String) currentDeck.get(deckIndex);
                System.out.println(index.toString());
            }
        } else {
            deckIndex = 0;
        }
        return nextWord;
    }

    private void updateButtonNext() {
        if (!isGameActive) {
            buttonNext.setEnabled(false);
        } else {
            buttonNext.setEnabled(true);
        }
    }

    @NonNull
    private String capitalize(String str) {
        String[] words = str.split("\\s");
        StringBuilder sb = new StringBuilder();
        for (String s : words) {
            if (!s.equals("")) {
                sb.append(Character.toUpperCase(s.charAt(0)));
                sb.append(s.substring(1));
            }
            sb.append(" ");
        }
        // trim() to remove extra space in the end before returning
        return sb.toString().trim();
    }

    @Override
    public void onBackPressed() {
        if (isGameActive || currentPointsA > 0 || currentPointsB > 0) {
            final AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Game In Progress!")
                    .setMessage("Are you sure you would like to cancel?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            PlayActivity.super.onBackPressed();
                            endGame();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }

    private void showAd() {
        try {
            if (interstitialAd.isLoaded() && getRandomBoolean()) {
                interstitialAd.show();
            }
            else {
                System.out.println("No ad shown");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private void timerUp() {
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setTitle("Timer Expired!")
                .setMessage("Timer is up.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        endGame();
                        showAd();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        textWord.setText("Click Start");
    }
}