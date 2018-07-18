package com.ssheetz.myweather;

import com.google.android.gms.maps.model.LatLng;

public interface OnCityCreatedListener {
    /**
     * A new city has been created by the user.
     *
     * @param label  The city name
     * @param location  The geographic location
     */
    void onCityCreated(String label, LatLng location);
}
