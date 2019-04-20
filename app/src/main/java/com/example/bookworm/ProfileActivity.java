package com.example.bookworm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import javax.annotation.meta.When;

public class ProfileActivity extends AppCompatActivity {

    private TextView Genre, FullName, Book;
    private Button Send, Decline;

    private DatabaseReference FriendRequestRef, UserRef ;
    private FirebaseAuth mAuth;

    private String SendUserID, passUserID;

    private String CURRENTSTATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        SendUserID = mAuth.getCurrentUser().getUid(); //Gets the current users ID

        FriendRequestRef = FirebaseDatabase.getInstance().getReference().child("FriendRequest");

        passUserID = getIntent().getExtras().get("Memeber_ID").toString(); //Gets the ID of the member the current user is looking that
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");


//Declaring variables
        Genre = (TextView) findViewById(R.id.txt_Profile_Genre);
        FullName = (TextView) findViewById(R.id.txt_Profile_Fullname);
        Book = (TextView) findViewById(R.id.etAccount_Book);

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

                    FullName.setText(Username);
                    Book.setText(Bookname);
                    Genre.setText(Genrename);

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
                }
            });

        }else //Sets the send and decline buttons to invisible if the user is looking up their own profile
            {
                Decline.setVisibility(View.INVISIBLE);
                Send.setVisibility(View.INVISIBLE);
            }


    }

    private void ButtonText() {
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
