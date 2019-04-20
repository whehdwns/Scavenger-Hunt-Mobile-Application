package com.example.login.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.login.R;
import com.example.login.main.Instructor;
import com.example.login.support.TaskManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskFragment extends Fragment {
    private ListView listView;
    private ArrayList<String> taskList;
    private ArrayAdapter<String> taskAdaptor;

    private FirebaseUser mUser;
    private DatabaseReference rootRef, roomRef, taskRef;
    private Query taskQuery;

    private String roomSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.taskfragment, container, false);

        roomSelected = ((Instructor) getActivity()).getRoomSelected();

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        taskRef = roomRef.child(roomSelected).child("Tasks");
        taskQuery = taskRef.orderByChild("taskDescription");

        listView = view.findViewById(R.id.listView);
        taskList = new ArrayList<>();
        taskAdaptor = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskList);

        listView.setAdapter(taskAdaptor);

        taskQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TaskManager taskManager = dataSnapshot.getValue(TaskManager.class);

                taskList.add(taskManager.getTaskDescription());
                taskAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
