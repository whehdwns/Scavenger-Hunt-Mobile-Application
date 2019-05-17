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
import android.widget.Toast;

import com.example.login.R;
import com.example.login.fragment.InstructorGrade;
import com.example.login.fragment.InstructorManage;
import com.example.login.fragment.InstructorSetting;
import com.example.login.fragment.InstructorSubmission;
import com.example.login.fragment.InstructorTask;

public class Instructor extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private Toolbar toolbar;

    private String roomSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        Intent intent = getIntent();
        roomSelected = intent.getStringExtra("roomSelected");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InstructorTask()).commit();

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                    selectFragment = new InstructorTask();
                    break;
                case R.id.navigation_grade:
                    selectFragment = new InstructorGrade();
                    break;
                case R.id.navigation_submission:
                    selectFragment = new InstructorSubmission();
                    break;
                case R.id.navigation_managegroup:
                    selectFragment = new InstructorManage();
                    break;
                case R.id.navigation_setting:
                    selectFragment = new InstructorSetting();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_instructor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.task:
                Intent intent = new Intent(this, SelectTask.class);
                intent.putExtra("roomSelected", roomSelected);

                if (roomSelected != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Select Room", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.create:
                startActivity(new Intent(this, CreateRoom.class));
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
