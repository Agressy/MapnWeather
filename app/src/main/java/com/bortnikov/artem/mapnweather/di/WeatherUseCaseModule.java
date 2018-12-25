package com.bortnikov.artem.mapnweather.di;

import com.bortnikov.artem.mapnweather.data.Endpoints;
import com.bortnikov.artem.mapnweather.data.usecases.DataUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class WeatherUseCaseModule {
    @Provides
    @Singleton
    DataUseCase feedUseCase(Endpoints endpoints) {
        return new DataUseCase(endpoints);
    }
}
