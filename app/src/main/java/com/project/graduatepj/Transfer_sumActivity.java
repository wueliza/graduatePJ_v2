package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transfer_sumActivity extends AppCompatActivity {
    private Button bt;
    private Button bt2;
    private RESTfulApi resTfulApi;
    String getname,getage,getbednum,getbloodtype;
    TextView confirmman,checkman,paitent_Num,paitent_name,bloodtype,age,bednum,haveblood,rqno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_sum);

        Bundle bundle = getIntent().getExtras();
        bt = findViewById(R.id.nextbt);
        bt2 = findViewById(R.id.frontbt);
        confirmman = findViewById(R.id.confirm);
        checkman = findViewById(R.id.check);
        paitent_Num = findViewById(R.id.paitentNumber);
        paitent_name = findViewById(R.id.paitent_name);
        bloodtype = findViewById(R.id.bloodtype);
        age = findViewById(R.id.age);
        bednum = findViewById(R.id.bednum);
        haveblood = findViewById(R.id.haveblood);
        rqno = findViewById(R.id.rqno);

        String confirm = bundle.getString("confirm");
        String check = bundle.getString("check");
        String paitent_num = bundle.getString("patient_num");
        ArrayList bloodbag = (ArrayList) bundle.getStringArrayList("bloodbag");
        String trans = bundle.getString("rqno");

        confirmman.setText(confirm);
        checkman.setText(check);
        paitent_Num.setText(paitent_num);
        rqno.setText(trans);
        for(int i = 1 ; i <= bloodbag.size();i++){
            haveblood.append("血袋號碼" + i + ": " + bloodbag.get(i-1)+"\n");
        }

        Retrofit retrofit = new Retrofit.Builder() //api連接
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);

        if(paitent_Num.getText() != null)
            Get_staff(retrofit,paitent_Num.getText().toString());

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 1 ; i <= bloodbag.size();i++)
                    post_transop(bloodbag.get(i-1).toString(),getname,getbloodtype,getbednum,paitent_Num.getText().toString(),checkman.getText().toString(),confirmman.getText().toString());
                Intent intent = new Intent(Transfer_sumActivity.this,blood_homeActivity.class);
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Transfer_sumActivity.this,TransferActivity.class);
                startActivity(intent);
            }
        });
    }

    private void post_transop(String rqno,String name,String bloodType,String bedNum,String qrChart,String emid,String confrimId){
        TransOperationRecord TransOperationRecord = new TransOperationRecord(rqno,name,bloodType,bedNum,qrChart,emid,confrimId);
        Call<TransOperationRecord> call = resTfulApi.post_TransOperationRecord(TransOperationRecord);

        call.enqueue(new Callback<TransOperationRecord>() {
            @Override
            public void onResponse(Call<TransOperationRecord> call, Response<TransOperationRecord> response) {
//                if(response.body() == null){
//                    text.setText("Code: " + response.code());
//                }
//                String code = "";
//                code += response.code();
//                text.setText(code);
            }

            @Override
            public void onFailure(Call<TransOperationRecord> call, Throwable t) {
                //text.setText(t.getMessage());
            }
        });

    }

    public void Get_staff(Retrofit retrofit,String id){
        RESTfulApi jsonPlaceHolderApi = retrofit.create(RESTfulApi.class);
        Call<Patient_Api> call = jsonPlaceHolderApi.getOne(id); //A00010
        call.enqueue(new Callback<Patient_Api>() {
            @Override
            public void onResponse(Call<Patient_Api> call, Response<Patient_Api> response) {
                if(!response.isSuccessful()){
                    paitent_name.setText("找不到這位病人");
                    return;
                }
                else {
                    getname = response.body().getName();
                    getage = response.body().getAge();
                    getbednum = response.body().getBedNum();
                    getbloodtype = response.body().getBloodType();

                    paitent_name.setText(getname);
                    age.setText(getage);
                    bednum.setText(getbednum);
                    bloodtype.setText(getbloodtype);
                }
            }
            @Override
            public void onFailure(Call<Patient_Api> call, Throwable t) {
                paitent_name.setText("尚未掃病歷號");
            }
        });
    }
}