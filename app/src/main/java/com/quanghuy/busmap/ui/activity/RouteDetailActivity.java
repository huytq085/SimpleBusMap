package com.quanghuy.busmap.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.quanghuy.busmap.R;
import com.quanghuy.busmap.entity.Route;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RouteDetailActivity extends AppCompatActivity {
    @InjectView(R.id.tvCodeValue) TextView tvCodeValue;
    @InjectView(R.id.tvNameValue) TextView tvNameValue;
    @InjectView(R.id.tvRouteABValue) TextView tvRouteABValue;
    @InjectView(R.id.tvRouteBAValue) TextView tvRouteBAValue;
    @InjectView(R.id.tvAgenciesValue) TextView tvAgenciesValue;
    @InjectView(R.id.tvTypeValue) TextView tvTypeValue;
    @InjectView(R.id.tvDistanceValue) TextView tvDistanceValue;
    @InjectView(R.id.tvVehicleTypeValue) TextView tvVehicleTypeValue;
    @InjectView(R.id.tvPriceValue) TextView tvPriceValue;
    @InjectView(R.id.tvTotalTripsValue) TextView tvTotalTripsValue;
    @InjectView(R.id.tvTripTimeValue) TextView tvTripTimeValue;
    @InjectView(R.id.tvTripsSpacingValue) TextView tvTripsSpacingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        ButterKnife.inject(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Route route = (Route) intent.getSerializableExtra("route");

        tvCodeValue.setText(String.valueOf(route.getCode()));
        tvNameValue.setText(route.getName());
        tvRouteABValue.setText(route.getRouteAB());
        tvRouteBAValue.setText(route.getRouteBA());
        tvAgenciesValue.setText(route.getAgencies());
        tvTypeValue.setText(route.getType());
        tvDistanceValue.setText(route.getDistance());
        tvVehicleTypeValue.setText(route.getVehicleType());
        tvPriceValue.setText(String.valueOf(route.getBasicPrice()) + " - " + String.valueOf(route.getStudentPrice()) + " - " + String.valueOf(route.getMonthlyPrice()) );
        tvTotalTripsValue.setText(route.getTotalTrips());
        tvTripTimeValue.setText(route.getTripTime());
        tvTripsSpacingValue.setText(route.getTripSpacing());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
