package com.bortnikov.artem.mapnweather.view.fragments.start;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bortnikov.artem.mapnweather.R;
import com.bortnikov.artem.mapnweather.view.fragments.mapweather.MapWeatherFragment;

import java.util.Objects;

public class StartFragment extends Fragment {

    EditText enterCityEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater
                .inflate(R.layout.fragment_start, container, false);

        enterCityEditText = root.findViewById(R.id.enter_city_edit_text);

        Button weatherButton = root.findViewById(R.id.button_find);
        weatherButton.setOnClickListener(view -> {
            FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                Fragment fragment = MapWeatherFragment
                        .newInstance(enterCityEditText.getText().toString());
                fm.beginTransaction()
                        .replace(R.id.container_fragments, fragment)
                        .addToBackStack(null)
                        .commit();
        });

        return root;
    }
}
