package com.ssheetz.myweather.weather

import com.google.android.gms.maps.model.LatLng
import com.ssheetz.myweather.model.UnitSystem
import spock.lang.Specification

class OpenWeatherMapUrlBuilderTest extends Specification {

    def "test constructor with null app id shall throw exception"() {
        when:
            new OpenWeatherMapUrlBuilder(null)
        then:
            thrown(NullPointerException)
    }

    def "test urlForTodaysForecast shall build correct url"() {
        setup:
            def appid = "some-app-id"
            def lat = 23.1
            def lon = -12.1
            def urlBuilder = new OpenWeatherMapUrlBuilder(appid)
        when:
            def result = urlBuilder.urlForTodaysForecast(new LatLng(lat, lon), UnitSystem.IMPERIAL)
        then:
            result == "http://api.openweathermap.org/data/2.5/weather?lat=23.100000&lon=-12.100000&appid=" + appid + "&units=imperial"
    }
}