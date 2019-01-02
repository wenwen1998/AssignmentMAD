package com.example.acer.lltgh_dogadoption;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SearchDog extends Fragment {

    Button btnSearch;
    String breed, sex, age, size;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_search_dog, null);

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_dog);

        btnSearch = view.findViewById(R.id.btnSearchDog);

        final Spinner spinner1 = view.findViewById(R.id.spinnerBreed);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.Breed, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        final Spinner spinner2 = view.findViewById(R.id.spinnerSex);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(view.getContext(), R.array.Sex, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        final Spinner spinner3 = view.findViewById(R.id.spinnerAge);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(view.getContext(), R.array.Age, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        final Spinner spinner4 = view.findViewById(R.id.spinnerSize);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(view.getContext(), R.array.Size, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breed = spinner1.getSelectedItem().toString();
                sex = spinner2.getSelectedItem().toString();
                age = spinner3.getSelectedItem().toString();
                size = spinner4.getSelectedItem().toString();
                Intent intent = new Intent(view.getContext(), SearchResults.class);
                intent.putExtra("EXTRA_BREED", breed);
                intent.putExtra("EXTRA_SEX", sex);
                intent.putExtra("EXTRA_AGE", age);
                intent.putExtra("EXTRA_SIZE", size);
                startActivity(intent);
            }
        });

        return view;
    }

}
