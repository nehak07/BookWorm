package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;

public class BookClubMemberActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView ClubName;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_club_member);

        mToolbar = (Toolbar) findViewById(R.id.Member_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Member Area");


        ClubName = findViewById(R.id.txt_ClubName_detail);

        Intent intent = getIntent();
        String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

        TextView textViewClubName = findViewById(R.id.txt_ClubName_detail);

        textViewClubName.setText(CLUBNAME);
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
        Intent mainintent = new Intent(BookClubMemberActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }

    @Override
    public void onClick(View v) {

    }
}
