package com.rutvik.bhagwatiholidays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocateUsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_us);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng bhagwatiHolidays = new LatLng(23.025819, 72.5096982);
        mMap.addMarker(new MarkerOptions().position(bhagwatiHolidays).title("Bhagwati Holidays"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bhagwatiHolidays));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }
}
