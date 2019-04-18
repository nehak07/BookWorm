package com.example.bookworm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button CreateClub;
    private View view;
    private TextView txt_link_search_book;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);

        CreateClub = view.findViewById(R.id.btn_create_club);
        CreateClub.setOnClickListener(this);



        CreateClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CreateClubActivity.class);
//                startActivity(intent);

                CreateClubFragment fragment = new CreateClubFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }

        });


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (view.getId()){

        }

    }
}

