package com.jacksonw765.phrasecatch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.jacksonw765.phrasecatch.intro.Intro;

public class MainActivity extends AppCompatActivity {

    private Button buttonPlay, buttonSettings, buttonHowTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            if(!isFinishing()) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("We've Upgraded!")
                        .setMessage("We are excited to announce that due to the success of Phrase Catch version 1, we have created a new version with over 100 improvements to make the game even more enjoyable.")
                        .setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent browse = new Intent(Intent.ACTION_VIEW , Uri.parse("https://play.google.com/store/apps/details?id=com.jacksonw765.phrase_catch2&hl=en_US"));
                                startActivity(browse);
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }

        if(!isNetworkConnected()) {
            if(!isFinishing()) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Please Play Fair")
                        .setMessage("Since this application is free, we rely on ads. " +
                                "While we can't stop you from playing, please consider connecting to internet to support this app. Thank you!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }

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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
