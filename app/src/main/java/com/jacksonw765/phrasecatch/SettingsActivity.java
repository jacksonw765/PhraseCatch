package com.jacksonw765.phrasecatch;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    //define UI
    private Button buttonPlus, buttonMinus, buttonRate, buttonContact;
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
    protected void onPause() {
        System.out.println("pause");
        super.onPause();
    }

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
        buttonRate = findViewById(R.id.buttonRate);
        buttonContact = findViewById(R.id.buttonContact);

        //instantiate other elements
        pointsToWin = data.loadPointsToWin();
        textViewPointsToWin.setText(""+pointsToWin);
        textViewPointsToWin.setInputType(InputType.TYPE_NULL);

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
                System.out.println(pointsToWin);
                if (pointsToWin >= 2) {
                    try {
                        final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.type);
                        mp.start();
                        pointsToWin--;
                        textViewPointsToWin.setText("" + pointsToWin);
                        data.savePointsToWin(pointsToWin);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Min score", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Max score", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.jacksonw765.phrasecatch&hl=en_US"));
                startActivity(browserIntent);
            }
        });

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:jacksonw765@gmail.com"));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Could't start activity", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
