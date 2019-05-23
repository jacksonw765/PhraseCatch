package com.jacksonw765.phrasecatch.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.jacksonw765.phrasecatch.Data;
import com.jacksonw765.phrasecatch.MainActivity;
import com.jacksonw765.phrasecatch.R;

public class MainLauncher extends AppCompatActivity {

    private Data data;

    //do nothing on back press
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //  Initialize SharedPreferences
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        data = new Data(this);

        boolean isFirstStart = data.loadFirstRun();

        if (isFirstStart) {
            Intent i = new Intent(MainLauncher.this, Intro.class);
            startActivity(i);
            data.setFirstRun(false);
        } else {
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            startActivity(i);
        }



    }
}
