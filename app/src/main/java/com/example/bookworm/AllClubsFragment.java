package com.example.bookworm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class AllClubsFragment extends Fragment implements View.OnClickListener  {

    private View view;

    private FirebaseAuth mAuth;
    private String UserId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Club");

    private NoteAdapter3 adapter3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_clubs, container, false);

        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        setupRecyclerView();
        adapter3.startListening();
    }

    private void finish() {
    }
    private void setupRecyclerView(){

            Query query = notebookRef.whereEqualTo("group", false);

            FirestoreRecyclerOptions<Note3> options = new FirestoreRecyclerOptions.Builder<Note3>()
                    .setQuery(query, Note3.class)
                    .build();
            Toast.makeText(getContext(), "Selected", Toast.LENGTH_SHORT).show();

            adapter3 = new NoteAdapter3(options);

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter3);


//Coding in the flow, 2018 [ONLINE] Available at: https://www.youtube.com/watch?v=dTuhMFP-a1g&t=185s [Accessed on the 19th March 2019]


            adapter3.setmOnNoteListener(new NoteAdapter3.OnNoteListener() {
                @Override
                public void onNoteClick(DocumentSnapshot documentSnapshot, int position) {
                    Note3 note3 = documentSnapshot.toObject(Note3.class);
                    String user = documentSnapshot.getId();
                    String path = documentSnapshot.getReference().getPath();
                    note3.getClubname();


                }
            });
        }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onClick(View v) {

    }
}
