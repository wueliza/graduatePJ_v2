package com.project.graduatepj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class eisairesult extends AppCompatActivity {
    private TextView staff_id , patient_id , eisai_id , result_tv;
    private Button completebt , upStepbt;
    private RESTfulApi resTfulApi;
    String expireSt , year , month , day;
    Date curDate , expire;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eisairesult);
        Intent intent = this.getIntent();
        curDate = new Date(System.currentTimeMillis()) ;

        staff_id = findViewById(R.id.staff_id);
        patient_id = findViewById(R.id.patient_id);
        eisai_id = findViewById(R.id.eisai_id);
        result_tv = findViewById(R.id.result_tv);
        completebt = findViewById(R.id.completebt);
        upStepbt = findViewById(R.id.upStep_bt);

        Bundle scan_result = intent.getExtras();
        String staff = scan_result.getString("staff_id");
        String patient = scan_result.getString("patient_id");
        String eisai = scan_result.getString("eisai_id");

        staff_id.setText(staff);
        patient_id.setText(patient);
        eisai_id.setText(eisai);

        //api連接
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);
        Get_eisai(retrofit , eisai);

        completebt.setOnClickListener(this::complete);
        upStepbt.setOnClickListener(this::upStep);

        //result_tv.setText("已過期\n到期日：" + expire.toString());

    }

    private void upStep(View view) {
        Intent uintent = new Intent();
        uintent.setClass(eisairesult.this , eisaicheck.class);
        startActivity(uintent);
    }

    public void Get_eisai(Retrofit retrofit, String id) {
        Call<Eisai_Api> call = resTfulApi.get_eisai(id);
        call.enqueue(new Callback<Eisai_Api>() {
            @Override
            public void onResponse(Call<Eisai_Api> call, Response<Eisai_Api> response) {
                if (response.body()==null) {
                    result_tv.setText("此id不存在，請重新掃描！");
                    return;
                }
                else {
                    expireSt = response.body().getExpiration();
                    result_tv.setText(expireSt);

                    year = expireSt.substring(0,4);
                    month = expireSt.substring(5,7);
                    day = expireSt.substring(8,10);
                    int y = Integer.parseInt(year);
                    int m = Integer.parseInt(month);
                    int d = Integer.parseInt(day);
                    expire = new Date(y-1900 , m-1 , d);
                    String str = formatter.format(expire);
                    if(curDate.after(expire)){
                        result_tv.setTextColor(Color.parseColor("#ff0000"));
                        result_tv.setText("已過期\n到期日：" + str);
                    }
                    else if(curDate.before(expire)){
                        result_tv.setTextColor(Color.parseColor("#000000"));
                        result_tv.setText("未過期\n到期日：" + str);
                    }

                }
            }

            @Override
            public void onFailure(Call<Eisai_Api> call, Throwable t) { }
        });
    }

    private void complete(View v){
        Intent intent = new Intent();
        intent.setClass(eisairesult.this , gotofunction.class);
        startActivity(intent);
    }
}