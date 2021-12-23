package com.project.graduatepj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.TextView;

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

public class confirmActivity extends AppCompatActivity {
    private Button bt;
    private Button bt2;
    private TextView show;
    SurfaceView surfaceView;
    TextView textView,step;
    private RESTfulApi resTfulApi;
    Bundle bundle = new Bundle();
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        show = findViewById(R.id.show);

        //相機製作
        getPermissionsCamera();

        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        textView=(TextView)findViewById(R.id.input);
        step=(TextView)findViewById(R.id.ehint1);

        Retrofit retrofit = new Retrofit.Builder() //api連接
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
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });

        TextView tv = (TextView)findViewById(R.id.title);
        bt = findViewById(R.id.nextbt);
        bt2 = findViewById(R.id.frontbt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                switch (count){
                    case 1:
                        tv.setText("核血作業-核血人員");
                        step.setText("請掃核血人員！");
                        break;
                    case 2:
                        tv.setText("核血作業-確認人員");
                        step.setText("請掃確認人員！");
                        break;
                    case 3:
                        Intent intent = new Intent(confirmActivity.this,Check_sumActivity.class);
                        intent.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent);
                        break;
                    default:
                        tv.setText("核血作業-領血單號");
                        step.setText("請掃領血單號！");
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
                        tv.setText("核血作業-核血人員");
                        step.setText("請掃核血人員！");
                        break;
                    case 2:
                        tv.setText("核血作業-確認人員");
                        step.setText("請掃確認人員！");
                        break;
                    case -1:
                        Intent intent = new Intent(confirmActivity.this,blood_homeActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        tv.setText("核血作業-領血單號");
                        step.setText("請掃領血單號！");
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

    public void Get_staff(Retrofit retrofit,String id){
        Call<TransOperation_Api> trans = resTfulApi.get_transoperation(id);
        Call<Staff_Api> staff_apiCall = resTfulApi.get_staff(id);

        if(count == 0){
            trans.enqueue(new Callback<TransOperation_Api>() {
                @Override
                public void onResponse(Call<TransOperation_Api> patient_apiCall1, Response<TransOperation_Api> response) {
                    if (response.body()==null) {
                        step.setText("此id不存在，請重新掃描領血單號！");
                        bt.setEnabled(false);
                        return;
                    }
                    String patient = response.body().getQrChart();
                    bundle.putString("patient",patient);
                    step.setText("掃描成功，請按下一步");
                    show.setText(id);
                    bundle.putString("trans", id);
                    bt.setEnabled(true);
                }
                @Override
                public void onFailure(Call<TransOperation_Api> patient_apiCall1, Throwable t) {
                    show.setText("請掃描條碼");
                }
            });
        }
        else {
            staff_apiCall.enqueue(new Callback<Staff_Api>() {
                @Override
                public void onResponse(Call<Staff_Api> call, Response<Staff_Api> response) {
                    if (response.body()==null) {
                        switch (count) {
                            case 1:
                                step.setText("此id不存在，請重新掃描核血人員編號！");
                                break;
                            case 2:
                                step.setText("此id不存在，請重新掃描確認人員編號！");
                                break;
                            case -1:
                                break;
                            default:
                                step.setText("此id不存在，請重新掃描領血單號！");
                        }
                        bt.setEnabled(false);
                        return;
                    }
                    String name = response.body().getName();
                    step.setText("掃描成功，請按下一步");
                    show.setText(name);
                    switch (count) {
                        case 1:
                            bundle.putString("confirm", show.getText().toString());
                            step.setText("掃描成功，請按下一步");
                            break;
                        case 2:
                            bundle.putString("check", show.getText().toString());
                            step.setText("掃描成功，請按下一步");
                            break;
                        case -1:
                            break;
                        default:
                    }
                    bt.setEnabled(true);
                }

                @Override
                public void onFailure(Call<Staff_Api> call, Throwable t) {
                    show.setText("請掃描條碼");
                }
            });
        }
    }
}