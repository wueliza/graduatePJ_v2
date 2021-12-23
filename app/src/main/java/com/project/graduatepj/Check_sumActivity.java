package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Check_sumActivity extends AppCompatActivity {
    private Button bt;
    private Button bt2;
    TextView confirmman,checkman,paitent_Num,paitent_name,bloodtype,age,bednum,transop,context;
    private RESTfulApi resTfulApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sum);

        bt = findViewById(R.id.nextbt);
        bt2 = findViewById(R.id.frontbt);
        Bundle bundle = getIntent().getExtras();
        context = findViewById(R.id.context);
        confirmman = findViewById(R.id.confirm);
        checkman = findViewById(R.id.check);
        paitent_Num = findViewById(R.id.qrchart);
        paitent_name = findViewById(R.id.name);
        bloodtype = findViewById(R.id.bloodtype);
        age = findViewById(R.id.age);
        bednum = findViewById(R.id.bednum);
        transop = findViewById(R.id.transop);

        String confirm = bundle.getString("confirm");
        String check = bundle.getString("check");
        String paitent_num = bundle.getString("patient" );
        String transo_num = bundle.getString("trans");

        confirmman.setText(confirm);
        checkman.setText(check);
        paitent_Num.setText(paitent_num);
        transop.setText(transo_num);

        Retrofit retrofit = new Retrofit.Builder() //api連接
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);

        Get_staff(retrofit,paitent_num);
        gettransop(retrofit,transo_num);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_check(transop.getText().toString(),paitent_name.getText().toString(),bloodtype.getText().toString(),bednum.getText().toString(),paitent_Num.getText().toString(),checkman.getText().toString(),confirmman.getText().toString());
                Intent intent = new Intent(Check_sumActivity.this,blood_homeActivity.class);
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Check_sumActivity.this,confirmActivity.class);
                startActivity(intent);
            }
        });
    }

    private void post_check(String Rqno,String Name,String Bloodtype,String BedNum,String QrChart,String Emid,String ConfrimId){
        BloodCheckRecord bloodCheckRecord = new BloodCheckRecord(Rqno,Name,Bloodtype,BedNum,QrChart,Emid,ConfrimId);
        Call<BloodCheckRecord> call = resTfulApi.post_BloodCheckRecord(bloodCheckRecord);

        call.enqueue(new Callback<BloodCheckRecord>() {
            @Override
            public void onResponse(Call<BloodCheckRecord> call, Response<BloodCheckRecord> response) {
//                if(response.body() == null){
//                    text.setText("Code: " + response.code());
//                }
//                String code = "";
//                code += response.code();
//                text.setText(code);
            }

            @Override
            public void onFailure(Call<BloodCheckRecord> call, Throwable t) {
                //text.setText(t.getMessage());
            }
        });
    }

    public void Get_staff(Retrofit retrofit,String id){
        Call<Patient_Api> call = resTfulApi.getOne(id); //A00010
        call.enqueue(new Callback<Patient_Api>() {
            @Override
            public void onResponse(Call<Patient_Api> call, Response<Patient_Api> response) {
                if (response.body()==null) {
                    bloodtype.setText("恭喜沒有這個人！");
                    return;
                }
                String type = response.body().getBloodType();
                String name = response.body().getName();
                String bed_num = response.body().getBedNum();
                String patient_age = response.body().getAge();
                bloodtype.setText(type);
                paitent_name.setText(name);
                bednum.setText(bed_num);
                age.setText(patient_age);
            }

            @Override
            public void onFailure(Call<Patient_Api> call, Throwable t) {
                bloodtype.setText("no this man");
            }
        });
    }
    private void gettransop(Retrofit retrofit,String id){
        Call<TransOperation_Api> call = resTfulApi.get_transoperation(id);

        call.enqueue(new Callback<TransOperation_Api>() {
            @Override
            public void onResponse(Call<TransOperation_Api> call, Response<TransOperation_Api> response) {
                if(response.body()!=null) {
                    BloodBanks[] a = response.body().getBloodBanks();

                    String content = "";
                    int n= 1;
                    for(BloodBanks as :a){
                        content += "血袋"+ n +": " +as.getBlnos() + "\n";
                        n++;
                    }
                    context.setText(content);
                }
                else {
                    context.setText("Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TransOperation_Api> call, Throwable t) {
                context.setText(t.getMessage());
            }
        });
    }
}