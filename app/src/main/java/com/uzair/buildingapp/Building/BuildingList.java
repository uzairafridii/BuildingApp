package com.uzair.buildingapp.Building;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.uzair.buildingapp.HomeDashBoard.LogModel;
import com.uzair.buildingapp.LoginAndSignUp.LoginActivity;
import com.uzair.buildingapp.LoginAndSignUp.SignUp;
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

public class BuildingList extends AppCompatActivity {

    private RecyclerView buildingListRecycler;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private String token;
    private List<BuildingModel> buildingArrayList;
    private AdapterForBuildingRv adapter;
    private double  buildingLat, buildingLng;
    private ProgressDialog progressDialog;
    private LogModel logModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        setTitle("Building List");

        initViews();
        getAllBuildingsData();


    }


    private void initViews() {


        logModel = new LogModel();
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);

        token = getIntent().getStringExtra("loginToken");
        Log.d("tokenInBuildingList", "initViews: " + token);

        toolbar = findViewById(R.id.buildingListToolbar);
        setSupportActionBar(toolbar);

        buildingListRecycler = findViewById(R.id.buildingListRecycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        buildingListRecycler.setLayoutManager(layoutManager);


        buildingArrayList = new ArrayList<>();
        adapter = new AdapterForBuildingRv(this, buildingArrayList);
    }


    // click on fab button to add building details
    public void addBuildingDetails(View view) {
        View formView = LayoutInflater.from(this).inflate(R.layout.add_building_details_form, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(formView);

        final AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);

        // edit text initailization
        final EditText name, floorNo, contactPersonId, companyId, status;
        final CheckBox locationCheckBox;
        name = formView.findViewById(R.id.buildingName);
        status = formView.findViewById(R.id.status);
        floorNo = formView.findViewById(R.id.noOfFloor);
        companyId = formView.findViewById(R.id.companyGuid);
        contactPersonId = formView.findViewById(R.id.contactPersonId);
        locationCheckBox = formView.findViewById(R.id.locationCheckBox);

        formView.findViewById(R.id.addBuildingBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buildingName, buildingStatus, buildingFloorNo, buildingCompany, buildingContactPerson;

                buildingName = name.getText().toString();
                buildingStatus = status.getText().toString();
                buildingFloorNo = floorNo.getText().toString();
                buildingCompany = companyId.getText().toString();
                buildingContactPerson = contactPersonId.getText().toString();


                if (!buildingName.isEmpty() && !buildingStatus.isEmpty()
                        && !buildingFloorNo.isEmpty() && !buildingCompany.isEmpty()
                        && !buildingContactPerson.isEmpty() && locationCheckBox.isChecked()) {

                     progressDialog.setMessage("Wait");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        addBuildingDetailsToDatabase(buildingName, buildingStatus,
                                buildingFloorNo, buildingCompany, buildingContactPerson);

                        dialog.dismiss();



                } else {
                    Toast.makeText(BuildingList.this, "Please all fields are require", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();

    }

    // method to add details to database
    private void addBuildingDetailsToDatabase(final String buildingName, final String buildingStatus,
                                              final String buildingFloorNo, final String buildingCompany,
                                              final String buildingContactPerson) {

        StringRequest addBuildingRequest = new StringRequest(Request.Method.POST, UrlsContract.CREATE_BUILDING_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("addBuildingDetails", "onResponse: " + response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String responseBody = null;
                try {

                    responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject jsonData = new JSONObject(responseBody);
                    String resultError = jsonData.get("error").toString();
                    Log.d("resultErrorInSignUp", "onResponse: " + resultError);
                    Toast.makeText(BuildingList.this, resultError, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("jsonError", "onResponse: " + e.getMessage());
                    progressDialog.dismiss();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> buildingData = new HashMap<>();
                buildingData.put("name", buildingName);
                buildingData.put("no_of_floor", buildingFloorNo);
                buildingData.put("status", buildingStatus);
                buildingData.put("contact_person_guid", buildingContactPerson);
                buildingData.put("company_guid", buildingCompany);
                buildingData.put("lng", String.valueOf(logModel.getLng()));
                buildingData.put("lat", String.valueOf(logModel.getLat()));

                return buildingData;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> buildingToken = new HashMap<>();
                buildingToken.put("Authorization", "Bearer " + token);
                return buildingToken;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(addBuildingRequest);

    }


    // get list of building details and show in adapter
    private void getAllBuildingsData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsContract.GET_BUILDING_LIST_URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        Log.d("buildingList", "onResponse: " + response);
                        Gson gson = new Gson();
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject result = jsonResponse.getJSONObject("response");
                            Log.d("jsonResultBuilding", "onResponse: " + result.getJSONArray("data"));
                            JSONArray jArray = result.getJSONArray("data");

                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);
                                BuildingModel rvdata = gson.fromJson(String.valueOf(json_data), BuildingModel.class);
                                Log.d("gsonData", "onResponse: " + rvdata);

                                buildingLng = rvdata.getGeoCordinate().getCoordinates().get(0);
                                buildingLat = rvdata.getGeoCordinate().getCoordinates().get(1);

                                Location currentLocation = new Location("currentLocation");
                                currentLocation.setLatitude(logModel.getLat());
                                currentLocation.setLongitude(logModel.getLng());

                                Location destinationLocation = new Location("buildingLocation");
                                destinationLocation.setLatitude(buildingLat);
                                destinationLocation.setLongitude(buildingLng);
                                double distance = currentLocation.distanceTo(destinationLocation) / 1000;
                                double distanceInKm = (double) Math.round(distance * 100) / 100;

                                rvdata.setDistance(distanceInKm);
                                rvdata.setTokenKey(token);

                                Toast.makeText(BuildingList.this, distanceInKm + "", Toast.LENGTH_SHORT).show();
                                buildingArrayList.add(rvdata);
                                buildingListRecycler.setAdapter(adapter);
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

        MySingleton.getInstance(BuildingList.this).addToRequestQueue(stringRequest);


    }






}
