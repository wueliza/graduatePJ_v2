package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chemopm3 extends AppCompatActivity {
    private Button sendbt , upbt;
    private TextView staffTv , checkTv , chemoTv , mednameTv , medsumTv , hint;
    private RadioButton y , n;
    int check_re = 0;
    Integer medamount = 0;
    private RESTfulApi resTfulApi;
    String medName , medNum , staff , check , chemo,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemopm3);
        Intent intent = this.getIntent();
        Bundle scan_result = intent.getExtras();
        staff = scan_result.getString("staff_id");
        check = scan_result.getString("check_id");
        chemo = scan_result.getString("chemo_id");
        sendbt = (Button)findViewById(R.id.sendbt);
        upbt = findViewById(R.id.cpfrontbt);
        staffTv = findViewById(R.id.cpstaffTv);
        checkTv = findViewById(R.id.cpcheckTv);
        chemoTv = findViewById(R.id.cpchemoTv);
        mednameTv = findViewById(R.id.cpmedTv);
        medsumTv = findViewById(R.id.cpamountTv);
        hint = findViewById(R.id.hint);
        y = findViewById(R.id.yes);
        n = findViewById(R.id.no);

//        staffTv.setText(staff);
        checkTv.setText(check);
        chemoTv.setText(chemo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);

        Get_staff(retrofit , staff);
        Get_med(retrofit , chemo);

        y.setOnClickListener(this::onRadioButtonClicked);
        n.setOnClickListener(this::onRadioButtonClicked);


        sendbt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_re == 1){
                    medsign(staff,check,chemo,medamount.toString(),medName);
                    Intent nintent = new Intent();
                    nintent.setClass(Chemopm3.this , Chemopm.class);
                    startActivity(nintent);
                }
                else{
                    hint.setText("未完成簽收請勿上傳資料");
                }

            }
        });
        upbt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uintent = new Intent();
                uintent.setClass(Chemopm3.this , Chemopm2.class);
                startActivity(uintent);
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.yes:
                if (checked)
                    check_re = 1;
                break;
            case R.id.no:
                if (checked)
                    check_re = 0;
                break;
        }
    }

    private void medsign(String emid,String transId,String tubg,String medAmount,String name){

        MedSignRecord medSignRecord = new MedSignRecord(emid,transId,tubg,medAmount,name);
        Call<MedSignRecord> call = resTfulApi.post_MedSignRecord(medSignRecord);

        call.enqueue(new Callback<MedSignRecord>() {
            @Override
            public void onResponse(Call<MedSignRecord> call, Response<MedSignRecord> response) {
                String code = "";
                code += response.code();
                Log.i("code:",code);
            }

            @Override
            public void onFailure(Call<MedSignRecord> call, Throwable t) {
                //text.setText(t.getMessage());
            }
        });
    }

    public void Get_med(Retrofit retrofit, String id) {
        Call<Medicine_Api> call = resTfulApi.get_medicine(id);
        call.enqueue(new Callback<Medicine_Api>() {
            @Override
            public void onResponse(Call<Medicine_Api> call, Response<Medicine_Api> response) {
                if (response.body()==null) {
                    mednameTv.setText("此id不存在，請重新掃描成品編號！");
                    return;
                }
                else {
                    MedDtails[] a = response.body().getMedDetails();

                    medName = "";
                    medNum = "";
                    int n= 1;
                    for(MedDtails as :a){
                        medName += "藥品"+ n +": " +as.getName() + "\n";
                        medNum += "劑量" + n + ": " + as.getDose() + "\n";
                        n++;
                        medamount++;
                    }
                    mednameTv.setText(medNum);
                    medsumTv.setText(medName);

                }
            }
            @Override
            public void onFailure(Call<Medicine_Api> call, Throwable t) {
                mednameTv.setText("錯誤！");
            }
        });
    }
    public void Get_staff(Retrofit retrofit, String id) {
        RESTfulApi jsonPlaceHolderApi = retrofit.create(RESTfulApi.class);
        Call<Staff_Api> call = resTfulApi.get_staff(id); //A00010
        call.enqueue(new Callback<Staff_Api>() {
            @Override
            public void onResponse(Call<Staff_Api> call, Response<Staff_Api> response) {
                if (response.body()==null) {
                    staffTv.setText("此id不存在，請重新掃描員工編號！");
                    return;
                }
                else {
                    String name = response.body().getName();
                    staffTv.setText(name);

                }
            }

            @Override
            public void onFailure(Call<Staff_Api> call, Throwable t) {
                staffTv.setText("請重新掃描員工編號！");
            }
        });
    }
}