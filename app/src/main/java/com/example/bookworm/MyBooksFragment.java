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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class MyBooksFragment extends Fragment implements View.OnClickListener {
    private View view;

    private FirebaseAuth mAuth;
    private String UserId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Reading");


    private NoteAdapter2 adapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_my_books, container, false);

        mAuth = FirebaseAuth.getInstance();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        setupRecyclerView();
        adapter2.startListening();
    }

    private void finish() {
    }
    private void setupRecyclerView(){
        UserId = mAuth.getCurrentUser().getUid();

        if (UserId != null) {

            Query query = notebookRef.whereEqualTo("user", UserId);
            System.out.println("BOOK ADDED TO THE USERS READING LIST!" + query);

            FirestoreRecyclerOptions<Note2> options = new FirestoreRecyclerOptions.Builder<Note2>()
                    .setQuery(query, Note2.class)
                    .build();
            Toast.makeText(getContext(), "Selected", Toast.LENGTH_SHORT).show();

            adapter2 = new NoteAdapter2(options);

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter2);


//Coding in the flow, 2018 [ONLINE] Available at: https://www.youtube.com/watch?v=dTuhMFP-a1g&t=185s [Accessed on the 19th March 2019]

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    adapter2.deleteItem(viewHolder.getAdapterPosition());

                }
            }).attachToRecyclerView(recyclerView);

//            adapter2.setmOnNoteListener(new NoteAdapter2.OnNoteListener() {
//                @Override
//                public void onNoteClick(DocumentSnapshot documentSnapshot, int position) {
//                    Note2 note2 = documentSnapshot.toObject(Note2.class);
//                    String user = documentSnapshot.getId();
//                    String path = documentSnapshot.getReference().getPath();
//                    note2.getAuthor();
//                    Toast.makeText(getContext(),"POSITITON: " + position + "ID: " + user,Toast.LENGTH_SHORT).show();
//
//                    System.out.println("USERS BOOK" + position + user);
//
//                    //Opens a new fragment, picking up the current boooks position in the recycler view and ID
//                    RateReviewBookFragment fragment = new RateReviewBookFragment(); //CREATE A NEW for the books to rate and write review FRAGMENT
//                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
//
//
//
//
//                }
//            });



        }
    }


    @Override
    public void onStop() {
        super.onStop();

    }




    @Override
    public void onClick(View v) {

    }
}

