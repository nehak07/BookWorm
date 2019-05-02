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

    private Button CreateClub, MentalHealth;
    private View view;
    private DatabaseReference UserRef ;
    private TextView FullName, UserName;
    private String currentUserID;
    private String Name;

    public static final String EXTRA_NAME = "MEMBERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        getResources().getColor(R.color.White);



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
                Intent detailsIntent3 = new Intent(BlankActivity.this, AllClubsActivity.class);
                detailsIntent3.putExtra(EXTRA_NAME, Name);
                startActivity(detailsIntent3);
            }
        });

        MentalHealth = findViewById(R.id.btn_health);
        MentalHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BlankActivity.this, MentalHealthActivity.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:

                        Intent detailsIntent = new Intent(BlankActivity.this, BlankActivity.class);
                        detailsIntent.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent);
                            break;

                    case R.id.nav_Club:
                        Intent detailsIntent2 = new Intent(BlankActivity.this, AllClubsActivity.class);
                       detailsIntent2.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent2);
                            break;

                    case R.id.nav_books:

                        Intent detailsIntent3 = new Intent(BlankActivity.this, MyBooksActivity.class);
                        detailsIntent3.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent3);
                            break;

                    case R.id.nav_MyFriends:

                        Intent detailsIntent4 = new Intent(BlankActivity.this, FriendsActivity.class);
                        detailsIntent4.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent4);
                        break;

                    case R.id.nav_FindFriends:

                        Intent detailsIntent5 = new Intent(BlankActivity.this, FindFriendsActivity.class);
                        detailsIntent5.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent5);
                        break;

                    case R.id.nav_Rec:

                        Intent detailsIntent6 = new Intent(BlankActivity.this, GenresActivity.class);
                        detailsIntent6.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent6);
                        break;

                    case R.id.nav_messages:

                        Intent detailsIntent7 = new Intent(BlankActivity.this, FriendsActivity.class);
                        detailsIntent7.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent7);
                        break;

                    case R.id.nav_rating:

                        Intent detailsIntent8 = new Intent(BlankActivity.this, DisplayReviewActivity.class);
                        detailsIntent8.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent8);
                        break;

                    case R.id.nav_searchbook:
                        Intent detailsIntent9 = new Intent(BlankActivity.this, Main2Activity.class);
                        detailsIntent9.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent9);
                        break;

                    case R.id.nav_setting:

                        Intent detailsIntent10 = new Intent(BlankActivity.this, SettingActivity.class);
                        detailsIntent10.putExtra(EXTRA_NAME, Name);
                        startActivity(detailsIntent10);
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
                    Name = dataSnapshot.child("fullname").getValue().toString();

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