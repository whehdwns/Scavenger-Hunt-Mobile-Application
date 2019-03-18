package com.example.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateRoom extends AppCompatActivity implements View.OnClickListener {
    EditText roomName, roomNumber, roomPassword;
    Button createButton;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        user = FirebaseAuth.getInstance().getCurrentUser();

        roomName = (EditText) findViewById(R.id.roomName);
        roomNumber = (EditText) findViewById(R.id.roomNumber);
        roomPassword = (EditText) findViewById(R.id.roomPassword);

        findViewById(R.id.createButton).setOnClickListener(this);
    }

    private void createRoom() {
        String name = roomName.getText().toString().trim();
        String number = roomNumber.getText().toString().trim();
        String password = roomPassword.getText().toString().trim();
        RoomManager room = new RoomManager(name, number, password);
        FirebaseDatabase.getInstance().getReference("Rooms")
                .child(FirebaseDatabase.getInstance().getReference("Rooms").push().getKey())
                .setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
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
