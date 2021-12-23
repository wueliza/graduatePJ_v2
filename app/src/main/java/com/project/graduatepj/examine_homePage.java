package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class examine_homePage extends AppCompatActivity {
    private Button bloodCollectHome,printExamineHome,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_home_page);

        bloodCollectHome = findViewById(R.id.bloodCollectHome);
        printExamineHome = findViewById(R.id.printExamineHome);
        home = findViewById(R.id.home);


        bloodCollectHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(examine_homePage.this, BloodCollect1.class);
                startActivity(intent);
            }
        });

        printExamineHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(examine_homePage.this, Print_examineNumber1.class);
                startActivity(intent);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(examine_homePage.this, gotofunction.class);
                startActivity(intent);
            }
        });
    }
}