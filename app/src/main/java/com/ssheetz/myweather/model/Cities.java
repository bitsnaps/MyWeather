package com.ssheetz.myweather.model;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.ssheetz.myweather.Preconditions.checkState;


/**
 * Manages a collection of cities that the user has selected.
 */
public class Cities {

    // The singleton instance
    private static Cities instance;

    private final Map<String, City> cityIdMap = new HashMap<>();
    private final List<City> cities = new ArrayList<>();

    /**
     * Gets/creates the singleton instance.
     *
     * @param context  The Activity context used for resources
     */
    public static Cities getInstance(Context context) {
        if (instance == null) {
            checkState(context != null, "WeatherManager must be initialized with a Context first.");
            instance = new Cities(context);
        }
        return instance;
    }

    // Private constructor for singleton
    private Cities(Context context) {
        createCity("Philadelphia", new LatLng(39.95, -75.2));
        createCity("San Antonio", new LatLng(29.42, -98.55));
        createCity("Petrofka", new LatLng(52.66, -106.9));
    }

    /**
     * Creates a {@link City} object and adds it to the collection.
     *
     * @param label  The visible city name
     * @param location  The geographic location
     * @return  The new City
     */
    public City createCity(String label, LatLng location) {
        String id = UUID.randomUUID().toString();
        City city = new City(id, label, location);
        cityIdMap.put(id, city);
        cities.add(city);
        sort();
        return city;
    }

    /**
     * Removes a {@link City} from the collection by its ID.
     *
     * @param id  The unique identifier.
     */
    public void removeCity(String id) {
        City city = cityIdMap.remove(id);
        if (city != null) {
            cities.remove(city);
        }
    }

    /**
     * @return A copy of the list of cities, in alphabetical order.
     */
    public List<City> getCities() {
        return new ArrayList<>(cities);
    }

    /**
     * Gets the {@link City} with the given ID.
     *
     * @param id The unique identifier
     * @return the City if found, or null
     */
    public City get(String id) {
        return cityIdMap.get(id);
    }

    private void sort() {
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City c1, City c2) {
                return c1.getLabel().compareTo(c2.getLabel());
            }
        });
    }
}
