package com.project.graduatepj;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tpr1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tpr1Fragment extends Fragment {
    private Button bt;
    private Button bt2;
    private RESTfulApi resTfulApi;
    EditText input,T,P,R1,BP1,BP2;
    TextView patient,nurse;
    LinearLayout soildetext;                        //使用LinearLayout來代替button去實踐頁面轉換
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tpr1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tpr1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static tpr1Fragment newInstance(String param1, String param2) {
        tpr1Fragment fragment = new tpr1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        Button fronbt = (Button) getView().findViewById(R.id.frontbt);
//        fronbt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(tpr1Fragment.this,TransferActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_tpr1, container, false);
        View view = inflater.inflate(R.layout.fragment_tpr1, null);
        soildetext = (LinearLayout) view.findViewById(R.id.soilclick);
        Bundle bundle = getArguments();;
        String patient_num = bundle.getString("patient");

        soildetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TPRActivity.class);              //getActivity,後的為想要跳轉的頁面
                startActivity(intent);
            }
        });
        input = view.findViewById(R.id.input);
        patient = view.findViewById(R.id.patient);
        T = view.findViewById(R.id.T);
        P = view.findViewById(R.id.P);
        R1 = view.findViewById(R.id.R);
        BP1 = view.findViewById(R.id.BP1);
        BP2 = view.findViewById(R.id.BP2);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss"); //自動抓時間
        Date curDate = new Date(System.currentTimeMillis()) ;
        String str = formatter.format(curDate);
        input.setText(str);
        patient.setText(patient_num);

        return view;
    }

    private void post_tpr1(String transTime,String T,String P,String R,String BP1,String BP2,String QrChart,String Emid){
        Tpr1Record tpr1Record = new Tpr1Record(transTime,T,P,R,BP1,BP2,QrChart,Emid);
        Call<Tpr1Record> call = resTfulApi.post_Tpr1Record(tpr1Record);

        call.enqueue(new Callback<Tpr1Record>() {
            @Override
            public void onResponse(Call<Tpr1Record> call, Response<Tpr1Record> response) {
//                if(response.body() == null){
//                    text.setText("Code: " + response.code());
//                }
//                String code = "";
//                code += response.code();
//                text.setText(code);
            }

            @Override
            public void onFailure(Call<Tpr1Record> call, Throwable t) {
                //text.setText(t.getMessage());
            }
        });

    }
}