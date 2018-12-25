package com.bortnikov.artem.mapnweather.view.fragments.mapweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bortnikov.artem.mapnweather.R;
import com.bortnikov.artem.mapnweather.data.model.view.DataViewModelItem;
import com.bortnikov.artem.mapnweather.presenter.MapWeatherPresenter;
import com.bortnikov.artem.mapnweather.presenter.MapWeatherView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MapWeatherFragment extends MvpAppCompatFragment implements MapWeatherView,
        SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    MapWeatherPresenter mapWeatherPresenter;

    private Adapter adapter = new Adapter();

    private RecyclerView weatherRecyclerView;

    private TextView nothingFoundTextView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private MapView mapView;
    private GoogleMap googleMap;

    private String cityName;

    @Override
    public void onStart() {
        super.onStart();
        mapWeatherPresenter.searchNewInfo(cityName);
    }

    public static MapWeatherFragment newInstance(String cityName) {
        MapWeatherFragment fragment = new MapWeatherFragment();
        Bundle args = new Bundle();
        args.putString("CITY", cityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map_n_weather, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        cityName = getArguments().getString("CITY");

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nothingFoundTextView = view.findViewById(R.id.nothing_found_text_view);

        weatherRecyclerView = view.findViewById(R.id.weather_recycler_view);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        weatherRecyclerView.setAdapter(adapter);

        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity())
                    .getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmptyText() {
        nothingFoundTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setItems(List<DataViewModelItem> items) {
        adapter.setItems(items);
    }

    @Override
    public void setMap(float lat, float lon) {

        LatLng coord = new LatLng(lat, lon);

        mapView.getMapAsync(mMap -> {
            googleMap = mMap;

            if (checkLocationPermission()) {
                if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    googleMap.getUiSettings().setAllGesturesEnabled(false);
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                }
            }

            googleMap.addMarker(new MarkerOptions().position(coord).
                    title("Title").snippet("TitleName"));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(coord).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition
                    (cameraPosition));

            // В задании было указано реализовать:
            // "...Карта...Ее можно потянуть и развернуть на весь экран"
            // Не смог придумать решения без костылей с реализацией через MapView
            // (Делать кастомную view от MapView - неблагодарное дело на мой взгляд.
            // С map фрагментом было бы попроще, но уже не так актуально,
            // а другие карты и вовсе не хотел использовать)
            // Вне зависимости от Вашего решения по мне, буду очень признателен,
            // если сможете мне хотя бы намекнуть через Эренста Анпилова
            // как такое можно было бы реализовать или как бы Вы это сделали.
            // Мне очень будет интересно услышать мнение профессионала.
            // Заранее большое Вам спасибо и с наступающими праздниками!

            googleMap.setOnMapLongClickListener(latLng -> {
                String uri = String.format(Locale.ROOT, "geo: %f, %f", lat, lon);
                Uri mapIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                MapWeatherFragment.this.startActivity(mapIntent);
            });
        });
    }

    @Override
    public void updateList() {
        nothingFoundTextView.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
        weatherRecyclerView.invalidate();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CITY", cityName);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage("")
                        .setPositiveButton("Ok", (dialogInterface, i) ->
                                ActivityCompat.requestPermissions(getActivity(), new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION}, 1))
                        .create()
                        .show();

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                }
            }

        }
    }

    @Override
    public void onRefresh() {
        mapWeatherPresenter.searchNewInfo(cityName);
    }
}
