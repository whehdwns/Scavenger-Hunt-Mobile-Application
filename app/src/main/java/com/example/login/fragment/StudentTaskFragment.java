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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.main.Instructor;
import com.example.login.main.Student;
import com.example.login.main.TakePicture;
import com.example.login.support.ImageManager;
import com.example.login.support.TaskManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentTaskFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> taskDescriptionList, taskKeyList;
    private ArrayAdapter<String> taskAdaptor;

    private FirebaseUser mUser;
    private DatabaseReference rootRef, roomRef, taskRef;
    private Query taskQuery;

    private String roomSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.studenttaskfragment, container, false);

        roomSelected = ((Student) getActivity()).getRoomSelected();

        if (roomSelected == null) {
            return inflater.inflate(R.layout.fragment_empty_task, container, false);
        }

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        taskRef = roomRef.child(roomSelected).child("Tasks");
        taskQuery = taskRef.orderByChild("description");

        listView = view.findViewById(R.id.listViewStudent);
        taskDescriptionList = new ArrayList<>();
        taskKeyList = new ArrayList<>();
        taskAdaptor = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskDescriptionList);

        listView.setAdapter(taskAdaptor);
        taskQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskDescriptionList.clear();
                taskKeyList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String taskKey = dataSnapshot1.getKey();
                    TaskManager taskManager = dataSnapshot1.getValue(TaskManager.class);

                    taskDescriptionList.add(taskManager.getDescription());
                    taskKeyList.add(taskKey);
                    taskAdaptor.notifyDataSetChanged();
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
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        Toast.makeText(getActivity(), "Task selected: " + taskDescriptionList.get(i), Toast.LENGTH_LONG).show();

        taskRef.child(taskKeyList.get(i))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String type = dataSnapshot.child("type").getValue(String.class);

                        if (!dataSnapshot.hasChild("Submissions")) {
                            taskRef.child(taskKeyList.get(i)).child("Submissions").push().setValue(new ImageManager());
                        }

                        if (type.equals("camera")) {
                            Intent intent = new Intent(getActivity(), TakePicture.class);
                            intent.putExtra("roomSelected", roomSelected);
                            intent.putExtra("taskSelected", taskKeyList.get(i));

                            startActivity(intent);
                        } else if (type.equals("pen")) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(this.toString(), databaseError.getMessage());
                    }
                });
    }
}
