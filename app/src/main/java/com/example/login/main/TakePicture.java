package com.example.login.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.support.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class TakePicture extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Button buttonSubmit, buttonTakePicture;
    private ImageView imageView;

    private Uri mImageUri = null;

    private FirebaseUser mUser;
    private DatabaseReference rootRef, roomRef, studentRef, taskRef;
    private StorageReference mStorageRef;

    private String task;

    public Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        Intent intent = getIntent();

        task = intent.getStringExtra("task");

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        taskRef = roomRef.child("Tasks");
        studentRef = taskRef.child("Student");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        buttonTakePicture = findViewById(R.id.buttonTakePicture);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

                buttonSubmit.setVisibility(View.VISIBLE);
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        imageView = findViewById(R.id.imageView);

    }

    private void uploadImage() {
        final Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR);
        final int minute = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] b = stream.toByteArray();

        studentRef.child(mUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mStorageRef.child("tasks/" + camera/" + task + "_" + hour + minute + second + ".png"); //change this later
                        //StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
                        storageReference.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Toast.makeText(TakePicture.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TakePicture.this,"Failed",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
             Bundle extras = data.getExtras();
             imageBitmap = (Bitmap) extras.get("data");
             imageView.setImageBitmap(imageBitmap);
        }
    }
}