package com.ssheetz.myweather.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ssheetz.myweather.OnCityChangeListener;
import com.ssheetz.myweather.R;

import java.io.IOException;
import java.util.List;


/**
 * Shows the user a dialog box where they can pick a single point on the map and choose
 * a city name.
 */
public class AddCityFragment extends DialogFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    private MapView mapView;
    private GoogleMap gmap;
    private Geocoder geocoder;
    private EditText editName;
    private Marker marker;
    private LatLng location;


    public AddCityFragment() {
        // Empty constructor required for DialogFragment
    }

    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
        if (location != null) {
            // save the currently selected location
            outState.putDouble(KEY_LATITUDE, location.latitude);
            outState.putDouble(KEY_LONGITUDE, location.longitude);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_city, container);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button saveButton = view.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveSelection()) {
                    getDialog().dismiss();
                }
            }
        });
        editName = view.findViewById(R.id.city_name);
        mapView.getMapAsync(this);
        geocoder = new Geocoder(getActivity());
        if (savedInstanceState != null) {
            // recover a previously marked location
            double lat = savedInstanceState.getDouble(KEY_LATITUDE, 1000);
            double lng = savedInstanceState.getDouble(KEY_LONGITUDE, 1000);
            if (lat <= 90.0 && lng <= 180.0) {
                location = new LatLng(lat, lng);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setOnMapClickListener(this);
        if (marker == null && location != null) {
            // recreate marker after screen rotation
            String city = getCityName(location);
            createMarker(location, city);
            // center map over location
            gmap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        String city = getCityName(latLng);
        createMarker(latLng, city);
        if (city != null && !city.isEmpty()) {
            editName.setText(marker.getTitle());
        }
    }

    private String getCityName(LatLng latLng) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (!addresses.isEmpty()) {
                return addresses.get(0).getLocality();
            }
        } catch(IOException e) {
            // do nothing
        }
        return "";
    }

    private void createMarker(LatLng latLng, String city) {
        location = latLng;
        if (marker != null) {
            marker.remove();
        }
        marker = gmap.addMarker(new MarkerOptions().position(latLng).title(city));
    }

    /**
     * If there is a valid label and location, and we can cast our parent activity to the
     * correct kind of interface, then notify it of the new city.
     * @return  True if the location was saved.
     */
    private boolean saveSelection() {
        String label = editName.getText().toString();
        if (location == null || label.isEmpty()) {
            return false;
        }
        Activity activity = getActivity();
        if (activity instanceof OnCityChangeListener) {
            ((OnCityChangeListener)activity).onCityCreated(label, location);
            return true;
        }
        return false;
    }

    private void setDialogPosition() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        window.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = screenHeight - FragmentUtils.dpToPx(getActivity(), 60);
        params.width = screenWidth - FragmentUtils.dpToPx(getActivity(),60);
        window.setAttributes(params);
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogPosition();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mapView != null) {
            mapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) {
            try {
                mapView.onDestroy();
            } catch (NullPointerException e) {
                //Log.e(TAG, "Error while attempting MapView.onDestroy(), ignoring exception", e);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }
}
