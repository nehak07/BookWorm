package com.example.bookworm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class ViewUsersDetailsFragment extends Fragment implements View.OnClickListener{
    private EditText Genre, FullName, Book;
    private Button Update;

    private DatabaseReference  UserRef ;
    private FirebaseAuth mAuth;

    private String currentUserID;

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      view = inflater.inflate(R.layout.fragment_view_users_details, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);


//Declaring variables
        Genre = (EditText) view.findViewById(R.id.txt_ViewDate);
        FullName = (EditText) view.findViewById(R.id.txt_ClubName);
        Book = (EditText) view.findViewById(R.id.txt_ViewTime);
        Update = (Button) view.findViewById(R.id.btnUpdate_Details);


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidateData();
            }
        });


        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String Username = dataSnapshot.child("fullname").getValue().toString();
                    String Bookname = dataSnapshot.child("book").getValue().toString();
                    String Genrename = dataSnapshot.child("genre").getValue().toString();

                    FullName.setText(Username);
                   Book.setText(Bookname);
                   Genre.setText(Genrename);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void ValidateData() {
        String genre = Genre.getText().toString();
        String fullname = FullName.getText().toString();
        String book = Book.getText().toString();

        if(TextUtils.isEmpty(book)){
            Toast.makeText(getContext(), "Please enter your favourite book name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(fullname)){
            Toast.makeText(getContext(), "Please enter your full name", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(genre)){
            Toast.makeText(getContext(), "Please enter your favourite genre", Toast.LENGTH_SHORT).show();
        }
        else{
            UpdateUserData(genre, fullname, book);
        }
    }

    private void UpdateUserData(String genre, String fullname, String book) {
        HashMap userDetails = new HashMap();
        userDetails.put("book", book);
        userDetails.put("fullname", fullname);
        userDetails.put("genre", genre);


        UserRef.updateChildren(userDetails).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Your account details have been updated", Toast.LENGTH_SHORT).show();
                    SettingsFragment fragment = new SettingsFragment(); //CREATE A NEW FRAGMENT
                  //  getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                }else{
                    String message = task.getException().getMessage();
                    Toast.makeText(getContext(), "An error has occurred" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

}
