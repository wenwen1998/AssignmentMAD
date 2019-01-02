package com.example.acer.lltgh_dogadoption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends Activity {
    private EditText txtEmail, txtPassword, txtConfirmPassword;
    private EditText txtName, txtIC, txtContactNum;
    private RadioButton radioMale, radioFemale;
    private Button btnRegister, btnCancel;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ProgressDialog dialog;
    private CollectionReference dbUser;
    private UserInformation user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);
        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancel);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtName = findViewById(R.id.txtName);
        txtIC = findViewById(R.id.txtIC);
        txtContactNum = findViewById(R.id.txtContactNum);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        dialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v == btnRegister) {
                    registerUser();
                }
            }

            private void registerUser() {
                String Username = txtName.getText().toString().trim();
                String UserIC = txtIC.getText().toString().trim();
                String UserGender;
                String UserContact = txtContactNum.getText().toString().trim();
                String Useremail = txtEmail.getText().toString().trim();
                String Userpassword = txtPassword.getText().toString().trim();
                String UserconfirmPassword = txtConfirmPassword.getText().toString().trim();

                dbUser = db.collection("User");
                if (radioMale.isChecked()) {
                    UserGender = radioMale.getText().toString().trim();
                } else {
                    UserGender = radioFemale.getText().toString().trim();
                }

                if (isValidEmpty() == true) {
                    user = new UserInformation(
                            Username,
                            UserIC,
                            UserGender,
                            UserContact,
                            Useremail
                    );


                    if (UserconfirmPassword.equals(Userpassword)) {


                        progressBar.setVisibility(View.VISIBLE);
                        auth.createUserWithEmailAndPassword(Useremail, Userpassword).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();

                                    dbUser.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(getApplicationContext(), "Detail Registered!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.setMessage("Register User Detail Failed >_<....");
                                            dialog.show();
                                            Toast.makeText(getApplicationContext(), "Detail Register Failed, Please Try Again!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });

                                    progressBar.setVisibility(View.GONE);
                                    auth = FirebaseAuth.getInstance();

                                    if (auth.getCurrentUser() != null) {
                                        auth.signOut();
                                        finish();
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "Email might already registered or incorrect format!!!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }

                        });


                    } else {
                        Toast.makeText(getApplicationContext(), "Password and Confirmation Password Must Be Same!", Toast.LENGTH_SHORT).show();
                        txtConfirmPassword.setError("Confirmation Password must match with password");

                    }
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private boolean isValidEmpty() {
        boolean isValid = false;
        String Username = txtName.getText().toString().trim();
        String UserIC = txtIC.getText().toString().trim();
        String UserContact = txtContactNum.getText().toString().trim();
        String Useremail = txtEmail.getText().toString().trim();
        String Userpassword = txtPassword.getText().toString().trim();
        String UserconfirmPassword = txtConfirmPassword.getText().toString().trim();


        if (TextUtils.isEmpty(Useremail)) {
            txtEmail.setError("Enter email address");
            isValid = false;
        } else if (TextUtils.isEmpty(Userpassword)) {
            txtPassword.setError("Enter Password");
            isValid = false;
        } else if (TextUtils.isEmpty(UserconfirmPassword)) {
            txtConfirmPassword.setError("Enter Confirmation Password");
            isValid = false;
        } else if (TextUtils.isEmpty(Username)) {
            txtName.setError("Enter Username");
            isValid = false;
        } else if (TextUtils.isEmpty(UserIC)) {
            txtIC.setError("Enter IC Number");
            isValid = false;
        } else if (TextUtils.isEmpty(UserContact)) {
            txtContactNum.setError("Enter Contact Number");
            isValid = false;
        } else {
            isValid = true;
        }


        return isValid;
    }

}

