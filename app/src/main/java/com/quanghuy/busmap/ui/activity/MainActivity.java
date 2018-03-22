package com.quanghuy.busmap.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.quanghuy.busmap.R;

public class MainActivity extends AppCompatActivity {
    private ListView listRoute;
    private ImageView imgBanner;

    public void setControl() {
        listRoute = findViewById(R.id.listRoute);
        imgBanner = findViewById(R.id.imgBanner);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_route_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add_route) {
            Intent intent = new Intent(getApplicationContext(), AddBusActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

}
