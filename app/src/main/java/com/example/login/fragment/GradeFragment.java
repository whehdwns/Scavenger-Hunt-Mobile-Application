package com.example.login.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.login.R;
import com.example.login.main.Instructor;
import com.example.login.support.GradeAdaptor;
import com.example.login.support.SubmissionManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GradeFragment extends Fragment {
    private ListView listView;
    private ArrayList<SubmissionManager> gradeList;
    private GradeAdaptor gradeAdaptor;

    private DatabaseReference rootRef, roomRef, submissionRef;
    private Query submissionQuery;

    private String roomSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gradefragment, container, false);

        roomSelected = ((Instructor) getActivity()).getRoomSelected();

        if (roomSelected == null) {
            return inflater.inflate(R.layout.fragment_empty_task, container, false);
        }

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        submissionRef = roomRef.child(roomSelected).child("Submissions");
        submissionQuery = submissionRef.orderByChild("name");

        listView = view.findViewById(R.id.listView);
        gradeList = new ArrayList<>();
        gradeAdaptor = new GradeAdaptor(getActivity(), gradeList);

        listView.setAdapter(gradeAdaptor);
        submissionQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gradeList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    SubmissionManager submissionManager = dataSnapshot1.getValue(SubmissionManager.class);

                    if (!submissionManager.getGrade().isEmpty()) {
                        gradeList.add(submissionManager);
                        gradeAdaptor.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(this.toString(), databaseError.getMessage());
            }
        });

        return view;
    }
}
