package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class AdminSettingsActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton Members, MeetUp, Vote, Info;
    private Toolbar mToolbar;

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
        MeetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminSettingsActivity.this, AdminMeetUpActivity.class));
            }
        });



        Vote = findViewById(R.id.btn_Vote_Member);
        Vote.setOnClickListener(this);

        Info = findViewById(R.id.btn_Info_Member);
        Info.setOnClickListener(this);

        Members = findViewById(R.id.btn_Member_Members);
        Members.setOnClickListener(this);
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
        Intent mainintent = new Intent(AdminSettingsActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }


    @Override
    public void onClick(View v) {

    }
}
