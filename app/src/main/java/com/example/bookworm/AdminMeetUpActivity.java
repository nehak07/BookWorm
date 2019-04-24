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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_USERNAME;

public class AdminMeetUpActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_MEETINGDESC = "MEETINGDESC";
    public static final String EXTRA_MEETINGDATE = "MEETINGDATE";
    public static final String EXTRA_MEETINGTIME = "MEETINGTIME";

    private TextView mDisplayDate,DisplayTime, MeetingInfo;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Toolbar mToolbar;
    private EditText  ClubDesc;

    private Button SaveUserDetails;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private ProgressBar progressBar;

    private String currentUserID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String UserId;

    private CollectionReference notebookRef = db.collection("Club");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_meet_up);

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

        TextView textViewClubName = findViewById(R.id.txt_ClubName_detail);

        textViewClubName.setText(CLUBNAME);

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
        UserId = mAuth.getCurrentUser().getUid();

        mDisplayDate = (TextView) findViewById(R.id.txt_Date);
        ClubDesc = findViewById(R.id.etClubDesc);
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
        final String MEETINGTIME = DisplayTime.getText().toString();
        final String MEETINGDATE = mDisplayDate.getText().toString();
       final String MEETINGDESC = ClubDesc.getText().toString();
        //String desc = Book.getText().toString();

        if(TextUtils.isEmpty(MEETINGTIME)){
            Toast.makeText(this, "Please enter your favourite book name", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(MEETINGDATE)){
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
        }

        //if(TextUtils.isEmpty(MEETINGDESC)){
           // Toast.makeText(this, "Please enter your favourite genre", Toast.LENGTH_SHORT).show();
      //  }
        else{


            FirebaseUser user = mAuth.getCurrentUser();
            String UserID = user.getUid();

            Map<String, Object> NewMember = new HashMap<>();

            NewMember.put("time", MEETINGTIME);
            NewMember.put("date", MEETINGDATE);
            NewMember.put("clubdesc", MEETINGDESC);
            Intent intent = getIntent();

            final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

            db.collection("Club").document(CLUBNAME).collection("Events").document(UserID)
                    .set(NewMember).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Intent detailsIntent = new Intent(AdminMeetUpActivity.this, ViewMeetingActivity.class);

                  detailsIntent.putExtra(EXTRA_MEETINGDESC, MEETINGDESC);

                   detailsIntent.putExtra(EXTRA_MEETINGDATE, MEETINGDATE);

                    detailsIntent.putExtra(EXTRA_MEETINGTIME, MEETINGTIME);

                    detailsIntent.putExtra(EXTRA_CLUBNAME, CLUBNAME);

                    startActivity(detailsIntent);
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

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);

        Intent i = new Intent(AdminMeetUpActivity.this, AdminSettingsActivity.class);
        i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
        i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
        i.putExtra(EXTRA_USERNAME,USERNAME);
        i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
        i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
        i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);

        startActivity(i);
    }



//https://www.youtube.com/watch?v=QMwaNN_aM3U Accessed on 18th April 2019

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView DisplayTime = (TextView) findViewById(R.id.txt_Time);
        DisplayTime.setText( hourOfDay + ":" + minute );
    }
}
