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

public class DisplayReviewActivity extends AppCompatActivity implements NoteAdapter8.OnNoteListener  {

    private FirebaseAuth mAuth;
    private String UserId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Rating");
    private Toolbar mToolbar;
    private ArrayList<Note8> ListOfNotes = new ArrayList<>();
    ArrayList<Query> ListOfOptions = new ArrayList<>();

    private NoteAdapter8 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_review);

        mToolbar = findViewById(R.id.DisplayReview_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Book Reviews");

        getResources().getColor(R.color.color_button_clicked);
        getResources().getColor(R.color.color_button_unclicked);

        mAuth = FirebaseAuth.getInstance();
        setupRecyclerView();

    }

    private void setupRecyclerView() {

        UserId = mAuth.getCurrentUser().getUid();
        Query query = notebookRef.whereEqualTo("user", UserId);
        ListOfOptions.add(query);

        adapter = new NoteAdapter8(ListOfOptions);
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onNoteClick(int position) {
    }

    public void removeItem(final int position){

        db.collection("Rating").document(ListOfNotes.remove(position).getName())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        adapter.notifyItemRemoved(position);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
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
        Intent intent = new Intent(DisplayReviewActivity.this, BlankActivity.class);
        startActivity(intent);
    }
}
