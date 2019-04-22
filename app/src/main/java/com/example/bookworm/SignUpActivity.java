package com.example.bookworm;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText Email, Password;
    private TextView signIn;
    private Button SignUp;
    private View view;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private boolean emailAddressCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Email = (EditText) findViewById(R.id.etEmailIn);
        Password = (EditText) findViewById(R.id.etPassIn);
        SignUp = findViewById(R.id.btnSignUp2);
        signIn = (TextView) findViewById(R.id.txtSignInBack);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.txtSignInBack).setOnClickListener(this);
        findViewById(R.id.btnSignUp2).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    // Simplified Coding, 2017. [ONLINE] Available at: https://www.youtube.com/watch?v=KetDpFv7HfU&t=326s [Accessed on the 6th March 2019 ]

    private void registerUser(){
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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){

                    SendVerificationEmail();
                    //finish();
                    //startActivity(new Intent(SignUpActivity.this, AccountSetUpActivity.class));
                   // Toast.makeText(getApplicationContext(),"User Registered Successfull", Toast.LENGTH_SHORT).show();
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"Email address already used!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }

    private void SendVerificationEmail()
    {
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Registration Successfull! We're send you an email, please check and veify your account", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        mAuth.signOut();
                    }else
                        {
                            String error = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),"Error:" + error , Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                        }
                }
            });
        }



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnSignUp2:
                registerUser();

                break;

            case R.id.txtSignInBack:
                finish();
                startActivity(new Intent(this, MainActivity.class));

                break;

        }

    }
}
