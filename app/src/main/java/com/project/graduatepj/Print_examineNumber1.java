package com.project.graduatepj;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

public class Print_examineNumber1 extends AppCompatActivity {
    Button bt;
    Button bt2;

    Bundle bundle = new Bundle();
    private TextView show;
    SurfaceView surfaceView;
    TextView textView , hint;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    int count = 0;
    private RESTfulApi resTfulApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_examine_number1);

        show = findViewById(R.id.show);
        getPermissionsCamera();

        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        textView=(TextView)findViewById(R.id.input);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource = new CameraSource.Builder(this,barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
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
            public void receiveDetections(Detector.Detections<Barcode>detections) {
                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(qrCodes.valueAt(0).displayValue);
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

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textView.getText().toString() != null) {
                    Get_staff(retrofit, editable.toString());
                }
                //show.setText(editable);
            }
        });
        //API結束 ， 下面還有


        TextView tv = (TextView)findViewById(R.id.title);
        TextView tv1 = (TextView)findViewById(R.id.input);
        TextView tv2 = (TextView)findViewById(R.id.show);
        hint = (TextView) findViewById(R.id.hint);
        bt = findViewById(R.id.nextbt);
        bt2 = findViewById(R.id.frontbt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                switch (count){
                    case 1:
                        hint.setText("請掃描檢驗單編號");
//                        tv.setText("檢驗單");
                        tv1.setText("檢驗單");
                        tv2.setText("號碼");
                        break;
                    case 2:
                        Intent intent = new Intent(Print_examineNumber1.this, Print_examineNumber2.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    default:
//                        tv.setText("手圈病歷號");
                        tv1.setText("手圈病歷號");
                        tv2.setText("號碼");
                }
            }
        });

        bt.setEnabled(false);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                switch (count){
                    case 1:
                        hint.setText("請掃描檢驗單編號");
//                        tv.setText("檢驗單");
                        tv1.setText("檢驗單");
                        tv2.setText("號碼");
                        break;
                    case -1:
                        Intent intent = new Intent(Print_examineNumber1.this, examine_homePage.class);
                        startActivity(intent);
                        break;
                    default:
                        hint.setText("請掃描手圈病例號");
//                        tv.setText("手圈病歷號");
                        tv1.setText("手圈病歷號");
                        tv2.setText("號碼");
                }
            }
        });
    }
    private void getPermissionsCamera() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
    }

    public void Get_staff(Retrofit retrofit, String id) {

        //RESTfulApi jsonPlaceHolderApi = retrofit.create(RESTfulApi.class);
        Call<Staff_Api> call = resTfulApi.get_staff(id); //A00010
        Call<Patient_Api> patient_apiCall = resTfulApi.getOne(id);//手圈病歷號
        Call<CheckOperation_Api> checkOperation_apiCall = resTfulApi.get_checkoperation(id);

        if (count == 0) {
            patient_apiCall.enqueue(new Callback<Patient_Api>() {
                @Override
                public void onResponse(Call<Patient_Api> patient_apiCall, Response<Patient_Api> response) {
                    if (response.body() == null) {
                        hint.setText("找不到這個id，請重新掃描病歷號");
                        bt.setEnabled(false);
                        return;
                    }
                    String name = response.body().getName();
                    show.setText(name);
                    bt.setEnabled(true);
                    bundle.putString("patientNumber1Check", show.getText().toString());
                    hint.setText("掃描完成，請按下一步！");
                    /*, show.getText().toString()*/
                }

                @Override
                public void onFailure(Call<Patient_Api> call, Throwable t) {
                    show.setText("請掃描條碼");
                }
            });
        } else {
            checkOperation_apiCall .enqueue(new Callback<CheckOperation_Api>() {
                @Override
                public void onResponse(Call<CheckOperation_Api> checkOperation_apiCall, Response<CheckOperation_Api> response) {
                    if (response.body() == null) {
                        show.setText("找不到這個id，請重新掃描檢體編號");
                        bt.setEnabled(false);
                        return;
                    }
                    String bsnos = response.body().getBsnos();
                    show.setText(bsnos);
                    bt.setEnabled(true);
                    bundle.putString("sampleNumberCheck", id);
                    hint.setText("掃描完成，請按下一步！");
                }
                @Override
                public void onFailure(Call<CheckOperation_Api> call, Throwable t) {
                    show.setText("請掃描條碼");
                }
            });
        }

    }
}



















