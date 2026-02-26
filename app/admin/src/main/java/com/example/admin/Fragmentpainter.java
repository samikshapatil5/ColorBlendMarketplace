package com.example.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragmentpainter extends Fragment {
    Button insert,delete, view1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmentpainter, container, false);

        insert = view.findViewById(R.id.Insertbtn);

        delete = view.findViewById(R.id.Deletetbtn);



        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), painter_insert.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), painter_delete.class);
                //intent.putExtra("id",position);
                startActivity(intent);

            }
        });


        return view;
    }
}