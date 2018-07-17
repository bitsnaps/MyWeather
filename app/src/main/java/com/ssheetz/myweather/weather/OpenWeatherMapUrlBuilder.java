package com.ssheetz.myweather.weather;

import com.google.android.gms.maps.model.LatLng;
import com.ssheetz.myweather.model.UnitSystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.ssheetz.myweather.Preconditions.checkNotNull;


/**
 * Generates URLs for OpenWeatherMap (http://openweathermap.org)
 */
public class OpenWeatherMapUrlBuilder implements WeatherUrlBuilder {

    private static final Map<UnitSystem, String> unitsMap;
    static {
        Map<UnitSystem, String> map = new HashMap<>();
        map.put(UnitSystem.IMPERIAL, "imperial");
        map.put(UnitSystem.METRIC, "metric");
        map.put(UnitSystem.STANDARD, "standard");
        unitsMap = Collections.unmodifiableMap(map);
    }

    private final String appId;

    /**
     * Constructs the url builder
     *
     * @param appId  The Open Weather Map app ID
     */
    OpenWeatherMapUrlBuilder(String appId) {
        this.appId = checkNotNull(appId, "appId cannot be null.");
    }

    @Override
    public String urlForTodaysForecast(LatLng location, UnitSystem unitSystem) {
        checkNotNull(location, "location cannot be null");
        checkNotNull(unitSystem, "unitSystem cannot be null");
        return String.format(Locale.getDefault(),
                "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=%s",
                location.latitude,
                location.longitude,
                appId,
                unitsMap.get(unitSystem));
    }
}
