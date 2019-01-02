package com.example.acer.lltgh_dogadoption;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchResults extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dogRef;
    private DogListVerticalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        dogRef = db.collection("Dog");
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        Intent intent = getIntent();
        Query query = dogRef.whereEqualTo("breed", intent.getStringExtra("EXTRA_BREED"))
                .whereEqualTo("sex", intent.getStringExtra("EXTRA_SEX"))
                .whereEqualTo("age", intent.getStringExtra("EXTRA_AGE"))
                .whereEqualTo("size", intent.getStringExtra("EXTRA_SIZE"))
                .orderBy("breed", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<DogEntity> options = new FirestoreRecyclerOptions.Builder<DogEntity>()
                .setQuery(query, DogEntity.class)
                .build();

        adapter = new DogListVerticalAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount()==0){
            Toast.makeText(SearchResults.this, "No Search Result.", Toast.LENGTH_SHORT).show();
        }
        adapter.setOnItemClickListener(new DogListVerticalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                DogEntity dog = documentSnapshot.toObject(DogEntity.class);
                String id = documentSnapshot.getId();
                Intent intent = new Intent(SearchResults.this, DogDetails.class);
                intent.putExtra("EXTRA_ID", id);
                startActivity(intent);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
