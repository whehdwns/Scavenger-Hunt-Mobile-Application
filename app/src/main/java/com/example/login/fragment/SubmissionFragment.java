package com.example.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.main.GradeSubmission;
import com.example.login.main.Instructor;
import com.example.login.support.SubmissionAdaptor;
import com.example.login.support.SubmissionManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubmissionFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> submissionKeyList;
    private ArrayList<SubmissionManager> submissionList;
    private SubmissionAdaptor submissionAdaptor;

    private DatabaseReference rootRef, roomRef, submissionRef;
    private Query submissionQuery;

    private String roomSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.submissionfragment, container, false);

        roomSelected = ((Instructor) getActivity()).getRoomSelected();

        if (roomSelected == null) {
            return inflater.inflate(R.layout.fragment_empty_task, container, false);
        }

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        submissionRef = roomRef.child(roomSelected).child("Submissions");
        submissionQuery = submissionRef.orderByChild("name");

        listView = view.findViewById(R.id.listView);
        submissionKeyList = new ArrayList<>();
        submissionList = new ArrayList<>();
        submissionAdaptor = new SubmissionAdaptor(getActivity(), submissionList);

        listView.setAdapter(submissionAdaptor);
        submissionQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                submissionKeyList.clear();
                submissionList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String submissionKey = dataSnapshot1.getKey();
                    SubmissionManager submissionManager = dataSnapshot1.getValue(SubmissionManager.class);

                    if (submissionManager.getGrade().isEmpty()) {
                        submissionKeyList.add(submissionKey);
                        submissionList.add(submissionManager);
                        submissionAdaptor.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(this.toString(), databaseError.getMessage());
            }
        });

        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), GradeSubmission.class);
        intent.putExtra("roomSelected", roomSelected);
        intent.putExtra("submissionSelected", submissionKeyList.get(i));

        startActivity(intent);
    }
}
