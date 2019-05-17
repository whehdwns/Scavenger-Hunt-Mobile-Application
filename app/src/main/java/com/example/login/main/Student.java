package com.example.login.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.login.R;
import com.example.login.fragment.StudentManage;
import com.example.login.fragment.StudentSetting;
import com.example.login.fragment.StudentSubmission;
import com.example.login.fragment.StudentTask;

public class Student extends AppCompatActivity{
    private BottomNavigationView navigationView;
    private TextView mTextMessage;
    private Toolbar toolbar;

    private String roomSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Intent intent = getIntent();
        roomSelected = intent.getStringExtra("roomSelected");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudentTask()).commit();

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTextMessage = findViewById(R.id.message);
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_task:
                    selectFragment = new StudentTask();
                    break;
                case R.id.navigation_submission:
                    selectFragment = new StudentSubmission();
                    break;
                case R.id.navigation_managegroup:
                    selectFragment = new StudentManage();
                    break;
                case R.id.navigation_setting:
                    selectFragment = new StudentSetting();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg ="";

        switch (item.getItemId()){
            case R.id.join:
                startActivity(new Intent(this, FindRoom.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getRoomSelected() {
        return roomSelected;
    }

    public void setRoomSelected(String roomSelected) {
        this.roomSelected = roomSelected;
    }
}
