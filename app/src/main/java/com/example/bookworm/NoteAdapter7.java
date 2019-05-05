package com.example.bookworm;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.ChangeEventListener;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.net.PortUnreachableException;
import java.security.PublicKey;
import java.util.ArrayList;


public class NoteAdapter7 extends RecyclerView.Adapter<NoteAdapter7.NoteHolder> {

    private ArrayList<Note7> ListOfNotes = new ArrayList<>();
    private NoteAdapter7.OnNoteListener mOnNoteListener;

    public interface OnNoteListener{
        void onNoteClick(int position);

    }
    //Coding in Flow, 2018 [ONLINE] Available at: https://www.youtube.com/watch?v=RFFu3dP5JDk&list=PLrnPJCHvNZuAXdWxOzsN5rgG2M4uJ8bH1&index=2 [Accessed on the 15th March 2019]


    public void setOnNoteListener (OnNoteListener listener){

        mOnNoteListener = listener;
    }

    public  NoteAdapter7(@NonNull ArrayList<Query> options) {
        for (Query option : options) {
            option.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ListOfNotes.addAll(task.getResult().toObjects(Note7.class));
                        notifyDataSetChanged();
                    } else {
                    }
                }
            });
        }
    }

    //Stores the data into a array list, so when a user clicks a book the position is picked ups
    public  ArrayList<Note7> NoteView(@NonNull ArrayList<Query> options) {
        for (Query option : options) {
            option.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        notifyDataSetChanged();
                    } else {
                    }
                }
            });
        }
        return ListOfNotes;
    }


    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.txt_view_ClubName.setText(ListOfNotes.get(position).getClubname());
    }

    @Override
    public int getItemCount() {
        return ListOfNotes.size();
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note7_item,
                parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView txt_view_ClubName;
        TextView txt_view_UserName;
        TextView txt_view_ClubDesc;


        public NoteHolder(@NonNull View itemView ) {
            super(itemView);
            txt_view_ClubName = itemView.findViewById(R.id.txt_view_ClubName);
            txt_view_UserName = itemView.findViewById(R.id.txt_view_UserName);
            txt_view_ClubDesc = itemView.findViewById(R.id.txt_view_ClubDesc);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnNoteListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION);
                        mOnNoteListener.onNoteClick(position);
                    }
                }
            });


        }

    }


}
