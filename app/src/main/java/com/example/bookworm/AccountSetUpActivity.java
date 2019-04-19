package com.example.bookworm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.HashMap;

public class AccountSetUpActivity extends AppCompatActivity {

    private EditText Username, FullName, Gender;
    private Button SaveUserDetails;
    private ImageView ProfilePicture;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private ProgressBar progressBar;

    String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_set_up);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        Username = (EditText) findViewById(R.id.etAccount_UserName);
        FullName = (EditText) findViewById(R.id.etAccount_FullName);
        Gender = (EditText) findViewById(R.id.etAccount_Gender);
        SaveUserDetails = (Button) findViewById(R.id.btn_SaveAccountDetails);
        ProfilePicture = (ImageView) findViewById(R.id.ProfilePic);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        SaveUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAccountInfo();
            }
        });
    }


    //Add fav book? or genre instead
    private void SaveAccountInfo() {
        String username = Username.getText().toString();
        String fullname = FullName.getText().toString();
        String gender = Gender.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(gender)){
            Toast.makeText(this, "Please enter your gender", Toast.LENGTH_SHORT).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);

            HashMap usermap = new HashMap();
            usermap.put("username", username);
            usermap.put("fullname", fullname);
            usermap.put("gender", gender);
            UserRef.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AccountSetUpActivity.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AccountSetUpActivity.this, GenresActivity.class));
                    }else{
                        String message = task.getException().getMessage();
                        Toast.makeText(AccountSetUpActivity.this, "Error has occured" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
    }
}
