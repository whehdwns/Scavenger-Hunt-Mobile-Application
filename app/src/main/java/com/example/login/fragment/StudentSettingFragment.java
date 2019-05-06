package com.example.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.login.R;
import com.example.login.main.MainActivity;

public class StudentSettingFragment extends Fragment {
    private Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.studentsettingfragment, container, false);

        logout= (Button)view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        // return super.onCreateView(inflater, container, savedInstanceState);
        return view;
        // return inflater.inflate(R.layout.studentsettingfragment, container, false);
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

}
