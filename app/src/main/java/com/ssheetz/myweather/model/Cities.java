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

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(City item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static City createDummyItem(int position) {
        return new City(String.valueOf(position), "City name " + position, new LatLng(1.0, 1.0));
    }
}
