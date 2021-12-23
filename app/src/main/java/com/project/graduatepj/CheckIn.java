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
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckIn extends AppCompatActivity {
    Button bt;
    Button bt2;
    SurfaceView surfaceView;
    TextView textView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    Bundle bundle = new Bundle();
    private TextView show;
    private RESTfulApi resTfulApi;
    ArrayList<String> ORA4 = new ArrayList<>();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        show = findViewById(R.id.show);
        textView = (TextView) findViewById(R.id.input);
        getPermissionsCamera();

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        textView = (TextView) findViewById(R.id.input);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
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
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0) {
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
        TextView tv = (TextView) findViewById(R.id.title);
        TextView tv1 = (TextView) findViewById(R.id.input);
        TextView tv2 = (TextView) findViewById(R.id.show);
        TextView hint = (TextView) findViewById(R.id.hint);

        bt = findViewById(R.id.nextbt);             //下一頁
        bt2 = findViewById(R.id.frontbt);           //上一頁

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                switch (count) {
                    case 1:
                        hint.setText("請掃描手圈病歷號");
//                        tv.setText("手圈病歷號");
                        tv1.setText("手圈病歷號");
                        tv2.setText("號碼");
                        break;
                    case 2:
                        hint.setText("請掃描確認員號碼");
//                        tv.setText("確認員號碼");
                        tv1.setText("確認員號碼");
                        tv2.setText("號碼");
                        break;
                    case 3:
                        Intent intent = new Intent(CheckIn.this, CheckIn2.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;

                    default:
//                        tv.setText("病歷號");
                        tv1.setText("病歷號");
                        tv2.setText("號碼");

                }
            }
        });
        bt.setEnabled(false);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                switch (count) {

                    case 1:
                        hint.setText("請掃描手圈病歷號");
//                        tv.setText("手圈病歷號");
                        tv1.setText("手圈病歷號");
                        tv2.setText("號碼");

                        break;

                    case 2:
                        hint.setText("請掃描檢驗員號碼");
//                        tv.setText("檢驗員");
                        tv1.setText("檢驗員");
                        tv2.setText("號碼");
                        break;
                    case -1:
                        Intent intent = new Intent(CheckIn.this, OperationHome.class);
                        startActivity(intent);
                        break;

                    default:
                        hint.setText("請掃描病歷號號碼");
//                        tv.setText("病歷號");
                        tv1.setText("病歷號");
                        tv2.setText("號碼");

                }
            }
        });
////相機

    }

    private void getPermissionsCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }
///相機結束


    private void Get_staff(Retrofit retrofit, String id) {

        Call<Staff_Api> call = resTfulApi.get_staff(id); //A00010
        Call<Patient_Api> patient_apiCall = resTfulApi.getOne(id);//手圈病歷號
        Call<ORA4_CHART_API> ora4_chart_apiCall = resTfulApi.get_ora4Chart(id);  //病歷號

        if (count == 0) {
            ora4_chart_apiCall.enqueue(new Callback<ORA4_CHART_API>() {
                @Override
                public void onResponse(Call<ORA4_CHART_API> ora4_chart_apiCall, Response<ORA4_CHART_API> response) {
                    if (response.body() == null) {
                        show.setText("找不到這個id");
                        bt.setEnabled(false);
                        return;
                    } else {

                        //String ora4Chart = response.body().getPatients();
                        show.setText("掃描成功 請按下一步");

                        Patients[] a = response.body().getPatients();
                        if (count == 0) {
                            for (Patients as : a) {
                                ORA4.add(as.getQrChart());
                            }
                        }

                        bt.setEnabled(true);
                        bundle.putString("ora4chart", id);

                    }

                }

                @Override
                public void onFailure(Call<ORA4_CHART_API> call, Throwable t) {
                    show.setText("請掃描條碼");
                }
            });


        } else if (count == 1) {
            patient_apiCall.enqueue(new Callback<Patient_Api>() {
                @Override
                public void onResponse(Call<Patient_Api> patient_apiCall, Response<Patient_Api> response) {
                    if (response.body() == null) {
                        show.setText("找不到這個id");
                        bt.setEnabled(false);
                        return;

                    }

                    if (ORA4.contains(id)) {
                        String name = response.body().getName();
                        String birth = response.body().getBirthDate();
                        show.setText(name);
                        bt.setEnabled(true);
                        bundle.putString("paitentNumbercheck", id);
                        bundle.putString("NameBox", show.getText().toString());
                        bundle.putString("birth", birth);
                    } else {
                        show.setText("此號碼不在病歷號裡面");
                        bt.setEnabled(false);
                    }

                }

                @Override
                public void onFailure(Call<Patient_Api> call, Throwable t) {
                    show.setText("請掃描條碼");
                }
            });
        } else {
            call.enqueue(new Callback<Staff_Api>() {
                @Override
                public void onResponse(Call<Staff_Api> call, Response<Staff_Api> response) {
                    if (response.body() == null) {
                        show.setText("找不到這個id");
                        bt.setEnabled(false);
                        return;
                    }
                    String emid = response.body().getName();
                    show.setText(emid);
                    bt.setEnabled(true);
                    bundle.putString("ManCheckBox", show.getText().toString());
                }

                @Override
                public void onFailure(Call<Staff_Api> call, Throwable t) {
                    show.setText("請掃描條碼");
                }
            });
        }

    }
}