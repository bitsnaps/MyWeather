package com.ssheetz.myweather.weather;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.ssheetz.myweather.model.Forecast;
import com.ssheetz.myweather.model.UnitSystem;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.ssheetz.myweather.Preconditions.checkNotNull;


/**
 * Downloads weather data from OpenWeatherMap (http://openweathermap.org) and returns it
 * asynchronously to a {@link }ForecastReceiver}.
 */
public class OpenWeatherMapDataProvider implements WeatherDataProvider {
    private static final String TAG = "DataProvider";

    private final WeatherUrlBuilder urlBuilder;
    private OkHttpClient httpClient;
    private final WeatherForecastParser forecastParser;
    private final ForecastReceiver forecastReceiver;

    /**
     * Constructs the data provider.
     *
     * @param urlBuilder  Builds URLs for the weather API
     * @param httpClient  Downloads data
     * @param forecastParser  Parses weather data into our model
     * @param forecastReceiver  Receives the new forecast object
     */
    OpenWeatherMapDataProvider(WeatherUrlBuilder urlBuilder, OkHttpClient httpClient, WeatherForecastParser forecastParser, ForecastReceiver forecastReceiver) {
        this.urlBuilder = checkNotNull(urlBuilder, "urlBuilder cannot be null");
        this.httpClient = checkNotNull(httpClient, "httpClient cannot be null");
        this.forecastParser = checkNotNull(forecastParser, "forecastParser cannot be null");
        this.forecastReceiver = checkNotNull(forecastReceiver, "forecastReceiver cannot be null");
    }

    @Override
    public void queryTodaysForecast(final LatLng location) {
        checkNotNull(location, "location cannot be null");
        String url = urlBuilder.urlForTodaysForecast(location, UnitSystem.IMPERIAL);
        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(@Nullable Call call, @Nullable IOException e) {
                Log.e(TAG, "forecast OkHttp onFailure");
                forecastReceiver.onTodaysForecastFailed(location);
            }

            public void onResponse(@Nullable Call call, @Nullable Response response) {
                if (response == null || !response.isSuccessful()) {
                    Log.e(TAG, "forecast OkHttp no response");
                    forecastReceiver.onTodaysForecastFailed(location);
                    return;
                }
                ResponseBody body = response.body();
                if (body == null) {
                    Log.e(TAG, "forecast OkHttp no body");
                    forecastReceiver.onTodaysForecastFailed(location);
                    return;
                }
                try {
                    Forecast forecast = forecastParser.parseWeatherData(body.string());
                    forecastReceiver.onReceiveTodaysForecast(location, forecast);
                } catch(IllegalArgumentException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    forecastReceiver.onTodaysForecastFailed(location);
                } catch(IllegalStateException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    forecastReceiver.onTodaysForecastFailed(location);
                } catch(IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    forecastReceiver.onTodaysForecastFailed(location);
                }
            }
        });
    }
}
