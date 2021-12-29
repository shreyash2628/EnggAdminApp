package com.nutan.enggadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

     TextInputLayout mobNoLayout,pwLayout;
     String mobNo,password;
     Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Admin").document("doc1");




        mobNoLayout = findViewById(R.id.mobNo);
        pwLayout = findViewById(R.id.password);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobNo = mobNoLayout.getEditText().getText().toString();
                password = pwLayout.getEditText().getText().toString();


                startActivity(new Intent(MainActivity.this,EnterData.class));
            }
        });
    }
}