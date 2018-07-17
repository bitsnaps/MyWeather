package com.ssheetz.myweather.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cities {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<City> ITEMS = new ArrayList<City>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, City> ITEM_MAP = new HashMap<String, City>();


    static {
        addItem(new City("1", "Philadelphia", new LatLng(39.95, -75.2)));
        addItem(new City("2", "San Antonio", new LatLng(29.42, -98.55)));
        addItem(new City("3", "Petrofka", new LatLng(52.66, -106.9)));
    }

    private static void addItem(City item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }
}
