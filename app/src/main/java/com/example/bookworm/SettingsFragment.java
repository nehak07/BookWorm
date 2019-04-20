package com.example.bookworm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SettingsFragment extends Fragment implements View.OnClickListener{
    private TextView Recommendations, FindFriend, ProfileSetting;
    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);


        Recommendations = view.findViewById(R.id.txt_Recom);
        Recommendations.setOnClickListener(this);

        FindFriend = view.findViewById(R.id.txt_Find_Friend);
        FindFriend.setOnClickListener(this);

        ProfileSetting = view.findViewById(R.id.txt_ProfileSetting);
        ProfileSetting.setOnClickListener(this);



        Recommendations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), GenresActivity.class);
//                startActivity(intent);
                GenresFragment fragment = new GenresFragment(); //CREATE A NEW FRAGMENT
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }

        });

        FindFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), FindFriendsActivity.class);
                //startActivity(intent);
            }

        });

        ProfileSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewUsersDetailsFragment fragment = new ViewUsersDetailsFragment(); //CREATE A NEW FRAGMENT
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                //Intent intent = new Intent(getActivity(), FindFriendsActivity.class);
                //startActivity(intent);
            }

        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
