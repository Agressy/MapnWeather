package com.bortnikov.artem.mapnweather.view;

import android.support.v4.app.Fragment;

import com.bortnikov.artem.mapnweather.view.fragments.start.StartFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createStartFragment() {
        return new StartFragment();
    }

}
