package com.bortnikov.artem.mapnweather.view.fragments.mapweather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bortnikov.artem.mapnweather.R;
import com.bortnikov.artem.mapnweather.data.model.view.DataViewModelItem;
import com.squareup.picasso.Picasso;

import java.util.Locale;

class ViewHolder extends RecyclerView.ViewHolder {

    private ImageView icon;
    private TextView timeTextView;
    private TextView temperatureTextView;
    private TextView detailsTextView;

    private ViewHolder(View view) {
        super(view);
        icon = view.findViewById(R.id.weather_icon);
        timeTextView = view.findViewById(R.id.time_field);
        temperatureTextView = view.findViewById(R.id.temperature_field);
        detailsTextView = view.findViewById(R.id.details_field);
    }

    void bind(DataViewModelItem model) {
        setWeatherIcon(model.getIcon());
        setCurrentTemp(model.getTemp());
        timeTextView.setText(model.getDt());
        setDetails(model.getDescription(), model.getHumidity(), model.getPressure());
    }

    static ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item, parent, false));
    }

    private void setWeatherIcon(String iconId) {
        String iconLink = String.format(Locale.ROOT,
                "http://openweathermap.org/img/w/%s.png", iconId);
        Picasso.get()
                .load(iconLink)
                .resize(80, 80)
                .into(icon);
    }

    private void setCurrentTemp(double currentTemp) {
        String currentTempText = String.format(Locale.ROOT, "%.2f\u2103", currentTemp);
        temperatureTextView.setText(currentTempText);
    }

    private void setDetails(String description, double humidity, double pressure) {

        String detailsText = String.format(Locale.ROOT,
                "%s \nHumidity:\n%s %%\nPressure:\n%s hPa",
                description.toUpperCase(), humidity, pressure);
        detailsTextView.setText(detailsText);
    }
}
