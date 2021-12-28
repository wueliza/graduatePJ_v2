package com.project.graduatepj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
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

public class TPRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private Button bt;
    private Button bt2;
    private RESTfulApi resTfulApi;
    SurfaceView surfaceView;
    TextView textView,step,show;
    Bundle bundle = new Bundle();
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    int count = 0;
    ZXingScannerView zXingScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpractivity);
        zXingScannerView = findViewById(R.id.ZXingScannerView_QRCode);
        step = (TextView) findViewById(R.id.step);
        show = findViewById(R.id.show);

        //取得相機權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this
                        , Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    100);
        }else{
            openQRCamera();

        }
        //相機製作
//        getPermissionsCamera();
//        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        textView=(TextView)findViewById(R.id.input);
//        barcodeDetector = new BarcodeDetector.Builder(this)
//                .setBarcodeFormats(Barcode.ALL_FORMATS).build();
//
//        cameraSource = new CameraSource.Builder(this,barcodeDetector)
//                .setRequestedPreviewSize(1920, 1080)
//                .setAutoFocusEnabled(true)
//                .build();
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
//            @Override
//            public void surfaceCreated(@NonNull SurfaceHolder holder) {
//                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED)
//                    return;
//                try{
//                    cameraSource.start(holder);
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
//       barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){

//            @Override
//            public void release() {
//
//            }
//
//            @Override
//            public void receiveDetections(Detector.Detections<Barcode> detections) {
//                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
//                if(qrCodes.size()!=0){
//                    textView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText(qrCodes.valueAt(0).displayValue);
//                        }
//                    });
//                }
//            }
//        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);

        textView.addTextChangedListener(new TextWatcher() { //監視editText是否有更變
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(textView.getText().toString() != null){
                    Get_staff(retrofit,editable.toString());
                }
                //show.setText(editable);
            }
        });

//

        TextView tv = (TextView)findViewById(R.id.title);
        bt = findViewById(R.id.nextbt);
        bt2 = findViewById(R.id.frontbt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                switch (count){
                    case 1:
                        tv.setText("輸血TPR-領血單號");
                        step.setText("請掃領血單號!");
                        break;
                    case 2:
                        tv.setText("輸血TPR-紀錄者");
                        step.setText("請掃紀錄者!");
                        break;
                    case 3:
                        Intent intent = new Intent(TPRActivity.this,PagerActivity.class);
                        intent.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent);
                        break;
                    default:
                        tv.setText("輸血TPR-手圈病歷號");
                        step.setText("請掃手圈病歷號!");
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
                        tv.setText("輸血TPR-領血單號");
                        step.setText("請掃領血單號");
                        break;
                    case 2:
                        tv.setText("輸血TPR-紀錄者");
                        step.setText("請掃紀錄者!");
                        break;
                    case -1:
                        Intent intent = new Intent(TPRActivity.this,blood_homeActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        tv.setText("輸血TPR-手圈病歷號");
                        step.setText("請掃手圈病歷號!");
                }
            }
        });
    }
//    private void getPermissionsCamera() {
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
//        }
//    }
    private void Get_staff(Retrofit retrofit,String id) {
        Call<Patient_Api> patient = resTfulApi.getOne(id);
        Call<Staff_Api> staff = resTfulApi.get_staff(id);
        Call<TransOperation_Api> trans = resTfulApi.get_transoperation(id);

        if(count == 0) {
            patient.enqueue(new Callback<Patient_Api>() {
                @Override
                public void onResponse(Call<Patient_Api> patient, Response<Patient_Api> response) {
                    if (response.body()==null) {
                        step.setText("此id不存在，請重新掃描手圈病歷號！");
                        bt.setEnabled(false);
                        return ;
                    }
                    String name = response.body().getName();
                    show.setText(name);
                    step.setText("掃描成功，請按下一步");
                    bundle.putString("patient_num", id);
                    bt.setEnabled(true);
                }

                @Override
                public void onFailure(Call<Patient_Api> patient, Throwable t) {
                    show.setText(t.getMessage());
                }
            });
        }
        else if(count == 2){
            staff.enqueue(new Callback<Staff_Api>() {
                @Override
                public void onResponse(Call<Staff_Api> staff, Response<Staff_Api> response) {
                    if (response.body()==null) {
                        step.setText("此id不存在，請重新掃描紀錄者！");
                        bt.setEnabled(false);
                        return;
                    }
                    String name = response.body().getName();
                    show.setText(name);
                    bundle.putString("confirm", show.getText().toString());
                    step.setText("掃描成功，請按下一步");
                    bt.setEnabled(true);
                }

                @Override
                public void onFailure(Call<Staff_Api> staff, Throwable t) {
                    show.setText("請掃描條碼");
                }
            });
        }
        else{
            trans.enqueue(new Callback<TransOperation_Api>() {
                @Override
                public void onResponse(Call<TransOperation_Api> staff, Response<TransOperation_Api> response) {
                    if (response.body()==null) {
                        step.setText("此id不存在，請重新掃描領血單號！");
                        bt.setEnabled(false);
                        return;
                    }
                    String name = response.body().getRqno();
                    show.setText(name);
                    bundle.putString("rqno", show.getText().toString());
                    step.setText("掃描成功，請按下一步");
                    bt.setEnabled(true);
                }

                @Override
                public void onFailure(Call<TransOperation_Api> staff, Throwable t) {
                    show.setText("請掃描條碼");
                }
            });
        }
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