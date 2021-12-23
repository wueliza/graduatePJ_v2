package com.project.graduatepj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BloodCollect2 extends AppCompatActivity {

    Intent intent = new Intent();
    TextView tv1, tv2, tv3, tv4;
    private RESTfulApi resTfulApi;
    String collectorNumber , patientNumber1 ,recheckNumber,sampleNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_collect2);

        Bundle patientNumber1Check = this.getIntent().getExtras();
        Bundle sampleNumberCheck = this.getIntent().getExtras();
        Bundle collectorNumberCheck = this.getIntent().getExtras();
        Bundle recheckNumberCheck = this.getIntent().getExtras();

        patientNumber1 = patientNumber1Check.getString("patientNumber1Check");
        sampleNumber = sampleNumberCheck.getString("sampleNumberCheck");
        collectorNumber = collectorNumberCheck.getString("collectorNumberCheck");
        recheckNumber = recheckNumberCheck.getString("recheckNumberCheck");

        tv1 = (TextView) findViewById(R.id.patientNumber1Box);
        tv2 = (TextView) findViewById(R.id.sampleNumberBox);
        tv3 = (TextView) findViewById(R.id.collectorNumberBox);
        tv4 = (TextView) findViewById(R.id.recheckNumberBox);

//        tv1.setText(patientNumber1);
        tv2.setText(sampleNumber);
//        tv3.setText(collectorNumber);
//        tv4.setText(recheckNumber);

        Button bt = (Button) findViewById(R.id.nextbt);
        Button bt2 = (Button) findViewById(R.id.frontbt);

        Retrofit retrofit = new Retrofit.Builder() //api連接
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);
        Get_patient(retrofit);
        Get_staff(retrofit);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_checkoperation(patientNumber1, sampleNumber,collectorNumber,recheckNumber);
                Intent intent = new Intent(BloodCollect2.this, examine_homePage.class);
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodCollect2.this, BloodCollect1.class);
                startActivity(intent);
            }
        });
    }

    private void post_checkoperation(String qrChart, String bsno, String emid, String confirmId) {

        CheckOperationRecord checkOperationRecord = new CheckOperationRecord(qrChart, bsno, emid, confirmId);
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

    private void post_Operation(String ORA4_CHART, String OR_CHART, String Emid,  String Birth,String Name) {
        OperationRecord operationRecord = new OperationRecord(ORA4_CHART, OR_CHART, Emid, Birth,Name );
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
    public void Get_patient(Retrofit retrofit) {
        RESTfulApi jsonPlaceHolderApi = retrofit.create(RESTfulApi.class);
        Call<Patient_Api> call = resTfulApi.getOne(patientNumber1); //A00010
        call.enqueue(new Callback<Patient_Api>() {
            @Override
            public void onResponse(Call<Patient_Api> call, Response<Patient_Api> response) {
                if (response.body()==null) {
                    tv1.setText("此id不存在");
                    return;
                }
                else {
                    //int id = response.body().getPatientNum();
                    tv1.setText(response.body().getName());

                }
            }

            @Override
            public void onFailure(Call<Patient_Api> call, Throwable t) {
                tv1.setText("錯誤");
            }
        });
    }
    public void Get_staff(Retrofit retrofit) {
        RESTfulApi jsonPlaceHolderApi = retrofit.create(RESTfulApi.class);
        Call<Staff_Api> call = resTfulApi.get_staff(collectorNumber); //A00010
        call.enqueue(new Callback<Staff_Api>() {
            @Override
            public void onResponse(Call<Staff_Api> call, Response<Staff_Api> response) {
                if (response.body()==null) {
                    tv3.setText("此id不存在，請重新掃描員工編號！");
                    return;
                }
                else {
                    String name = response.body().getName();
                    tv3.setText(name);

                }
            }

            @Override
            public void onFailure(Call<Staff_Api> call, Throwable t) {
                tv3.setText("錯誤！");
            }
        });
        Call<Staff_Api> call2 = resTfulApi.get_staff(recheckNumber); //A00010
        call2.enqueue(new Callback<Staff_Api>() {
            @Override
            public void onResponse(Call<Staff_Api> call, Response<Staff_Api> response) {
                if (response.body()==null) {
                    tv4.setText("此id不存在，請重新掃描員工編號！");
                    return;
                }
                else {
                    String name = response.body().getName();
                    tv4.setText(name);

                }
            }

            @Override
            public void onFailure(Call<Staff_Api> call, Throwable t) {
                tv4.setText("錯誤！");
            }
        });
    }





}

