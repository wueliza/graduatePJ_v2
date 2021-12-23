package com.project.graduatepj;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Print_examineNumber2 extends AppCompatActivity {

    Intent intent = new Intent();
    TextView tv1, tv2 ;
    private RESTfulApi resTfulApi;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_print_examine_number2);

            Bundle patientNumber1Check = this.getIntent().getExtras();
            Bundle sampleNumberCheck = this.getIntent().getExtras();

            String patientNumber1 = patientNumber1Check.getString("patientNumber1Check");
            String sampleNumber = sampleNumberCheck.getString("sampleNumberCheck");

            tv1 = (TextView) findViewById(R.id.patientNumber1Box);
            tv2 = (TextView) findViewById(R.id.checkPaperNumberBox);

            tv1.setText(patientNumber1);
            tv2.setText(sampleNumber);

            Button bt = (Button) findViewById(R.id.nextbt);
            Button bt2 = (Button) findViewById(R.id.frontbt);



            bt.setOnClickListener(v -> {
                post_checkoperation(tv1.getText().toString(), tv2.getText().toString());
                Intent intent = new Intent(Print_examineNumber2.this,examine_homePage.class);
                startActivity(intent);
            });
            bt2.setOnClickListener(v -> {
                Intent intent = new Intent(Print_examineNumber2.this,Print_examineNumber1.class);
                startActivity(intent);
            });
    }


    private void post_checkoperation(String qrChart, String bsno) {

        CheckOperationRecord checkOperationRecord = new CheckOperationRecord(qrChart, bsno);
        Call<CheckOperationRecord> call = resTfulApi.post_CheckOperationRecord(checkOperationRecord);

        call.enqueue(new Callback<CheckOperationRecord>() {
            @Override
            public void onResponse(Call<CheckOperationRecord> call, Response<CheckOperationRecord> response) {

            }

            @Override
            public void onFailure(Call<CheckOperationRecord> call, Throwable t) {

            }
        });
    }
}