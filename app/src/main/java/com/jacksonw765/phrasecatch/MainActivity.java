package com.jacksonw765.phrasecatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jacksonw765.phrasecatch.intro.Intro;

public class MainActivity extends AppCompatActivity {

    private Button buttonPlay, buttonSettings, buttonHowTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonHowTo = findViewById(R.id.buttonHowTo);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonSettings = findViewById(R.id.buttonSettings);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        buttonHowTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Intro.class);
                startActivity(intent);
            }
        });
    }
}
