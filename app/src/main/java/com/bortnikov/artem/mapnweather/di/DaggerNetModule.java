package com.bortnikov.artem.mapnweather.di;

import com.bortnikov.artem.mapnweather.data.Endpoints;
import com.bortnikov.artem.mapnweather.data.ServiceGenerator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class DaggerNetModule {
    @Provides
    @Singleton
    Endpoints getEndpoints() {
        return new ServiceGenerator().createService(Endpoints.class);
    }
}