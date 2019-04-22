package com.example.bookworm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendsActivity extends AppCompatActivity {

    private RecyclerView myMemberList;
    private DatabaseReference FriendsRef, UsersRef;
    private FirebaseAuth mAuth;
    private String CurrentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        mAuth =FirebaseAuth.getInstance();
        CurrentUserID = mAuth.getCurrentUser().getUid();

        FriendsRef = FirebaseDatabase.getInstance().getReference().child("Friends").child(CurrentUserID);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        myMemberList = (RecyclerView) findViewById(R.id.friend_list_Recycler);
        myMemberList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        myMemberList.setLayoutManager(linearLayoutManager);

        DisplayAllMembers();
    }

    private void DisplayAllMembers()
    {

        FirebaseRecyclerOptions<MemberFriends> options=new
                FirebaseRecyclerOptions.Builder<MemberFriends>().
                setQuery(FriendsRef, MemberFriends.class).build(); //query build past the query to FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter<MemberFriends,FriendsViewHolder>
                adapter=new FirebaseRecyclerAdapter<MemberFriends, FriendsActivity.FriendsViewHolder>(options)
        {


            @Override
            protected void onBindViewHolder(@NonNull final FriendsActivity.FriendsViewHolder holder, int position,
                                            final MemberFriends model)
            {

                holder.date.setText(model.getDate());

                final String UserMember_ID = getRef(position).getKey();

                UsersRef.child(UserMember_ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists())

                        {
                            String Username = dataSnapshot.child("fullname").getValue().toString();
                            //retrieving the users fullname from (child node) the Users Database

                            holder.username.setText(Username);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener()

                {


                    @Override
                    public void onClick(View v)
                    {
                        CharSequence options [] = new CharSequence[]
                                {
                                     "View Members Profile",
                                        "Send Message"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(FriendsActivity.this);
                        builder.setTitle("Select a option");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if (which == 0)
                                {
                                   Intent profile = new Intent(FriendsActivity.this, ProfileActivity.class);
                                   profile.putExtra("Member_ID",UserMember_ID);
                                   startActivity(profile);
                                }
                                if (which == 1)
                                {
                                    Intent message = new Intent(FriendsActivity.this, MessageActivity.class);
                                    message.putExtra("Member_ID",UserMember_ID);
                                  //  message.putExtra("UserName",username);
                                    startActivity(message);
                                }
                            }
                        });
                        builder.show();

//                        Intent findOthersIntent = new Intent(FriendsActivity.this, ProfileActivity.class);
//                        findOthersIntent.putExtra("Member_ID", UserMember_ID);
//                        startActivity(findOthersIntent);
                    }
                });
            }
            @NonNull
            @Override
            public FriendsActivity.FriendsViewHolder
            onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
            {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_user_layout,viewGroup,false);

                FriendsActivity.FriendsViewHolder viewHolder=new FriendsActivity.FriendsViewHolder(view);
                return viewHolder;
            }
        };
        myMemberList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder
    {
        final TextView username, date;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
          username = itemView.findViewById(R.id.user_full_name);
            date = itemView.findViewById(R.id.user_FavBook);
        }
    }





}
