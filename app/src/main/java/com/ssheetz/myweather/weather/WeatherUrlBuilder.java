package com.ssheetz.myweather.weather;

import com.google.android.gms.maps.model.LatLng;
import com.ssheetz.myweather.model.UnitSystem;

/**
 * Generates URLs for a weather API.
 */
public interface WeatherUrlBuilder {

    /**
     * Generates URL for today's forecast.
     *
     * @param location  The grographic location
     * @param unitSystem  The unit system
     * @return  The URL
     */
    String urlForTodaysForecast(LatLng location, UnitSystem unitSystem);
}
