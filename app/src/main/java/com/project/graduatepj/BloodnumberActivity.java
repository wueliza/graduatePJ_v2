package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class BloodnumberActivity extends AppCompatActivity {
    private Button bt;
    private Button bt2;
    TextView bloodnum,bloodtype;
    //Bundle bundleget = getIntent().getExtras();
    //Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodnumber);

        bt = findViewById(R.id.nextbt);
        bt2 = findViewById(R.id.frontbt);
        bloodnum = findViewById(R.id.bloodnum);
        bloodtype = findViewById(R.id.bloodtype);

        //String transfer = bundleget.getString("transfer");
        //String nurse = bundleget.getString("nurse");
        //String bloodnum = bundleget.getString("bloodnum");
        //bundle.putString("transfer", transfer);
        //bundle.putString("nurse", nurse);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodnumberActivity.this,Sign_sumActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodnumberActivity.this,SignActivity.class);
                startActivity(intent);
            }
        });
    }
}