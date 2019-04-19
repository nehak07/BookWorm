package com.example.bookworm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FindFriendsActivity extends AppCompatActivity {

    private ImageButton SearchButton;
    private EditText SearchInput;

    private RecyclerView searchResultsList;

    private DatabaseReference allUserDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

       allUserDB = FirebaseDatabase.getInstance().getReference().child("Users");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        searchResultsList = (RecyclerView) findViewById(R.id.friend_results);
        searchResultsList.setHasFixedSize(true);
        searchResultsList.setLayoutManager(new LinearLayoutManager(this));

        SearchButton = (ImageButton) findViewById(R.id.Friend_search);
        SearchInput = (EditText) findViewById(R.id.search_box_input);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String searchboxinput = SearchInput.getText().toString();

                SearchFriends(searchboxinput);


            }
        });
    }

    private void SearchFriends(String searchboxinput)
    {
        Toast.makeText(this, "Searching..", Toast.LENGTH_SHORT).show();

        Query searchQuery = allUserDB.orderByChild("fullname")
                .startAt(searchboxinput).endAt(searchboxinput + "\uf8ff");


        FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder>
                (
                        FindFriends.class,
                        R.layout.all_user_layout,
                        FindFriendsViewHolder.class,
                        searchQuery

                )

        {
            @NonNull
            @Override
            public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull FindFriendsViewHolder holder, int position, @NonNull FindFriends model) {
                holder.setFullname(model.getFullname());
                holder.setBook(model.getBook());

            }


        };
        searchResultsList.setAdapter(firebaseRecyclerAdapter);


    }



    public static class FindFriendsViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public FindFriendsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            mView = mView;
        }

        public void setFullname (String fullname)
        {
            TextView name = (TextView) mView.findViewById(R.id.user_full_name);
            name.setText(fullname);

        }

        public void setBook(String book){

            TextView favBook = (TextView) mView.findViewById(R.id.user_FavBook);
            favBook.setText(book);
        }

    }







}


