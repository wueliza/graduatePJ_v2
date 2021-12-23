package com.project.graduatepj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;



import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chemopm2 extends AppCompatActivity {
    private Button nextbt , upbt;
    private TextView hint1 , hint2 , hint3;
    Bundle bundle = new Bundle();
    Intent intent = new Intent();
    int count = 0;
    private RESTfulApi resTfulApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemopm2);
        intent.setClass(Chemopm2.this , Chemopm3.class);

        nextbt = findViewById(R.id.cpnextBt);
        upbt = findViewById(R.id.cpfrontbt);
        hint1 = findViewById(R.id.cphint1);
        hint2 = findViewById(R.id.cphint2);
        hint3 = findViewById(R.id.cphint3);
        hint1.setText("請掃描成品單號");
        nextbt.setEnabled(false);

        upbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                    Intent uintent = new Intent();
                    uintent.setClass(Chemopm2.this , Chemopm.class);
                    startActivity(uintent);
                    nextbt.setEnabled(false);
                }
                else if(count == 1){
                    hint1.setText("請掃描成品單號");
                    count = 0;
                    nextbt.setEnabled(false);
                }
                else if(count == 2){
                    hint1.setText("請掃描護理人員編號");
                    count = 1;
                    nextbt.setEnabled(false);
                }
            }
        });

        nextbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                    bundle.putString("chemo_id", hint2.getText().toString());
                    intent.putExtras(bundle);
                    hint1.setText("請掃描護理人員編號");
                    hint3.setText(" ");
                    count = 1;
                    nextbt.setEnabled(false);
                }
                else if(count == 1){
                    bundle.putString("staff_id", hint2.getText().toString());
                    intent.putExtras(bundle);
                    hint1.setText("請掃描傳送人員編號");
                    hint3.setText(" ");
                    nextbt.setText("傳送");
                    count = 2;

                }
                else if (count == 2){
                    bundle.putString("check_id", hint2.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });

        SurfaceView surfaceView;
        CameraSource cameraSource;
        BarcodeDetector barcodeDetector;

        getPermissionsCamera();

        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(this,barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                try{
                    cameraSource.start(holder);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    hint2.post(new Runnable() {
                        @Override
                        public void run() {
                            hint2.setText(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });

        //api連接
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);
        //監視TextView是否有更變
        hint2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (count == 0) {
                    Get_Medicine(retrofit, editable.toString());
                }
                else if (count == 1) {
                    Get_staff(retrofit, editable.toString());
                }
                else if (count == 2) {
                    hint1.setText("掃描成功，請按下一步");
                }
                //result.setText(editable);
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
                    hint1.setText("此id不存在，請重新掃描員工編號！");
                    return;
                }
                else {
                    String name = response.body().getName();
                    hint3.setText(name);
                    hint1.setText("掃描成功，請按下一步");
                    nextbt.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Staff_Api> call, Throwable t) {
                hint1.setText("請重新掃描員工編號！");
            }
        });
    }

    private void getPermissionsCamera() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
    }
    public void Get_Medicine(Retrofit retrofit, String id) {
        Call<Medicine_Api> call = resTfulApi.get_medicine(id); //A00010
        call.enqueue(new Callback<Medicine_Api>() {
            @Override
            public void onResponse(Call<Medicine_Api> call, Response<Medicine_Api> response) {
                if (response.body()==null) {
                    hint1.setText("此id不存在，請重新掃描成品單號！");
                    return;
                }
                else {
                    String name = response.body().getTubg();
                    hint3.setText(name);
                    hint1.setText("掃描成功，請按下一步");
                    nextbt.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Medicine_Api> call, Throwable t) {
                hint1.setText("請重新掃描成品單號！");
            }
        });
    }
}
