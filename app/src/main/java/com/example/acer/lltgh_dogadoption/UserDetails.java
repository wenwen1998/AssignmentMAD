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


public class UserDetails extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference UserRef;

    private UserDetailsAdapter adapterUser;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.user_list, null);
        UserRef = db.collection("User");
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView(){

        Query query1 = UserRef.whereEqualTo("userName",Query.Direction.ASCENDING).orderBy("userIC", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<UserInformation> options1 = new FirestoreRecyclerOptions.Builder<UserInformation>()
                .setQuery(query1, UserInformation.class)
                .build();


        adapterUser = new UserDetailsAdapter(options1);


        RecyclerView recyclerView2 = view.findViewById(R.id.UserRecyclerView);


        recyclerView2.setHasFixedSize(true);

        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView2.setAdapter(adapterUser);


        adapterUser.setOnItemClickListener(new UserDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                UserInformation User = documentSnapshot.toObject(UserInformation.class);
                String id = documentSnapshot.getId();
                Intent intent = new Intent(view.getContext(), UserDetails.class);
                intent.putExtra("EXTRA_ID", id);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        adapterUser.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapterUser.stopListening();

    }
}
