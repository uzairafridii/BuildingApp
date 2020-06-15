package com.uzair.buildingapp.Building;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.uzair.buildingapp.R;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.MyCurrentLocation;
import com.uzair.buildingapp.Utils.UrlsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView noBuildingFound;
    private String[] statusArray = {"Approve", "Pending"};
    private List<String> userIdList = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();
    private List<String> companyIdList = new ArrayList<>();
    private List<String> companyNamesList = new ArrayList<>();
    private RecyclerView buildingListRecycler;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private String token ;
    private String buildingName, buildingFloorNo, buildingCompany, buildingStatus ,buildingContactPerson;
    private List<BuildingModel> buildingArrayList;
    private AdapterForBuildingRv adapter;
    private double buildingLat, buildingLng, currentLat, currentLng;
    private ProgressDialog progressDialog;
    private EditText name, floorNo;
    private AppCompatSpinner status , contactPersonId , companyId;
    private CheckBox locationCheckBox;
    private ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        setTitle("Building List");

        initViews();
        getAllBuildingsData();

    }


    private void initViews() {

        noBuildingFound = findViewById(R.id.noBuildingFound);
        currentLat = MyCurrentLocation.getCurrentLat();
        currentLng = MyCurrentLocation.getCurrentLng();
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);

        token = getIntent().getStringExtra("loginToken");
        Log.d("tokenInBuildingList", "initViews: " + token);

        toolbar = findViewById(R.id.buildingListToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        name = formView.findViewById(R.id.buildingName);
        floorNo = formView.findViewById(R.id.noOfFloor);
        locationCheckBox = formView.findViewById(R.id.locationCheckBox);

        // spinner
        status = formView.findViewById(R.id.status);
        status.setOnItemSelectedListener(this);
        setAdapter(status, Arrays.asList(statusArray));

        contactPersonId = formView.findViewById(R.id.contactPersonId);
        contactPersonId.setOnItemSelectedListener(this);
        setAdapter(contactPersonId, userNameList);

        companyId = formView.findViewById(R.id.companyGuid);
        companyId.setOnItemSelectedListener(this);
        setAdapter(companyId, companyNamesList);

        formView.findViewById(R.id.addBuildingBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                buildingName = name.getText().toString();
                buildingFloorNo = floorNo.getText().toString();


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
                    Toast.makeText(BuildingList.this, "Please all fields are require " +
                            "and checked box", Toast.LENGTH_SHORT).show();
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
                buildingData.put("lng", String.valueOf(currentLng));
                buildingData.put("lat", String.valueOf(currentLat));

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

    @Override
    protected void onResume() {
        super.onResume();
        getUsersUid();
        getCompanies();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.status) {
            buildingStatus = adapterView.getItemAtPosition(i).toString();
        }
        else if(adapterView.getId() == R.id.companyGuid)
        {
            buildingCompany = companyIdList.get(adapterView.getSelectedItemPosition());
            Log.d("buildingContactPerson", "onItemSelected: "+buildingCompany);
        }
        else if(adapterView.getId() == R.id.contactPersonId)
        {
            buildingContactPerson = userIdList.get((adapterView.getSelectedItemPosition()));
            Log.d("buildingContactPerson", "onItemSelected: "+buildingContactPerson);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}


    //spinner adapter
    private void setAdapter(Spinner spinner, List<String> items) {
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        spinner.setAdapter(spinnerAdapter);
    }



    // get list of building details and show in adapter
    private void getAllBuildingsData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsContract.GET_BUILDING_LIST_URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        Log.d("buildingList", "onResponse: " + response);
                        if(response == null)
                        {
                            return;
                        }
                        noBuildingFound.setVisibility(View.INVISIBLE);
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
                                currentLocation.setLatitude(currentLat);
                                currentLocation.setLongitude(currentLng);

                                Location destinationLocation = new Location("buildingLocation");
                                destinationLocation.setLatitude(buildingLat);
                                destinationLocation.setLongitude(buildingLng);
                                double distance = currentLocation.distanceTo(destinationLocation) / 1000;
                                double distanceInKm = (double) Math.round(distance * 100) / 100;

                                rvdata.setLatValue(MyCurrentLocation.getCurrentLat());
                                rvdata.setLngValue(MyCurrentLocation.getCurrentLng());
                                rvdata.setDistance(distanceInKm);
                                rvdata.setTokenKey(token);

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
    // to get users uid from whoami
    private void getUsersUid()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsContract.GET_USERS_UID,
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

                                String uid = json_data.get("guid").toString();
                                String userName = json_data.get("name").toString();
                                userIdList.add(uid);
                                userNameList.add(userName);

                                Log.d("jsonDataList", "onResponse: " + uid);
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

    // get companies details
    private void getCompanies()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsContract.GET_USERS_COMPANIES,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        Log.d("buildingList", "onResponse: " + response);
                        Gson gson = new Gson();
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject result = jsonResponse.getJSONObject("response");

                            String name = result.getString("name");
                            Log.d("jsonResultBuilding", "onResponse: " + name);
                            Log.d("jsonResultBuilding", "onResponse: " + result.getJSONArray("companies"));
                            JSONArray jArray = result.getJSONArray("companies");


                            for(int i=0; i<jArray.length(); i++)
                            {
                                JSONObject jsonObject = jArray.getJSONObject(i);

                                String companyName = jsonObject.getString("name");
                                String companyUid = jsonObject.getString("guid");

                                companyIdList.add(companyUid);
                                companyNamesList.add(companyName);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String responseBody = null;
                try {

                    responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String resultError = data.get("error").toString();
                    Log.d("BuildingList", "onResponse: " + resultError);
                    Toast.makeText(BuildingList.this, resultError, Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("jsonError", "onResponse: " + e.getMessage());
                }
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


