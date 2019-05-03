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
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.support.TaskManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Pop extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText editTextDescription;
    private Button buttonCreateTask, buttonSetTimer;
    private RadioButton yesButton, noButton;

    private DatabaseReference rootRef, roomRef, taskRef;

    private String roomSelected, type;

    private int dayStart, monthStart, yearStart, hourStart, minuteStart;
    private int dayEnd, monthEnd, yearEnd, hourEnd, minuteEnd;

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
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSetTimer.setVisibility(View.VISIBLE);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSetTimer.setVisibility(View.GONE);
            }
        });

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
        findViewById(R.id.buttonSetTimer).setOnClickListener(this);
    }

    private void createTask() {
        Toast.makeText(Pop.this, roomSelected, Toast.LENGTH_SHORT).show();
        String description = editTextDescription.getText().toString().trim();

        String timeStart;
        String timeEnd;

        if (minuteEnd != 0) {
            timeStart = dayStart + "_" + monthStart + "_" + yearStart + "_" + hourStart + "_" + minuteStart;
            timeEnd = dayEnd + "_" + monthEnd + "_" + yearEnd + "_" + hourEnd + "_" + minuteEnd;
        } else {
            timeStart = "";
            timeEnd = "";
        }

        TaskManager taskManager = new TaskManager(type, description, timeStart, timeEnd);

        String taskKey = taskRef.push().getKey();
        //String submissionKey = taskRef.child(taskKey).child("Submission").push().getKey();

        if (type.equals("camera")) {
            taskRef.child(taskKey).setValue(taskManager);
            //taskRef.child(taskKey).child("Submission").child(submissionKey).setValue(new TaskSubmission());

            Toast.makeText(Pop.this, "Camera task created", Toast.LENGTH_SHORT).show();
            finish();
        } else if (type.equals("pen")) {
            taskRef.child(taskKey).setValue(taskManager);
            //taskRef.child(taskKey).child("Submission").child(submissionKey).setValue(new TaskSubmission());

            Toast.makeText(Pop.this, "Pen task created", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearEnd = year;
        monthEnd = month;
        dayEnd = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hourStart = c.get(Calendar.HOUR_OF_DAY);
        minuteStart = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(Pop.this,Pop.this, hourStart, minuteStart, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourEnd = hourOfDay;
        minuteEnd = minute;
    }

    public void onClick(View view){
        if (view.getId() == R.id.buttonSetTimer) {
            Calendar c = Calendar.getInstance();

            yearStart = c.get(Calendar.YEAR);
            monthStart = c.get(Calendar.MONTH);
            dayStart = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Pop.this, Pop.this, yearStart, monthStart, dayStart);
            datePickerDialog.show();

            createTask();
        } else if (view.getId() == R.id.buttonCreateTask) {
            createTask();
        }
    }
}
