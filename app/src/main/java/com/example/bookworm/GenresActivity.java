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

public class GenresActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        mToolbar = (Toolbar) findViewById(R.id.Genres_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Genres");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
       // bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new GenresFragment()).commit();

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
        Intent mainintent = new Intent(GenresActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener(){
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment =null;
//
//                    switch (item.getItemId()){
//                        case R.id.nav_home:
//                            selectedFragment = new HomeFragment();
//                            Toast.makeText(getApplicationContext(), "Home Selected", Toast.LENGTH_SHORT).show();
//                            break;
//                        case R.id.nav_Club:
//                            selectedFragment = new AllClubsFragment(); //Changes fragment to my books
//                            Toast.makeText(getApplicationContext(), "Clubs Selected", Toast.LENGTH_SHORT).show();
//                            break;
//                        case R.id.nav_books:
//                            selectedFragment = new MyBooksFragment(); //Changes fragment to my books
//                            Toast.makeText(getApplicationContext(), "Books Selected", Toast.LENGTH_SHORT).show();
//                            break;
//                        case R.id.nav_Myclub:
//                            selectedFragment = new ClubsFragment();
//                            Toast.makeText(getApplicationContext(), "My Club Selected", Toast.LENGTH_SHORT).show();
//                            break;
//
//                        case R.id.nav_setting:
//                            selectedFragment = new SettingsFragment(); //Change fragment to settings
//                            Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_SHORT).show();
//                            break;
//
//                    }
//
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            selectedFragment).commit();
//
//                    return true;
//                }
//            };




}


