package com.ashraf.faraaz.hydannapurnas;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomLayout extends ConstraintLayout {
    public CustomLayout(Context context) {
        super(context);
    }
    public void init(Activity activity, String singleAnnapurna) {
        inflate(getContext(), R.layout.custom_layout, this);
        setTextViews(singleAnnapurna);
        setListeners(activity, singleAnnapurna);
    }

    public void init(Activity activity, String singleAnnapurna, double minDistance) {
        inflate(getContext(), R.layout.custom_layout, this);
        setTextViews(singleAnnapurna);
        setTextViews(minDistance);
        setListeners(activity, singleAnnapurna);
    }

    private void setTextViews(String singleAnnapurna) {
        TextView locationTextView, wardTextView, circleTextView;
        locationTextView = findViewById(R.id.locationTextView);
        wardTextView = findViewById(R.id.wardTextView);
        circleTextView = findViewById(R.id.circleTextView);

        String ward = singleAnnapurna.split(",")[1];
        String circle = singleAnnapurna.split(",")[2];
        String location = singleAnnapurna.split(",")[4];

        locationTextView.setText(location);
        wardTextView.setText(ward);
        circleTextView.setText(circle);

        TextView distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setVisibility(INVISIBLE);
    }

    private void setTextViews(double minDistance) {
        TextView distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setVisibility(View.VISIBLE);
        distanceTextView.setText("Distance: " + String.format("%.2f", minDistance) + "km");
    }

    private void setListeners(final Activity activity, final String singleAnnapurna) {
        Button b = findViewById(R.id.locateButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UtilsClass().onAnnapurnaSelected(activity, singleAnnapurna);
            }
        });
//        ConstraintLayout customConstraintLayout = findViewById(R.id.customConstraintLayout);

//        TextView locationTextView, wardTextView, circleTextView;
//        locationTextView = findViewById(R.id.locationTextView);
//        wardTextView = findViewById(R.id.wardTextView);
//        circleTextView = findViewById(R.id.circleTextView);
//
//        TextView locationLabelTextView, wardLabelTextView, circleLabelTextView;
//        locationLabelTextView = findViewById(R.id.locationTextView);
//        wardLabelTextView = findViewById(R.id.wardLabelTextView);
//        circleLabelTextView = findViewById(R.id.circleLabelTextView);

        ViewGroup viewGroup = findViewById(R.id.customConstraintLayout);
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UtilsClass().onAnnapurnaSelected(activity, singleAnnapurna);
            }
        });
    }
}
