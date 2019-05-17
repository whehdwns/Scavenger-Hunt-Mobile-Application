package com.example.login.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.login.R;
import com.example.login.support.SubmissionManager;

import java.util.ArrayList;

public class InstructorSubmissionAdaptor extends ArrayAdapter<SubmissionManager> {

    public InstructorSubmissionAdaptor(Context context, ArrayList<SubmissionManager> submissions) {
        super(context, 0, submissions);
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        SubmissionManager submission = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.adaptor_submission_instructor, container, false);
        }

        TextView submissionName = view.findViewById(R.id.submissionName);
        TextView submissionId = view.findViewById(R.id.submissionId);
        TextView submissionDescription = view.findViewById(R.id.submissionDescription);

        submissionName.setText(submission.getName());
        submissionId.setText(submission.getId());
        submissionDescription.setText(submission.getDescription());

        return view;
    }
}
