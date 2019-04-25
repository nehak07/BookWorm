package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;

public class AdminDeleteActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView ClubName, ClubDesc, ClubAdmin;
    private Button Join;
    private Toolbar mToolbar;


    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete);

        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

        Join = findViewById(R.id.btnJoin_Club);
        Join.setOnClickListener(this);


        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                String UserID = user.getUid();


                db.collection("Club").document(CLUBNAME)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AdminDeleteActivity.this, "DocumentSnapshot successfully deleted!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(AdminDeleteActivity.this, "Error deleting document "+ e, Toast.LENGTH_SHORT).show();

                            }
                        });

            }

        });


    }

    @Override
    public void onClick(View v) {

    }
}

