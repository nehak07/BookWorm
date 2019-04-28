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

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                    return 0;
                }

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    adapter2.deleteItem(viewHolder.getAdapterPosition());

                }
            }).attachToRecyclerView(recyclerView);


//Coding in the flow, 2018 [ONLINE] Available at: https://www.youtube.com/watch?v=dTuhMFP-a1g&t=185s [Accessed on the 19th March 2019]


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

