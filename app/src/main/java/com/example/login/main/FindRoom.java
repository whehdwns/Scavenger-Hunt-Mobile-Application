package com.example.login.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.support.LoginManager;
import com.example.login.R;
import com.example.login.support.RoomDisplay;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindRoom extends AppCompatActivity implements View.OnClickListener {
    private EditText roomSearch;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<String> roomList;

    private FirebaseRecyclerOptions<RoomDisplay> firebaseRecyclerOptions;
    private FirebaseRecyclerAdapter<RoomDisplay, RoomsViewHolder> firebaseRecyclerAdapter;

    private FirebaseUser mUser;
    private DatabaseReference rootRef, roomRef, studentRef;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        studentRef = rootRef.child("Student");

        roomSearch = findViewById(R.id.roomSearch);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        roomList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        findViewById(R.id.searchButton).setOnClickListener(this);
    }

    private void firebaseRoomSearch(String roomSearchText) {
        query = roomRef.orderByChild("instructor").startAt(roomSearchText);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot room : dataSnapshot.getChildren()) {
                    String roomKey = room.getKey();
                    roomList.add(roomKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(this.toString(), databaseError.getMessage());
            }
        });

        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<RoomDisplay>()
                .setQuery(query, new SnapshotParser<RoomDisplay>() {
                    @NonNull
                    @Override
                    public RoomDisplay parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new RoomDisplay(
                                snapshot.child("instructor").getValue(String.class),
                                snapshot.child("name").getValue(String.class),
                                snapshot.child("number").getValue(String.class)
                        );
                    }
                })
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RoomDisplay, RoomsViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull RoomsViewHolder holder, final int position, @NonNull RoomDisplay model) {
                holder.setDisplay(model.getInstructor(), model.getName(), model.getNumber());
                progressBar.setVisibility(View.INVISIBLE);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        studentRef.child(mUser.getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        LoginManager student = dataSnapshot.getValue(LoginManager.class);

                                        roomRef.child(roomList.get(position)).child("Student").child(mUser.getUid())
                                                .setValue(student)
                                                .addOnCompleteListener(FindRoom.this, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(
                                                                FindRoom.this,
                                                                "Joined room: " + roomList.get(position),
                                                                Toast.LENGTH_SHORT
                                                        ).show();
                                                        startActivity(new Intent(FindRoom.this, Student.class));
                                                    }
                                                });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.d(this.toString(), databaseError.getMessage());
                                    }
                                });
                    }
                });
            }

            @NonNull
            @Override
            public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.room_display, viewGroup, false);
                return new RoomsViewHolder(view);
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class RoomsViewHolder extends RecyclerView.ViewHolder {
        public TextView roomInstructor, roomName, roomNumber;

        public RoomsViewHolder(@NonNull View itemView) {
            super(itemView);
            roomInstructor = itemView.findViewById(R.id.roomInstructor);
            roomName = itemView.findViewById(R.id.roomName);
            roomNumber = itemView.findViewById(R.id.roomNumber);
        }

        public void setDisplay(String instructor, String name, String number) {
            roomInstructor.setText(instructor);
            roomName.setText(name);
            roomNumber.setText(number);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.searchButton) {
            progressBar.setVisibility(View.VISIBLE);
            firebaseRoomSearch(roomSearch.getText().toString());
            firebaseRecyclerAdapter.startListening();
        }
    }
}
