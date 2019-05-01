package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_USERNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class MemberSettingActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView ClubName, ClubDesc, ClubAdmin;
    private Button Join;
    private Toolbar mToolbar;
    private String NAME;


    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_setting);

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        NAME = intent.getStringExtra(EXTRA_NAME);

        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        Join = findViewById(R.id.btnJoin_Club);
        Join.setOnClickListener(this);


        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                String UserID = user.getUid();



                Map<String,Object> updates = new HashMap<>();
                updates.put("memberID", FieldValue.delete());

                Task<Void> docRef = db.collection("Club").document(CLUBNAME).collection("Members").document(UserID)
                        .update(updates).addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                Intent detailsIntent = new Intent(MemberSettingActivity.this, AllClubsActivity.class);
                                detailsIntent.putExtra(EXTRA_CLUBNAME, CLUBNAME);
                                startActivity(detailsIntent);
                            }
                        });


//
//                db.collection("Club").document(CLUBNAME).collection("Members").document(UserID)
//                        .set(NewMember).addOnSuccessListener(new OnSuccessListener<Void>()
//                {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Intent detailsIntent = new Intent(MemberSettingActivity.this, BookClubMemberActivity.class);
//                        detailsIntent.putExtra(EXTRA_CLUBNAME, CLUBNAME);
//                        startActivity(detailsIntent);
//                    }
//                });
            }

        });




    }

    @Override
    public void onClick(View v) {

    }
}
