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

public class DogListVerticalAdapter extends FirestoreRecyclerAdapter<DogEntity, DogListVerticalAdapter.DogVHolder> {

    private OnItemClickListener listener;
    public DogListVerticalAdapter(@NonNull FirestoreRecyclerOptions<DogEntity> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull DogVHolder holder, int position, @NonNull DogEntity model) {
        Glide.with(holder.dogImg.getContext()).load(model.getImage()).into(holder.dogImg);
        holder.textViewName.setText(model.getName());
        holder.textViewBreed.setText(model.getBreed());
        holder.textViewSex.setText(model.getSex());
        holder.textViewAge.setText(model.getAge());
        holder.textViewSize.setText(model.getSize());
    }

    @NonNull
    @Override
    public DogVHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_list_vertical_item,
                parent, false);
        return new DogVHolder(v);
    }

    class DogVHolder extends RecyclerView.ViewHolder{
        ImageView dogImg;
        TextView textViewName, textViewBreed, textViewSex, textViewAge, textViewSize;

        public DogVHolder(@NonNull View itemView) {
            super(itemView);
            dogImg = itemView.findViewById(R.id.img_dog);
            textViewName = itemView.findViewById(R.id.textVName);
            textViewBreed = itemView.findViewById(R.id.textVBreed);
            textViewSex = itemView.findViewById(R.id.textVSex);
            textViewAge = itemView.findViewById(R.id.textVAge);
            textViewSize = itemView.findViewById(R.id.textVSize);

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
