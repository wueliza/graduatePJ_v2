package com.project.graduatepj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class eisaicheck extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    private SurfaceView surfaceView;
    private TextView step , result , show;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private Button nextBt , upstepbt;
    int count = 0;
    Bundle bundle = new Bundle();
    Intent intent = new Intent();
    private RESTfulApi resTfulApi;
    ZXingScannerView zXingScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eisaicheck);
//        getPermissionsCamera();             //取得相機權限
        zXingScannerView = findViewById(R.id.ZXingScannerView_QRCode);
        //取得相機權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this
                        , Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    100);
        }else{
            openQRCamera();

        }
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        step=(TextView)findViewById(R.id.ehint1); //
        result = (TextView)findViewById(R.id.ehint2);
        show = (TextView)findViewById(R.id.ehint3);
        nextBt = (Button)findViewById(R.id.nextbt);
        upstepbt = (Button)findViewById(R.id.upStep_bt);

        nextBt.setOnClickListener(this::nextStep);
        upstepbt.setOnClickListener(this::upStep);
        step.setText("請掃描檢驗員員工編號條碼");
        intent.setClass(eisaicheck.this , eisairesult.class);
        nextBt.setEnabled(false);

        //camera
//        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
//        cameraSource = new CameraSource.Builder(this,barcodeDetector)
//                .setRequestedPreviewSize(1920, 1080)
//                .setAutoFocusEnabled(true)
//                .build();
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
//            @Override
//            public void surfaceCreated(@NonNull SurfaceHolder holder) {
//                if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA)
//                        !=PackageManager.PERMISSION_GRANTED)
//                    return;
//                try{
//                    cameraSource.start(holder);
//
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
//                cameraSource.stop();
//            }
//        });
//
//        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){
//            @Override
//            public void release() {
//
//            }
//
//            @Override
//            public void receiveDetections(Detector.Detections<Barcode> detections) {
//                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
//                if(qrCodes.size()!=0){
//                    result.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            result.setText(qrCodes.valueAt(0).displayValue); //qrcode result
//                        }
//                    });
//                }
//            }
//        });

        //api連接
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);
        //監視TextView是否有更變
        result.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (count == 0) {
                    Get_staff(retrofit, editable.toString());
                }
                else if (count == 1) {
                    Get_patient(retrofit, editable.toString());
                }
                else if (count == 2) {
                    Get_eisai(retrofit, editable.toString());
                }
            }
        });
    }

    private void upStep(View view) {
        if(count == 0){
            Intent uintent = new Intent();
            uintent.setClass(eisaicheck.this , gotofunction.class);
            startActivity(uintent);
        }
        else if(count == 1){
            step.setText("請掃描檢驗員員工編號條碼");
            show.setText("");
            result.setText("");
            count = 0;
        }
        else if(count == 2){
            step.setText("請掃描病歷號條碼");
            show.setText("");
            result.setText("");
            count = 1;
        }
    }

    private void nextStep(View V){
        if(count == 0 ){
            bundle.putString("staff_id", show.getText().toString());
            intent.putExtras(bundle);
            show.setText(" ");
            result.setText(" ");
            count = 1;
            step.setText("請掃描病歷號條碼");
            nextBt.setEnabled(false);
        }
        else if(count == 1){
            bundle.putString("patient_id", show.getText().toString());
            intent.putExtras(bundle);
            show.setText(" ");
            result.setText(" ");
            step.setText("請掃描衛材條碼");
            nextBt.setText("傳送");
            count = 2;
            nextBt.setEnabled(false);
        }
        else if(count == 2){
            bundle.putString("eisai_id", result.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
            nextBt.setEnabled(false);
        }
    }

//    private void getPermissionsCamera() {
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
//        }
//    }

    public void Get_staff(Retrofit retrofit, String id) {
        RESTfulApi jsonPlaceHolderApi = retrofit.create(RESTfulApi.class);
        Call<Staff_Api> call = resTfulApi.get_staff(id); //A00010
        call.enqueue(new Callback<Staff_Api>() {
            @Override
            public void onResponse(Call<Staff_Api> call, Response<Staff_Api> response) {
                if (response.body()==null) {
                    if(count == 0) {
                        step.setText("此id不存在，請重新掃描員工編號！");
                    }
                    else if(count == 1) {
                        step.setText("此id不存在，請重新掃描病歷號！");
                    }
                    if(count == 2) {
                        step.setText("此id不存在，請重新掃描衛材號碼！");
                    }
                    return;
                }
                else {
                    String name = response.body().getName();
                    show.setText(name);
                    step.setText("掃描成功，請按下一步");
                    nextBt.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Staff_Api> call, Throwable t) {
                step.setText("請重新掃描員工編號！");
            }
        });
    }
    public void Get_patient(Retrofit retrofit, String id) {
        RESTfulApi jsonPlaceHolderApi = retrofit.create(RESTfulApi.class);
        Call<Patient_Api> call = resTfulApi.getOne(id); //A00010
        call.enqueue(new Callback<Patient_Api>() {
            @Override
            public void onResponse(Call<Patient_Api> call, Response<Patient_Api> response) {
                if (response.body()==null) {
                    if(count == 0) {
                        step.setText("此id不存在，請重新掃描員工編號！");
                    }
                    else if(count == 1) {
                        step.setText("此id不存在，請重新掃描病歷號！");
                    }
                    if(count == 2) {
                        step.setText("此id不存在，請重新掃描衛材號碼！");
                    }
                    return;
                }
                else {
                    String name = response.body().getName();
                    show.setText(name);
                    step.setText("掃描成功，請按下一步");
                    nextBt.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Patient_Api> call, Throwable t) {
                step.setText("請重新掃描病歷號！");
            }
        });
    }
    public void Get_eisai(Retrofit retrofit, String id) {
        Call<Eisai_Api> call = resTfulApi.get_eisai(id);
        call.enqueue(new Callback<Eisai_Api>() {
            @Override
            public void onResponse(Call<Eisai_Api> call, Response<Eisai_Api> response) {
                if (response.body()==null) {
                    if(count == 0) {
                        step.setText("此id不存在，請重新掃描員工編號！");
                    }
                    else if(count == 1) {
                        step.setText("此id不存在，請重新掃描病歷號！");
                    }
                    if(count == 2) {
                        step.setText("此id不存在，請重新掃描衛材號碼！");
                    }
                    return;
                }
                else {
                    String name = response.body().getName();
                    show.setText(name);
                    step.setText("掃描成功，請按傳送");
                    nextBt.setEnabled(true);
                }
            }
            @Override
            public void onFailure(Call<Eisai_Api> call, Throwable t) {
                step.setText("請重新掃描衛材條碼！");
            }
        });
    }

    private void openQRCamera() {
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }
    /**取得權限回傳*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults[0] ==0){
            openQRCamera();
        }else{
            Toast.makeText(this, "權限勒？", Toast.LENGTH_SHORT).show();
        }
    }

    /**關閉QRCode相機*/
    @Override
    protected void onStop() {
        zXingScannerView.stopCamera();
        super.onStop();
    }
    @Override
    public void handleResult(Result rawResult) {
        TextView tvResult = findViewById(R.id.input);
        tvResult.setText(rawResult.getText());
        //ZXing相機預設掃描到物件後就會停止，以此這邊再次呼叫開啟，使相機可以為連續掃描之狀態
        openQRCamera();
    }
}
