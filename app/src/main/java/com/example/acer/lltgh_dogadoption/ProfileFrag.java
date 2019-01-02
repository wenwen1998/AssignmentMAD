package com.example.acer.lltgh_dogadoption;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getSystemService;

public class ProfileFrag extends Fragment {
    private Button btnUsername, btnIC, btnGender, btnContact, btnLogOut, btnChangePassword, btnRehomeRecords;
    private Button btnConfirmUsername, btnConfirmGender, btnConfirmIC, btnConfirmContact, btnConfirmPassword;
    private CardView cvEditUsername, cvEditGender, cvEditIC, cvEditContact, cvEditPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private List<UserInformation> userList;
    private UserInformation userInformation;
    private TextView viewIC, viewUsername, viewContact, viewGender;
    private RadioButton changeRadioMale, changeRadioFemale;
    private EditText txtChangeUsername, txtChangeIC, txtChangeCNewPassword, txtChangeNewPassword, txtChangePassword, txtChangeContact;
    private RadioGroup radio_groupGender;
    private FirebaseFirestore db;
    private int i;
    private int position;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle SavedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_frag, container, false);
        auth = FirebaseAuth.getInstance();
        //card view
        cvEditUsername = view.findViewById(R.id.cvEditUsername);
        cvEditGender = view.findViewById(R.id.cvEditGender);
        cvEditIC = view.findViewById(R.id.cvEditIC);
        cvEditContact = view.findViewById(R.id.cvEditContact);
        cvEditPassword = view.findViewById(R.id.cvEditPassword);

        //user detail view
        viewIC = view.findViewById(R.id.viewIC);
        viewUsername = view.findViewById(R.id.viewUsername);
        viewContact = view.findViewById(R.id.viewContact);
        viewGender = view.findViewById(R.id.viewGender);

        //button confirm
        btnConfirmUsername = view.findViewById(R.id.btnConfirmUsername);
        btnConfirmGender = view.findViewById(R.id.btnConfirmGender);
        btnConfirmIC = view.findViewById(R.id.btnConfirmIC);
        btnConfirmContact = view.findViewById(R.id.btnConfirmContact);
        btnConfirmPassword = view.findViewById(R.id.btnConfirmPassword);

        //Edit Detail
        txtChangeUsername = view.findViewById(R.id.txtChangeUsername);
        txtChangeContact = view.findViewById(R.id.txtChangeContact);
        txtChangeIC = view.findViewById(R.id.txtChangeIC);
        txtChangeCNewPassword = view.findViewById(R.id.txtChangeCNewPassword);
        txtChangeNewPassword = view.findViewById(R.id.txtChangeNewPassword);
        txtChangePassword = view.findViewById(R.id.txtChangePassword);
        changeRadioMale = view.findViewById(R.id.changeRadioMale);
        changeRadioFemale = view.findViewById(R.id.changeRadioFemale);

        radio_groupGender = view.findViewById(R.id.radio_groupGender);

        //button change detail
        btnUsername = view.findViewById(R.id.btnUsername);
        btnIC = view.findViewById(R.id.btnIC);
        btnGender = view.findViewById(R.id.btnGender);
        btnContact = view.findViewById(R.id.btnContact);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnRehomeRecords = view.findViewById(R.id.btnRehomeRecords);
        //progress
        dialog = new ProgressDialog(getActivity());
        progressBar = view.findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }


        userList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        db.collection("User").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {
                                UserInformation p = d.toObject(UserInformation.class);
                                p.setId(d.getId());
                                userList.add(p);
                                if (userList.get(i).getUserEmail().equals(auth.getCurrentUser().getEmail())) {
                                    position = i;
                                }
                                i++;

                            }
                            viewIC.setText(userList.get(position).getUserIC());
                            viewContact.setText(userList.get(position).getUserContact());
                            viewGender.setText(userList.get(position).getUserGender());
                            viewUsername.setText(String.valueOf(userList.get(position).getUserName()));
                            btnRehomeRecords.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(view.getContext(), RehomeRecords.class);
                                    intent.putExtra("EXTRA_EMAIL", userList.get(position).getUserEmail());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });

        //CHANGE SECTION
        btnUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvEditUsername.getVisibility() == View.VISIBLE) {
                    cvEditUsername.setVisibility(View.GONE);

                } else {
                    cvEditUsername.setVisibility(View.VISIBLE);
                }
                cvEditGender.setVisibility(View.GONE);
                cvEditIC.setVisibility(View.GONE);
                cvEditContact.setVisibility(View.GONE);
                cvEditPassword.setVisibility(View.GONE);


            }
        });
        btnIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvEditIC.getVisibility() == View.VISIBLE) {
                    cvEditIC.setVisibility(View.GONE);

                } else {
                    cvEditIC.setVisibility(View.VISIBLE);
                }
                cvEditUsername.setVisibility(View.GONE);
                cvEditGender.setVisibility(View.GONE);
                cvEditContact.setVisibility(View.GONE);
                cvEditPassword.setVisibility(View.GONE);

            }
        });
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvEditContact.getVisibility() == View.VISIBLE) {
                    cvEditContact.setVisibility(View.GONE);

                } else {
                    cvEditContact.setVisibility(View.VISIBLE);
                }
                cvEditUsername.setVisibility(View.GONE);
                cvEditGender.setVisibility(View.GONE);
                cvEditIC.setVisibility(View.GONE);
                cvEditPassword.setVisibility(View.GONE);

            }
        });
        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvEditGender.getVisibility() == View.VISIBLE) {
                    cvEditGender.setVisibility(View.GONE);

                } else {
                    cvEditGender.setVisibility(View.VISIBLE);
                }
                cvEditUsername.setVisibility(View.GONE);
                cvEditIC.setVisibility(View.GONE);
                cvEditContact.setVisibility(View.GONE);
                cvEditPassword.setVisibility(View.GONE);


            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvEditPassword.getVisibility() == View.VISIBLE) {
                    cvEditPassword.setVisibility(View.GONE);

                } else {
                    cvEditPassword.setVisibility(View.VISIBLE);
                }
                cvEditGender.setVisibility(View.GONE);
                cvEditUsername.setVisibility(View.GONE);
                cvEditIC.setVisibility(View.GONE);
                cvEditContact.setVisibility(View.GONE);

            }
        });


        //CONFIRM SECTION

        btnConfirmUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvEditUsername.setVisibility(View.GONE);
                viewUsername.setText(txtChangeUsername.getText().toString().trim());
                userList.get(position).setUserName(viewUsername.getText().toString().trim());
                dialog.setMessage("Changing Username....");
                dialog.show();
                UpdateProduct();
            }
        });

        btnConfirmGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvEditGender.setVisibility(View.GONE);
                if (changeRadioMale.isChecked()) {
                    viewGender.setText(changeRadioMale.getText().toString().trim());
                } else {
                    viewGender.setText(changeRadioFemale.getText().toString().trim());

                }
                userList.get(position).setUserGender(viewGender.getText().toString().trim());
                dialog.setMessage("Changing Gender....");
                dialog.show();
                UpdateProduct();

            }
        });
        btnConfirmIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvEditIC.setVisibility(View.GONE);
                viewIC.setText(txtChangeIC.getText().toString().trim());
                userList.get(position).setUserIC(viewIC.getText().toString().trim());
                dialog.setMessage("Changing IC Number....");
                dialog.show();
                UpdateProduct();

            }
        });

        btnConfirmContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvEditContact.setVisibility(View.GONE);
                viewContact.setText(txtChangeContact.getText().toString().trim());
                userList.get(position).setUserContact(viewContact.getText().toString().trim());
                dialog.setMessage("Changing Contact Number....");
                dialog.show();
                UpdateProduct();

            }
        });

        btnConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.signInWithEmailAndPassword(userList.get(position).getUserEmail(), txtChangePassword.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (txtChangePassword.getText().toString().length() < 6) {
                                        txtChangePassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    FirebaseUser user = auth.getCurrentUser();
                                    if (user != null) {
                                        if (txtChangeNewPassword.getText().toString().equals(txtChangeCNewPassword.getText().toString())) {

                                            dialog.setMessage("Changing Password....");
                                            dialog.show();
                                            user.updatePassword(txtChangeNewPassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                dialog.dismiss();
                                                                Toast.makeText(getActivity(), "Update Complete!", Toast.LENGTH_SHORT).show();
                                                                cvEditPassword.setVisibility(getView().GONE);

                                                            } else {
                                                                Toast.makeText(getActivity(), "Update Failed!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                        } else {
                                            Toast.makeText(getActivity(), "New Password must same with Confirm password!", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }
                            }
                        });


            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("SignZing out....");
                dialog.show();
                if (auth.getCurrentUser() != null)
                    auth.signOut();
                Intent intent = new Intent(getActivity(), Login_Activity.class);
                startActivity(intent);
                dialog.dismiss();
                getActivity().finish();
            }
        });

        return view;

    }


    private void UpdateProduct() {
        String UserName = viewUsername.getText().toString().trim();
        String UserIC = viewIC.getText().toString().trim();
        String UserGender = viewGender.getText().toString().trim();
        String UserContact = viewContact.getText().toString().trim();
        String UserEmail = userList.get(position).getUserEmail();


        UserInformation userInformation = new UserInformation(
                UserName,
                UserIC,
                UserGender,
                UserContact,
                UserEmail
        );


        db.collection("User").document(userList.get(position).getId())
                .set(userInformation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Update Complete!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });
    }

}
