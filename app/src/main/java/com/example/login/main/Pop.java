package com.example.login.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.support.TaskManager;
import com.example.login.test.TaskSubmission;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Pop extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText editTextDescription;
    private Button buttonCreateTask, buttonSetTimer;

    private DatabaseReference rootRef, roomRef, taskRef;

    private String roomSelected, type;
    int day,month,year,hour,minute;
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        Intent intent = getIntent();
        roomSelected = intent.getStringExtra("roomSelected");
        type = intent.getStringExtra("type");

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

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 60;
        getWindow().setAttributes(params);

        findViewById(R.id.buttonCreateTask).setOnClickListener(this);
        buttonSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Pop.this,Pop.this,year,month,day);
                datePickerDialog.show();
            }
        });
    }

    private void createTask() {
        Toast.makeText(Pop.this, roomSelected, Toast.LENGTH_SHORT).show();
        String description = editTextDescription.getText().toString().trim();
        TaskManager taskManager = new TaskManager(type, description);

        String taskKey = taskRef.push().getKey();
        String submissionKey = taskRef.child(taskKey).child("Submission").push().getKey();

        if (type.equals("camera")) {
            taskRef.child(taskKey).setValue(taskManager);
            taskRef.child(taskKey).child("Submission").child(submissionKey).setValue(new TaskSubmission());

            Toast.makeText(Pop.this, "Camera task created", Toast.LENGTH_SHORT).show();
            finish();
        } else if (type.equals("pen")) {
            taskRef.child(taskKey).setValue(taskManager);
            taskRef.child(taskKey).child("Submission").child(submissionKey).setValue(new TaskSubmission());

            Toast.makeText(Pop.this, "Pen task created", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(Pop.this,Pop.this,hour,minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;

    }
    public void onClick(View view){
        if (view.getId() == R.id.buttonSetTimer) {
            startActivity(new Intent(Pop.this, Timer.class));
        } else if (view.getId() == R.id.buttonCreateTask) {
            createTask();
        }
    }
}
