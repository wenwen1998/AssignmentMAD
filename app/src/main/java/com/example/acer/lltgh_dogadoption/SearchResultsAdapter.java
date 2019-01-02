package com.example.acer.lltgh_dogadoption;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsHolder> {

    public static class SearchResultsHolder extends RecyclerView.ViewHolder{
        public ImageView dogImg;
        public TextView textViewName, textViewBreed, textViewSex, textViewAge, textViewSize;


        public SearchResultsHolder(@NonNull View itemView) {
            super(itemView);
            dogImg = itemView.findViewById(R.id.img_dog);
            textViewName = itemView.findViewById(R.id.textVName);
            textViewBreed = itemView.findViewById(R.id.textVBreed);
            textViewSex = itemView.findViewById(R.id.textVSex);
            textViewAge = itemView.findViewById(R.id.textVAge);
            textViewSize = itemView.findViewById(R.id.textVSize);
        }
    }



    @NonNull
    @Override
    public SearchResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_list_vertical_item,
                parent, false);
        return new SearchResultsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsHolder searchResultsHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
