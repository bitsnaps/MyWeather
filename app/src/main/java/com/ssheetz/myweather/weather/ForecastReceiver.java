package com.ssheetz.myweather.weather;

import com.google.android.gms.maps.model.LatLng;
import com.ssheetz.myweather.model.Forecast;

/**
 * Receives forecasts from a {@link WeatherDataProvider}.
 */
public interface ForecastReceiver {
    /**
     * A forecast for a location and time has been downloaded.
     *
     * @param location  The location
     * @param forecast  The forecast
     */
    void onReceiveTodaysForecast(LatLng location, Forecast forecast);

    /**
     * An attempt to download a forecast for a location and time has failed.
     *
     * @param location  The location
     */
    void onTodaysForecastFailed(LatLng location);
}
