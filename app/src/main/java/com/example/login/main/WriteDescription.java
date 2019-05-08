package com.example.login.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.login.R;
import com.example.login.support.SubmissionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WriteDescription extends AppCompatActivity {
    private Button buttonSubmit;
    private EditText editText;

    private FirebaseUser mUser;
    private DatabaseReference rootRef, roomRef, studentRef, submissionRef, taskRef;


    private String roomSelected, taskSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_description);

        Intent intent = getIntent();

        roomSelected = intent.getStringExtra("roomSelected");
        taskSelected = intent.getStringExtra("taskSelected");

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        taskRef = roomRef.child(roomSelected).child("Tasks");
        studentRef = rootRef.child("Student").child(mUser.getUid());
        submissionRef = taskRef.child(taskSelected).child("Submissions");

        buttonSubmit = findViewById(R.id.buttonSubmit);
        editText = findViewById(R.id.editText);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String content = editText.getText().toString();
                        String id = dataSnapshot.child("id").getValue(String.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String uid = mUser.getUid();
                        String submissionKey = submissionRef.push().getKey();

                        getWriteDescription(content, id, name, uid, submissionKey);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(this.toString(), databaseError.getMessage());
                    }
                });
            }
        });
    }

    private void getWriteDescription(final String content, final String id, final String name, final String uid, final String submissionKey) {
        taskRef.child(taskSelected).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String description = dataSnapshot.child("description").getValue(String.class);

                SubmissionManager submissionManager = new SubmissionManager(content, "", id, name, description, uid);

                roomRef.child(roomSelected).child("Submissions").child(submissionKey).setValue(submissionManager);
                studentRef.child("Rooms").child(roomSelected).child("Submissions").child(submissionKey).setValue(submissionManager);

                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(this.toString(), databaseError.getMessage());
            }
        });
    }
}
