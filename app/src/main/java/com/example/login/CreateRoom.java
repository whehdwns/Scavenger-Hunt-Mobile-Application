package com.example.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateRoom extends AppCompatActivity implements View.OnClickListener {
    private EditText roomName, roomNumber, roomPassword;

    private DatabaseReference rootRef, roomRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");

        roomName = findViewById(R.id.roomName);
        roomNumber = findViewById(R.id.roomNumber);
        roomPassword = findViewById(R.id.roomPassword);

        findViewById(R.id.createButton).setOnClickListener(this);
    }

    private void createRoom() {
        String name = roomName.getText().toString().trim();
        String number = roomNumber.getText().toString().trim();
        String password = roomPassword.getText().toString().trim();

        RoomManager room = new RoomManager(name, number, password);

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
    public void onClick(View view) {
        if (view.getId() == R.id.createButton) {
            createRoom();
        }
    }
}
