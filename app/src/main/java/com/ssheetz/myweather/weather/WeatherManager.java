package com.ssheetz.myweather.weather;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.ssheetz.myweather.R;
import com.ssheetz.myweather.model.City;
import com.ssheetz.myweather.model.Forecast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

import static com.ssheetz.myweather.Preconditions.checkState;


/**
 * The entry point for the app to access downloaded weather data and refresh it.
 */
public class WeatherManager implements ForecastReceiver {

    // The singleton instance
    private static WeatherManager instance;

    private final WeatherDataProvider dataProvider;
    private final Map<LatLng, Forecast> todaysForecastCache;
    private Handler handler = new Handler();

    // Private constructor for singleton
    private WeatherManager(Context context) {
        todaysForecastCache = new HashMap<>();
        String openWeatherMapAppId = context.getString(R.string.open_weather_map_app_id);

        // Configure a data provider for OpenWeatherMap
        dataProvider = new OpenWeatherMapDataProvider(
                new OpenWeatherMapUrlBuilder(openWeatherMapAppId),
                new OkHttpClient.Builder().build(),
                new OpenWeatherMapForecastParser(),
                this);
    }

    /**
     * Gets/creates the singleton instance.
     *
     * @param context  The Activity context used for resources
     */
    public static WeatherManager getInstance(Context context) {
        if (instance == null) {
            checkState(context != null, "WeatherManager must be initialized with a Context first.");
            instance = new WeatherManager(context);
        }
        return instance;
    }

    /**
     * Gets the forecast for today from cache.
     *
     * @param location  The geographic location
     * @return  The forecast or null if it's not present
     */
    @Nullable
    public Forecast getTodaysForecast(LatLng location) {
        return todaysForecastCache.get(location);
    }

    /**
     * Refreshes the today's forecast for the given locations.
     *
     * @param locations  The locations to get.
     */
    public void refreshTodaysForecast(List<City> locations) {
        for (City location : locations) {
            dataProvider.queryTodaysForecast(location.getLocation());
        }
    }

    @Override
    public void onReceiveTodaysForecast(final LatLng location, final Forecast forecast) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                todaysForecastCache.put(location, forecast);

            }
        });
    }

    @Override
    public void onTodaysForecastFailed(LatLng location) {
        // do nothing
    }
}
