package com.sahayatra.samyatra;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;

import static android.content.ContentValues.TAG;
import static com.sahayatra.samyatra.variables.myPrefs;
import static com.sahayatra.samyatra.variables.phoneno;

public class Main8navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8navigation);
        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);


        phoneno= myPrefs.getString("ageKey","No name");
        Log.i(TAG, "oila : " + phoneno);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(Main8navigation.this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_myaccount:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyAccountFragment()).addToBackStack(null).commit();
                toolbar.setTitle("My Account");
                break;
            case R.id.nav_book:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FavouritesFragment()).addToBackStack(null).commit();
                toolbar.setTitle("Booked Rides");
                break;
            case R.id.nav_delete:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BlockFragment()).addToBackStack(null).commit();
                toolbar.setTitle("Delete Account");
                break;
            case R.id.nav_home:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).addToBackStack(null).commit();
                toolbar.setTitle("Home");
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        drawer.requestLayout();
        return true;
    }

    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }

        else {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

}
