package com.example.acer.lltgh_dogadoption;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;

public class RehomeAdopted extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String dogDocID;

    ImageView dogImg;
    TextView name, breed, sex, age, size, desc, reason, ownerName, contactNum, dateTime, txtVDogInfo, txtVOwnerDetails;
    String number, dogName, getOwnerName, imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehome_adopted);

        dogImg = findViewById(R.id.imageView1);
        name = findViewById(R.id.txtVName);
        breed = findViewById(R.id.txtVBreed);
        sex = findViewById(R.id.txtVSex);
        age = findViewById(R.id.txtVAge);
        size = findViewById(R.id.txtVSize);
        desc = findViewById(R.id.txtVDesc);
        reason = findViewById(R.id.txtVReason);
        ownerName = findViewById(R.id.txtVOwnerName);
        contactNum = findViewById(R.id.txtVContactNum);
        dateTime = findViewById(R.id.txtVDateTime);

        txtVDogInfo = findViewById(R.id.txtVDogInfo);
        txtVOwnerDetails = findViewById(R.id.txtVOwnerDetails);
        txtVDogInfo.setPaintFlags(txtVDogInfo.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        txtVOwnerDetails.setPaintFlags(txtVOwnerDetails.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        Intent intent = getIntent();
        dogDocID = intent.getStringExtra("EXTRA_ID");
        db.collection("Dog")
                .document(dogDocID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                DogEntity dog = documentSnapshot.toObject(DogEntity.class);

                Glide.with(RehomeAdopted.this).load(dog.getImage()).into(dogImg);
                name.setText("Name: " + dog.getName());
                breed.setText("Breed: " + dog.getBreed());
                sex.setText("Sex: " + dog.getSex());
                age.setText("Age: " + dog.getAge());
                size.setText("Size: " + dog.getSize());
                desc.setText("Description: " + dog.getDesc());
                reason.setText("Reason: " + dog.getReason());
                ownerName.setText("Owner Name: " + dog.getOwnerName());
                contactNum.setText("Contact Number: " + dog.getContactNum());
                dateTime.setText("Upload Date: " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(dog.getDateTime()).toString());

                number = dog.getContactNum();
                dogName = dog.getName();
                getOwnerName = dog.getOwnerName();
                imagePath = dog.getImage();
            }
        });

    }
}
