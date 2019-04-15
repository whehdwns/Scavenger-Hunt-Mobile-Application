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
import com.example.login.fragment.gradefragment;
import com.example.login.fragment.managefragment;
import com.example.login.fragment.settingfragment;
import com.example.login.fragment.submissionfragment;
import com.example.login.fragment.taskfragment;
import com.google.firebase.auth.FirebaseAuth;

public class Instructor extends AppCompatActivity{
    //Toolbar toolbar;
    private TextView mTextMessage;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // getSupportActionBar().setTitle("Testing");
        // SearchView searchView = (SearchView)findViewById(R.id.search_view);
        //mTextMessage = (TextView) findViewById(R.id.message);

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
                    selectFragment = new taskfragment();
                    break;
                //return true;
                case R.id.navigation_grade:
                    selectFragment = new gradefragment();
                    break;
                //mTextMessage.setText(R.string.title_dashboard);
                //return true;
                case R.id.navigation_submission:
                    selectFragment = new submissionfragment();
                    break;

                case R.id.navigation_managegroup:
                    selectFragment = new managefragment();
                    break;
                // mTextMessage.setText(R.string.title_notifications);
                //  return true;
                case R.id.navigation_setting:
                    //auth.signOut();
                    //finish();
                    //Intent intent = new Intent(Instructortest.this, MainActivity.class);
                    //startActivity(intent);
                    //return true;
                    selectFragment = new settingfragment();
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
        getMenuInflater().inflate(R.menu.instructormenu, menu);
        return true;
        // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg ="";
        switch (item.getItemId()){
            case R.id.task:
                //msg ="task";
                startActivity(new Intent(this, CreateTask.class));
                break;
            case R.id.create:
                startActivity(new Intent(this, CreateRoom.class));
                break;
            case R.id.edit:
                msg ="Edit";
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
