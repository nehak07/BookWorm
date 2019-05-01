package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class CreateBookClubActivity extends AppCompatActivity implements View.OnClickListener  {


    private Button CreateClub;
    private EditText ClubName, UserName, ClubDesc;
    private CheckBox ClubState;

    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    private Toolbar mToolbar;
    private String NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book_club);

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        NAME = intent.getStringExtra(EXTRA_NAME);

        mToolbar = (Toolbar) findViewById(R.id.CreateBookClub_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create Book Club");

        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        CreateClub= findViewById(R.id.btnAddClub);
        CreateClub.setOnClickListener(this);

        ClubName = findViewById(R.id.etClubName);
        UserName = findViewById(R.id.etUserName);
        ClubState = findViewById(R.id.checkBox);
        ClubDesc = findViewById(R.id.etClubDesc);
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
        Intent mainintent = new Intent(CreateBookClubActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }


    @Override
    public void onClick(View v) {
        FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();
        boolean group = false;

        if (ClubState.isChecked()){
            group = true;
        }

        String CLUBNAME = ClubName.getText().toString();
        String USERNAME = UserName.getText().toString();
        String CLUBDESC = ClubDesc.getText().toString();


        //Create a hash map
        Map<String, Object> docData = new HashMap<>();
        docData.put("admin", UserID);
        docData.put("clubname", CLUBNAME);
        docData.put("clubdesc", CLUBDESC);
        docData.put("username", USERNAME);
        docData.put("group", group);

        mFirestore.collection("Club").document(CLUBNAME).set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CreateBookClubActivity.this, "ClUB CREATED", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateBookClubActivity.this, AllClubsActivity.class));
            }
        });


    }

}
