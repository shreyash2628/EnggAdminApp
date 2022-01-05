package com.nutan.enggadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

     TextInputLayout mobNoLayout,pwLayout;
     String mobNo,password;
     Button login;
     TextView invalidTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Admin").document("doc1");


        invalidTv = findViewById(R.id.invalidCredentialsTv);
        mobNoLayout = findViewById(R.id.mobNo);
        pwLayout = findViewById(R.id.password);
        login = findViewById(R.id.login_button);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   mobNo = mobNoLayout.getEditText().getText().toString();
                password = pwLayout.getEditText().getText().toString();
                startActivity(new Intent(MainActivity.this, EnterData.class));


                mobNo = Objects.requireNonNull(mobNoLayout.getEditText().getText()).toString().trim();


                if (!(mobNo.isEmpty() || mobNo.length() < 10)) {
                    db.collection("Admin").whereEqualTo("mobile", mobNo).limit(1)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                db.collection("Admin").whereEqualTo("password", password)
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            //change activity

                                        } else {
                                            invalidTv.setVisibility(View.VISIBLE);
                                        }

                                    }
                                });

                            }


                        }


                        //onclicl method end
                    });


                }

            }
        });


    }}



