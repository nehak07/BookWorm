package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

        final TextView textViewClubName = findViewById(R.id.txt_ClubName);
        final TextView ViewDate = findViewById(R.id.txt_ViewDate);
       final TextView ViewTime = findViewById(R.id.txt_ViewTime);
       final TextView ViewDesc = findViewById(R.id.txt_view_MeetingDesc);


        notebookRef.document(CLUBNAME).collection("Events")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().isEmpty()){

                        textViewClubName.setText("NO MEET UP SCHEDULED");
                        ViewDate.setVisibility(View.INVISIBLE);
                        ViewTime.setVisibility(View.INVISIBLE);
                        ViewDesc.setVisibility(View.INVISIBLE);
                        //There is no meeting set up!
                    }
                    else {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                QuerySnapshot query = task.getResult();

                                Note9 note = query.toObjects(Note9.class).get(0);
                                textViewClubName.setText("Club Name: " + CLUBNAME);
                                ViewDate.setText("Meeting Date: " + note.getDate());
                                ViewTime.setText("Meeting Time: " + note.getTime());
                                ViewDesc.setText("Meeting Description: " + note.getClubdesc());

                            }
                        }
                    }
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
