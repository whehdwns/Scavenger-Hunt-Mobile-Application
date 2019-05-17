package com.example.login.fragment;

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
import com.example.login.main.Student;
import com.example.login.support.RoomJoined;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentManage extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> roomKeyList, roomNameList;
    private ArrayAdapter<String> roomAdaptor;

    private FirebaseUser mUser;
    private DatabaseReference rootRef, studentRef, studentRoomRef;
    private Query studentRoomQuery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        studentRef = rootRef.child("Student");
        studentRoomRef = studentRef.child(mUser.getUid()).child("Rooms");
        studentRoomQuery = studentRoomRef.orderByChild("name");

        listView = view.findViewById(R.id.listView);
        roomKeyList = new ArrayList<>();
        roomNameList = new ArrayList<>();
        roomAdaptor = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, roomNameList);

        listView.setAdapter(roomAdaptor);
        studentRoomQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomKeyList.clear();
                roomNameList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String roomKey = dataSnapshot1.getKey();
                    RoomJoined room = dataSnapshot1.getValue(RoomJoined.class);

                    roomKeyList.add(roomKey);
                    roomNameList.add(room.getName());
                    roomAdaptor.notifyDataSetChanged();
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
        ((Student) getActivity()).setRoomSelected(roomKeyList.get(i));
        Toast.makeText(getActivity(), "Room selected: " + roomNameList.get(i), Toast.LENGTH_LONG).show();
    }
}
