package com.example.bookworm;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
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

public class ProfileDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText Genre, FullName, Book;
    private Button Update;

    private DatabaseReference UserRef ;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        mToolbar = (Toolbar) findViewById(R.id.ProfileDetails_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Update Profile Details");

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);


//Declaring variables
        Genre = (EditText) findViewById(R.id.txt_Profile_Genre);
        FullName = (EditText) findViewById(R.id.txt_Profile_Fullname);
        Book = (EditText) findViewById(R.id.txt_Profile_Book);
        Update = (Button) findViewById(R.id.btnUpdate_Details);


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
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            SendUserToHome();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToHome()
    {
        Intent mainintent = new Intent(ProfileDetailsActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }


    private void ValidateData() {
        String genre = Genre.getText().toString();
        String fullname = FullName.getText().toString();
        String book = Book.getText().toString();

        if(TextUtils.isEmpty(book)){
            Toast.makeText(ProfileDetailsActivity.this, "Please enter your favourite book name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(fullname)){
            Toast.makeText(ProfileDetailsActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(genre)){
            Toast.makeText(ProfileDetailsActivity.this, "Please enter your favourite genre", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ProfileDetailsActivity.this, "Your account details have been updated", Toast.LENGTH_SHORT).show();

                    Intent Setting = new Intent(ProfileDetailsActivity.this, SettingActivity.class);
                    startActivity(Setting);

                }else{
                    String message = task.getException().getMessage();
                    Toast.makeText(ProfileDetailsActivity.this, "An error has occurred" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
