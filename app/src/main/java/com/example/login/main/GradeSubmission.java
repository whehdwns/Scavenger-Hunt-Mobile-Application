package com.example.login.main;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class GradeSubmission extends AppCompatActivity {
    private TextView urlText;
    private ImageView imageView;
    private FirebaseUser mUser;
    private DatabaseReference rootRef, roomRef, studentRef, submissionRef, taskRef;
    private String roomSelected,taskSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_submission);
        urlText = (TextView)findViewById(R.id.urlText);
        imageView = (ImageView)findViewById(R.id.imageView);
        Intent intent = getIntent();
        taskSelected = intent.getStringExtra("taskSelected");
        roomSelected = intent.getStringExtra("roomSelected");

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        taskRef = roomRef.child(roomSelected).child("Tasks");
        submissionRef = taskRef.child(taskSelected).child("Submissions");

        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    protected  void onStart(){
        super.onStart();
        submissionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                urlText.setText(message);
                Picasso.get()
                        .load(message)
                        .into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
