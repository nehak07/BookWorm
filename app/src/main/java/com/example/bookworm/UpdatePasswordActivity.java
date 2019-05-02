package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UpdatePasswordActivity extends AppCompatActivity {
    private EditText EmailPassword;
    private Button Reset;
    private FirebaseAuth firebaseAuth;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        mToolbar = (Toolbar) findViewById(R.id.password_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Update Password");


        EmailPassword = (EditText)findViewById(R.id.etPasswordEmail);
        Reset = (Button)findViewById(R.id.btnResetPassword);
        firebaseAuth = FirebaseAuth.getInstance();


        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = EmailPassword.getText().toString().trim();

                if(userEmail.equals("")){
                    Toast.makeText(UpdatePasswordActivity.this, "Please enter your registered email address", Toast.LENGTH_SHORT).show();

                }else
                {
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UpdatePasswordActivity.this, "Password update email sent" + "\n Please update password and log back in", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(UpdatePasswordActivity.this, MainActivity.class));
                                firebaseAuth.signOut();
                            }else{
                                Toast.makeText(UpdatePasswordActivity.this, "Error in sending password reset email!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
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
        Intent mainintent = new Intent(UpdatePasswordActivity.this, SettingActivity.class);
        startActivity(mainintent);
    }
}
