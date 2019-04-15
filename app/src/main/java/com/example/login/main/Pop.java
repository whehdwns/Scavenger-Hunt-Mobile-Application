package com.example.login.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.login.R;

public class Pop extends AppCompatActivity {
        EditText editTextDescription;
        Button buttonCreateTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        Button buttonCreateTask = (Button) findViewById(R.id.buttonCreateTask);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        WindowManager.LayoutParams params =  getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 60;
        getWindow().setAttributes(params);
    }
}
