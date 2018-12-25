package com.bortnikov.artem.mapnweather;

import android.app.Application;

import com.bortnikov.artem.mapnweather.di.AppComponent;
import com.bortnikov.artem.mapnweather.di.DaggerAppComponent;


public class MainApp extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder().build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}