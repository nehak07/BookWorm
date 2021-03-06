package com.example.bookworm;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    private EditText Email, Password;
    ProgressBar progressBar;
    private TextView ResetPassword;
    private boolean emailAddressCheck;


    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Email = (EditText) findViewById(R.id.etUserEmailSign);
        Password = (EditText) findViewById(R.id.etPasswordSign);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        ResetPassword = (TextView)findViewById(R.id.txt_Forgot_Password);


        findViewById(R.id.etUserEmailSign).setOnClickListener(this);
        findViewById(R.id.etPasswordSign).setOnClickListener(this);
        findViewById(R.id.btnLogIN).setOnClickListener(this);
        findViewById(R.id.txtSignUp).setOnClickListener(this);

        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));

            }
        });


        onStart();

    }

    private void userLogin(){

        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please enter a vaild email");
            Email.requestFocus();
            return;

        }
        if(password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }

        if(Password.length()<6){
            Password.setError("Password should be 6 characters or more");
            Password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    VerifyEmailAddress();

                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Code to check if the user has is already signed in or not
    @Override
    protected void onStart() { //If the user has already logged into the application they will be taken straight to the Home Fragment
        super.onStart();
        if(mAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(this, BlankActivity.class));
        }
    }

    private void VerifyEmailAddress()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        emailAddressCheck = user.isEmailVerified();

        if (emailAddressCheck)
        {
            finish();
            Intent intent = new Intent(MainActivity.this, AccountSetUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else
        {
            Toast.makeText(getApplicationContext(),"Please verify your account first!", Toast.LENGTH_SHORT).show();
            mAuth.signOut(); //If user has not verified thier email address they will be logged out to the application even though they have created an account
        }

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.txtSignUp:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));

                break;
            case R.id.btnLogIN:
                userLogin();
                break;

        }

    }
}
