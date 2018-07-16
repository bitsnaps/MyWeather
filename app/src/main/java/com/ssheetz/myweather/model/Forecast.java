package com.ssheetz.myweather.model;

/**
 * Represents the details of a weather forecast at one location, at one point in time.
 */
public class Forecast {

    private float temperature;
    private float humidity;
    private float rainChance;
    private float windSpeed;
    private float windDirection;

    /**
     * Constructs the Forecast object.
     *
     * @param temperature  Temperature in Fahrenheit
     * @param humidity  Humidity percentage
     * @param rainChance  Chance of rain
     * @param windSpeed  Wind speed in mph
     * @param windDirection  Wind direction in degrees CW from north
     */
    public Forecast(float temperature, float humidity, float rainChance, float windSpeed, float windDirection) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.rainChance = rainChance;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    /**
     * @return  Temperature in Fahrenheit
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * @return  Humidity percentage
     */
    public float getHumidity() {
        return humidity;
    }

    /**
     * @return  Chance of rain
     */
    public float getRainChance() {
        return rainChance;
    }

    /**
     * @return  Wind speed in mph
     */
    public float getWindSpeed() {
        return windSpeed;
    }

    /**
     * @return  Wind direction in degrees CW from north
     */
    public float getWindDirection() {
        return windDirection;
    }
}
