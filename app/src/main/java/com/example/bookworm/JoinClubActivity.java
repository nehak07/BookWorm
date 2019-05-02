package com.example.bookworm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubsActivity.EXTRA_USERNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class JoinClubActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView ClubName, ClubDesc, ClubAdmin;
    private Button Join;
    private Toolbar mToolbar;


    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private String NAME;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_club);

        mToolbar = (Toolbar) findViewById(R.id.Join_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Join Book Clubs");

        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        ClubName = findViewById(R.id.txt_ClubName_detail);
        ClubDesc = findViewById(R.id.txt_ClubDesc_Details);
        ClubAdmin = findViewById(R.id.txt_ClubAdmin);

        Join = findViewById(R.id.btnJoin_Club);
        Join.setOnClickListener(this);

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String CLUBADMIN = intent.getStringExtra(EXTRA_USERNAME);
        NAME = intent.getStringExtra(EXTRA_NAME);

        //Toast.makeText(getApplicationContext(), NAME, Toast.LENGTH_SHORT).show();



        //save book club members user ID into an array list onto the database

        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                String UserID = user.getUid();

                Map<String, Object> NewMember = new HashMap<>();
                NewMember.put("memberID", UserID);
                NewMember.put("memberName", NAME);

                db.collection("Club").document(CLUBNAME).collection("Members").document(UserID)
                        .set(NewMember).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent detailsIntent = new Intent(JoinClubActivity.this, BookClubMemberActivity.class);
                        detailsIntent.putExtra(EXTRA_CLUBNAME, CLUBNAME);
                        detailsIntent.putExtra(EXTRA_USERNAME, CLUBADMIN);
                        detailsIntent.putExtra(EXTRA_CLUBDESC, CLUBDESC);
                        startActivity(detailsIntent);
                    }
                });
            }

        });

        TextView textViewClubName = findViewById(R.id.txt_ClubName_detail);
        TextView textViewClubDesc = findViewById(R.id.txt_ClubDesc_Details);
        TextView textViewClubAdmin = findViewById(R.id.txt_ClubAdmin);

        textViewClubName.setText(CLUBNAME);
        textViewClubDesc.setText(CLUBDESC);
        textViewClubAdmin.setText(CLUBADMIN);
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
        Intent mainintent = new Intent(JoinClubActivity.this, AllClubsActivity.class);
        startActivity(mainintent);
    }

    @Override
    public void onClick(View v) {

    }
}
