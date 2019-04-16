package com.example.login.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.login.R;

public class CreateTask extends AppCompatActivity implements View.OnClickListener {
    private Button buttonCamera, buttonPen;

    private String roomSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Intent intent = getIntent();
        roomSelected = intent.getStringExtra("roomSelected");

        buttonCamera = findViewById(R.id.buttonCamera);
        buttonPen = findViewById(R.id.buttonPen);

        findViewById(R.id.buttonCamera).setOnClickListener(this);
        findViewById(R.id.buttonPen).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCamera) {
            Intent intent = new Intent(CreateTask.this, Pop.class);
            intent.putExtra("questionType", "camera");
            intent.putExtra("roomSelected", roomSelected);

            startActivity(intent);
        } else if (view.getId() == R.id.buttonPen) {

        }
    }
}
