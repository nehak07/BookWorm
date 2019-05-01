package com.example.bookworm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

import static com.example.bookworm.BooksFragment.EXTRA_AUTHOR;
import static com.example.bookworm.BooksFragment.EXTRA_URL;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView ProfileSetting, PasswordSetting;


    private FirebaseAuth firebaseAuth;
    private Toolbar mToolbar;
    private String NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        NAME = intent.getStringExtra(BlankActivity.EXTRA_NAME);

        mToolbar = (Toolbar) findViewById(R.id.Setting_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Settings");


        firebaseAuth = FirebaseAuth.getInstance();

        ProfileSetting = findViewById(R.id.txt_ProfileSetting);
        ProfileSetting.setOnClickListener(this);

        PasswordSetting = findViewById(R.id.txt_PasswordSetting);
        PasswordSetting.setOnClickListener(this);

        ProfileSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingActivity.this, ProfileDetailsActivity.class);
                startActivity(intent);
            }

        });

        PasswordSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
            }

        });

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
        Intent mainintent = new Intent(SettingActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }


    @Override
    public void onClick(View v) {

    }
}
