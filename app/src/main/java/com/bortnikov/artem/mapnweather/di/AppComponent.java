package com.bortnikov.artem.mapnweather.di;

import com.bortnikov.artem.mapnweather.presenter.MapWeatherPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DaggerNetModule.class, WeatherUseCaseModule.class})
public interface AppComponent {

    void injectsToMapWeatherPresenter(MapWeatherPresenter mapWeatherPresenter);
}