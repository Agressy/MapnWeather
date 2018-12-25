package com.bortnikov.artem.mapnweather.presenter;

import com.arellomobile.mvp.MvpView;
import com.bortnikov.artem.mapnweather.data.model.view.DataViewModelItem;

import java.util.List;

public interface MapWeatherView extends MvpView {
    void startLoading();

    void hideLoading();

    void showEmptyText();

    void showError(Throwable ex);

    void setItems(List<DataViewModelItem> items);

    void setMap(float lat, float lon);

    void updateList();

}
