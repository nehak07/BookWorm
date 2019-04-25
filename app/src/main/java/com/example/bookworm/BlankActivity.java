package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BlankActivity extends AppCompatActivity {


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;

    private Button CreateClub;
    private View view;
    private DatabaseReference UserRef ;
    private TextView FullName, UserName;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        FullName = (TextView) findViewById(R.id.txt_ClubName);

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

        UserName = (TextView) navView.findViewById(R.id.UserNameNav) ;


        CreateClub = findViewById(R.id.btn_create_club);

        CreateClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BlankActivity.this, CreateBookClubActivity.class));
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //Fragment selectedFragment =null;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(BlankActivity.this, BlankActivity.class));

                            Toast.makeText(getApplicationContext(), "Home Selected", Toast.LENGTH_SHORT).show();
                            break;

                    case R.id.nav_Club:
                            startActivity(new Intent(BlankActivity.this, AllClubsActivity.class));
                            Toast.makeText(getApplicationContext(), "Clubs Selected", Toast.LENGTH_SHORT).show();
                            break;

                    case R.id.nav_books:
                           // selectedFragment = new MyBooks2Fragment(); //Changes fragment to my books
                            startActivity(new Intent(BlankActivity.this, MyBooksActivity.class));
                            Toast.makeText(getApplicationContext(), "Books Selected", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(BlankActivity.this, FriendsActivity.class));
                        Toast.makeText(getApplicationContext(), "Message Selected", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_rating:
                        startActivity(new Intent(BlankActivity.this, DisplayReviewActivity.class));
                        Toast.makeText(getApplicationContext(), "Rating Selected", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_setting:
                        //selectedFragment = new SettingsFragment(); //Change fragment to settings
                        startActivity(new Intent(BlankActivity.this, SettingActivity.class));
                        Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_logout:
                        Toast.makeText(getApplicationContext(), "Logout Selected", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        startActivity(new Intent(BlankActivity.this, MainActivity.class));
                        break;
            }

                return false;
            }
        });

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String Username = dataSnapshot.child("fullname").getValue().toString();
                    String Name = dataSnapshot.child("fullname").getValue().toString();

                    FullName.setText("Welcome: "+ Username);
                    UserName.setText(Name);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

}