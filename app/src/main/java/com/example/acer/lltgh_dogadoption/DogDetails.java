package com.example.acer.lltgh_dogadoption;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

public class DogDetails extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String dogDocID;
    private static final int REQUEST_CALL = 1;

    ImageView dogImg;
    TextView name, breed, sex, age, size, desc, reason, ownerName, contactNum, dateTime, txtVDogInfo, txtVOwnerDetails;
    ImageButton imgBtnCall, imgBtnSms, imgBtnShare;
    String number, dogName, getOwnerName, imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_details);
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

        imgBtnCall = findViewById(R.id.imgBtnCall);
        imgBtnSms = findViewById(R.id.imgBtnSms);
        imgBtnShare = findViewById(R.id.imgBtnShare);

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

                Glide.with(DogDetails.this).load(dog.getImage()).into(dogImg);
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

        imgBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }

        });

        imgBtnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSmsApp();
            }
        });

        imgBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                sharingIntent.setType("image/*");
//                sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
//                startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "--- Dog Adoption ---\n" + dogName +
                        " is waiting for you to ADOPT!\nWant to get more info about " + dogName +
                        "?\nGet info from Dog Adoption App!\nDownload Link: https://play.google.com/store/apps/details?id=com.dog.adoption.app";
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));
            }
        });

    }

    private void makePhoneCall() {
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(DogDetails.this,
                    android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(DogDetails.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(DogDetails.this,"Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(DogDetails.this,"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchSmsApp() {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", number);
        smsIntent.putExtra("sms_body","Hi " + getOwnerName + ", is your dog " + dogName + " still available?");
        startActivity(smsIntent);
    }

}
