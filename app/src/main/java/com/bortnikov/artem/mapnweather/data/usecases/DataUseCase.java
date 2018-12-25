package com.bortnikov.artem.mapnweather.data.usecases;

import com.bortnikov.artem.mapnweather.data.Endpoints;
import com.bortnikov.artem.mapnweather.data.model.retrofit.MainList;
import com.bortnikov.artem.mapnweather.data.model.view.DataViewModelItem;
import com.bortnikov.artem.mapnweather.data.model.view.DataViewModelMain;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataUseCase {
    private static final String API_KEY = "8a732ed528bccc9beeb9c6cca127bd4f";
    private static final String FORMAT = "json";
    private static final String UNITS = "metric";

    private Endpoints endpoints;

    public DataUseCase(Endpoints endpoints) {
        this.endpoints = endpoints;
    }


    public Flowable<DataViewModelMain> getSearch(String tag) {
        return endpoints.getSearchResponse(tag, FORMAT, API_KEY, UNITS)
                .map(searchList -> {
                    List<MainList> weatherList = searchList.getList();
                    ArrayList<DataViewModelItem> resultList = new ArrayList<>();
                    int i = 0;
                    for (MainList r : weatherList) {
                        DataViewModelItem dataItem = new DataViewModelItem(i,
                                r.getMain().getHumidity(),
                                r.getMain().getTemp(),
                                r.getMain().getPressure(),
                                r.getDtTxt(),
                                r.getWeather().get(0).getDescription(),
                                r.getWeather().get(0).getIcon());
                        resultList.add(dataItem);
                        i++;
                    }
                    DataViewModelMain result = new DataViewModelMain();
                    result.setCityName(tag);
                    result.setLat(searchList.getCity().getCoord().getLat());
                    result.setLon(searchList.getCity().getCoord().getLon());
                    result.setDataList(resultList);
                    return result;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
