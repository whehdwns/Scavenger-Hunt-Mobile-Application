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
import com.example.login.support.ImageManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class TakePicture extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Button buttonSubmit, buttonTakePicture;
    private ImageView imageView;

    private FirebaseUser mUser;
    private DatabaseReference rootRef, roomRef, submissionRef, taskRef;
    private StorageReference mStorageRef;

    private String roomSelected, taskSelected;

    public Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        Intent intent = getIntent();

        roomSelected = intent.getStringExtra("roomSelected");
        taskSelected = intent.getStringExtra("taskSelected");

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        taskRef = roomRef.child(roomSelected).child("Tasks");
        submissionRef = taskRef.child(taskSelected).child("Submissions");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        buttonTakePicture = findViewById(R.id.buttonTakePicture);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        imageView = findViewById(R.id.imageView);

        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    buttonSubmit.setVisibility(View.VISIBLE);
                }
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        final Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR);
        final int minute = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

        byte[] b = stream.toByteArray();

        StorageReference userImage = mStorageRef.child("tasks/" + taskSelected + "/camera/" + mUser + "_" + hour + minute + second); //change this later

        userImage.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(TakePicture.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TakePicture.this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });

        userImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageManager imageURL = new ImageManager(uri.toString());

                submissionRef.push().setValue(uri.toString());
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