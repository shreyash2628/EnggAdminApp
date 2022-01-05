package com.nutan.enggadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EnterData extends AppCompatActivity {
 //   TextView colgNameTv,colgAddTv,feesTv,cetScoreTv,placementPercentageTv;

    EditText colgNameEt,colgAddEt,feesEt,cetScoreEt,placementPercentageEt;
    Button imgBtn,saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);


        colgNameEt =findViewById(R.id.colg_name_et);
        colgAddEt =findViewById(R.id.address_Et);
        feesEt =findViewById(R.id.feesEt);
        cetScoreEt =findViewById(R.id.cetScoreEt);
        placementPercentageEt =findViewById(R.id.placementPercentageEt);

        imgBtn = findViewById(R.id.colgImgIdBtn);
        saveBtn = findViewById(R.id.SaveBtnId);


    }
}