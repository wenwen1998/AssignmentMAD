package com.example.acer.lltgh_dogadoption;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class RehomePost extends Fragment {

    public RehomePost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rehome_post, container, false);

        Button btnNext = (Button) view.findViewById(R.id.btn_next);
        addListenerOnButton(view);
        addToFirebase();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapFragment();
            }
        });
        return view;
    }

    public void addListenerOnButton(View view){
        final RadioGroup grpPost=(RadioGroup)view.findViewById(R.id.radgrpPost);
        Button btnNext = (Button) view.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=grpPost.getCheckedRadioButtonId();

                RadioButton radPost=(RadioButton) getView().findViewById(selectedId);
                radPost.getText().toString();
            }
        });
    }

    public void addToFirebase(){
        FirebaseDatabase FD=FirebaseDatabase.getInstance();

    }

    public void swapFragment(){
        RehomeDetails rehomeDetails = new RehomeDetails();
        FragmentTransaction FT2=getActivity().getSupportFragmentManager().beginTransaction();
        FT2.replace(R.id.container, rehomeDetails);
        FT2.addToBackStack(null);
        FT2.commit();
    }

}
