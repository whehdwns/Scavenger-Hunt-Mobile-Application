package com.example.login.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.login.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PasswordDialog extends AppCompatDialogFragment {
    private EditText roomPassword;

    private DatabaseReference rootRef, roomRef;

    private String roomID;
    private Boolean passwordMatch;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(builder.getContext())
                .inflate(R.layout.password_dialog,null, false);

        roomID = getArguments().getString("roomID");

        rootRef = FirebaseDatabase.getInstance().getReference();
        roomRef = rootRef.child("Rooms");

        roomPassword = view.findViewById(R.id.roomPassword);

        builder.setView(view)
                .setTitle("Enter Room Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public Boolean getPasswordMatch() {
        return passwordMatch;
    }
}
