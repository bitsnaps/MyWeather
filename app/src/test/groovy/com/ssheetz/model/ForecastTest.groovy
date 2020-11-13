package com.ssheetz.myweather.model

import spock.lang.Specification


class ForecastTest extends Specification {

    def "test getters shall return correct values"() {
        setup:
        def forecast = new Forecast(21.7, 8.8, "some clouds", 5.6, 2.1)
        expect:
        forecast.temperature == 21.7d
        forecast.humidity == 8.8d
        forecast.description == "some clouds"
        forecast.windSpeed == 5.6d
        forecast.windDirection == 2.1d
    }
}
