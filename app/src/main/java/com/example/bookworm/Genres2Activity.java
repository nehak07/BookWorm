package com.example.bookworm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;

public class Genres2Activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres2);

        mToolbar = (Toolbar) findViewById(R.id.Genres2_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Genres");


        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Genres2Fragment()).commit();

        firebaseAuth = FirebaseAuth.getInstance();

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
        Intent mainintent = new Intent(Genres2Activity.this, BlankActivity.class);
        startActivity(mainintent);
    }



}


