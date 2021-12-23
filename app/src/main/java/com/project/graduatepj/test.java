package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class test extends AppCompatActivity {
    private TextView text;
    private EditText in;
    private RESTfulApi resTfulApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        in = findViewById(R.id.in);
        text = findViewById(R.id.text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);

        in.addTextChangedListener(new TextWatcher() { //監視editText是否有更變
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(in.getText().length() == 8){
                    getQrchart(retrofit,editable.toString());
                }
                //show.setText(editable);
            }
        });
    }

    private void getQrchart(Retrofit retrofit,String id) {
        Call<Patient_Api> call = resTfulApi.getOne(id);

        call.enqueue(new Callback<Patient_Api>() {
            @Override
            public void onResponse(Call<Patient_Api> call, Response<Patient_Api> response) {
                if (!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                }

                String content = "";
                content += "qrChart: " + response.body().getQrChart() + "\n";
                content += "sex: " + response.body().getSex() + "\n";
                content += "name: " + response.body().getName() + "\n";
                content += "division: " + response.body().getDivision() + "\n";
                content += "height: " + response.body().getHeight() + "\n";
                content += "age: " + response.body().getAge() + "\n";
                content += "weight: " + response.body().getWeight() + "\n";
                content += "bsnos: " + response.body().getBsnos() + "\n";
                content += "birthDate: " + response.body().getBirthDate() + "\n";
                content += "ora4Chart: " + response.body().getOra4Chart() + "\n";
                text.append(content);
            }

            @Override
            public void onFailure(Call<Patient_Api> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }
}