package com.example.login.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.R;
import com.squareup.picasso.Picasso;

public class ViewSubmission extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private TextView textViewDescription;

    private String taskContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submission);

        Intent intent = getIntent();

        taskContent = intent.getStringExtra("taskContent");

        textViewDescription = findViewById(R.id.textViewDescription);
        imageView = findViewById(R.id.imageView);

        findViewById(R.id.buttonDone).setOnClickListener(this);
    }

    protected void onStart() {
        super.onStart();

        if (taskContent.contains("https")) {
            Picasso.get()
                    .load(taskContent)
                    .into(imageView);
        } else {
            imageView.setVisibility(View.INVISIBLE);
            textViewDescription.setVisibility(View.VISIBLE);
            textViewDescription.setText(taskContent);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonDone) {
            finish();
        }
    }
}
