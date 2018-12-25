package com.bortnikov.artem.mapnweather.data;


import com.bortnikov.artem.mapnweather.data.model.retrofit.Example;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Endpoints {

    @GET("data/2.5/forecast")
    Flowable<Example> getSearchResponse(@Query("q") String city,
                                        @Query("mode") String mode,
                                        @Query("appid") String api_key,
                                        @Query("units") String units);


}
