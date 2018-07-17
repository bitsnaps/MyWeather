package com.ssheetz.myweather.weather;


import com.google.android.gms.maps.model.LatLng;
import com.ssheetz.myweather.model.Forecast;


/**
 * Downloads weather data from an API.
 */
public interface WeatherDataProvider {

    /**
     * Downloads today's forecast.
     *
     * @param location  The geographic location
     */
    void queryTodaysForecast(LatLng location);
}
