package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class FindFriendsActivity extends AppCompatActivity {

    private ImageButton SearchButton;
    private EditText SearchInput;

    private RecyclerView searchResultsList;

    private DatabaseReference allUserDB;
    private Toolbar mToolbar;
    private String NAME;


    // private FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder> firebaseRecyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        NAME = intent.getStringExtra(EXTRA_NAME);

        mToolbar = findViewById(R.id.FindFriends_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Friends");


        allUserDB = FirebaseDatabase.getInstance().getReference().child("Users");

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
        Intent mainintent = new Intent(FindFriendsActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }


    private void SearchFriends(String searchBoxInput)
    {
        FirebaseRecyclerOptions<FindFriends> options=new
                FirebaseRecyclerOptions.Builder<FindFriends>().
                setQuery(allUserDB, FindFriends.class).build(); //query build past the query to FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter<FindFriends, FindFriendViewHolder>
                adapter=new FirebaseRecyclerAdapter<FindFriends, FindFriendViewHolder>(options)

        {
            @Override
            protected void onBindViewHolder(@NonNull FindFriendsActivity.FindFriendViewHolder holder, int position,
                                                    FindFriends model)
            {
                final String Member_ID = getRef(position).getKey();

                holder.username.setText(model.getFullname());
                holder.book.setText(model.getBook());


                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        Intent findOthersIntent = new Intent(FindFriendsActivity.this, ProfileActivity.class);
                        findOthersIntent.putExtra("Member_ID", Member_ID);
                        startActivity(findOthersIntent);
                    }
                });
            }
            @NonNull
            @Override
            public FindFriendViewHolder
            onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
            {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_user_layout,viewGroup,false);

                FindFriendsActivity.FindFriendViewHolder viewHolder=new FindFriendsActivity.FindFriendViewHolder(view);
                return viewHolder;
            }
        };
        searchResultsList.setAdapter(adapter);
        adapter.startListening();
    }

    public class FindFriendViewHolder extends RecyclerView.ViewHolder
    {
        TextView username, book;

        public FindFriendViewHolder(@NonNull View itemView)
        {
            super(itemView);
            username = itemView.findViewById(R.id.user_full_name);
            book = itemView.findViewById(R.id.user_FavBook);


        }
    }



}


