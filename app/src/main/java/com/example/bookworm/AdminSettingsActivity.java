package com.example.bookworm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDATE;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDESC;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGTIME;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_USERNAME;

public class AdminSettingsActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton Members, MeetUp, Vote, Info, Book;
    private Toolbar mToolbar;
    private TextView ClubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        mToolbar = (Toolbar) findViewById(R.id.AdminAdministration_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Admin Setting Area");

        MeetUp = findViewById(R.id.btn_MeetUp_Members);

        ClubName = findViewById(R.id.txt_ClubName_detail);

        Info = findViewById(R.id.btn_Info_Member);
        Info.setOnClickListener(this);

        Members = findViewById(R.id.btn_Member_Members);
        Members.setOnClickListener(this);

        Book = findViewById(R.id.btn_Book_Member);
        Book.setOnClickListener(this);

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);

        TextView textViewClubName = findViewById(R.id.txt_ClubName_detail);

        textViewClubName.setText(CLUBNAME);

        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminSettingsActivity.this, EditClubDescActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);

                startActivity(i);
            }
        });

        MeetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminSettingsActivity.this, AdminMeetUpActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);

                startActivity(i);
            }
        });

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminSettingsActivity.this, Genres2Activity.class);
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
        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);

        Intent i = new Intent(AdminSettingsActivity.this, BookClubAdminActivity.class);
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
