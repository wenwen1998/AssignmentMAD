package com.example.acer.lltgh_dogadoption;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ResetPassword extends Activity {
    private EditText txtResetEmail;
    private Button btnResetCancel, btnResetConfirm;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private List<UserInformation> userList;
    private int i;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        txtResetEmail = findViewById(R.id.txtResetEmail);

        btnResetCancel = findViewById(R.id.btnResetCancel);
        btnResetConfirm = findViewById(R.id.btnResetConfirm);

        auth = FirebaseAuth.getInstance();
        userList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();


        btnResetConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = txtResetEmail.getText().toString().trim();
                auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(ResetPassword.this, "Email sent!", Toast.LENGTH_SHORT).show();
                            TextView textResetHint = findViewById(R.id.textResetHint);
                            txtResetEmail.setVisibility(View.VISIBLE);
                        }else
                        {
                            Toast.makeText(ResetPassword.this, "Email is wrong!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        btnResetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
