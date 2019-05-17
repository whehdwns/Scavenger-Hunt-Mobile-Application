package com.example.login.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.login.support.LoginManager;
import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private EditText editEmail, editPassword, editId ,editName;
    private ProgressBar progressBar;
    private RadioButton studentButton;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        progressBar = findViewById(R.id.progressBar);
        studentButton = findViewById(R.id.studentButton);

        findViewById(R.id.editRegister).setOnClickListener(this);
        findViewById(R.id.editTextLogin).setOnClickListener(this);
    }

    private void userRegister(final String role) {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String id = editId.getText().toString().trim();
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

        final LoginManager user = new LoginManager(role, name, email, id);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    rootRef.child(user.getRole()).child(mUser.getUid())
                            .setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>(){
                          @Override
                          public void onComplete(@NonNull Task<Void> task){
                              if (task.isSuccessful()) {
                                  Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                              }
                          }
                    });

                    if (user.getRole() == "Instructor") {
                        startActivity(new Intent(Register.this, CreateRoom.class));
                    } else {
                        startActivity(new Intent(Register.this, FindRoom.class));
                    }

                    finish();
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
            startActivity(new Intent(this, Login.class));
            finish();
        }
    }
}
