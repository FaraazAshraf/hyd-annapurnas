package com.ashraf.faraaz.hydannapurnas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewOnMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    double lat, lon;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_on_map);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String singleAnnapurna = getIntent().getExtras().getString("singleAnnapurna");

        lat = Double.parseDouble(singleAnnapurna.split(",")[5]);
        lon = Double.parseDouble(singleAnnapurna.split(",")[6]);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.annapurnaMapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng annapurnaLatLng = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions()
                .position(annapurnaLatLng)
                .title("ANNAPURNA POINT"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(annapurnaLatLng));

        mMap.setMyLocationEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(annapurnaLatLng)
                .zoom(17.0f)
                .build();
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cu);
    }
}
