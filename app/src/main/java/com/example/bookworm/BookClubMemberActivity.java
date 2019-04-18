package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;

public class BookClubMemberActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView ClubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_club_member);


        ClubName = findViewById(R.id.txt_ClubName_detail);

        Intent intent = getIntent();
        String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

        TextView textViewClubName = findViewById(R.id.txt_ClubName_detail);

        textViewClubName.setText(CLUBNAME);
    }

    @Override
    public void onClick(View v) {

    }
}
