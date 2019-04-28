package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBNAME;

public class ViewMembersActivity extends AppCompatActivity implements NoteAdapter10.OnNoteListener {

    private FirebaseAuth mAuth;
    private String UserId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Club");

    private Toolbar mToolbar;
    public String CLUBNAME;


    private ArrayList<Note10> ListOfNotes = new ArrayList<>();
    ArrayList<Query> ListOfOptions = new ArrayList<>();

    private NoteAdapter10 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_members);

        Intent intent = getIntent();
        CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

        mAuth = FirebaseAuth.getInstance();
        setupRecyclerView();

    }

    private void setupRecyclerView() {

        UserId = mAuth.getCurrentUser().getUid();

        // if (UserId == null) {
//
//        for (String BrandSelected : queryBundle.getStringArrayList("Genres")) {
//
//            Query query1 = notebookRef.whereEqualTo("Genres", BrandSelected).whereLessThanOrEqualTo("Price",Price);
//
//            ListOfOptions.add(query1);
//
//        }



        Query query = notebookRef.document(CLUBNAME).collection("Members");

        ListOfOptions.add(query);
        // }


        adapter = new NoteAdapter10(ListOfOptions);
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

//        System.out.println("onClickNote: Clicked  !!" + position);
        // Intent detailsIntent = new Intent(DisplayReviewActivity.this, BlankActivity.class);
//        Log.d("TESTING", String.valueOf(ListOfNotes.size()));
//        Note8 clickedItem = ListOfNotes.get(position);
//
//
//        //Carry out the outfit details onto the next fragment
//
//
//        detailsIntent.putExtra(EXTRA_URL, clickedItem.getURL());
//        //detailsIntent.putExtra(EXTRA_GENRE, clickedItem.getGenres());
//        detailsIntent.putExtra(EXTRA_NAME, clickedItem.getName());
//        // detailsIntent.putExtra(EXTRA_PRICE, clickedItem.getPrice());
//        // detailsIntent.putExtra(EXTRA_CAT,clickedItem.getCategory());
//        detailsIntent.putExtra(EXTRA_AUTHOR, clickedItem.getAuthor());
//        //detailsIntent.putExtra(EXTRA_BOOKURL, clickedItem.getWebsiteURL());

        // startActivity(detailsIntent);


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
        Intent intent = new Intent(ViewMembersActivity.this, BlankActivity.class);
        startActivity(intent);
    }
}
