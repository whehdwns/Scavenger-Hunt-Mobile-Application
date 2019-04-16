package com.example.login.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.login.R;
import com.example.login.fragment.StudentManageFragment;
import com.example.login.fragment.StudentSettingFragment;
import com.example.login.fragment.StudentSubmissionFragment;
import com.example.login.fragment.StudentTaskFragment;
import com.google.firebase.auth.FirebaseAuth;

public class Student extends AppCompatActivity{
    private Toolbar toolbar;
    private TextView mTextMessage;

    private FirebaseAuth auth;

    private String roomJoined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Intent intent = getIntent();

        roomJoined = intent.getStringExtra("roomJoined");

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setTitle("Testing");
        // SearchView searchView = (SearchView)findViewById(R.id.search_view);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.instructormenu, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg ="";
        return super.onOptionsItemSelected(item);
    }
}
