package com.ssheetz.myweather.weather;

import com.ssheetz.myweather.model.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.ssheetz.myweather.Preconditions.checkNotNull;


/**
 * Parses weather data from OpenWeatherMap (http://openweathermap.org)
 */
public class OpenWeatherMapForecastParser implements WeatherForecastParser {

    private static final String KEY_MAIN = "main";
    private static final String KEY_TEMPERATURE = "temp";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_WEATHER = "weather";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_WIND = "wind";
    private static final String KEY_SPEED = "speed";
    private static final String KEY_DEGREES = "deg";


    @Override
    public Forecast parseWeatherData(String data) throws IllegalArgumentException {
        checkNotNull(data, "data cannot be null");
        try {
            JSONObject root = new JSONObject(data);
            JSONObject main = root.getJSONObject(KEY_MAIN);
            double temperature = main.getDouble(KEY_TEMPERATURE);
            double humidity = main.getDouble(KEY_HUMIDITY);
            JSONArray weather = root.getJSONArray(KEY_WEATHER);
            String description = weather.getJSONObject(0).getString(KEY_DESCRIPTION);
            JSONObject wind = root.getJSONObject(KEY_WIND);
            double windSpeed = wind.getDouble(KEY_SPEED);
            double windDirection = wind.getDouble(KEY_DEGREES);
            return new Forecast(temperature, humidity, description, windSpeed, windDirection);
        } catch(JSONException e) {
            throw new IllegalArgumentException("Failed to parse forecast data.");
        }
    }
}
