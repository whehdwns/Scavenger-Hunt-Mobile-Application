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
import com.example.login.fragment.StudentGradeFragment;
import com.example.login.fragment.StudentManageFragment;
import com.example.login.fragment.StudentSettingFragment;
import com.example.login.fragment.StudentSubmissionFragment;
import com.example.login.fragment.StudentTaskFragment;
import com.example.login.test.JoinRoom;
import com.google.firebase.auth.FirebaseAuth;

public class Student extends AppCompatActivity{
    private BottomNavigationView navigationView;
    private TextView mTextMessage;
    private Toolbar toolbar;

    private FirebaseAuth auth;

    private String roomSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Intent intent = getIntent();
        roomSelected = intent.getStringExtra("roomSelected");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudentTaskFragment()).commit();

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // getSupportActionBar().setTitle("Testing");
        // SearchView searchView = (SearchView)findViewById(R.id.search_view);

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
                    //mTextMessage.setText(R.string.title_home);
                    selectFragment = new StudentTaskFragment();
                    break;
                //return true;
                case R.id.navigation_grade:
                    selectFragment = new StudentGradeFragment();
                    break;
                case R.id.navigation_submission:
                    selectFragment = new StudentSubmissionFragment();
                    break;

                case R.id.navigation_managegroup:
                    selectFragment = new StudentManageFragment();
                    break;
                // mTextMessage.setText(R.string.title_notifications);
                //  return true;
                case R.id.navigation_setting:
                    //auth.signOut();
                    //finish();
                    //Intent intent = new Intent(Instructortest.this, MainActivity.class);
                    //startActivity(intent);
                    //return true;
                    selectFragment = new StudentSettingFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
            // return false;
            return true;
        }
    };

    // @Override
    // public void onClick(View view) {
    //if (view.getId() == R.id.create){
    //   startActivity(new Intent(this, createroom.class));
    //  }
    //}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.studentmenu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg ="";
        switch (item.getItemId()){
            case R.id.join:
                startActivity(new Intent(this, FindRoom.class)); // FIND ROOM or Join ROOM?
               // startActivity(new Intent(this, JoinRoom.class));
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
