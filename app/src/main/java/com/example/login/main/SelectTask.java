package com.example.login.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.login.R;

public class SelectTask extends AppCompatActivity implements View.OnClickListener {
    private String roomSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Intent intent = getIntent();
        roomSelected = intent.getStringExtra("roomSelected");

        findViewById(R.id.buttonCamera).setOnClickListener(this);
        findViewById(R.id.buttonPen).setOnClickListener(this);
        findViewById(R.id.buttonCancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCamera) {
            Intent intent = new Intent(SelectTask.this, CreateTask.class);
            intent.putExtra("type", "camera");
            intent.putExtra("roomSelected", roomSelected);

            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.buttonPen) {
            Intent intent = new Intent(SelectTask.this, CreateTask.class);
            intent.putExtra("type", "pen");
            intent.putExtra("roomSelected", roomSelected);

            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.buttonCancel) {
            finish();
        }
    }
}
