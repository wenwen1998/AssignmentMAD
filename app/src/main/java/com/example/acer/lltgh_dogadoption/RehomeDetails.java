package com.example.acer.lltgh_dogadoption;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RehomeDetails extends Fragment {

    private Button btnUpload;
    private ImageView imgViewUpload;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST= 1;

    FirebaseStorage storage;
    StorageReference storageReference;

    public RehomeDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rehome_details, container, false);

        Spinner spinBreed=view.findViewById(R.id.spinnerBreed);
        String[] breedItem=new String[]{"Canaan Dog","Chiwawa","Corgi","Dachshund","Pomeranian","Poodle"};
        ArrayAdapter<String> breedAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,breedItem);
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBreed.setAdapter(breedAdapter);

        Spinner spinGender=view.findViewById(R.id.spinnerGender);
        String[] genderItem=new String[]{"Male","Female"};
        ArrayAdapter<String> genderAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,genderItem);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(genderAdapter);

        Spinner spinAge=view.findViewById(R.id.spinnerAge);
        String[] ageItem=new String[]{"Baby","Young","Adult","Senior"};
        ArrayAdapter<String> ageAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,ageItem);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAge.setAdapter(ageAdapter);

        Spinner spinSize=view.findViewById(R.id.spinnerSize);
        String[] sizeItem=new String[]{"S","M","L","XL"};
        ArrayAdapter<String> sizeAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,sizeItem);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSize.setAdapter(sizeAdapter);


        btnUpload=(Button)view.findViewById(R.id.btnUpload);
        imgViewUpload=(ImageView)view.findViewById(R.id.imgViewUpload);

        storage=FirebaseStorage.getInstance();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                chooseImage();
                /*uploadImage();*/
            }
        });

        return view;
    }

    private void chooseImage() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==Activity.RESULT_OK){
            filePath=data.getData();
            try{
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);
                imgViewUpload.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    /*public void uploadImage() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }*/

}
