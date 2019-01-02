package com.example.acer.lltgh_dogadoption;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CharityFrag extends Fragment {

    private FragmentTransaction ft;
    private FragmentManager fm;
    private Fragment frag;
    private Button btnTesing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle SavedInstanceState) {
        if (container == null)
            return inflater.inflate(R.layout.charity_frag, container, false);

        View view = inflater.inflate(R.layout.charity_frag, container, false);
        Button gotodonate = view.findViewById(R.id.btnDonate);
        btnTesing = view.findViewById(R.id.btnTesting);
        gotodonate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                getFragmentManager().beginTransaction().replace(R.id.main_container, new DonationFrag()).commit();
            }
        });
//        Button gotovolunteer = view.findViewById(R.id.btnVolunteerAs);
//        gotovolunteer.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick (View v){
//                getFragmentManager().beginTransaction().replace(R.id.main_container, new VolunteerFrag()).commit();
//            }
//        });

        btnTesing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new StaffModule()).commit();
            }
        });

        return view;
    }
}
