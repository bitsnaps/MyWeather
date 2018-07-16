package com.ssheetz.myweather.model;

import com.google.android.gms.maps.model.LatLng;


/**
 * A single city in the user's collection
 */
public class City {
    private final String id;
    private final String label;
    private final LatLng location;

    /**
     * Constructs the City object.
     *
     * @param id  A unique ID
     * @param label  The city's label
     * @param location  The geographic location
     */
    public City(String id, String label, LatLng location) {
        this.id = id;
        this.label = label;
        this.location = location;
    }

    /**
     * @return  The unique ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return  The visible label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return  The geographic location
     */
    public LatLng getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return label;
    }
}
