package com.example.login.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.login.R;
import com.google.firebase.database.DatabaseReference;

public class Student extends AppCompatActivity {
    private DatabaseReference rootRef, roomRef, studentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Intent intent = getIntent();

        String roomJoined = intent.getStringExtra("roomJoined");


    }
}
