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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;


public class CreateClubFragment extends Fragment implements View.OnClickListener {

    private Button CreateClub;
    private EditText ClubName, UserName, ClubDesc;
    private CheckBox ClubState;

    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_create_club, container, false);

        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        CreateClub= view.findViewById(R.id.btnAddClub);
        CreateClub.setOnClickListener(this);

        ClubName = view.findViewById(R.id.etClubName);
        UserName = view.findViewById(R.id.etUserName);
        ClubState = view.findViewById(R.id.checkBox);
        ClubDesc = view.findViewById(R.id.etClubDesc);


        return view;
    }


    @Override
    public void onClick(View v) {
        FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();
        boolean group = false;

        if (ClubState.isChecked()){
            group = true;
        }

        String CLUBNAME = ClubName.getText().toString();
        String USERNAME = UserName.getText().toString();
        String CLUBDESC = ClubDesc.getText().toString();

//Coding With Mitch, 2018. Inserting Data Android Firestore [ONLINE] Available at: https://www.youtube.com/watch?v=xnFnwbiDFuE&t=14s [Accessed on the 28th Feb 2019]
//Simplified Coding, 2018 [ONLINE] Available at: https://www.youtube.com/watch?v=fGiIUi3FqfQ [Accessed on the 13th March 2019]
//        Note3 note3 = new Note3(
//                CLUBNAME,
//                USERNAME,
//                CLUBDESC,
//                UserID,
//                group
//
//        );

        //Create a hash map
        Map<String, Object> docData = new HashMap<>();
        docData.put("admin", UserID);
        docData.put("clubname", CLUBNAME);
        docData.put("clubdesc", CLUBDESC);
        docData.put("username", USERNAME);
        docData.put("group", group);

        mFirestore.collection("Club").document(CLUBNAME).set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "ClUB CREATED", Toast.LENGTH_SHORT).show();
                HomeFragment fragment = new HomeFragment();
                      //getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });





//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(getContext(), "ClUB CREATED", Toast.LENGTH_SHORT).show();
//
//                        HomeFragment fragment = new HomeFragment();
//                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
//
//
//                        //startActivity(new Intent(CreateClubActivity.this, BlankActivity.class));
//                    }
//                });
    }

    }

