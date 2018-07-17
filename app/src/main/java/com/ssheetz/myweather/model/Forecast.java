package com.ssheetz.myweather.model;

import static com.ssheetz.myweather.Preconditions.checkNotNull;

/**
 * Represents the details of a weather forecast at one location, at one point in time.
 */
public class Forecast {

    private final double temperature;
    private final double humidity;
    private final String description;
    private final double windSpeed;
    private final double windDirection;

    /**
     * Constructs the Forecast object.
     *
     * @param temperature  Temperature in Fahrenheit
     * @param humidity  Humidity percentage
     * @param description  Text description
     * @param windSpeed  Wind speed in mph
     * @param windDirection  Wind direction in degrees CW from north
     */
    public Forecast(double temperature, double humidity, String description, double windSpeed, double windDirection) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = checkNotNull(description, "description cannot be null");
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    /**
     * @return  Temperature in Fahrenheit
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * @return  Humidity percentage
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * @return  Text description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return  Wind speed in mph
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * @return  Wind direction in degrees CW from north
     */
    public double getWindDirection() {
        return windDirection;
    }
}
