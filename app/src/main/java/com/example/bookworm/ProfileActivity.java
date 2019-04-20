package com.example.bookworm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView Genre, FullName, Book;
    private Button Send, Decline;

    private DatabaseReference ProfileUserRef, UserRef ;
    private FirebaseAuth mAuth;

    private String SendUserID, passUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        passUserID = getIntent().getExtras().get("Memeber_ID").toString();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");


//        SendUserID = mAuth.getCurrentUser().getUid();
//        ProfileUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(SendUserID);

//Declaring variables
        Genre = (TextView) findViewById(R.id.txt_Profile_Genre);
        FullName = (TextView) findViewById(R.id.txt_Profile_Fullname);
        Book = (TextView) findViewById(R.id.etAccount_Book);

        Send = (Button)  findViewById(R.id.btnSend_Request);
        Decline = (Button) findViewById(R.id.btnCancel_Request);

        UserRef.child(passUserID).addValueEventListener(new ValueEventListener() {
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


    }
}
