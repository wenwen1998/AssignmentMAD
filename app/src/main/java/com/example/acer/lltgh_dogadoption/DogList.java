package com.example.acer.lltgh_dogadoption;

import android.content.Intent;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.opencensus.tags.Tag;

public class DogList extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dogRef;

    private DogListHorizontalAdapter adapterHor;
    private DogListVerticalAdapter adapterVer;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_dog_list, null);
        dogRef = db.collection("Dog");
        setUpRecyclerView();
        return view;
    }


//    protected View OnCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle SavedInstanceState) {
//        //super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_dog_list);
//
//
//        return inflater.inflate(R.layout.activity_dog_list, null);
//
//    }

    private void setUpRecyclerView(){
        Query query1 = dogRef.whereEqualTo("adopted", false).orderBy("dateTime", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<DogEntity> options1 = new FirestoreRecyclerOptions.Builder<DogEntity>()
                .setQuery(query1, DogEntity.class)
                .build();

        Query query2 = dogRef.whereEqualTo("adopted", false).orderBy("breed", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<DogEntity> options2 = new FirestoreRecyclerOptions.Builder<DogEntity>()
                .setQuery(query2, DogEntity.class)
                .build();

        adapterHor = new DogListHorizontalAdapter(options1);
        adapterVer = new DogListVerticalAdapter(options2);

        RecyclerView recyclerView1 = view.findViewById(R.id.horizontalRecyclerView);
        RecyclerView recyclerView2 = view.findViewById(R.id.verticalRecyclerView);

        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);

        recyclerView1.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView1.setAdapter(adapterHor);
        recyclerView2.setAdapter(adapterVer);

        adapterHor.setOnItemClickListener(new DogListHorizontalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                DogEntity dog = documentSnapshot.toObject(DogEntity.class);
                String id = documentSnapshot.getId();
                Intent intent = new Intent(view.getContext(), DogDetails.class);
                intent.putExtra("EXTRA_ID", id);
                startActivity(intent);
            }

        });
        adapterVer.setOnItemClickListener(new DogListVerticalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                DogEntity dog = documentSnapshot.toObject(DogEntity.class);
                String id = documentSnapshot.getId();
                Intent intent = new Intent(view.getContext(), DogDetails.class);
                intent.putExtra("EXTRA_ID", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterHor.startListening();
        adapterVer.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterHor.stopListening();
        adapterVer.stopListening();
    }

}
