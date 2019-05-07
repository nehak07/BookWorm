package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDATE;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGDESC;
import static com.example.bookworm.AdminMeetUpActivity.EXTRA_MEETINGTIME;
import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBNAME;
import static com.example.bookworm.AllClubsActivity.EXTRA_USERNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class MemberViewClubBooksActivity extends AppCompatActivity implements NoteAdapter11.OnNoteListener {

    private FirebaseAuth mAuth;
    private String UserId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Club");

    private Toolbar mToolbar;
    private String NAME;
    public String CLUBNAME;

    private ArrayList<Note11> ListOfNotes = new ArrayList<>();
    ArrayList<Query> ListOfOptions = new ArrayList<>();

    private NoteAdapter11 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_view_club_books);

        Intent intent = getIntent();
        NAME = intent.getStringExtra(EXTRA_NAME);
        CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);


        mToolbar = findViewById(R.id.memberBooks_Toolbar);
        setSupportActionBar(mToolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setTitle("Book Club Books");

        getResources().getColor(R.color.color_button_clicked);
        getResources().getColor(R.color.color_button_unclicked);

        mAuth = FirebaseAuth.getInstance();
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        UserId = mAuth.getCurrentUser().getUid();
        Query query = notebookRef.document(CLUBNAME).collection("ClubReading");
        ListOfOptions.add(query);

        adapter = new NoteAdapter11(ListOfOptions);
        adapter.setOnNoteListener(this);
        ListOfNotes = adapter.NoteView(ListOfOptions);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        //adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //adapter.startListening();
    }

    @Override
    public void onNoteClick(int position) {
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


        Intent i = new Intent(MemberViewClubBooksActivity.this, BookClubMemberActivity.class);
        i.putExtra(EXTRA_NAME,NAME);
        i.putExtra(EXTRA_CLUBNAME,CLUBNAME);
        i.putExtra(EXTRA_CLUBDESC,CLUBDESC);
        i.putExtra(EXTRA_USERNAME,USERNAME);
        i.putExtra(EXTRA_MEETINGDESC,MEETINGDESC);
        i.putExtra(EXTRA_MEETINGDATE,MEETINGDATE);
        i.putExtra(EXTRA_MEETINGTIME,MEETINGTIME);

        startActivity(i);

    }
}
