package com.ssheetz.myweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.ssheetz.myweather.fragments.AddCityFragment;
import com.ssheetz.myweather.fragments.SettingsFragment;
import com.ssheetz.myweather.fragments.WebViewerFragment;
import com.ssheetz.myweather.model.Cities;
import com.ssheetz.myweather.model.City;
import com.ssheetz.myweather.weather.WeatherManager;

import java.io.IOException;
import java.util.Collections;

import okhttp3.OkHttpClient;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements OnCityChangeListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private Cities cities;
    private WeatherManager weatherManager;
    private RecyclerView.Adapter<CityListAdapter.ViewHolder> listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        cities = Cities.getInstance(this);
        weatherManager = WeatherManager.getInstance(this);
        weatherManager.refreshTodaysForecast(cities.getCities());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAdd();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

//        String url = "http://192.168.1.44/yii2rest/web/post";
//        RequestExample req = new RequestExample(new OkHttpClient());
//        req.queryForPosts(url);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        listAdapter = new CityListAdapter(this, cities, mTwoPane);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onCityCreated(String label, LatLng location) {
        City city = cities.createCity(label, location);
        weatherManager.refreshTodaysForecast(Collections.singletonList(city));
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCityDeleted(String id) {
        cities.removeCity(id);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAllCitiesDeleted() {
        cities.removeAll();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        cities.saveCities(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_help:
                showHelp();
                return true;
            case R.id.action_add:
                showAdd();
                return true;
            case R.id.action_settings:
                showSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showHelp() {
        try {
            String url = "file:///android_asset/html/help.html";
            String title = getString(R.string.action_help);
            WebViewerFragment fragment = WebViewerFragment.newInstance(url, title);
            fragment.show(getFragmentManager(), "HelpDialog");
        }
        catch(Exception ex) {
            String msg = "";
            if (ex.getClass().getSimpleName().contains("IOException")){
                msg = "Help file not found!";
            } else {
                msg = "Error! Cannot show help page.";
            }
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void showAdd() {
        AddCityFragment fragment = AddCityFragment.newInstance();
        fragment.show(getFragmentManager(), "AddDialog");
    }

    private void showSettings() {
        SettingsFragment fragment = SettingsFragment.newInstance();
        fragment.show(getFragmentManager(), "SettingsDialog");
    }
}
