package com.ssheetz.myweather.model;

import com.google.android.gms.maps.model.LatLng;

import static com.ssheetz.myweather.Preconditions.checkNotNull;


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
    City(String id, String label, LatLng location) {
        this.id = checkNotNull(id, "id cannot be null");
        this.label = checkNotNull(label, "label cannot be null");
        this.location = checkNotNull(location, "location cannot be null");
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
