package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gotofunction extends AppCompatActivity {

    private Button eisaiBt , checkWorkBt , surgeryBt , bloodTransBt , chemBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gotofunction);

        eisaiBt = findViewById(R.id.eisaiBt);
        checkWorkBt = findViewById(R.id.checkWorkBt);
        surgeryBt = findViewById(R.id.surgeryBt);
        bloodTransBt = findViewById(R.id.bloodTransBt);
        chemBt = findViewById(R.id.chemBt);


        eisaiBt.setOnClickListener(this::go_eisai);
        chemBt.setOnClickListener(this::go_chem);
        surgeryBt.setOnClickListener(this::go_surgery);
        bloodTransBt.setOnClickListener(this::go_bloodTrans);
        checkWorkBt.setOnClickListener(this::go_checkWork);

    }

    public void go_eisai(View v){
        Intent intent = new Intent();
        intent.setClass(gotofunction.this , eisaicheck.class);
        startActivity(intent);
    }

    public void go_chem(View v){
        Intent intent = new Intent();
        intent.setClass(gotofunction.this , Chemopm.class);
        startActivity(intent);
    }

    public void go_surgery(View v){
        Intent intent = new Intent();
        intent.setClass(gotofunction.this , OperationHome.class);
        startActivity(intent);
    }

    public void go_bloodTrans(View v){
        Intent intent = new Intent();
        intent.setClass(gotofunction.this , blood_homeActivity.class);
        startActivity(intent);
    }
    public void go_checkWork(View v){
        Intent intent = new Intent();
        intent.setClass(gotofunction.this , examine_homePage.class);
        startActivity(intent);
    }
}