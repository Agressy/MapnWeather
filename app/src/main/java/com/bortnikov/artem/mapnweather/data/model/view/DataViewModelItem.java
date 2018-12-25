package com.bortnikov.artem.mapnweather.data.model.view;

public class DataViewModelItem {
    private int id;
    private double humidity;
    private double temp;
    private double pressure;
    private String dt;
    private String description;
    private String icon;


    public DataViewModelItem(int id,
                             double humidity,
                             double temp,
                             double pressure,
                             String dt,
                             String description,
                             String icon) {
        this.id = id;
        this.humidity = humidity;
        this.temp = temp;
        this.pressure = pressure;
        this.dt = dt;
        this.description = description;
        this.icon = icon;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
