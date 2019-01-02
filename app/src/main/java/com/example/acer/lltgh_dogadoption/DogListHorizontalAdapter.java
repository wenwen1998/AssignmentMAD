package com.example.acer.lltgh_dogadoption;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class DogListHorizontalAdapter extends FirestoreRecyclerAdapter<DogEntity, DogListHorizontalAdapter.DogHolder> {

    private OnItemClickListener listener;
    public DogListHorizontalAdapter(@NonNull FirestoreRecyclerOptions<DogEntity> options) {
        super(options);
    }

    protected void onBindViewHolder(@NonNull DogHolder holder, int position, @NonNull DogEntity model) {
        Glide.with(holder.dogImg.getContext()).load(model.getImage()).into(holder.dogImg);
        holder.textViewName.setText(model.getName());
    }

    @NonNull
    @Override
    public DogHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_list_horizontal_item,
                parent, false);
        return new DogHolder(v);
    }

    class DogHolder extends RecyclerView.ViewHolder{
        ImageView dogImg;
        TextView textViewName;

        public DogHolder(@NonNull View itemView) {
            super(itemView);
            dogImg = itemView.findViewById(R.id.hor_img_dog);
            textViewName = itemView.findViewById(R.id.textVHorName);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
