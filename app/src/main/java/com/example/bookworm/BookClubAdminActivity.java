package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;

public class BookClubAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView ClubName;
    ImageButton Book, Members, MeetUp, Vote, Info, Setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_club_admin);

        ClubName = findViewById(R.id.txt_ClubName_detail);

        Book = findViewById(R.id.btn_Book_Member);
        Book.setOnClickListener(this);

        Members = findViewById(R.id.btn_Member_Members);
        Members.setOnClickListener(this);

        MeetUp = findViewById(R.id.btn_MeetUp_Members);
        MeetUp.setOnClickListener(this);

        Vote = findViewById(R.id.btn_Vote_Member);
        Vote.setOnClickListener(this);

        Info = findViewById(R.id.btn_Info_Member);
        Info.setOnClickListener(this);


        Setting = findViewById(R.id.btn_Setting_Member);
        //Setting.setOnClickListener(this);

        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BookClubAdminActivity.this, AdminSettingsActivity.class));
            }
        });



        Intent intent = getIntent();
        String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

        TextView textViewClubName = findViewById(R.id.txt_ClubName_detail);

        textViewClubName.setText(CLUBNAME);
    }

    @Override
    public void onClick(View v) {

    }
}
