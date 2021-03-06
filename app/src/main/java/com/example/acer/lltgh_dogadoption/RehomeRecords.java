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

public class RehomeRecords extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dogRef;
    private DogListVerticalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehome_records);

        dogRef = db.collection("Dog");
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        Intent intent = getIntent();
        Query query = dogRef.whereEqualTo("email", intent.getStringExtra("EXTRA_EMAIL"))
                .orderBy("dateTime", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<DogEntity> options = new FirestoreRecyclerOptions.Builder<DogEntity>()
                .setQuery(query, DogEntity.class)
                .build();

        adapter = new DogListVerticalAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount()==0){
            Toast.makeText(RehomeRecords.this, "No Search Result.", Toast.LENGTH_SHORT).show();
        }
        adapter.setOnItemClickListener(new DogListVerticalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                DogEntity dog = documentSnapshot.toObject(DogEntity.class);
                String id = documentSnapshot.getId();
                Intent intent = new Intent(RehomeRecords.this, RehomeAdopted.class);
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
