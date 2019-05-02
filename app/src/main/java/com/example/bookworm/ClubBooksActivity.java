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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBNAME;

public class ClubBooksActivity extends AppCompatActivity implements NoteAdapter5.OnNoteListener  {

    private FirebaseAuth mAuth;
    private String UserId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Club");

    private Toolbar mToolbar;
    public String CLUBNAME;

    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_GENRE = "GENRE";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_PRICE = "PRICE";
    public static final String EXTRA_AUTHOR = "AUTHOR";
    public static final String EXTRA_BOOKURL = "BOOKURL";

    private ArrayList<Note5> ListOfNotes = new ArrayList<>();
    ArrayList<Query> ListOfOptions = new ArrayList<>();

    private NoteAdapter5 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_books);
        Intent intent = getIntent();
        CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

        getResources().getColor(R.color.color_button_clicked);
        getResources().getColor(R.color.color_button_unclicked);

        mAuth = FirebaseAuth.getInstance();
        setupRecyclerView();

    }

    private void setupRecyclerView() {

        UserId = mAuth.getCurrentUser().getUid();
        Query query = notebookRef.document(CLUBNAME).collection("ClubReading");
        ListOfOptions.add(query);

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
    }

    public void removeItem(final int position){

        db.collection("Club").document(CLUBNAME).collection("ClubReading").document(ListOfNotes.remove(position).getName())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(MyBooksActivity.this, "BYEEEE", Toast.LENGTH_SHORT).show();
                        adapter.notifyItemRemoved(position);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(MyBooksActivity.this, "not working", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDeleteClick(int position) {
        removeItem(position);
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
        Intent intent = new Intent(ClubBooksActivity.this, AdminSettingsActivity.class);
        startActivity(intent);
    }
}
