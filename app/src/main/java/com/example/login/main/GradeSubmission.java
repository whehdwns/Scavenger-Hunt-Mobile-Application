package com.example.login.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private String roomSelected,submissionSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_submission);

        Intent intent = getIntent();
        roomSelected = intent.getStringExtra("roomSelected");
        submissionSelected = intent.getStringExtra("submissionSelected");

        urlText = findViewById(R.id.urlText);
        imageView = findViewById(R.id.imageView);

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        submissionRef = roomRef.child(roomSelected).child("Submissions").child(submissionSelected);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    protected  void onStart(){
        super.onStart();
        submissionRef.child("content").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String content = dataSnapshot.getValue(String.class);

                urlText.setText(content);
                Picasso.get()
                        .load(content)
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(this.toString(), databaseError.getMessage());
            }
        });
    }
}
