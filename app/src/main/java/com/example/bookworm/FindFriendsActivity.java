package com.example.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

public class FindFriendsActivity extends AppCompatActivity {

    private ImageButton SearchButton;
    private EditText SearchInput;

    private RecyclerView searchResultsList;

    private DatabaseReference allUserDB;

   // private FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder> firebaseRecyclerAdapter;



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
                final String PostKey = getRef(position).getKey();

                holder.username.setText(model.getFullname());
                holder.book.setText(model.getBook());


                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent findOthersIntent = new Intent(FindFriendsActivity.this,
                                FindFriendsActivity.class);
                        findOthersIntent.putExtra("PostKey", PostKey);
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



//    private void SearchFriends(String searchboxinput)
//    {
//        Toast.makeText(this, "Searching..", Toast.LENGTH_SHORT).show();
//
//        Query searchQuery = allUserDB.orderByChild("fullname")
//                .startAt(searchboxinput).endAt(searchboxinput + "\uf8ff");
//
//
//        FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder> firebaseRecyclerAdapter
//                = new FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder>
//                (
//                        FindFriends.class,
//                        R.layout.all_user_layout,
//                        FindFriendsViewHolder.class,
//                        searchQuery
//                )
//        {
//
//            @NonNull
//            @Override
//            public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                return null;
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull FindFriendsViewHolder holder, final int position, @NonNull FindFriends model) {
//
//                holder.setFullname(model.getFullname());
//                holder.setBook(model.getBook());
//
//                holder.mView.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        String Memeber_ID = getRef(position).getKey();
//
//                        Intent profile = new Intent(FindFriendsActivity.this, ProfileActivity.class);
//                        profile.putExtra("Member_ID", Memeber_ID);
//                        startActivity(profile);
//                    }
//                });
//
//            }
//
////            @Override
////            protected void populateViewHolder(FindFriendsViewHolder holder, FindFriends model, final int position)
////            {
////                holder.setFullname(model.getFullname());
////                holder.setBook(model.getBook());
////
////                holder.mView.setOnClickListener(new View.OnClickListener()
////                {
////                    @Override
////                    public void onClick(View v)
////                    {
////                       String Memeber_ID = getRef(position).getKey();
////
////                        Intent profile = new Intent(FindFriendsActivity.this, ProfileActivity.class);
////                        profile.putExtra("Member_ID", Memeber_ID);
////                        startActivity(profile);
////                    }
////                });
////
////            }
//
//        };
//        searchResultsList.setAdapter(firebaseRecyclerAdapter);
//    }


//    public static class FindFriendsViewHolder extends RecyclerView.ViewHolder
//    {
//        View mView;
//
//        public FindFriendsViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//
//        public void setFullname (String fullname)
//        {
//            TextView name = (TextView) mView.findViewById(R.id.user_full_name);
//            name.setText(fullname);
//
//        }
//
//        public void setBook(String book){
//
//            TextView favBook = (TextView) mView.findViewById(R.id.user_FavBook);
//            favBook.setText(book);
//        }
//
//    }


}


