package com.quanghuy.busmap.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.quanghuy.busmap.R;
import com.quanghuy.busmap.entity.Route;
import com.quanghuy.busmap.pc.RouteAPIClient;
import com.quanghuy.busmap.ui.adapters.ListRouteAdapter;
import com.quanghuy.busmap.utils.JsonUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewRouteActivity extends AppCompatActivity {
    private final String TAG = "NewRoute";
    private int routePosition;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);
        ButterKnife.inject(this);
        Intent intent = getIntent();

        if (intent.hasExtra("route")) {
            final Route route = (Route) intent.getSerializableExtra("route");
            routePosition = intent.getIntExtra("routePosition",0);
            Log.d(TAG, "hasExtra: " + JsonUtils.encode(route));
            inputCode.setText(String.valueOf(route.getCode()));
            inputName.setText(route.getName());
            inputRouteAB.setText(route.getRouteAB());
            inputRouteBA.setText(route.getRouteBA());
            inputType.setText(route.getType());
            inputDistance.setText(route.getDistance());
            inputVehicleType.setText(route.getVehicleType());
            inputAgencies.setText(route.getAgencies());
            inputTime.setText(route.getTime());
            inputBasicPrice.setText(String.valueOf(route.getBasicPrice()));
            inputStudenPrice.setText(String.valueOf(route.getStudentPrice()));
            inputMonthlyPrice.setText(String.valueOf(route.getMonthlyPrice()));
            inputTotalTrips.setText(route.getTotalTrips());
            inputTripTime.setText(route.getTripTime());
            inputTripSpacing.setText(route.getTripSpacing());
            btnAddRoute.setText(getString(R.string.newroute_update));
            btnAddRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new UpdateRouteTask().execute(getRouteFromInput(route));
                }
            });
        } else {
            btnAddRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Route route = new Route();
                    route = getRouteFromInput(route);
                    new AddRouteTask().execute(route);

                }
            });
        }


    }

    public Route getRouteFromInput(Route route) {
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
        return route;
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
    class UpdateRouteTask extends AsyncTask<Route, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Route... routes) {
            Route route = routes[0];
            RouteAPIClient client = new RouteAPIClient();
            if(client.updateRoute(route)){
                Intent resultIntent = new Intent();
                // TODO Add extras or a data URI to this intent as appropriate.
                resultIntent.putExtra("route", route);
                resultIntent.putExtra("routePosition", routePosition);
                setResult(Activity.RESULT_OK, resultIntent);

                return true;
            }
            return false;
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
                            finish();
                        }
                    });
            if (aBoolean) {
                alertDialog.setMessage("The route has been updated");

            } else {
                alertDialog.setMessage("Error");
            }
            alertDialog.show();

        }
    }

}
