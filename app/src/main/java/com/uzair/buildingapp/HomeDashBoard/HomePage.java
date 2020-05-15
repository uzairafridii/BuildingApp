package com.uzair.buildingapp.HomeDashBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.uzair.buildingapp.Building.BuildingList;
import com.uzair.buildingapp.Building.BuildingModel;
import com.uzair.buildingapp.LoginAndSignUp.LoginActivity;
import com.uzair.buildingapp.R;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.UrlsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    public static final int REQUEST_CODE = 7;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private RecyclerView logRecyclerView;
    private LinearLayoutManager layoutManager;
    private String token;
    private List<LogModel> logModelList;
    private double logLat , logLng , currentLat , currentLng;
    private AdapterForLogRecycler adapter;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initViews();
        getAllLogs();
    }


    private void initViews()
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        token = getIntent().getStringExtra("loginToken");
        Log.d("tokenInBuildingList", "onNavigationItemSelected: "+token);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout();

        logRecyclerView = findViewById(R.id.logRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        logRecyclerView.setLayoutManager(layoutManager);

        logModelList = new ArrayList<>();
        adapter = new AdapterForLogRecycler(this, logModelList);
     }


    private void drawerLayout()
    {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationMenu);
        navigationView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this , drawerLayout ,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this , LoginActivity.class));
        this.finish();
    }

    // toggle of navigation drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //on toggle button the navigation drawer action
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // search view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);

        return super.onCreateOptionsMenu(menu);
    }

    // click on navigation menu items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.building:
            {
                Log.d("tokenInBuildingList", "onNavigationItemSelected: "+token);
                Intent buildingIntent = new Intent(this , BuildingList.class);
                buildingIntent.putExtra("loginToken" , token);
                startActivity(buildingIntent);
                break;

            }
        }

        return false;
    }



    // get list of log details and show in adapter
    private void getAllLogs() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsContract.Get_DETECTION_LOGS_URLS,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        Log.d("buildingList", "onResponse: " + response);
                        Gson gson = new Gson();
                        try {

                            // get the json array and store it in logmodel type list
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject result = jsonResponse.getJSONObject("response");
                            Log.d("jsonResultBuilding", "onResponse: " + result.getJSONArray("data"));
                            JSONArray jArray = result.getJSONArray("data");

                            // to get all one by one
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);
                                LogModel rvdata = gson.fromJson(String.valueOf(json_data), LogModel.class);
                                Log.d("gsonData", "onResponse: " + rvdata);

                                logLng= rvdata.getGeoCordinate().getCoordinates().get(0);
                                logLat = rvdata.getGeoCordinate().getCoordinates().get(1);

                                Location currentLocation = new Location("currentLocation");
                                currentLocation.setLatitude(currentLat);
                                currentLocation.setLongitude(currentLng);

                                Location destinationLocation = new Location("buildingLocation");
                                destinationLocation.setLatitude(logLat);
                                destinationLocation.setLongitude(logLng);
                                double distance = currentLocation.distanceTo(destinationLocation) / 1000;
                                double distanceInKm = (double) Math.round(distance * 100) / 100;

                                rvdata.setLat(currentLat);
                                rvdata.setLng(currentLng);
                                rvdata.setDistance(distanceInKm);

                                Toast.makeText(HomePage.this, distanceInKm + "", Toast.LENGTH_SHORT).show();
                                logModelList.add(rvdata);
                                logRecyclerView.setAdapter(adapter);
                                Log.d("jsonDataList", "onResponse: " + json_data);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "buildingList: Error" + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> tokenData = new HashMap<>();
                tokenData.put("Authorization", "Bearer " + token);
                return tokenData;
            }
        };

        MySingleton.getInstance(HomePage.this).addToRequestQueue(stringRequest);


    }


    // to ge the last location of user
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check persmission and gps enable or not
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                // if location is not available then request for location
                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    currentLng = location.getLongitude();
                                    currentLat = location.getLatitude();
                                    Toast.makeText(HomePage.this, "Current location" + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    // request to get the new current location if the user location is not available
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    // location call back to get the current location
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            currentLat = mLastLocation.getLatitude();
            currentLng = mLastLocation.getLongitude();
            Toast.makeText(HomePage.this, mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    };

    //check permission
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    // request user to allow permision
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE
        );
    }

    // check gps enable or not
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }




}

