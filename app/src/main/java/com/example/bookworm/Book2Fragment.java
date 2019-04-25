package com.example.bookworm;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;

public class Book2Fragment extends Fragment implements NoteAdapter.OnNoteListener {

    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_GENRE = "GENRE";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_PRICE = "PRICE";
    // public static final String EXTRA_CAT = "CAT";
    public static final String EXTRA_AUTHOR = "AUTHOR";
    public static final String EXTRA_BOOKURL = "BOOKURL";




    private View view;

    private Bundle queryBundle;
    private ArrayList<String> Brand;
    private int Price;
    private ArrayList <String> Theme;
    private ArrayList <String> ListOfBrands;
    private ArrayList<Note> ListOfNotes = new ArrayList<>();
    ArrayList<Query> ListOfOptions = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Books");

    private NoteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        queryBundle = this.getArguments();
        Brand = queryBundle.getStringArrayList("Genres");

        Price = queryBundle.getInt("Prices");

        Theme = queryBundle.getStringArrayList("Category");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_books, container, false);

        getResources().getColor(R.color.color_button_clicked);
        getResources().getColor(R.color.color_button_unclicked);


        Intent intent = getActivity().getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);


        setupRecyclerView();

        return view;
    }


    private void setupRecyclerView(){


        for (String BrandSelected : queryBundle.getStringArrayList("Genres")) {

            Query query1 = notebookRef.whereEqualTo("Genres", BrandSelected).whereLessThanOrEqualTo("Price",Price);

            ListOfOptions.add(query1);

        }


        adapter = new NoteAdapter(ListOfOptions);
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
        Intent detailsIntent = new Intent (getActivity(), BookDetails2Activity.class);
        Log.d("TESTING",String.valueOf(ListOfNotes.size()));
        Note clickedItem = ListOfNotes.get(position);


        //Carry out the outfit details onto the next fragment



        detailsIntent.putExtra(EXTRA_URL,clickedItem.getURL());
        detailsIntent.putExtra(EXTRA_GENRE,clickedItem.getGenres());
        detailsIntent.putExtra(EXTRA_NAME,clickedItem.getName());
        detailsIntent.putExtra(EXTRA_PRICE,clickedItem.getPrice());
        // detailsIntent.putExtra(EXTRA_CAT,clickedItem.getCategory());
        detailsIntent.putExtra(EXTRA_AUTHOR,clickedItem.getAuthor());
        detailsIntent.putExtra(EXTRA_BOOKURL,clickedItem.getWebsiteURL());


        startActivity(detailsIntent);





    }



}
