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

public class NoteAdapter2 extends FirestoreRecyclerAdapter<Note2, NoteAdapter2.NoteHolder> {
    private NoteAdapter2.OnNoteListener listener;

    public NoteAdapter2(@NonNull FirestoreRecyclerOptions<Note2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteAdapter2.NoteHolder holder, int position, @NonNull Note2 model) {
        holder.txt_view_Name.setText(model.getName());
        holder.txt_view_Brand.setText(model.getGenres());
        holder.txt_view_Price.setText(String.valueOf(model.getPrice()));
        Picasso.get().load(model.getURL()).into(holder.img_Outfit);

    }


    @NonNull
    @Override
    public NoteAdapter2.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note2_item,
                parent, false);
        return new NoteAdapter2.NoteHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class NoteHolder extends RecyclerView.ViewHolder{
       // TextView txt_view_Theme;
        TextView txt_view_Name;
        TextView txt_view_Price;
        TextView txt_view_Brand;
        ImageView img_Outfit;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            txt_view_Name = itemView.findViewById(R.id.txt_view_Name);
            txt_view_Brand = itemView.findViewById(R.id.txt_view_Brand);
            txt_view_Price = itemView.findViewById(R.id.txt_view_Price);
            //txt_view_Theme =itemView.findViewById(R.id.txt_view_Theme);
            img_Outfit = itemView.findViewById(R.id.img_Outfit);

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
        void onNoteClick(DocumentSnapshot documentSnapshot, int position);

    }


    public void setmOnNoteListener(NoteAdapter2.OnNoteListener listener){
        this.listener = listener;
    }

}
