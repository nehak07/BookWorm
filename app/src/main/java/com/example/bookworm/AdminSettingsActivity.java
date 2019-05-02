package com.example.bookworm;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDATE;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDESC;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGTIME;

import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubsActivity.EXTRA_USERNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class AdminSettingsActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton Members, MeetUp,  Setting, Info, Book;
    private Toolbar mToolbar;
    private TextView ClubName;
    private String NAME;

    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        mToolbar = (Toolbar) findViewById(R.id.AdminAdministration_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Admin Setting Area");

        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Do you need help using the Admin Area?");
       // alert.setMessage("Message");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                Intent intent = getIntent();
                final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
                final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
                final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
                final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
                final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
                final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);
                NAME = intent.getStringExtra(EXTRA_NAME);

                Intent i = new Intent(AdminSettingsActivity.this, AdminHelpActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                i.putExtra(EXTRA_NAME, NAME);

                startActivity(i);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();


        MeetUp = findViewById(R.id.btn_MeetUp_Members);

        ClubName = findViewById(R.id.txt_ClubName_detail);

        Setting = findViewById(R.id.btn_Setting_Member);

        Info = findViewById(R.id.btn_Info_Member);
        Info.setOnClickListener(this);

        Book = findViewById(R.id.btn_Book_Member);
        Book.setOnClickListener(this);

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);
        NAME = intent.getStringExtra(EXTRA_NAME);

        TextView textViewClubName = findViewById(R.id.txt_ClubName_detail);

        textViewClubName.setText(CLUBNAME);

        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminSettingsActivity.this, EditClubDescActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                i.putExtra(EXTRA_NAME, NAME);

                startActivity(i);
            }
        });

        MeetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminSettingsActivity.this, AdminMeetUpActivity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                i.putExtra(EXTRA_NAME, NAME);

                startActivity(i);
            }
        });

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminSettingsActivity.this, Genres2Activity.class);
                i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                i.putExtra(EXTRA_USERNAME,USERNAME);
                i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                i.putExtra(EXTRA_NAME, NAME);
                startActivity(i);
            }
        });

        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(AdminSettingsActivity.this);

                alert.setTitle("Are you sure you want to delete this club??");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String UserID = user.getUid();

                        db.collection("Club").document(CLUBNAME)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AdminSettingsActivity.this, "Club Deleted!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AdminSettingsActivity.this, AllClubsActivity.class);
                                        i.putExtra(EXTRA_NAME, NAME);
                                        i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
                                        i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
                                        i.putExtra(EXTRA_USERNAME,USERNAME);
                                        i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
                                        i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
                                        i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
                                        startActivity(i);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(AdminSettingsActivity.this, "Error deleting document "+ e, Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();

            }
        });
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
        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);

        Intent i = new Intent(AdminSettingsActivity.this, BookClubAdminActivity.class);
        i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
        i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
        i.putExtra(EXTRA_USERNAME,USERNAME);
        i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
        i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
        i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);

        startActivity(i);
    }


    @Override
    public void onClick(View v) {

    }
}
