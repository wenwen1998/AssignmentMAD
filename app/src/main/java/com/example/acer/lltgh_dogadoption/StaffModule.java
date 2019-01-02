package com.example.acer.lltgh_dogadoption;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StaffModule extends Fragment {
    private Button btnUser, btnStaff;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle SavedInstanceState) {

        final View view = inflater.inflate(R.layout.staff_module, container, false);

        btnUser = view.findViewById(R.id.btnUser);
        btnStaff = view.findViewById(R.id.btnStaff);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new UserDetails()).commit();
            }
        });

//        btnStaff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(StaffModule.this , StaffDetails.class));
//            }
//        });

        return view;
    }
}
