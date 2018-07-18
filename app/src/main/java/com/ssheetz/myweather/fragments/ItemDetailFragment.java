package com.ssheetz.myweather.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssheetz.myweather.ItemDetailActivity;
import com.ssheetz.myweather.ItemListActivity;
import com.ssheetz.myweather.R;
import com.ssheetz.myweather.model.Cities;
import com.ssheetz.myweather.model.City;
import com.ssheetz.myweather.model.Forecast;
import com.ssheetz.myweather.weather.WeatherManager;

import java.util.Locale;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private final WeatherManager weatherManager = WeatherManager.getInstance(null);
    private final Cities cities = Cities.getInstance(null);
    private City cityItem;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_ITEM_ID)) {
            // Load the content specified by the fragment arguments.
            cityItem = cities.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity activity = this.getActivity();
        if (activity != null) {
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(cityItem.getLabel());
            }
        }
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        View forecastView = rootView.findViewById(R.id.forecast_view);
        View noForecast = rootView.findViewById(R.id.no_data);

        // Show the content
        if (cityItem != null) {
            Forecast forecast = weatherManager.getTodaysForecast(cityItem.getLocation());
            if (forecast == null) {
                forecastView.setVisibility(View.GONE);
                noForecast.setVisibility(View.VISIBLE);
            } else {
                noForecast.setVisibility(View.GONE);
                forecastView.setVisibility(View.VISIBLE);
                ((TextView)rootView.findViewById(R.id.description)).setText(forecast.getDescription());
                String temperatureStr = String.format(Locale.getDefault(), "%.1f \u00B0", forecast.getTemperature());
                ((TextView)rootView.findViewById(R.id.temperature)).setText(temperatureStr);
                String humidityStr = String.format(Locale.getDefault(), "%.0f %% %s", forecast.getHumidity(), getString(R.string.humidity));
                ((TextView)rootView.findViewById(R.id.humidity)).setText(humidityStr);
                String windStr = String.format(Locale.getDefault(), "%s %.0f \u00B0 at %.0f mph", getString(R.string.wind), forecast.getWindDirection(), forecast.getWindSpeed());
                ((TextView)rootView.findViewById(R.id.wind)).setText(windStr);
            }
        }

        return rootView;
    }
}
