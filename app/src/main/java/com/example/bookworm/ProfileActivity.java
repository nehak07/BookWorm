package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    // https://www.youtube.com/watch?v=sbOdwk4C_9s&index=37&list=PLxefhmF0pcPnTQ2oyMffo6QbWtztXu1W_&fbclid=IwAR1IaRl6bepOHsRHz6wr5G1vQXyfEv1WQfIkhTfpr6Eiqm4bOmyb9K6wwSw
    //Videos 37- 55

    private TextView Genre, FullName, Book;
    private Button Send, Decline;

    private DatabaseReference FriendRequestRef, UserRef, FriendsRef;
    private FirebaseAuth mAuth;
    private String saveCurrentDate;
    private String SendUserID, passUserID;
    private String CURRENTSTATE;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = findViewById(R.id.Friendrequest_Toolbar);
        setSupportActionBar(mToolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Messages");


        mAuth = FirebaseAuth.getInstance();
        SendUserID = mAuth.getCurrentUser().getUid(); //Gets the current users ID

        FriendRequestRef = FirebaseDatabase.getInstance().getReference().child("FriendRequest");
        FriendsRef = FirebaseDatabase.getInstance().getReference().child("Friends");

        passUserID = getIntent().getExtras().get("Member_ID").toString();//Gets the ID of the member the current user is looking that
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");


//Declaring variables
        Genre = (TextView) findViewById(R.id.txt_ViewDate);
        FullName = (TextView) findViewById(R.id.txt_ClubName);
        Book = (TextView) findViewById(R.id.txt_ViewTime);
        Send = (Button)  findViewById(R.id.btnSend_Request);
        Decline = (Button) findViewById(R.id.btnCancel_Request);


        CURRENTSTATE= "Not_Friends";

        UserRef.child(passUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String Username = dataSnapshot.child("fullname").getValue().toString();
                    String Bookname = dataSnapshot.child("book").getValue().toString();
                    String Genrename = dataSnapshot.child("genre").getValue().toString();

                    FullName.setText("Members Name: " + Username);
                    Book.setText("Favourite Book: " + Bookname);
                    Genre.setText("Favourite Genre: " + Genrename);

                    ButtonText();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Decline.setVisibility(View.INVISIBLE);
        Decline.setEnabled(false);

        if(!SendUserID.equals(passUserID))
         {
            Send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Send.setEnabled(false);

                    if(CURRENTSTATE.equals("Not_Friends"))
                    {
                        SendFriendRequest();
                    }
                    if (CURRENTSTATE.equals("request_sent"))
                    {
                        CancelFriendRequest();
                    }
                    if(CURRENTSTATE.equals("request_received"))
                    {
                        AcceptFriendRequest();
                    }
                    if (CURRENTSTATE.equals("friends"))
                    {
                        UnfriendRequest();
                    }
                }
            });

        }else //Sets the send and decline buttons to invisible if the user is looking up their own profile
            {
                Decline.setVisibility(View.INVISIBLE);
                Send.setVisibility(View.INVISIBLE);
            }


    }

    @Override
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
        Intent mainintent = new Intent(ProfileActivity.this, MessageActivity.class);
        startActivity(mainintent);
    }

    private void UnfriendRequest()
    {
        FriendsRef.child(SendUserID).child(passUserID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())

                        //When a friend request is send the database request two different child (node) Stores the sender ID and the member ID for who the request is sent too
                        {
                            FriendsRef.child(passUserID).child(SendUserID)
                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Send.setEnabled(true);
                                        CURRENTSTATE = "not_friends";
                                        Send.setText("Send Friend Request");

                                        Decline.setVisibility(View.INVISIBLE);
                                        Decline.setEnabled(false);
                                    }
                                }
                            });
                        }
                    }
                });
    }

    private void AcceptFriendRequest()
    {
        Calendar calForDate = Calendar.getInstance(); //Gets the current date, this will be used to save into the database so the memebers know when they became friends
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        FriendsRef.child(SendUserID).child(passUserID)
                .child("date").setValue(saveCurrentDate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                      if(task.isSuccessful())
                      {
                          FriendsRef.child(passUserID).child(SendUserID)
                                  .child("date").setValue(saveCurrentDate)
                                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @Override
                                      public void onComplete(@NonNull Task<Void> task)
                                      {
                                          if(task.isSuccessful())
                                          {
                                              FriendRequestRef.child(SendUserID).child(passUserID)
                                                      .removeValue()
                                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<Void> task)
                                                          {
                                                              if(task.isSuccessful())

                                                              //Removes both the users IDs from the database and deletes the data
                                                              {
                                                                  FriendRequestRef.child(passUserID).child(SendUserID)
                                                                          .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                      @Override
                                                                      public void onComplete(@NonNull Task<Void> task) {
                                                                          if (task.isSuccessful()){
                                                                              Send.setEnabled(true);
                                                                              CURRENTSTATE = "friends";
                                                                              Send.setText("Unfriend this member?");

                                                                              Decline.setVisibility(View.INVISIBLE);
                                                                              Decline.setEnabled(false);
                                                                          }
                                                                      }
                                                                  });
                                                              }
                                                          }
                                                      });

                                          }
                                      }
                                  });
                      }
                    }
                });

    }


    private void CancelFriendRequest()
    {
        FriendRequestRef.child(SendUserID).child(passUserID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())

                        //When a friend request is send the database request two different child (node) Stores the sender ID and the member ID for who the request is sent too
                        {
                            FriendRequestRef.child(passUserID).child(SendUserID)
                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Send.setEnabled(true);
                                        CURRENTSTATE = "not_friends";
                                        Send.setText("Send Friend Request");

                                        Decline.setVisibility(View.INVISIBLE);
                                        Decline.setEnabled(false);
                                    }
                                }
                            });
                        }
                    }
                });


    }

    private void ButtonText()
    {
        FriendRequestRef.child(SendUserID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(passUserID)){

                            String request_type = dataSnapshot.child(passUserID).child("request_type")
                                    .getValue().toString();

                            if (request_type.equals("sent")){

                                CURRENTSTATE = "request_type";
                                Send.setText("Cancel Friend Request");

                                Decline.setVisibility(View.INVISIBLE);
                                Decline.setEnabled(false);
                            }
                            else if (request_type.equals("received"))
                                {
                                    CURRENTSTATE = "request_received";
                                    Send.setText("Accept Friend Request");

                                    Decline.setVisibility(View.VISIBLE);
                                    Decline.setEnabled(true);

                                    Decline.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v)
                                        {
                                          CancelFriendRequest();
                                        }
                                    });
                                }
                            else
                            {
                                FriendsRef.child(SendUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(passUserID))
                                        {
                                            CURRENTSTATE = "friends";
                                            Send.setText("Unfriend this member?");

                                            Decline.setVisibility(View.INVISIBLE);
                                            Decline.setEnabled(false);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void SendFriendRequest() {

        FriendRequestRef.child(SendUserID).child(passUserID)
                .child("request_type").setValue("sent") //Creating a new child node
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())

  //When a friend request is send the database request two different child (node) Stores the sender ID and the member ID for who the request is sent too
                        {
                            FriendRequestRef.child(passUserID).child(SendUserID)
                                    .child("request_type").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Send.setEnabled(true);
                                        CURRENTSTATE = "request_sent";
                                        Send.setText("Cancel Friend Request");

                                        Decline.setVisibility(View.INVISIBLE);
                                        Decline.setEnabled(false);
                                    }
                                }
                            });
                        }
                    }
                });
    }
}
