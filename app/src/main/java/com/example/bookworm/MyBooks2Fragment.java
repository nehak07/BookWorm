package com.example.bookworm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class MyBooks2Fragment extends Fragment implements NoteAdapter5.OnNoteListener {

    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_GENRE = "GENRE";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_PRICE = "PRICE";
    // public static final String EXTRA_CAT = "CAT";
    public static final String EXTRA_AUTHOR = "AUTHOR";
    public static final String EXTRA_BOOKURL = "BOOKURL";


    private View view;

    private FirebaseAuth mAuth;
    private String UserId;


    private ArrayList<Note5> ListOfNotes = new ArrayList<>();
    ArrayList<Query> ListOfOptions = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Reading");

    private NoteAdapter5 adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_my_books2, container, false);


        getResources().getColor(R.color.color_button_clicked);
        getResources().getColor(R.color.color_button_unclicked);


        mAuth = FirebaseAuth.getInstance();
        setupRecyclerView();

        return view;
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

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        System.out.println("onClickNote: Clicked  !!" + position);
        Intent detailsIntent = new Intent(getActivity(), RateBookActivity.class);
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

    public void removeItem(int position){
        ListOfNotes.remove(position);
        adapter.notifyItemRemoved(position);

    }

    @Override
    public void onDeleteClick(int position) {
        removeItem(position);
    }


}
