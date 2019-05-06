package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDATE;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDESC;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGTIME;
import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubsActivity.EXTRA_USERNAME;

public class EditClubDescActivity extends AppCompatActivity implements View.OnClickListener {

    private Button CreateClub;
    private EditText ClubName, UserName, ClubDesc;
    private CheckBox ClubState;
    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_club_desc);

        mToolbar = (Toolbar) findViewById(R.id.EditClubDecs_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Update Book Club Info");

        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        CreateClub= findViewById(R.id.btnAddClub);
        CreateClub.setOnClickListener(this);


        ClubName = findViewById(R.id.etClubName);
        UserName = findViewById(R.id.etUserName);
        ClubState = findViewById(R.id.checkBox);
        ClubDesc = findViewById(R.id.etClubDesc);




        Intent intent = getIntent();
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);

        EditText textViewClubDesc = findViewById(R.id.etClubDesc);
        textViewClubDesc.setText(CLUBDESC);

        EditText textViewClubName = findViewById(R.id.etClubName);
        textViewClubName.setText(CLUBNAME);

        EditText textViewUserName = findViewById(R.id.etUserName);
        textViewUserName.setText(USERNAME);

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
        Intent mainintent = new Intent(EditClubDescActivity.this, AdminSettingsActivity.class);
        startActivity(mainintent);
    }

    @Override
    public void onClick(View v) {
        FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();

       boolean group = false;

        if (ClubState.isChecked()){
           group = true;
        }

       final String CLUBNAME = ClubName.getText().toString();
        final String USERNAME = UserName.getText().toString();
       final  String CLUBDESC = ClubDesc.getText().toString();


        //Create a hash map
        Map<String, Object> docData = new HashMap<>();
        docData.put("admin", UserID);
        docData.put("clubname", CLUBNAME);
        docData.put("clubdesc", CLUBDESC);
        docData.put("username", USERNAME);
        docData.put("group", group);


        mFirestore.collection("Club").document(CLUBNAME).update(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditClubDescActivity.this, "Club information updated", Toast.LENGTH_SHORT).show();

                Intent intent = getIntent();
                final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
                final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
                final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);


                Intent i = new Intent(EditClubDescActivity.this, AdminSettingsActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                startActivity(i);


            }
        });


    }
}
