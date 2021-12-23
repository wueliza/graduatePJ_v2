package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_sumActivity extends AppCompatActivity {
    private Button bt;
    private Button bt2;
    private RESTfulApi resTfulApi;
    private RadioButton y , n;
    int check_re = 0;
    TextView nurse,transfer,bloodnum,bloodtype,transop ,hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_sum);

        Bundle bundle = getIntent().getExtras();
        bt = findViewById(R.id.nextbt);
        bt2 = findViewById(R.id.frontbt);
        nurse = findViewById(R.id.nurse);
        transfer = findViewById(R.id.transfer);
        bloodnum = findViewById(R.id.bloodnum);
        bloodtype = findViewById(R.id.bloodtype);
        transop = findViewById(R.id.transop);
        y = findViewById(R.id.yes);
        n = findViewById(R.id.no);
        hint = findViewById(R.id.hint);

        String nurseman = bundle.getString("nurse");
        String transferman = bundle.getString("transfer");
        String bloodnumcount = bundle.getString("bloodnum");
        String patients = bundle.getString("patient");
        String transoper = bundle.getString("transop");

        nurse.setText(nurseman);
        transfer.setText(transferman);
        bloodnum.setText(bloodnumcount+"袋");
        transop.setText(transoper);


        Retrofit retrofit = new Retrofit.Builder() //api連接
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);

        Get_staff(retrofit,patients);

        y.setOnClickListener(this::onRadioButtonClicked);
        n.setOnClickListener(this::onRadioButtonClicked);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_re == 1) {
                    post_bloodbagsign(transop.getText().toString(), bloodtype.getText().toString(), nurse.getText().toString(), transfer.getText().toString(), bloodnum.getText().toString());
                    Intent intent = new Intent(Sign_sumActivity.this, blood_homeActivity.class);
                    startActivity(intent);
                }
                else{
                    hint.setText("未完成簽收請勿上傳資料");
                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_sumActivity.this,SignActivity.class);
                startActivity(intent);
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
    private void post_bloodbagsign(String rqno,String bloodType,String emid,String transId,String bloodAmount){
        BloodBagSignRecord bloodBagSignRecord = new BloodBagSignRecord(rqno,bloodType,emid,transId,bloodAmount);
        Call<BloodBagSignRecord> call = resTfulApi.post_BloodBagSignRecord(bloodBagSignRecord);

        call.enqueue(new Callback<BloodBagSignRecord>() {
            @Override
            public void onResponse(Call<BloodBagSignRecord> call, Response<BloodBagSignRecord> response) {
//                if(response.body() == null){
//                    text.setText("Code: " + response.code());
//                }
//                String code = "";
//                code += response.code();
//                text.setText(code);
            }

            @Override
            public void onFailure(Call<BloodBagSignRecord> call, Throwable t) {
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
                    bloodtype.setText("恭喜沒有血型！");
                    return;
                }
                String type = response.body().getBloodType();
                bloodtype.setText(type);
            }

            @Override
            public void onFailure(Call<Patient_Api> call, Throwable t) {
                bloodtype.setText("no bloodtype");
            }
        });
    }
}