package com.example.bookworm;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView MessageRecycler;
    private final List<messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private messagesAdapter messagesAdapter;

    private EditText WriteMessage;
    private ImageButton Send;
    private TextView MemberName;

    private String MessageRecID, MessageRecName, MessageSenderID, saveCurrentDate, passUserID, saveCurrentTime;

    private DatabaseReference UserRef, RootRef ;

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mToolbar = findViewById(R.id.Message_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Messages");

        mAuth = FirebaseAuth.getInstance();
        MessageSenderID = mAuth.getCurrentUser().getUid();

        passUserID = getIntent().getExtras().get("Member_ID").toString();//Gets the ID of the member the current user is looking that
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RootRef = FirebaseDatabase.getInstance().getReference();

        MessageRecID = getIntent().getExtras().get("Member_ID").toString();

        MemberName = (TextView) findViewById(R.id.txtMessageUsername);
        MessageRecycler = (RecyclerView) findViewById(R.id.messageRecyclerView);
        WriteMessage = (EditText) findViewById(R.id.Input_Message);
        Send = (ImageButton) findViewById(R.id.btnSendMessage);

        messagesAdapter = new messagesAdapter(messagesList);
        MessageRecycler = (RecyclerView) findViewById(R.id.messageRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        MessageRecycler.setHasFixedSize(true);
        MessageRecycler.setLayoutManager(linearLayoutManager);
        MessageRecycler.setAdapter(messagesAdapter);




        UserRef.child(passUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String Username = dataSnapshot.child("fullname").getValue().toString();

                    MemberName.setText("You are messaging: "+ Username);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SendMessage();
            }
        });

        GrabMessages();


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
        Intent mainintent = new Intent(MessageActivity.this, FriendsActivity.class);
        startActivity(mainintent);
    }


    private void GrabMessages()
    {
        RootRef.child("Messages").child(MessageSenderID).child(MessageRecID)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                    {
                        if (dataSnapshot.exists())
                        {
                            messages messages = dataSnapshot.getValue(messages.class);
                            messagesList.add(messages);
                            messagesAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void SendMessage()
    {
        String messageText = WriteMessage.getText().toString();

        if(TextUtils.isEmpty(messageText))
        {
            Toast.makeText(this, "Please type a message",Toast.LENGTH_SHORT).show();
        }else
            {
                String message_Sender_Ref = "Messages/" + MessageSenderID + "/" + MessageRecID;
                String message_Receiver_Ref = "Messages/" + MessageRecID + "/" + MessageSenderID;

                final DatabaseReference user_message_key = RootRef.child("Messages").child(MessageSenderID)
                        .child(MessageRecID).push(); //The push method created a new unique random key

                String message_push = user_message_key.getKey();

                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                saveCurrentDate = currentDate.format(calForDate.getTime());

                Calendar calForTime = Calendar.getInstance();
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm aa");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                Map messageTextBody = new HashMap();
                messageTextBody.put("message", messageText);
                messageTextBody.put("time",saveCurrentTime);
                messageTextBody.put("date", saveCurrentDate);
                messageTextBody.put("type", "text");
                messageTextBody.put("from", MessageSenderID);

                Map messageBodyDetails = new HashMap();
                messageBodyDetails.put(message_Sender_Ref + "/" + message_push, messageTextBody);
                messageBodyDetails.put(message_Receiver_Ref + "/" + message_push, messageTextBody);

                RootRef.updateChildren(messageBodyDetails).addOnCompleteListener //Saves all the above data into the database
                        (new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(MessageActivity.this, "Message sent ",Toast.LENGTH_SHORT).show();
                                    WriteMessage.setText("");
                                }else
                                    {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(MessageActivity.this, "Error: "+ message,Toast.LENGTH_SHORT).show();
                                        WriteMessage.setText("");
                                    }

                            }
                        }) ;
            }
    }
}
