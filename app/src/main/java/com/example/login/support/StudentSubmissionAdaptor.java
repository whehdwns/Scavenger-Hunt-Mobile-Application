package com.example.login.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.login.R;

import java.util.ArrayList;

public class StudentSubmissionAdaptor extends ArrayAdapter<SubmissionManager> {

    public StudentSubmissionAdaptor(Context context, ArrayList<SubmissionManager> submissions) {
        super(context, 0, submissions);
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        SubmissionManager submission = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.student_submission_display, container, false);
        }

        TextView submissionDescription = view.findViewById(R.id.submissionDescription);
        TextView submissionGrade = view.findViewById(R.id.submissionGrade);

        submissionDescription.setText(submission.getDescription());

        if (submission.getGrade().isEmpty()) {
            submissionGrade.setText("Pending");
        } else {
            submissionGrade.setText(submission.getGrade() + "/1");
        }

        return view;
    }
}
