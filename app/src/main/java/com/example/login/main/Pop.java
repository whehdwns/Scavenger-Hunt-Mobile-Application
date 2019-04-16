package com.example.login.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.support.TaskManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Pop extends AppCompatActivity {
    private EditText editTextDescription;
    private Button buttonCreateTask, buttonSetTimer;

    private DatabaseReference rootRef, roomRef, taskRef;

    private String roomSelected, questionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        Intent intent = getIntent();
        roomSelected = intent.getStringExtra("roomSelected");
        questionType = intent.getStringExtra("questionType");

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        taskRef = roomRef.child(roomSelected).child("Tasks");


        editTextDescription = findViewById(R.id.editTextDescription);
        buttonCreateTask = findViewById(R.id.buttonCreateTask);
        buttonSetTimer = findViewById(R.id.buttonSetTimer);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9),(int)(height*0.7));

        WindowManager.LayoutParams params =  getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 60;
        getWindow().setAttributes(params);
    }

    private void createTask() {
        Toast.makeText(Pop.this, roomSelected, Toast.LENGTH_SHORT).show();
        String taskDescription = editTextDescription.getText().toString().trim();
        TaskManager taskManager = new TaskManager(questionType, taskDescription);

        if (questionType.equals("camera")) {
            taskRef.push()
                    .setValue(taskManager);
            Toast.makeText(Pop.this, "Camera task created", Toast.LENGTH_SHORT).show();
            finish();
        } else if (questionType.equals("pen")) {
            taskRef.push()
                    .setValue(taskManager);
            Toast.makeText(Pop.this, "Pen task created", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onClick(View view){
        if(view.getId() == R.id.buttonSetTimer) {
            startActivity(new Intent(Pop.this, Timer.class));
        }else if(view.getId() == R.id.buttonCreateTask) {
            createTask();
        }
    }
}
