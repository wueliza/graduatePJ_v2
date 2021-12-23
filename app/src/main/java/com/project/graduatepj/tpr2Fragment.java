package com.project.graduatepj;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tpr2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tpr2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText input;
    LinearLayout soildetext;                        //使用LinearLayout來代替button去實踐頁面轉換
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tpr2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tpr2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static tpr2Fragment newInstance(String param1, String param2) {
        tpr2Fragment fragment = new tpr2Fragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tpr1, null);
        soildetext = (LinearLayout) view.findViewById(R.id.soilclick);
        soildetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TPRActivity.class);              //getActivity,後的為想要跳轉的頁面
                startActivity(intent);
            }
        });
        input = view.findViewById(R.id.input);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");//自動抓時間
        Date curDate = new Date(System.currentTimeMillis()) ;
        String str = formatter.format(curDate);
        input.setText(str);
        return view;
    }

}