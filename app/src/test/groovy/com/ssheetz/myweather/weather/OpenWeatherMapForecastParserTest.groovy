package com.ssheetz.myweather.weather

import spock.lang.Specification


class OpenWeatherMapForecastParserTest extends Specification {

    def "test parseWeatherData with null data shall throw exception"() {
        when:
            new OpenWeatherMapForecastParser().parseWeatherData(null)
        then:
            thrown(NullPointerException)
    }

    def "test parseWeatherData with valid string shall return forecast"() {
        when:
            def jsonStr = '{"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"base":"stations","main":{"temp":24.23,"pressure":1029.54,"humidity":100,"temp_min":24.23,"temp_max":24.23,"sea_level":1029.53,"grnd_level":1029.54},"wind":{"speed":5.86,"deg":159.002},"clouds":{"all":100},"dt":1531788665,"sys":{"message":0.0034,"sunrise":1531807354,"sunset":1531850986},"id":6295630,"name":"Earth","cod":200}'
            def result = new OpenWeatherMapForecastParser().parseWeatherData(jsonStr)
        then:
            result.temperature == 24.23d
            result.humidity == 100d
            result.description == "overcast clouds"
            result.windDirection == 159.002d
            result.windSpeed == 5.86d
    }
}
