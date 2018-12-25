package com.bortnikov.artem.mapnweather.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bortnikov.artem.mapnweather.MainApp;
import com.bortnikov.artem.mapnweather.data.model.view.DataViewModelMain;
import com.bortnikov.artem.mapnweather.data.usecases.DataUseCase;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

@InjectViewState
public class MapWeatherPresenter extends MvpPresenter<MapWeatherView>
        implements Subscriber<DataViewModelMain> {

    @Inject
    DataUseCase usecase;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        MainApp.getComponent().injectsToMapWeatherPresenter(this);
    }


    public void searchNewInfo(String city) {
        getViewState().startLoading();
        usecase.getSearch(city).subscribe(this);
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(DataViewModelMain data) {
        getViewState().setItems(data.getDataList());
        getViewState().setMap(data.getLat(), data.getLon());
    }

    @Override
    public void onError(Throwable e) {
        getViewState().showError(e);
        getViewState().hideLoading();
        getViewState().showEmptyText();
    }

    @Override
    public void onComplete() {
        getViewState().updateList();
        getViewState().hideLoading();
    }
}
