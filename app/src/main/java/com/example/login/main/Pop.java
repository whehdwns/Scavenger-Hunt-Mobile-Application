package com.example.login.main;


import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


import com.example.login.R;

public class Pop extends AppCompatActivity {
    EditText editTextDescription;
    Button buttonCreateTask;
    //Button setTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        Button setTimer= (Button) findViewById(R.id.setTimer);
        Button createTask =(Button)findViewById(R.id.buttonCreateTask);

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
    public void onClick(View view){
        if(view.getId() == R.id.setTimer) {
            startActivity(new Intent(Pop.this, Timer.class));
        }else if(view.getId() == R.id.buttonCreateTask) {

        }
    }
}