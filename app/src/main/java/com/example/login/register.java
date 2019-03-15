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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText editEmail, editPassword, editID ,editName;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    RadioButton studentButton, instructorButton;

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
        studentButton = (RadioButton) findViewById(R.id.studentButton);
        instructorButton = (RadioButton) findViewById(R.id.instructorButton);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.editRegister).setOnClickListener(this);
        findViewById(R.id.editTextLogin).setOnClickListener(this);
    }

    private void userRegister(final String role) {
        final String name = editName.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String ID = editID.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Minimum length of password should be 6");
            editPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    LoginManager user = new LoginManager(role, name, email, ID);
                    FirebaseDatabase.getInstance().getReference(user.getRole())
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){
                          @Override
                          public void onComplete(@NonNull Task<Void> task){
                              if (task.isSuccessful()) {
                                  Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                              }
                          }
                    });
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void onClick(View view) {
        if(view.getId() == R.id.editRegister) {
            String role = (studentButton.isChecked()) ? "Student" : "Instructor";
            userRegister(role);
        } else if (view.getId() == R.id.editTextLogin) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
