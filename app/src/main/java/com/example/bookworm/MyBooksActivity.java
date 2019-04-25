package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MyBooksActivity extends AppCompatActivity implements NoteAdapter5.OnNoteListener  {

    private FirebaseAuth mAuth;
    private String UserId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Reading");

    private Toolbar mToolbar;

    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_GENRE = "GENRE";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_PRICE = "PRICE";
    // public static final String EXTRA_CAT = "CAT";
    public static final String EXTRA_AUTHOR = "AUTHOR";
    public static final String EXTRA_BOOKURL = "BOOKURL";

    private ArrayList<Note5> ListOfNotes = new ArrayList<>();
    ArrayList<Query> ListOfOptions = new ArrayList<>();

    private NoteAdapter5 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        mToolbar = findViewById(R.id.MyBooks1_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Reading List");

        getResources().getColor(R.color.color_button_clicked);
        getResources().getColor(R.color.color_button_unclicked);


        mAuth = FirebaseAuth.getInstance();
        setupRecyclerView();

    }

    private void setupRecyclerView() {

        UserId = mAuth.getCurrentUser().getUid();

        // if (UserId == null) {


        Query query = notebookRef.whereEqualTo("user", UserId);

        ListOfOptions.add(query);
        // }


        adapter = new NoteAdapter5(ListOfOptions);
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

        System.out.println("onClickNote: Clicked  !!" + position);
        Intent detailsIntent = new Intent(MyBooksActivity.this, RateBookActivity.class);
        Log.d("TESTING", String.valueOf(ListOfNotes.size()));
        Note5 clickedItem = ListOfNotes.get(position);


        //Carry out the outfit details onto the next fragment


        detailsIntent.putExtra(EXTRA_URL, clickedItem.getURL());
        //detailsIntent.putExtra(EXTRA_GENRE, clickedItem.getGenres());
        detailsIntent.putExtra(EXTRA_NAME, clickedItem.getName());
        // detailsIntent.putExtra(EXTRA_PRICE, clickedItem.getPrice());
        // detailsIntent.putExtra(EXTRA_CAT,clickedItem.getCategory());
        detailsIntent.putExtra(EXTRA_AUTHOR, clickedItem.getAuthor());
        //detailsIntent.putExtra(EXTRA_BOOKURL, clickedItem.getWebsiteURL());

        startActivity(detailsIntent);


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
        Intent intent = new Intent(MyBooksActivity.this, BlankActivity.class);
        startActivity(intent);
    }
}
