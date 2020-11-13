package com.ssheetz.myweather.weather

import com.google.android.gms.maps.model.LatLng
import com.ssheetz.myweather.model.UnitSystem
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import spock.lang.Specification
import spock.lang.Unroll


@Unroll
class OpenWeatherMapDataProviderTest extends Specification {

    def "test constructor with null #description shall throw exception"() {
        when:
        new OpenWeatherMapDataProvider(urlBuilder, httpClient, forecastParser, forecastReceiver)
        then:
        thrown(NullPointerException)
        where:
        description        | urlBuilder              | httpClient         | forecastParser              | forecastReceiver
        "urlBuilder"       | null                    | Stub(OkHttpClient) | Stub(WeatherForecastParser) | Stub(ForecastReceiver)
        "httpClient"       | Stub(WeatherUrlBuilder) | null               | Stub(WeatherForecastParser) | Stub(ForecastReceiver)
        "forecastParser"   | Stub(WeatherUrlBuilder) | Stub(OkHttpClient) | null                        | Stub(ForecastReceiver)
        "forecastReceiver" | Stub(WeatherUrlBuilder) | Stub(OkHttpClient) | Stub(WeatherForecastParser) | null
    }

    def "test queryTodaysForecast with null location shall throw exception"() {
        setup:
        def provider = new OpenWeatherMapDataProvider(Stub(WeatherUrlBuilder), Stub(OkHttpClient), Stub(WeatherForecastParser), Stub(ForecastReceiver))
        when:
        provider.queryTodaysForecast(null)
        then:
        thrown(NullPointerException)
    }

    def "test queryTodaysForecast with response failure shall call onTodaysForecastFailed"() {
        setup:
        def urlBuilder = Mock(WeatherUrlBuilder)
        def httpClient = Mock(OkHttpClient)
        def parser = Mock(WeatherForecastParser)
        def receiver = Mock(ForecastReceiver)
        def provider = new OpenWeatherMapDataProvider(urlBuilder, httpClient, parser, receiver)
        def latlng = new LatLng(23.112, -98.78)
        def url = "http://openweathermap.org"
        def call = Mock(Call)
        when:
        provider.queryTodaysForecast(latlng)
        then:
        1 * urlBuilder.urlForTodaysForecast(latlng, UnitSystem.IMPERIAL) >> url
        1 * httpClient.newCall(_ as Request) >> call
        1 * call.enqueue(_ as Callback) >> { Callback c -> c.onFailure(null, null) }
        1 * receiver.onTodaysForecastFailed(latlng)
        when:
        provider.queryTodaysForecast(latlng)
        then:
        1 * urlBuilder.urlForTodaysForecast(latlng, UnitSystem.IMPERIAL) >> url
        1 * httpClient.newCall(_ as Request) >> call
        1 * call.enqueue(_ as Callback) >> { Callback c -> c.onResponse(null, null) }
        1 * receiver.onTodaysForecastFailed(latlng)
    }
}
