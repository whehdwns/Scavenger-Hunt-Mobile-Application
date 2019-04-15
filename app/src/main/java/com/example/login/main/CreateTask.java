package com.example.login.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.login.R;

public class CreateTask extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Button buttonCamera = (Button) findViewById(R.id.buttonCamera);
        Button buttonPen = (Button) findViewById(R.id.buttonPen);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateTask.this,Pop.class));
            }
        });

    }
}
