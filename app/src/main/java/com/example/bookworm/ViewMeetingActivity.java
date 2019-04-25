package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDATE;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDESC;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGTIME;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_USERNAME;
import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBNAME;


public class ViewMeetingActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private TextView Time, Date, Desc;

    //private DatabaseReference UserRef ;
    private FirebaseAuth mAuth;
    private String currentUserID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Club");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);

        mToolbar = (Toolbar) findViewById(R.id.ViewMeeting_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Admin Area");


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);

        TextView textViewClubName = findViewById(R.id.txt_ClubName);

        textViewClubName.setText("Club Name: " + CLUBNAME);

      final TextView ViewDate = findViewById(R.id.txt_ViewDate);

        ViewDate.setText("Meeting Date: " + MEETINGDATE);

        TextView ViewTime = findViewById(R.id.txt_ViewTime);

        ViewTime.setText("Meeting Time: " + MEETINGTIME);

        TextView ViewDesc = findViewById(R.id.txt_view_MeetingDesc);

        ViewDesc.setText("Meeting Description: " + MEETINGDESC);

//        notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                String data = "";
//
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                    Note9 note = documentSnapshot.toObject(Note9.class);
//
//                    String time = note.getTime();
//                    String date = note.getDate();
//                    String desc = note.getClubdesc();
//
//                    date += "Time: " + time + "\nDate: " + data
//                            +"\nDesc: " + desc + "\n\n";
//                }
//
//                ViewDate.setText(data);
//            }
//        });


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
        final String CLUBDESC = intent.getStringExtra(EXTRA_CLUBDESC);
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        final String USERNAME = intent.getStringExtra(EXTRA_USERNAME);
        final String MEETINGDESC = intent.getStringExtra(EXTRA_MEETINGDESC);
        final String MEETINGDATE = intent.getStringExtra(EXTRA_MEETINGDATE);
        final String MEETINGTIME = intent.getStringExtra(EXTRA_MEETINGTIME);

        Intent mainintent = new Intent(ViewMeetingActivity.this, BookClubAdminActivity.class);

        mainintent.putExtra(EXTRA_CLUBNAME,CLUBNAME);
        mainintent.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
        mainintent.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
        mainintent.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);
        mainintent.putExtra(EXTRA_CLUBDESC,CLUBDESC);
        mainintent.putExtra(EXTRA_USERNAME,USERNAME);


        startActivity(mainintent);
    }
}
