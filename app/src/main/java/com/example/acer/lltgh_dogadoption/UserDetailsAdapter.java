package com.example.acer.lltgh_dogadoption;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserDetailsAdapter extends FirestoreRecyclerAdapter<UserInformation, UserDetailsAdapter.UserVHolder> {

    private UserDetailsAdapter.OnItemClickListener listener;
    public UserDetailsAdapter(@NonNull FirestoreRecyclerOptions<UserInformation> options1) {
        super(options1);

    }

    @Override
    protected void onBindViewHolder(@NonNull UserDetailsAdapter.UserVHolder holder, int position, @NonNull UserInformation model) {

        holder.textUserName.setText(model.getUserName());
        holder.textUserIC.setText(model.getUserIC());

    }

    @NonNull
    @Override
    public UserDetailsAdapter.UserVHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details,
                parent, false);
        return new UserDetailsAdapter.UserVHolder(v);
    }


    class UserVHolder extends RecyclerView.ViewHolder {
        TextView textUserName,textUserIC;

        public UserVHolder(@NonNull View itemView) {
            super(itemView);

            textUserName = itemView.findViewById(R.id.textUserName);
            textUserIC = itemView.findViewById(R.id.textUserIC);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(UserDetailsAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

}

