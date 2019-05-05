package com.example.bookworm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.bookworm.AllClubsActivity.EXTRA_CLUBNAME;
import static com.example.bookworm.BlankActivity.EXTRA_NAME;

public class FriendsActivity extends AppCompatActivity {

    private RecyclerView myMemberList;
    private DatabaseReference FriendsRef, UsersRef;
    private FirebaseAuth mAuth;
    private String CurrentUserID;
    private Toolbar mToolbar;
    private String NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        NAME = intent.getStringExtra(EXTRA_NAME);

        mToolbar = (Toolbar) findViewById(R.id.Friends_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Friends");

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
                setQuery(FriendsRef, MemberFriends.class).build();
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
                        }else {
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
                                    startActivity(message);
                                }
                            }
                        });
                        builder.show();

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
        Intent mainintent = new Intent(FriendsActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }


}
