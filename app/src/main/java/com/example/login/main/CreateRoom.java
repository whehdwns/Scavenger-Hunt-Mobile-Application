package com.example.login.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.support.RoomManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateRoom extends AppCompatActivity implements View.OnClickListener {
    private EditText roomName, roomNumber, roomPassword;

    private FirebaseUser mUser;
    private DatabaseReference rootRef, instructorRef, roomRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        rootRef = FirebaseDatabase.getInstance().getReference();
        instructorRef = rootRef.child("Instructor");
        roomRef = rootRef.child("Rooms");

        roomName = findViewById(R.id.roomName);
        roomNumber = findViewById(R.id.roomNumber);
        roomPassword = findViewById(R.id.roomPassword);

        roomPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    createRoom();
                }
                return false;
            }
        });

        findViewById(R.id.createButton).setOnClickListener(this);
    }

    private void createRoom() {
        instructorRef.child(mUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String instructor = dataSnapshot.child("name").getValue(String.class);
                String name = roomName.getText().toString().trim();
                String number = roomNumber.getText().toString().trim();
                String password = roomPassword.getText().toString().trim();

                RoomManager room = new RoomManager(instructor, name, number, password);

                roomRef.child(roomRef.push().getKey())
                        .setValue(room)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateRoom.this, "Room created", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(CreateRoom.this, Instructor.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(this.toString(), databaseError.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createButton) {
            createRoom();
        }
    }
}
