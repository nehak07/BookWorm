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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDATE;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDESC;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGTIME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_USERNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class ViewClubInfo2Activity extends AppCompatActivity implements View.OnClickListener{

    private Button CreateClub;
    private TextView ClubName, UserName, ClubDesc;
    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private String NAME;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_club_info2);

        mToolbar = (Toolbar) findViewById(R.id.ViewCLubInfo2_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Book Club Info");

        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        ClubName = findViewById(R.id.etClubName);
        UserName = findViewById(R.id.etUserName);

        ClubDesc = findViewById(R.id.etClubDesc);


        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);
        NAME = intent.getStringExtra(EXTRA_NAME);

        TextView textViewClubDesc = findViewById(R.id.etClubDesc);
        textViewClubDesc.setText("Club Description: \n" + CLUBDESC);

        TextView textViewClubName = findViewById(R.id.etClubName);
        textViewClubName.setText("Club Name: \n" + CLUBNAME);

        TextView textViewUserName = findViewById(R.id.etUserName);
        textViewUserName.setText("Club Admin: \n" + USERNAME);

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
        Intent intent = getIntent();
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);


        Intent i = new Intent(ViewClubInfo2Activity.this, BookClubMemberActivity.class);
        i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
        i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
        i.putExtra(EXTRA_USERNAME,USERNAME);
        i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
        i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
        i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);

        startActivity(i);


    }

    @Override
    public void onClick(View v) {

    }
}
