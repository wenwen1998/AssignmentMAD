package com.example.acer.lltgh_dogadoption;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.*;
import android.view.MenuItem;
import android.widget.TextView;

public class Home_Activity extends AppCompatActivity {
    private TextView passingTV1;
    private Fragment fragment;
    private FragmentManager fragMng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new DogList()).commit();

        BottomNavigationView navbar = (BottomNavigationView) findViewById(R.id.bottomNav);
        navbar.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;


            switch(menuItem.getItemId()){
                case R.id.navigation_home: selectedFragment = new DogList(); break;
                case R.id.navigation_search: selectedFragment = new SearchDog(); break;
                case R.id.navigation_rehome: selectedFragment = new RehomePost(); break;
                case R.id.navigation_charity: selectedFragment = new CharityFrag(); break;
                case R.id.navigation_profile: selectedFragment = new ProfileFrag(); break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, selectedFragment).commit();
            return true;
        }
    };

}
