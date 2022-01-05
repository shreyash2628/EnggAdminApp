package com.nutan.enggadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterData extends AppCompatActivity {

    String colgNameStr,colgAddStr,feesStr,cetScoreStr,placementPercentageStr,colgDescriptionStr;
    EditText colgNameEt,colgAddEt,feesEt,cetScoreEt,placementPercentageEt,addBranch,colgDescription;
    Button imgBtn,uploadDataBtn,AddBranchBtn,doneBtn;
    String branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);

        colgDescription = findViewById(R.id.ColgDescriptionEt);
        addBranch = findViewById(R.id.addBranchesEt);
        colgNameEt =findViewById(R.id.colg_name_et);
        colgAddEt =findViewById(R.id.address_Et);
        feesEt =findViewById(R.id.feesEt);
        cetScoreEt =findViewById(R.id.cetScoreEt);
        placementPercentageEt =findViewById(R.id.placementPercentageEt);

        imgBtn = findViewById(R.id.AddcolgImgId);
        uploadDataBtn = findViewById(R.id.upload_Data_Btn);

        AddBranchBtn = findViewById(R.id.AddBranchBtn);
        doneBtn = findViewById(R.id.DoneBtnId);


        branch=addBranch.getText().toString();

            if(branch.isEmpty())
            {
                doneBtn.setVisibility(View.INVISIBLE);

            }

        List<String> branchList = new ArrayList<>();

        AddBranchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch=addBranch.getText().toString();



                branchList.add(branch);

                doneBtn.setVisibility(View.VISIBLE);
                Toast.makeText(EnterData.this,branch + " Branch Added", Toast.LENGTH_SHORT).show();
                   addBranch.setText(null);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //upload branch list to firebase


                Toast.makeText(EnterData.this, "Added successfully", Toast.LENGTH_SHORT).show();
            }
        });


        Map<String,String> map = new HashMap<String,String>();
        uploadDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colgNameStr = colgNameEt.getText().toString();
                colgAddStr = colgAddEt.getText().toString();
                feesStr = feesEt.getText().toString();
                cetScoreStr = cetScoreEt.getText().toString();
                placementPercentageStr = placementPercentageEt.getText().toString();
                colgDescriptionStr = colgDescription.getText().toString();

                map.put("collegeName",colgNameStr);
                map.put("Address",colgAddStr);
                map.put("Fees",feesStr);
                map.put("PlacementPercentage",placementPercentageStr);
                map.put("CollegeDescription",colgDescriptionStr);
                map.put("CetScore",cetScoreStr);

                Toast.makeText(EnterData.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
}