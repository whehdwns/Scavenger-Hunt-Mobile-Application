package com.example.login.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.R;
import com.example.login.support.SubmissionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class GradeSubmission extends AppCompatActivity {
    private ImageView imageView;
    private TextView nameText, textViewDescription;
    private Button buttonCheckMark,buttonXMark;

    private DatabaseReference rootRef, roomRef, studentRef, submissionRef, taskRef;

    private String roomSelected, submissionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_submission);

        Intent intent = getIntent();

        roomSelected = intent.getStringExtra("roomSelected");
        submissionSelected = intent.getStringExtra("submissionSelected");

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        submissionRef = roomRef.child(roomSelected).child("Submissions").child(submissionSelected);

        nameText = findViewById(R.id.nameText);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageView = findViewById(R.id.imageView);
        buttonCheckMark = findViewById((R.id.buttonCheckMark));
        buttonXMark = findViewById((R.id.buttonXMark));
    }

    protected void onStart(){
        super.onStart();

        submissionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SubmissionManager submission = dataSnapshot.getValue(SubmissionManager.class);
                String content = submission.getContent();
                String name = submission.getName();
                final String studentKey = submission.getUid();

                studentRef = rootRef.child("Student").child(studentKey);

                nameText.setText(name);

                if(content.contains("https")) {
                    Picasso.get()
                            .load(content)
                            .into(imageView);
                }
                else {
                    imageView.setVisibility(View.INVISIBLE);
                    textViewDescription.setVisibility(View.VISIBLE);
                    textViewDescription.setText(content);
                }

                buttonCheckMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        studentRef.child("Rooms").child(roomSelected).child("Submissions").child(submissionSelected).child("grade")
                                .setValue("1");
                        submissionRef.child("grade")
                                .setValue("1");
                        finish();
                    }
                });

                buttonXMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        studentRef.child("Rooms").child(roomSelected).child("Submissions").child(submissionSelected).child("grade")
                                .setValue("0");
                        submissionRef.child("grade")
                                .setValue("0");
                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(this.toString(), databaseError.getMessage());
            }
        });
    }
}
