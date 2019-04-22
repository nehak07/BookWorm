package com.example.bookworm;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class messagesAdapter extends RecyclerView.Adapter<messagesAdapter.MessageViewHolder>
{
    private List<messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabaseRef;

    public messagesAdapter (List<messages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView SenderMessageText, ReceiverMessageText;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            SenderMessageText = (TextView) itemView.findViewById(R.id.sender_message_text);
            ReceiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {

        View V = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_layout_user, parent, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position)
    {
        String messageSenderID = mAuth.getCurrentUser().getUid();
        messages messages = userMessagesList.get(position);

        String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();

        usersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);

        if (fromMessageType.equals("text"))
        {
            holder.ReceiverMessageText.setVisibility(View.INVISIBLE);

            if(fromUserID.equals(messageSenderID))
            {
                holder.SenderMessageText.setBackgroundResource(R.drawable.sender_message_text_background);
                holder.SenderMessageText.setTextColor(Color.WHITE);
                holder.SenderMessageText.setGravity(Gravity.LEFT);
                holder.SenderMessageText.setText(messages.getMessage());
            }
            else
                {
                    holder.SenderMessageText.setVisibility(View.INVISIBLE);

                    holder.ReceiverMessageText.setVisibility(View.VISIBLE);
                    holder.ReceiverMessageText.setBackgroundResource(R.drawable.receiver_massage_text);
                    holder.ReceiverMessageText.setTextColor(Color.WHITE);
                    holder.ReceiverMessageText.setGravity(Gravity.LEFT);
                    holder.ReceiverMessageText.setText(messages.getMessage());
                }
        }

    }

    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }
}
