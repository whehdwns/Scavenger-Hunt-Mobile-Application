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
import com.example.login.adaptor.TaskAdaptor;
import com.example.login.support.TaskManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InstructorTask extends Fragment {
    private ListView listView;
    private ArrayList<TaskManager> taskList;
    private TaskAdaptor taskAdaptor;

    private DatabaseReference rootRef, roomRef, taskRef;
    private Query taskQuery;

    private String roomSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        roomSelected = ((Instructor) getActivity()).getRoomSelected();

        if (roomSelected == null) {
            return inflater.inflate(R.layout.fragment_empty_task, container, false);
        }

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        taskRef = roomRef.child(roomSelected).child("Tasks");
        taskQuery = taskRef.orderByChild("description");

        listView = view.findViewById(R.id.listView);
        taskList = new ArrayList<>();
        taskAdaptor = new TaskAdaptor(getActivity(), taskList);

        listView.setAdapter(taskAdaptor);
        taskQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TaskManager taskManager = dataSnapshot1.getValue(TaskManager.class);

                    taskList.add(taskManager);
                    taskAdaptor.notifyDataSetChanged();
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