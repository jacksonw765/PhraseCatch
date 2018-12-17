package com.jacksonw765.phrasecatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CategoryRefactor extends AppCompatActivity {

    Button buttonEverything;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_refactor);
        buttonEverything = findViewById(R.id.buttonCatagoryEverything);

        buttonEverything.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "soon", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
