package com.ssheetz.myweather;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssheetz.myweather.model.Cities;
import com.ssheetz.myweather.model.City;
import com.ssheetz.myweather.model.Forecast;
import com.ssheetz.myweather.weather.WeatherData;

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

    /**
     * The dummy content this fragment is presenting.
     */
    private City mItem;

    private final WeatherData weatherData = WeatherData.getInstance();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Cities.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getLabel());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            Forecast forecast = weatherData.getForecast(mItem.getLocation());
            if (forecast == null) {
                ((TextView) rootView.findViewById(R.id.item_detail)).setText(getString(R.string.forecast_unknown));
            } else {
                ((TextView) rootView.findViewById(R.id.item_detail)).setText(String.format("temp: %f, humid: %f, rain: %f, windsp: %f, winddir: %f", forecast.getTemperature(), forecast.getHumidity(), forecast.getRainChance(), forecast.getWindSpeed(), forecast.getWindDirection()));
            }
        }

        return rootView;
    }
}
