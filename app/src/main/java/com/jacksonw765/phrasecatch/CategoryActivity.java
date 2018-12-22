package com.jacksonw765.phrasecatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

    Button buttonEverything, buttonSports, buttonMovies, buttonCollege, buttonPlaces, buttonMusic;

    public final static String CATEGORY_TYPE = "CATEGORY";
    public final static int EVERYTHING = 1;
    public final static int SPORTS = 2;
    public final static int COLLEGE = 3;
    public final static int ENTERTAINMENT = 4;
    public final static int WORLD = 5;
    public final static int ERROR_VALUE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        buttonEverything = findViewById(R.id.buttonCatagoryEverything);
        buttonSports = findViewById(R.id.buttonSports);
        buttonMovies = findViewById(R.id.buttonMovies);
        buttonCollege = findViewById(R.id.buttonCollegeLife);
        buttonPlaces = findViewById(R.id.buttonPlaces);
        buttonMusic = findViewById(R.id.buttonMusic);


        buttonEverything.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, EVERYTHING);
                startActivity(intent);
            }
        });

        buttonSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, SPORTS);
                startActivity(intent);
            }
        });

        buttonMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, ENTERTAINMENT);
                startActivity(intent);
            }
        });

        buttonCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, COLLEGE);
                startActivity(intent);
            }
        });

        buttonPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, WORLD);
                startActivity(intent);
            }
        });
    }
}
