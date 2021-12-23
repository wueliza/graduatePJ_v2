package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Waiting2 extends AppCompatActivity {
    Intent intent = new Intent();
    TextView tv1, tv2, tv3, tv4, BirthdayBox, patientName;
    private RESTfulApi resTfulApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting2);

        Bundle paitentNumbercheck = this.getIntent().getExtras();
        Bundle wistNumbercheck = this.getIntent().getExtras();
        Bundle ManCheckBox = this.getIntent().getExtras();
        Bundle NameBox = this.getIntent().getExtras();
        Bundle birthday = this.getIntent().getExtras();

        String patientNumber = paitentNumbercheck.getString("ora4chart");
        String wistNumber = wistNumbercheck.getString("paitentNumbercheck");
        String ManCheckNumber = ManCheckBox.getString("ManCheckBox");
        String patientname = NameBox.getString("NameBox");
        String birth = birthday.getString("birth");


        tv1 = (TextView) findViewById(R.id.PatientNumberBox);
        tv2 = (TextView) findViewById(R.id.wistNumberBox);
        tv3 = (TextView) findViewById(R.id.ManCheckBox);
        tv4 = (TextView) findViewById(R.id.BirthdayBox);

        patientName = findViewById(R.id.NameBox);
        BirthdayBox = findViewById(R.id.BirthdayBox);

        tv1.setText(patientNumber);
        tv2.setText(wistNumber);
        tv3.setText(ManCheckNumber);
        tv4.setText(birth);
        patientName.setText(patientname);

        Button BackButton = (Button) findViewById(R.id.frontbt);
        Button Upload = (Button) findViewById(R.id.Upload);
        Retrofit retrofit = new Retrofit.Builder() //api連接
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setClass(Waiting2.this, Waiting.class);
                startActivity(intent);
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_Operation(tv1.getText().toString(), tv2.getText().toString(),tv3.getText().toString(), tv4.getText().toString(),patientName.getText().toString() );
                intent.setClass(Waiting2.this, OperationHome.class);
                startActivity(intent);

            }
        });
    }

    private void post_Operation(String ORA4_CHART, String OR_CHART, String Emid, String Birth, String Name) {
        OperationRecord operationRecord = new OperationRecord(ORA4_CHART, OR_CHART, Emid, Birth, Name);
        Call<OperationRecord> call = resTfulApi.post_OperationRecord(operationRecord);

        call.enqueue(new Callback<OperationRecord>() {
            @Override
            public void onResponse(Call<OperationRecord> call, Response<OperationRecord> response) {

            }

            @Override
            public void onFailure(Call<OperationRecord> call, Throwable t) {

            }
        });
    }

}