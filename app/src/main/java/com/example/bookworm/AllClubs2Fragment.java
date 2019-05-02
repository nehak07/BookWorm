package com.example.bookworm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AllClubs2Fragment extends Fragment implements NoteAdapter7.OnNoteListener{

    public static final String EXTRA_CLUBNAME = "CLUBNAME";
    public static final String EXTRA_USERNAME = "USERNAME";
    public static final String EXTRA_CLUBDESC = "CLUBDESC";

    private View view;

    private FirebaseAuth mAuth;
    private String UserId;


    private ArrayList<Note7> ListOfNotes = new ArrayList<>();
    ArrayList<Query> ListOfOptions = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Club");

    private NoteAdapter7 adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_clubs2, container, false);

        mAuth = FirebaseAuth.getInstance();
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {

        UserId = mAuth.getCurrentUser().getUid();
        Query query = notebookRef.whereEqualTo("group", false);
        ListOfOptions.add(query);

        adapter = new NoteAdapter7(ListOfOptions);
        adapter.setOnNoteListener(this);
        ListOfNotes = adapter.NoteView(ListOfOptions);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
         final Note7 clickedItem = ListOfNotes.get(position);

        DocumentReference docRef = db.collection("Club").document(clickedItem.getClubname());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    String adminID = document.getString("admin");

                    if (adminID.equals(UserId)){
                        Intent detailsIntent = new Intent(getActivity(), BookClubAdminActivity.class);

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

                        Intent detailsIntent = new Intent(getActivity(), BookClubMemberActivity.class);

                        detailsIntent.putExtra(EXTRA_CLUBNAME, clickedItem.getClubname());

                        detailsIntent.putExtra(EXTRA_USERNAME, clickedItem.getUsername());

                        detailsIntent.putExtra(EXTRA_CLUBDESC, clickedItem.getClubdesc());

                        startActivity(detailsIntent);
                    }

                }
            }
        });

        Intent detailsIntent = new Intent(getActivity(), JoinClubActivity.class);
        detailsIntent.putExtra(EXTRA_CLUBNAME, clickedItem.getClubname());
        detailsIntent.putExtra(EXTRA_USERNAME, clickedItem.getUsername());
        detailsIntent.putExtra(EXTRA_CLUBDESC, clickedItem.getClubdesc());
        startActivity(detailsIntent);
    }

}
