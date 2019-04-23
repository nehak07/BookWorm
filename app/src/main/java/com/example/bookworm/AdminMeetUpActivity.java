package com.example.bookworm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class AdminMeetUpActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView mDisplayDate,DisplayTime, MeetingInfo;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Toolbar mToolbar;

    private Button SaveUserDetails;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private ProgressBar progressBar;

    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_meet_up);

        mToolbar = (Toolbar) findViewById(R.id.AdminCreateMeeting_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create Meeting");

        SaveUserDetails = (Button) findViewById(R.id.btn_SaveAccountDetails);

        SaveUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAccountInfo();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Testing").child(currentUserID);


        mDisplayDate = (TextView) findViewById(R.id.txt_Date);

        DisplayTime = (TextView) findViewById(R.id.txt_Time);

        Button button = (Button) findViewById(R.id.btn_Time);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal  = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AdminMeetUpActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

//https://www.youtube.com/watch?v=hwe1abDO2Ag Accessed on: 18th April 2019


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);

                System.out.println("onDate: date:" + year +"/" + month + "/" + dayOfMonth);
            }
        };

    }

    //Add fav book? or genre instead
    private void SaveAccountInfo() {
        String time = DisplayTime.getText().toString();
        String date = mDisplayDate.getText().toString();
        //String desc = Book.getText().toString();

        if(TextUtils.isEmpty(time)){
            Toast.makeText(this, "Please enter your favourite book name", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(date)){
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
        }

        //if(TextUtils.isEmpty(desc)){
           // Toast.makeText(this, "Please enter your favourite genre", Toast.LENGTH_SHORT).show();
      //  }
        else{
           // progressBar.setVisibility(View.VISIBLE);

            HashMap usermap = new HashMap();
            usermap.put("time", time);
            usermap.put("date", date);
            //usermap.put("desc", desc);
            UserRef.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                   // progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AdminMeetUpActivity.this, "Event Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminMeetUpActivity.this, AdminSettingsActivity.class));
                    }else{
                        String message = task.getException().getMessage();
                        Toast.makeText(AdminMeetUpActivity.this, "An error has occurred" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
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
        Intent mainintent = new Intent(AdminMeetUpActivity.this, AdminSettingsActivity.class);
        startActivity(mainintent);
    }


//https://www.youtube.com/watch?v=QMwaNN_aM3U Accessed on 18th April 2019

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView DisplayTime = (TextView) findViewById(R.id.txt_Time);
        DisplayTime.setText( hourOfDay + ":" + minute );
    }
}
