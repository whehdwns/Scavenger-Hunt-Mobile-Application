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

import com.example.login.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class TakePicture extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Button buttonTakePicture;
    private ImageView imageView;
    private Button buttonsubmit;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference rootRef, roomRef, taskRef, submissionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        buttonTakePicture = findViewById(R.id.buttonTakePicture);
        buttonsubmit = findViewById(R.id.buttonsubmit);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");
        taskRef = roomRef.child("Tasks");
        submissionRef = taskRef.child("Submissions");

        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsubmit.setVisibility(View.VISIBLE);
                File file = getFile();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        imageView = findViewById(R.id.imageView);

    }
    private File getFile(){
        File folder = new File("submissions");
        if(!folder.exists()){
            folder.mkdir();
        }
        File image_file = new File(folder,System.currentTimeMillis()+".jpg");
        return image_file;
    }
    private void uploadImage(){
        final StorageReference ref = mStorageRef;
        Uri file = Uri.fromFile(new File("submissions"));
        UploadTask uploadTask = ref.putFile(file);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    DatabaseReference submissionRef = taskRef.push();
                    submissionRef.child("imageLink").setValue(downloadUri.toString());

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode ==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

        }
    }



}
