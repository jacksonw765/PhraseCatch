package com.jacksonw765.phrasecatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

    private Button buttonEverything;
    private Button buttonSports;
    private Button buttonEntertainment;
    private Button buttonCollege;
    private Button buttonPlaces;
    private Button buttonScience;
    private Button buttonAnimals;
    private Button buttonFood;

    public final static String CATEGORY_TYPE = "CATEGORY";
    public final static int EVERYTHING = 1;
    public final static int SPORTS = 2;
    public final static int COLLEGE = 3;
    public final static int ENTERTAINMENT = 4;
    public final static int PLACES = 5;
    public final static int ANIMAL = 6;
    public final static int SCIENCE = 7;
    public final static int FOOD = 8;
    public final static int ERROR_VALUE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        buttonEverything = findViewById(R.id.buttonCatagoryEverything);
        buttonSports = findViewById(R.id.buttonSports);
        buttonEntertainment = findViewById(R.id.buttonEntertainment);
        buttonCollege = findViewById(R.id.buttonCollegeLife);
        buttonPlaces = findViewById(R.id.buttonPlaces);
        buttonScience = findViewById(R.id.buttonScience);
        buttonAnimals = findViewById(R.id.buttonAnimals);
        buttonFood = findViewById(R.id.buttonFood);

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

        buttonScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, SCIENCE);
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

        buttonAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, ANIMAL);
                startActivity(intent);
            }
        });
        buttonEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, ENTERTAINMENT);
                startActivity(intent);
            }
        });
        buttonPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, PLACES);
                startActivity(intent);
            }
        });
        buttonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra(CATEGORY_TYPE, FOOD);
                startActivity(intent);
            }
        });
    }
}
