package com.ssheetz.myweather.weather;

import com.google.android.gms.maps.model.LatLng;
import com.ssheetz.myweather.model.Forecast;


public class WeatherData {

    private static WeatherData instance;

    private WeatherData() {
    }

    public static WeatherData getInstance() {
        if (instance == null) {
            instance = new WeatherData();
        }
        return instance;
    }

    public Forecast getForecast(LatLng location) {
        return new Forecast(1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
    }

}
