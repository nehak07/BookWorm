package com.example.bookworm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button CreateClub;
    private View view;
    private TextView FullName;

    private DatabaseReference  UserRef ;
    private FirebaseAuth mAuth;

    private String currentUserID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        FullName = (TextView) view.findViewById(R.id.txt_Profile_Fullname);


        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String Username = dataSnapshot.child("fullname").getValue().toString();

                    FullName.setText("Welcome: "+ Username);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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

