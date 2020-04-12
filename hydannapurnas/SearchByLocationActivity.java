package com.ashraf.faraaz.hydannapurnas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchByLocationActivity extends AppCompatActivity {

    LinearLayout searchByLocationLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_location);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchByLocationLinearLayout = findViewById(R.id.searchByLocationLinearLayout);

        PackageManager pm = getPackageManager();
        int hasPerm = pm.checkPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                getPackageName());
        if (hasPerm != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission missing", Toast.LENGTH_SHORT).show();
        }
        else {
            final ProgressDialog progressDialog = new ProgressDialog(SearchByLocationActivity.this);
            progressDialog.setMessage("Fetching your location...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            final ArrayList<String> allAnnapurnas = new com.ashraf.faraaz.hydannapurnas.UtilsClass().getAllAnnapurnas(SearchByLocationActivity.this);

            final double[] x1 = {0};
            final double[] y1 = {0};

            final LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

            LocationListener mLocationListener = new LocationListener() {
                @SuppressLint("ResourceType")
                public void onLocationChanged(Location location) {
                    locationManager.removeUpdates(this);
                    progressDialog.hide();

                    x1[0] = location.getLatitude();
                    y1[0] = location.getLongitude();

                    ArrayList<Integer> displayedIndices = new ArrayList<>();

                    for (int j = 0; j < 5; j++) {
                        int minIndex = 0;
                        double minDistance = Double.MAX_VALUE;
                        for (int i = 0; i < allAnnapurnas.size(); i++) {
                            if(displayedIndices.contains(i)) continue;

                            String annapurna = allAnnapurnas.get(i);
                            double x2 = Double.parseDouble(annapurna.split(",")[5]);
                            double y2 = Double.parseDouble(annapurna.split(",")[6]);

                            double thisDistance = new com.ashraf.faraaz.hydannapurnas.UtilsClass().getDistanceFromLatLonInKm(x1[0], y1[0], x2, y2);

                            if(thisDistance < minDistance) {
                                minDistance = thisDistance;
                                minIndex = i;
                            }
                        }
                        final int finalMinIndex = minIndex;
                        displayedIndices.add(finalMinIndex);

                        final String singleAnnapurna = allAnnapurnas.get(finalMinIndex);

//                        String id = singleAnnapurna.split(",")[0];
//                        String ward = singleAnnapurna.split(",")[1];
//                        String circle = singleAnnapurna.split(",")[2];
//                        String zone = singleAnnapurna.split(",")[3];
//                        String address = singleAnnapurna.split(",")[4];
//
//                        TextView detailsTextView = new TextView(SearchByLocationActivity.this);
//                        detailsTextView.setText((minDistance + "").substring(0,4) + " km"+
//                                "\nAddress: \n" + address + "\n" +
//                                "Ward: " + ward);
//                        detailsTextView.setTextIsSelectable(true);
//                        detailsTextView.setId(View.generateViewId());
//                        detailsTextView.setVerticalScrollBarEnabled(true);
//                        detailsTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        detailsTextView.setPadding(10,10,10,10);
//
//                        RelativeLayout rl = new RelativeLayout(SearchByLocationActivity.this);
//                        rl.setBackgroundResource(R.drawable.red_border);
//                        rl.addView(detailsTextView, 2*UtilsClass.getScreenWidth(SearchByLocationActivity.this)/3, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                        Button trackAnnapurnaButton = new Button(SearchByLocationActivity.this);
//                        trackAnnapurnaButton.setId(View.generateViewId());
//                        trackAnnapurnaButton.setBackgroundResource(R.drawable.my_button);
//                        trackAnnapurnaButton.setText("LOCATE");
//                        trackAnnapurnaButton.setTextColor(Color.WHITE);
//                        trackAnnapurnaButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        trackAnnapurnaButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String singleAnnapurna = allAnnapurnas.get(finalMinIndex);
//                                new UtilsClass().onAnnapurnaSelected(SearchByLocationActivity.this, singleAnnapurna);
//                            }
//                        });
//
//                        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        buttonParams.addRule(RelativeLayout.RIGHT_OF, detailsTextView.getId());
//                        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);
//                        buttonParams.setMargins(10,0,10,0);
//                        trackAnnapurnaButton.setLayoutParams(buttonParams);
//
//                        rl.addView(trackAnnapurnaButton);
//
//                        LinearLayout.LayoutParams marginParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        marginParams.setMargins(10,10,10,10);
//                        rl.setLayoutParams(marginParams);
//
//                        searchByLocationLinearLayout.addView(rl);

                        CustomLayout customLayout = new CustomLayout(SearchByLocationActivity.this);
                        customLayout.init(SearchByLocationActivity.this, singleAnnapurna, minDistance);
                        searchByLocationLinearLayout.addView(customLayout);
                    }
                }
                @Override
                public void onProviderDisabled(String provider) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onProviderEnabled(String provider) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                    // TODO Auto-generated method stub
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);

        }
    }
}
