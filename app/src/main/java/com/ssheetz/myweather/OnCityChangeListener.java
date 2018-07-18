package com.ssheetz.myweather;

import com.google.android.gms.maps.model.LatLng;

public interface OnCityChangeListener {
    /**
     * A new city has been created by the user.
     *
     * @param label  The city name
     * @param location  The geographic location
     */
    void onCityCreated(String label, LatLng location);

    /**
     * The user has opted to delete an existing city.
     *
     * @param id  The city's unique identifier
     */
    void onCityDeleted(String id);
}
