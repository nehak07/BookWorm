package com.example.bookworm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDATE;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDESC;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGTIME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_USERNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class BookClubAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView ClubName;
    ImageButton Book, Members, MeetUp, Vote, Info, Setting;
    private Toolbar mToolbar;
    private String NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_club_admin);

        mToolbar = (Toolbar) findViewById(R.id.Admin_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Admin Area");

        ClubName = findViewById(R.id.txt_ClubName_detail);

        Book = findViewById(R.id.btn_Book_Member);
        Book.setOnClickListener(this);

        Members = findViewById(R.id.btn_Member_Members);
        Members.setOnClickListener(this);

        MeetUp = findViewById(R.id.btn_MeetUp_Members);
        MeetUp.setOnClickListener(this);

        Info = findViewById(R.id.btn_Info_Member);
        Info.setOnClickListener(this);

        Setting = findViewById(R.id.btn_Setting_Member);

        Intent intent = getIntent();
       final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);
        NAME = intent.getStringExtra(EXTRA_NAME);

        TextView textViewClubName = findViewById(R.id.txt_ClubName_detail);

        textViewClubName.setText(CLUBNAME);

        Members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BookClubAdminActivity.this, ViewMembersActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                i.putExtra(EXTRA_NAME, NAME);
                startActivity(i);
            }
        });

        MeetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BookClubAdminActivity.this, ViewMeetingActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                i.putExtra(EXTRA_NAME, NAME);
                startActivity(i);
            }
        });

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BookClubAdminActivity.this, ClubBooksActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                i.putExtra(EXTRA_NAME, NAME);
                startActivity(i);
            }
        });



        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BookClubAdminActivity.this, AdminSettingsActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                i.putExtra(EXTRA_NAME, NAME);

                startActivity(i);
            }
        });

        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BookClubAdminActivity.this, ViewClubInfoActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                i.putExtra(EXTRA_NAME, NAME);

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

        Intent i = new Intent(BookClubAdminActivity.this, AllClubsActivity.class);
        i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
        i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
        i.putExtra(EXTRA_USERNAME,USERNAME);
        i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
        i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
        i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
        i.putExtra(EXTRA_NAME, NAME);

        startActivity(i);
    }

    @Override
    public void onClick(View v) {

    }
}
