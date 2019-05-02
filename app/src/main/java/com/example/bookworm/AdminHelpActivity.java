package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDATE;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDESC;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGTIME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_USERNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class AdminHelpActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private String NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_help);

        mToolbar = (Toolbar) findViewById(R.id.help_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Admin Help");

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);
        NAME = intent.getStringExtra(EXTRA_NAME);
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


        Intent i = new Intent(AdminHelpActivity.this, AdminSettingsActivity.class);
        i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
        i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
        i.putExtra(EXTRA_USERNAME,USERNAME);
        i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
        i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
        i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
        i.putExtra(EXTRA_NAME, NAME);
        startActivity(i);

    }
}
