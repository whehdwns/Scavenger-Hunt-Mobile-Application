package com.example.login.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.R;
import com.example.login.support.TaskManager;

import java.util.ArrayList;

public class TaskAdaptor extends ArrayAdapter<TaskManager> {

    public TaskAdaptor(Context context, ArrayList<TaskManager> submissions) {
        super(context, 0, submissions);
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        TaskManager task = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_task, container, false);
        }

        ImageView imageType = view.findViewById(R.id.imageType);
        TextView textDescription = view.findViewById(R.id.textDescription);

        String type = task.getType();

        if (type.equals("camera")) {
            imageType.setImageResource(R.drawable.cameraicon);
        } else if (type.equals("pen")) {
            imageType.setImageResource(R.drawable.penicon);
        }

        textDescription.setText(task.getDescription());

        return view;
    }
}
