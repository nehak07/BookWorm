package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllClubsActivity extends AppCompatActivity implements NoteAdapter7.OnNoteListener{

    public static final String EXTRA_CLUBNAME = "CLUBNAME";
    public static final String EXTRA_USERNAME = "USERNAME";
    public static final String EXTRA_CLUBDESC = "CLUBDESC";


    private FirebaseAuth mAuth;
    private String UserId;


    private ArrayList<Note7> ListOfNotes = new ArrayList<>();
    ArrayList<Query> ListOfOptions = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Club");

    private NoteAdapter7 adapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clubs);

        mToolbar = (Toolbar) findViewById(R.id.AllClub_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Friends");

        mAuth = FirebaseAuth.getInstance();
        setupRecyclerView();
    }

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
        Intent mainintent = new Intent(AllClubsActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }

    private void setupRecyclerView() {

        UserId = mAuth.getCurrentUser().getUid();

        // if (UserId == null) {


        Query query = notebookRef.whereEqualTo("group", false);

        ListOfOptions.add(query);
        // }


        adapter = new NoteAdapter7(ListOfOptions);
        adapter.setOnNoteListener(this);
        ListOfNotes = adapter.NoteView(ListOfOptions);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllClubsActivity.this));
        recyclerView.setAdapter(adapter);

        //}

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
        final Note7 clickedItem = ListOfNotes.get(position);

        DocumentReference docRef = db.collection("Club").document(clickedItem.getClubname());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    String adminID = document.getString("admin");

                    if (adminID.equals(UserId)){
                        Intent detailsIntent = new Intent(AllClubsActivity.this, BookClubAdminActivity.class);

                        detailsIntent.putExtra(EXTRA_CLUBNAME, clickedItem.getClubname());

                        detailsIntent.putExtra(EXTRA_USERNAME, clickedItem.getUsername());

                        detailsIntent.putExtra(EXTRA_CLUBDESC, clickedItem.getClubdesc());

                        startActivity(detailsIntent);
                    }
                }
            }
        });

        db.collection("Club").document(clickedItem.getClubname()).collection("Members").whereEqualTo("memberID", UserId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Intent detailsIntent = new Intent(AllClubsActivity.this, BookClubMemberActivity.class);

                        detailsIntent.putExtra(EXTRA_CLUBNAME, clickedItem.getClubname());

                        detailsIntent.putExtra(EXTRA_USERNAME, clickedItem.getUsername());

                        detailsIntent.putExtra(EXTRA_CLUBDESC, clickedItem.getClubdesc());

                        startActivity(detailsIntent);
                    }

                }
            }
        });


        // have an if statement so it directs the user to the right activity

        System.out.println("onClickNote: Clicked  !!" + position);
        //  Intent detailsIntent = new Intent(getActivity(), JoinClubActivity.class);

        Intent detailsIntent = new Intent(AllClubsActivity.this, JoinClubActivity.class);


        //Log.d("TESTING", String.valueOf(ListOfNotes.size()));


        //Carry out the outfit details onto the next fragment


        detailsIntent.putExtra(EXTRA_CLUBNAME, clickedItem.getClubname());

        detailsIntent.putExtra(EXTRA_USERNAME, clickedItem.getUsername());

        detailsIntent.putExtra(EXTRA_CLUBDESC, clickedItem.getClubdesc());

        startActivity(detailsIntent);


    }
}