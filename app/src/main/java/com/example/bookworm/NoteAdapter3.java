package com.example.bookworm;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class NoteAdapter3 extends FirestoreRecyclerAdapter<Note3, NoteAdapter3.NoteHolder> {

    private NoteAdapter3.OnNoteListener listener;


    public NoteAdapter3(@NonNull FirestoreRecyclerOptions<Note3> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull NoteAdapter3.NoteHolder holder, int position, @NonNull Note3 model) {
        holder.txt_view_ClubName.setText(model.getClubname());
        holder.txt_view_UserName.setText(model.getUsername());


    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public NoteAdapter3.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note3_item,
                parent, false);
        return new NoteAdapter3.NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView txt_view_UserName;
        TextView txt_view_ClubName;



        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            txt_view_ClubName = itemView.findViewById(R.id.txt_view_ClubName);
            txt_view_UserName = itemView.findViewById(R.id.txt_view_UserName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(mOnNoteListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && listener !=null){
                            listener.onNoteClick(getSnapshots().getSnapshot(position), position);
                        }
                        //mOnNoteListener.onNoteClick(position);
                        //System.out.println("PLEASE WORKKKKKKKKKKK" + position);
                   // }
                }
            });


        }


    }

    public interface OnNoteListener{
        void onNoteClick(DocumentSnapshot documentSnapshot,int position);

    }

    public void setmOnNoteListener(OnNoteListener listener){
        this.listener = listener;
    }

}