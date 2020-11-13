package com.ssheetz.myweather.model

import com.google.android.gms.maps.model.LatLng
import spock.lang.Specification
import spock.lang.Unroll


@Unroll
class CityTest extends Specification {

    def "test constructor with null #description shall throw exception"() {
        when:
        new City(id, label, location)
        then:
        thrown(NullPointerException)
        where:
        description | id     | label     | location
        "id"        | null   | "Denver"  | new LatLng(44.3, 5.8)
        "label"     | "234"  | null      | new LatLng(44.3, 5.8)
        "location"  | "345"  | "Omaha"   | null
    }

    def "test getters shall return correct values"() {
        setup:
        def city = new City("54321", "Hamburg", new LatLng(9.0, -1.23))
        expect:
        city.id == "54321"
        city.label == "Hamburg"
        city.location == new LatLng(9.0, -1.23)
    }
}
