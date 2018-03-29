package com.quanghuy.busmap.ui.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.quanghuy.busmap.R;
import com.quanghuy.busmap.entity.Route;
import com.quanghuy.busmap.pc.RouteAPIClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewRouteActivity extends AppCompatActivity {
    @InjectView(R.id.btnAddRoute) Button btnAddRoute;
    @InjectView(R.id.input_code) EditText inputCode;
    @InjectView(R.id.input_name) EditText inputName;
    @InjectView(R.id.input_routeAB) EditText inputRouteAB;
    @InjectView(R.id.input_routeBA) EditText inputRouteBA;
    @InjectView(R.id.input_agencies) EditText inputAgencies;
    @InjectView(R.id.input_type) EditText inputType;
    @InjectView(R.id.input_distance) EditText inputDistance;
    @InjectView(R.id.input_time) EditText inputTime;
    @InjectView(R.id.input_vehicleType) EditText inputVehicleType;
    @InjectView(R.id.input_basicPrice) EditText inputBasicPrice;
    @InjectView(R.id.input_studentPrice) EditText inputStudenPrice;
    @InjectView(R.id.input_monthlyPrice) EditText inputMonthlyPrice;
    @InjectView(R.id.input_totalTrips) EditText inputTotalTrips;
    @InjectView(R.id.input_tripTime) EditText inputTripTime;
    @InjectView(R.id.input_tripSpacing) EditText inputTripSpacing;
//    private void setControl() {
//        btnAddRoute = findViewById(R.id.btnAddRoute);
//        inputCode = findViewById(R.id.input_code);
//        inputName = findViewById(R.id.input_name);
//        inputRouteAB = findViewById(R.id.input_routeAB);
//        inputRouteBA = findViewById(R.id.input_routeBA);
//        inputAgencies = findViewById(R.id.input_agencies);
//        inputType= findViewById(R.id.input_type);
//        inputDistance = findViewById(R.id.input_distance);
//        inputTime = findViewById(R.id.input_time);
//        inputvehicleType = findViewById(R.id.input_vehicleType);
//        inputBasicPrice = findViewById(R.id.input_basicPrice);
//        inputStudenPrice= findViewById(R.id.input_studentPrice);
//        inputMonthlyPrice= findViewById(R.id.input_monthlyPrice);
//        inputTotalTrips= findViewById(R.id.input_totalTrips);
//        inputTripTime= findViewById(R.id.input_tripTime);
//        inputTripSpacing= findViewById(R.id.input_tripSpacing);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);
        ButterKnife.inject(this);

        btnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Route route = new Route();
                route.setCode(Integer.parseInt(inputCode.getText().toString()));
                route.setName(inputName.getText().toString());
                route.setRouteAB(inputRouteAB.getText().toString());
                route.setRouteBA(inputRouteBA.getText().toString());
                route.setType(inputType.getText().toString());
                route.setDistance(inputDistance.getText().toString());
                route.setVehicleType(inputVehicleType.getText().toString());
                route.setAgencies(inputAgencies.getText().toString());
                route.setTime(inputTime.getText().toString());
                route.setBasicPrice(Integer.parseInt(inputBasicPrice.getText().toString()));
                route.setStudentPrice(Integer.parseInt(inputStudenPrice.getText().toString()));
                route.setMonthlyPrice(Integer.parseInt(inputMonthlyPrice.getText().toString()));
                route.setTotalTrips(inputTotalTrips.getText().toString());
                route.setTripTime(inputTripTime.getText().toString());
                route.setTripSpacing(inputTripSpacing.getText().toString());

                new AddRouteTask().execute(route);
            }
        });

    }

    class AddRouteTask extends AsyncTask<Route, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Route... routes) {
            Route route = routes[0];
            RouteAPIClient client = new RouteAPIClient();
            return client.addRoute(route);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            AlertDialog alertDialog = new AlertDialog.Builder(NewRouteActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            if (aBoolean) {
                alertDialog.setMessage("The route has been added");

            } else {
                alertDialog.setMessage("Error");
            }
            alertDialog.show();
        }
    }
}
