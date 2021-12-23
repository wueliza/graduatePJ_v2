package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Chemogiveinfo2 extends AppCompatActivity {
    private Button sendbt , backbt;
    private TextView pa_id ,pa_name , pa_gender , pa_bed , pa_bsa ,pa_height , pa_weight , pa_age;
    private TextView staffTv , checkTv , chemoTv , mname , mdose , mfre;
    private RESTfulApi resTfulApi;
    String name ,gender,bed,BSA ,height,weight , age , staff , check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        setContentView(R.layout.activity_chemogiveinfo2);
        pa_id = findViewById(R.id.pa_id);
        pa_name = findViewById(R.id.pa_name);
        pa_gender = findViewById(R.id.pa_gender);
        pa_bed = findViewById(R.id.pa_bed);
        pa_bsa = findViewById(R.id.pa_bsa);
        pa_height = findViewById(R.id.pa_height);
        pa_weight = findViewById(R.id.pa_weight);
        pa_age = findViewById(R.id.pa_age);
        mname = findViewById(R.id.medname);
        mdose = findViewById(R.id.meddose);
        mfre = findViewById(R.id.medfre);

        staffTv = findViewById(R.id.sTv);
        checkTv = findViewById(R.id.checkTV);
        chemoTv = findViewById(R.id.chemoTv);

        Bundle scan_result = intent.getExtras();
        String patient = scan_result.getString("givepatient_id");
        staff = scan_result.getString("givestaff_id");
        check = scan_result.getString("givecheck_id");
        String chemo = scan_result.getString("givechemo_id");

        pa_id.setText(patient);
//        staffTv.setText(staff);
//        checkTv.setText(check);
        chemoTv.setText(chemo);

        sendbt = (Button)findViewById(R.id.sendbt);
        sendbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_med_give(patient,name,gender,bed,BSA,height,weight,age,staff,check,chemo);
                Intent intent = new Intent();
                intent.setClass(Chemogiveinfo2.this , Chemopm.class);
                startActivity(intent);
            }
        });
        backbt = findViewById(R.id.BackButtoni);
        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Chemogiveinfo2.this , Chemogiveinfo.class);
                startActivity(intent);
            }
        });
        //api連接
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);
        Get_patient(retrofit, patient);
        Get_staff(retrofit);
        Get_med(retrofit , chemo);

    }

    public void Get_patient(Retrofit retrofit, String id) {
        RESTfulApi jsonPlaceHolderApi = retrofit.create(RESTfulApi.class);
        Call<Patient_Api> call = resTfulApi.getOne(id); //A00010
        call.enqueue(new Callback<Patient_Api>() {
            @Override
            public void onResponse(Call<Patient_Api> call, Response<Patient_Api> response) {
                if (response.body()==null) {
                    pa_id.setText("此id不存在");
                    return;
                }
                else {
                    //int id = response.body().getPatientNum();
                    name = response.body().getName();
                    gender = response.body().getSex();
                    bed = response.body().getBedNum();
                    BSA = response.body().getName();
                    height = response.body().getHeight();
                    weight = response.body().getWeight();
                    age = response.body().getAge();
                    pa_name.setText(name);
                    pa_gender.setText(gender);
                    pa_bed.setText(bed);

                    pa_height.setText(height);
                    pa_weight.setText(weight);
                    pa_age.setText(age);
                    Double h = Double.parseDouble(height);
                    Double w = Double.parseDouble(weight);
                    Double ibw = (Math.pow(h/100 , 2) * 22);
                    Double abw , b;
                    if(w > ibw){
                        abw = ibw + 0.25 * (w - ibw);
                        b = Math.pow(h * abw /3600 , 0.5);
                        b = Math.round(b*100.0)/100.0;
                        BSA = b.toString();
                    }
                    else{
                        b = Math.pow(h * w /3600 , 1/2);
                        BSA = b.toString();
                    }
                    pa_bsa.setText(BSA);
                }
            }

            @Override
            public void onFailure(Call<Patient_Api> call, Throwable t) {
                pa_id.setText("錯誤");
            }
        });
    }
    public void Get_staff(Retrofit retrofit) {
        RESTfulApi jsonPlaceHolderApi = retrofit.create(RESTfulApi.class);
        Call<Staff_Api> call = resTfulApi.get_staff(staff); //A00010
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
                staffTv.setText("錯誤！");
            }
        });
        Call<Staff_Api> call2 = resTfulApi.get_staff(check); //A00010
        call2.enqueue(new Callback<Staff_Api>() {
            @Override
            public void onResponse(Call<Staff_Api> call, Response<Staff_Api> response) {
                if (response.body()==null) {
                    checkTv.setText("此id不存在，請重新掃描員工編號！");
                    return;
                }
                else {
                    String name = response.body().getName();
                    checkTv.setText(name);
                }
            }

            @Override
            public void onFailure(Call<Staff_Api> call, Throwable t) {
                checkTv.setText("錯誤！");
            }
        });
    }
    public void Get_med(Retrofit retrofit, String id) {
        Call<Medicine_Api> call = resTfulApi.get_medicine(id);
        call.enqueue(new Callback<Medicine_Api>() {
            @Override
            public void onResponse(Call<Medicine_Api> call, Response<Medicine_Api> response) {
                if (response.body()==null) {
                    chemoTv.setText("此id不存在，請重新掃描成品編號！");
                    return;
                }
                else {
                    MedDtails[] a = response.body().getMedDetails();

                    String medname = "";
                    String d = "";
                    String f = "";
                    int n= 1;
                    for(MedDtails as :a){
                        medname += "藥品"+ n +": " +as.getName() + "\n";
                        d += "劑量" + n + ": " + as.getDose() + "\n";
                        f += "流速" + n +": " + as.getFrequence() + "\n";
                        n++;
                    }
                    mname.setText(medname);
                    mdose.setText(d);
                    mfre.setText(f);
//                    String name = response.body().getmedicineName();
//                    mname.setText("名稱：" + name);
//                    String d = response.body().getDose();
//                    mdose.setText("劑量："+ d);
//                    String f = response.body().getFrequence();
//                    mfre.setText("流速：" + f);
                }
            }
            @Override
            public void onFailure(Call<Medicine_Api> call, Throwable t) {
                chemoTv.setText("錯誤！");
            }
        });
    }
    private void post_med_give(String qrChart, String name, String sex, String bedNum, String bsa, String height, String weight, String age, String emid, String confirmId, String tubg){
        MedGiveRecord medGiveRecord = new MedGiveRecord(qrChart,  name,  sex,  bedNum,  bsa,  height,  weight,  age,  emid,  confirmId, tubg);
        Call<MedGiveRecord> call = resTfulApi.post_MedGiveRecord(medGiveRecord);

        call.enqueue(new Callback<MedGiveRecord>() {
            @Override
            public void onResponse(Call<MedGiveRecord> call, Response<MedGiveRecord> response) {
            }

            @Override
            public void onFailure(Call<MedGiveRecord> call, Throwable t) {
            }
        });

    }
}