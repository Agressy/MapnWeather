package com.bortnikov.artem.mapnweather.data.model.view;

import java.util.ArrayList;

public class DataViewModelMain {

    private ArrayList<DataViewModelItem> dataList;
    private String cityName;
    private float lat;
    private float lon;

    public ArrayList<DataViewModelItem> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DataViewModelItem> dataList) {
        this.dataList = dataList;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
