package com.uzair.buildingapp.HomeDashBoard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
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
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.uzair.buildingapp.Building.BuildingList;
import com.uzair.buildingapp.Utils.MyCurrentLocation;
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
    private TextView noLogFound;
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
    private TextView logLocation , logDistance , logTime;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initViews();

    }


    private void initViews()
    {
        noLogFound = findViewById(R.id.noLogFound);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        token = getIntent().getStringExtra("loginToken");
        sharedPreferences = getSharedPreferences(LoginActivity.USER_DETAILS , MODE_PRIVATE);


        Log.d("tokenInBuildingList", "onNavigationItemSelected: "+token);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout();

        logLocation = findViewById(R.id.location);
        logDistance = findViewById(R.id.distanceText);
        logTime = findViewById(R.id.timeText);

        logRecyclerView = findViewById(R.id.logRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        logRecyclerView.setLayoutManager(layoutManager);

        logModelList = new ArrayList<>();
        adapter = new AdapterForLogRecycler(this, logModelList);

        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
     }


    // get list of log details and show in adapter
    private void getAllLogs() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsContract.Get_DETECTION_LOGS_URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        Log.d("buildingList", "onResponse: " + response);
                        if(response == null)
                        {
                            return;
                        }
                        noLogFound.setVisibility(View.INVISIBLE);
                        Gson gson = new Gson();
                        try {

                            // get the json array and store it in logmodel type list
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject result = jsonResponse.getJSONObject("response");
                            Log.d("jsonResultBuilding", "onResponse: " + result.getJSONArray("data"));
                            JSONArray jArray = result.getJSONArray("data");

                            if(jArray.length() > 0)
                            {

                            // to get all one by one
                            for (int i = 0; i < jArray.length(); i++)
                            {
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

                                MyCurrentLocation.setLocation(currentLat , currentLng);
                                rvdata.setLat(MyCurrentLocation.getCurrentLat());
                                rvdata.setLng(MyCurrentLocation.getCurrentLng());

                                rvdata.setDistance(distanceInKm);

                                logModelList.add(rvdata);
                                logRecyclerView.setAdapter(adapter);
                                Log.d("jsonDataList", "onResponse: " + json_data);

                                if(i == 0 && rvdata.getBuilding().getName() !=null) {

                                    logDistance.setText(distanceInKm+" km");
                                    logLocation.setText(rvdata.getBuilding().getName());
                                }


                            }

                            }
                            else
                            {
                                Log.d("arraysizeinlog", "onRespone"+"Array empty");
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


    /**
     *  drawer layout and navigation menu methods are below
     */

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
            case R.id.building: {
                Log.d("tokenInBuildingList", "onNavigationItemSelected: " + token);
                Intent buildingIntent = new Intent(this, BuildingList.class);
                buildingIntent.putExtra("loginToken", token);
                startActivity(buildingIntent);
                break;

            }

            case R.id.dashboard:
            {

                break;
            }

            case R.id.logOut:
            {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("userStatus","offline");
                edit.commit();
                edit.apply();

                startActivity(new Intent(this , LoginActivity.class));
                this.finish();
                break;
            }
        }

        return false;
    }

    /**
     * Google location methods below to get the user current
     * location
     */

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
                                    getAllLogs();
                                } else {

                                    currentLng = location.getLongitude();
                                    currentLat = location.getLatitude();
                                    getAllLogs();

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
            getLastLocation();

    }

    @Override
    protected void onStart() {
        super.onStart();

       // sharedPreferences = getSharedPreferences(LoginActivity.USER_DETAILS , MODE_PRIVATE);
        collapsingToolbarLayout.setTitle(sharedPreferences.getString("userName", "")+" welcome here!");
        String status = sharedPreferences.getString("userStatus", "");
        token = sharedPreferences.getString("token","null");

        if(!status.equals("online"))
        {
          startActivity(new Intent(HomePage.this , LoginActivity.class));
          this.finish();
        }


    }

}

