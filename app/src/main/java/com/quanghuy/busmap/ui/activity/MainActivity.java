package com.quanghuy.busmap.ui.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.quanghuy.busmap.R;
import com.quanghuy.busmap.entity.Route;
import com.quanghuy.busmap.client.RouteAPIClient;
import com.quanghuy.busmap.ui.adapters.ListRouteAdapter;
import com.quanghuy.busmap.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "MainActivity";

    private ListView lvRoutes;

    private List<Route> routes = new ArrayList<>();;

    private ListRouteAdapter listRouteAdapter;
    private ImageView imgBanner;
    private final int NEW_ROUTE_REQCODE = 1;
    private final int UPDATE_ROUTE_REQCODE = 2;

    public void setControl() {
        lvRoutes = findViewById(R.id.listRoute);
        imgBanner = findViewById(R.id.imgBanner);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginActivity.fa.finish();
        setControl();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAddRoute = (FloatingActionButton) findViewById(R.id.fabAddRoute);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        handleIntent(getIntent());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new GetRoutesTask().execute();
        registerForContextMenu(lvRoutes);
        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: remove");

            }
        });
//        Add route
        fabAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRouteIntent = new Intent(MainActivity.this, NewRouteActivity.class);
                startActivityForResult(addRouteIntent, UPDATE_ROUTE_REQCODE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Tag", "onQueryTextChange: " + newText);
//                if (newText != null && TextUtils.getTrimmedLength(newText) > 0) {
                    listRouteAdapter.getFilter().filter(newText);
//                }
                return false;
            }
        });

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(TAG, "onCreateContextMenu: " );
        if (v.getId()==R.id.listRoute) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            System.out.println(listRouteAdapter.getItem(info.position).getClass());
            Route route = (Route)listRouteAdapter.getItem(info.position);
            menu.setHeaderTitle("Mã tuyến: " + String.valueOf(route.getCode()));
            String[] menuItems = getResources().getStringArray(R.array.action_menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final Route route = (Route)listRouteAdapter.getItem(info.position);
        int menuItemIndex = item.getItemId();
        if (menuItemIndex == 0){
            Log.d(TAG, "onContextItemSelected: EDIT");
            Intent editIntent = new Intent(this, NewRouteActivity.class);
            editIntent.putExtra("route", route);
            editIntent.putExtra("routePosition", info.position);
            startActivityForResult(editIntent, UPDATE_ROUTE_REQCODE);

        } else {
            Log.d(TAG, "onContextItemSelected: DELETE");
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Confirm")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new DeleteRouteTask().execute(route.getCode(), info.position);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (NEW_ROUTE_REQCODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    Route route = (Route) data.getSerializableExtra("route");
                    routes.add(route);
                    listRouteAdapter.notifyDataSetChanged();
                    lvRoutes.setSelection(routes.indexOf(route));
                }
                break;
            }
            case (UPDATE_ROUTE_REQCODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    Route route = (Route) data.getSerializableExtra("route");
                    int routePosition = data.getIntExtra("routePosition",0);
                    routes.set(routePosition, route);
                    listRouteAdapter.notifyDataSetChanged();
                    lvRoutes.setSelection(routePosition);
                }
                break;
            }

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class GetRoutesTask extends AsyncTask<Void, Void, List<Route>> {
        @Override
        protected List<Route> doInBackground(Void... voids) {
            routes = new ArrayList<>();
            RouteAPIClient client = new RouteAPIClient();
            routes = client.getRoutes();
            Log.d("TEST", "doInBackground: " + JsonUtils.encode(routes));
            if (routes != null) {
                return routes;
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Route> routes) {
            super.onPostExecute(routes);
            System.out.println(JsonUtils.encode(routes));
            listRouteAdapter = new ListRouteAdapter(MainActivity.this, routes);
            lvRoutes.setAdapter(listRouteAdapter);
            lvRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), RouteDetailActivity.class);
                    intent.putExtra("route", routes.get(i));
                    startActivity(intent);
                }
            });
        }
    }


    class DeleteRouteTask extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            int code = integers[0];
            int pos = integers[1];
            Log.d(TAG, "code: " + code + " \npos: " + pos);
            RouteAPIClient client = new RouteAPIClient();
            if (client.deleteRoute(code)){
                routes.remove(pos);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listRouteAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
            return false;
        }
    }
}
