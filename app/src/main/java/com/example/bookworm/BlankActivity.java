package com.example.bookworm;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class BlankActivity extends AppCompatActivity {


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        mAuth  = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");

        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerID);
        actionBarDrawerToggle = new ActionBarDrawerToggle(BlankActivity.this,drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView)findViewById(R.id.NavID);
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);



//         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//          new HomeFragment()).commit();



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //Fragment selectedFragment =null;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
//                            selectedFragment = new HomeFragment();
//                            HomeFragment fragment = new HomeFragment();
//                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


                            Toast.makeText(getApplicationContext(), "Home Selected", Toast.LENGTH_SHORT).show();
                            break;

                    case R.id.nav_Club:
                           // selectedFragment = new AllClubs2Fragment(); //Changes fragment to all clubs
                            Toast.makeText(getApplicationContext(), "Clubs Selected", Toast.LENGTH_SHORT).show();
                            break;

                    case R.id.nav_books:
                           // selectedFragment = new MyBooks2Fragment(); //Changes fragment to my books
                            Toast.makeText(getApplicationContext(), "Books Selected", Toast.LENGTH_SHORT).show();
                            break;

                    case R.id.nav_Myclub:
                           // selectedFragment = new ClubsFragment();
                            Toast.makeText(getApplicationContext(), "My Club Selected", Toast.LENGTH_SHORT).show();
                            break;

                    case R.id.nav_MyFriends:
                        //selectedFragment = new SettingsFragment(); //Change fragment to settings
                        startActivity(new Intent(BlankActivity.this, FriendsActivity.class));
                        Toast.makeText(getApplicationContext(), "My Friends Selected", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_FindFriends:
                        Intent intent = new Intent(BlankActivity.this, FindFriendsActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Find Friends Selected", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_Rec:
                        Intent intent2 = new Intent(BlankActivity.this, GenresActivity.class);
                        startActivity(intent2);
                        Toast.makeText(getApplicationContext(), "Self-Help Selected", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_messages:
                        Toast.makeText(getApplicationContext(), "Message Selected", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_setting:
                        //selectedFragment = new SettingsFragment(); //Change fragment to settings
                        Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.nav_logout:
                        Toast.makeText(getApplicationContext(), "Logout Selected", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        startActivity(new Intent(BlankActivity.this, MainActivity.class));
                        break;




            }
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                           selectedFragment).commit();
                return false;
            }
        });


//
//
//



//        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
//        bottomNav.setOnNavigationItemSelectedListener(navListener);
//
     // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            //  new HomeFragment()).commit();

        //firebaseAuth = FirebaseAuth.getInstance();


    }
//When the toggle is clicked the Nav drawer opens
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//
//    }
//    //Simplified Coding, 2017. [ONLINE] Available at: https:www.youtube.com/watch?v=FmZLWe_gaSY&list=PLk7v1Z2rk4hi_LdvJ2V5-VvZfyfSdY5hy&index=6 [Accessed on the 6th March 2019 ]
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()){
//            case R.id.logoutMenu:{
//
//                firebaseAuth.signOut();
//                finish();
//                startActivity(new Intent(BlankActivity.this, MainActivity.class));
//            }
//            case R.id.SearchMenu:{
//                startActivity(new Intent(BlankActivity.this, Main2Activity.class));
//            }
//            case R.id.Friend_search:{
//                //startActivity(new Intent(BlankActivity.this, FindFriendsActivity.class));
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
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
//                            selectedFragment = new AllClubs2Fragment(); //Changes fragment to my books
//                            Toast.makeText(getApplicationContext(), "Clubs Selected", Toast.LENGTH_SHORT).show();
//                            break;
//                        case R.id.nav_books:
//                            selectedFragment = new MyBooks2Fragment(); //Changes fragment to my books
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