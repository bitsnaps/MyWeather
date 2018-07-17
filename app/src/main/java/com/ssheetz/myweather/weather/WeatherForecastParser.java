package com.ssheetz.myweather.weather;

import com.ssheetz.myweather.model.Forecast;


/**
 * Parses data from a weather API into our model.
 */
public interface WeatherForecastParser {

    /**
     * Parses a forecast for a certain location and time.
     *
     * @param data  The string data from the weather API
     * @return  The resulting forecast
     * @throws IllegalArgumentException if there was a problem with the string
     */
    Forecast parseWeatherData(String data) throws IllegalArgumentException;
}
