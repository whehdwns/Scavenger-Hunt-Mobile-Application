package com.example.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener{
    EditText editEmail, editPassword, editID ,editName;
    ProgressBar progressBar;
   // RadioButton student,instructor;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editEmail = (EditText)findViewById(R.id.editEmail);
        editPassword = (EditText)findViewById(R.id.editPassword);
        editID = (EditText)findViewById(R.id.editID);
        editName = (EditText)findViewById(R.id.editName);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.editRegister).setOnClickListener(this);
        findViewById(R.id.editTextLogin).setOnClickListener(this);
    }
    private void registerStudent() {
        final String semail = editEmail.getText().toString().trim();
        String spassword = editPassword.getText().toString().trim();
        final String sID = editID.getText().toString().trim();
        final String sname = editName.getText().toString().trim();

        if (semail.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            editEmail.setError("Please enter a valid email");
            editEmail.requestFocus();
            return;
        }

        if (spassword.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }

        if (spassword.length() < 6) {
            editPassword.setError("Minimum lenght of password should be 6");
            editPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    StudentInfo student = new StudentInfo(
                            sname,
                            semail,
                            sID
                    );
                    FirebaseDatabase.getInstance().getReference("Student")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>(){
                          @Override
                          public void onComplete(@NonNull Task<Void> task){
                              if(task.isSuccessful()){
                                  Toast.makeText(register.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                              }
                          }
                    });
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "You are already registered!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.editRegister:
                registerStudent();
                break;
            case R.id.editTextLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
    
        }
    }
}